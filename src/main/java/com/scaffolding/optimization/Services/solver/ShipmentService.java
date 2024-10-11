package com.scaffolding.optimization.Services.solver;


import com.scaffolding.optimization.database.Entities.Response.AssigmentResponseWrapper;
import com.scaffolding.optimization.database.Entities.Response.shipment.*;
import com.scaffolding.optimization.database.Entities.models.*;
import com.scaffolding.optimization.database.dtos.AddressesDTO;
import com.scaffolding.optimization.database.dtos.VehiclesDTO;
import com.scaffolding.optimization.database.dtos.WarehousesDTO;
import com.scaffolding.optimization.database.repositories.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ShipmentService {

    private final SimplexOptimizationService simplexOptimizationService;
    private final AssignmentsRepository assignmentsRepository;
    private final DriversRepository driversRepository;
    private final VehiclesRepository vehiclesRepository;
    private final WarehousePickupRepository warehousePickupRepository;
    private final WarehousesRepository warehousesRepository;
    private final StatusRepository statusRepository;
    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailsRepository;

    public ShipmentService(SimplexOptimizationService simplexOptimizationService, AssignmentsRepository assignmentsRepository, DriversRepository driversRepository, VehiclesRepository vehiclesRepository, WarehousePickupRepository warehousePickupRepository, WarehousesRepository warehousesRepository, StatusRepository statusRepository, OrdersRepository ordersRepository, OrderDetailRepository orderDetailsRepository) {
        this.simplexOptimizationService = simplexOptimizationService;
        this.assignmentsRepository = assignmentsRepository;
        this.driversRepository = driversRepository;
        this.vehiclesRepository = vehiclesRepository;
        this.warehousePickupRepository = warehousePickupRepository;
        this.warehousesRepository = warehousesRepository;
        this.statusRepository = statusRepository;
        this.ordersRepository = ordersRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<ShipmentResponseWrapper> getShipments() {

        List<ShipmentResponseWrapper> shipmentResponseWrappers = new ArrayList<>();
        List<Orders> orders = ordersRepository.findAll().stream().filter(order -> order.getAssignment() != null
                && order.getAssignment().getStatus().getName().equalsIgnoreCase("ASIGNADO")).toList();

        for (Orders order : orders) {
            ShipmentResponseWrapper shipmentResponseWrapper = new ShipmentResponseWrapper();
            Assignments assignment = order.getAssignment();
            shipmentResponseWrapper.setDeliveryTransportationCost(assignment.getDeliveryTransportationCost());
            shipmentResponseWrapper.setTotalWarehousePickupCost(assignment.getTotalWarehousePickupCost());
            CustomerWrapper customerWrapper = new CustomerWrapper(
                    order.getCustomer().getFirstName(),
                    order.getCustomer().getPhone(),
                    order.getAddress().getName(),
                    order.getAddress().getLatitude(),
                    order.getAddress().getLongitude()
            );

            String schedule = String.format("%s - %s", assignment.getDriver().getSchedule().getStartTime(), assignment.getDriver().getSchedule().getEndTime());
            DriverWrapper driverWrapper = new DriverWrapper(
                    assignment.getDriver().getName(),
                    assignment.getDriver().getPhone(),
                    schedule
            );
            List<OrderDetail> orderDetails = orderDetailsRepository.findByOrder(order);
            OrderWrapper orderWrapper = new OrderWrapper(
                    order.getId(),
                    order.getOrderDate(),
                    order.getTotal(),
                    orderDetails.stream().map(orderDetail -> new OrderDetailWrapper(
                            orderDetail.getProduct().getName()
                            , orderDetail.getQuantity(),
                            orderDetail.getProduct().getPrice(),
                            orderDetail.getLineTotal()
                    )).toList()
            );

            VehicleWrapper vehicleWrapper = new VehicleWrapper(
                    assignment.getVehicle().getLicensePlate(),
                    assignment.getVehicle().getStopLimit(),
                    assignment.getVehicle().getDescription(),
                    driverWrapper
            );


            List<WarehousePickup> warehousePickups = warehousePickupRepository.findByAssignmentId(assignment.getId());
            List<WarehouseWrapper> warehouseWrappers = getWarehouseWrappers(warehousePickups);


            shipmentResponseWrapper.setCustomer(customerWrapper);
            shipmentResponseWrapper.setOrder(orderWrapper);
            shipmentResponseWrapper.setVehicle(vehicleWrapper);
            shipmentResponseWrapper.setWarehouses(warehouseWrappers);
            shipmentResponseWrappers.add(shipmentResponseWrapper);
        }

        return shipmentResponseWrappers;
    }

    @NotNull
    private static List<WarehouseWrapper> getWarehouseWrappers(List<WarehousePickup> warehousePickups) {
        List<WarehouseWrapper> warehouseWrappers = new ArrayList<>();
        for (WarehousePickup warehousePickup : warehousePickups) {
            Warehouses warehouse = warehousePickup.getWarehouse();
            WarehouseWrapper warehouseWrapper = new WarehouseWrapper(
                    warehouse.getAddress(),
                    warehouse.getLatitude(),
                    warehouse.getLongitude()
            );
            warehouseWrappers.add(warehouseWrapper);
        }
        return warehouseWrappers;
    }

    public String executeShipmentProcessing() {

        if (simplexOptimizationService.getOrdersForToday().isEmpty()) {
            return "Todas las órdenes ya han sido asignadas";
        }

        List<Drivers> availableDrivers = driversRepository.findAll();
        Set<Long> assignedDriverIds = new HashSet<>();
        int driverIndex = 0;

        List<AssigmentResponseWrapper> assignmentResponseWrappers = simplexOptimizationService.assignWarehousesAndVehiclesForOrders();

        for (AssigmentResponseWrapper wrapper : assignmentResponseWrappers) {
            Assignments assignment = new Assignments();

            // Asignar el vehículo
            assignment.setVehicle(vehiclesRepository.findById(wrapper.getVehicle().getId()).orElse(null));

            // Asignar el conductor sin repetir
            Drivers assignedDriver = getNextAvailableDriver(availableDrivers, assignedDriverIds, driverIndex);
            if (assignedDriver != null) {
                assignment.setDriver(assignedDriver);
                assignedDriverIds.add(assignedDriver.getId());
                driverIndex++;
            }


            Status status = statusRepository.findByName("ASIGNADO");
            assignment.setStatus(status);


            BigDecimal totalPickupCost = getTotalWarehousePickupCost(wrapper.getWarehouses(), wrapper.getVehicle());
            assignment.setTotalWarehousePickupCost(totalPickupCost);

            BigDecimal deliveryCost = calculateDeliveryCost(wrapper.getWarehouses(), wrapper.getOrder().getAddress(), wrapper.getVehicle());
            assignment.setDeliveryTransportationCost(deliveryCost);


            Assignments savedAssignment = assignmentsRepository.save(assignment);


            Orders order = ordersRepository.findById(wrapper.getOrder().getId()).orElse(null);
            if (order != null) {
                order.setAssignment(savedAssignment);
                ordersRepository.save(order);
            }


            for (WarehousesDTO warehouseDTO : wrapper.getWarehouses()) {
                WarehousePickup warehousePickup = new WarehousePickup();
                warehousePickup.setWarehouse(warehousesRepository.findById(warehouseDTO.getId()).orElse(null));
                warehousePickup.setAssignment(savedAssignment);
                warehousePickup.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                saveWarehousePickup(warehousePickup);
            }
        }

        return "Proceso de asignación de órdenes completado";
    }

    private void saveWarehousePickup(WarehousePickup warehousePickup) {
        warehousePickupRepository.save(warehousePickup);
    }

    private BigDecimal getTotalWarehousePickupCost(List<WarehousesDTO> warehouses, VehiclesDTO vehicle) {
        Vehicles vehicleFound = vehiclesRepository.findById(vehicle.getId()).orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
        Map<Warehouses, String> warehouseDistanceMap = simplexOptimizationService.calculateDistancesToWarehouses();

        BigDecimal totalCost = BigDecimal.ZERO;

        for (WarehousesDTO warehouseDTO : warehouses) {
            Optional<Warehouses> warehouseFoundOptional = warehouseDistanceMap.keySet()
                    .stream()
                    .filter(warehouse -> warehouse.getId().equals(warehouseDTO.getId()))
                    .findFirst();

            if (warehouseFoundOptional.isPresent()) {
                Warehouses warehouseFound = warehouseFoundOptional.get();
                String distanceStr = warehouseDistanceMap.get(warehouseFound);

                double distanceKm = simplexOptimizationService.extractDistanceInKm(distanceStr);

                double costPerKm = calculateVehicleCostPerKm(vehicleFound);

                double cost = costPerKm * distanceKm;
                totalCost = totalCost.add(BigDecimal.valueOf(cost));
            } else {
                throw new EntityNotFoundException("Warehouse not found");
            }
        }

        return totalCost;
    }

    private double calculateDistance(WarehousesDTO warehouse, AddressesDTO address) {
        String warehouseLat = warehouse.getLatitude();
        String warehouseLon = warehouse.getLongitude();
        String addressLat = address.getLatitude();
        String addressLon = address.getLongitude();

        String[] origins = {warehouseLat + "," + warehouseLon};
        String[] destinations = {addressLat + "," + addressLon};

        return simplexOptimizationService.getDistanceBetweenLocations(origins, destinations);
    }

    private BigDecimal calculateDeliveryCost(List<WarehousesDTO> warehouses, AddressesDTO address, VehiclesDTO vehicle) {
        Vehicles vehicleFound = vehiclesRepository.findById(vehicle.getId()).orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));

        // Calculate distances between each warehouse and the client's address
        List<WarehouseDistance> warehouseDistances = new ArrayList<>();
        for (WarehousesDTO warehouse : warehouses) {
            double distanceKm = calculateDistance(warehouse, address);
            warehouseDistances.add(new WarehouseDistance(warehouse, distanceKm));
        }

        // Sort warehouses by distance in ascending order
        warehouseDistances.sort(Comparator.comparingDouble(WarehouseDistance::getDistance));

        // Get the warehouse with the smallest distance
        WarehousesDTO closestWarehouse = warehouseDistances.get(0).getWarehouse();

        double distanceKm = calculateDistance(closestWarehouse, address);
        double costPerKm = calculateVehicleCostPerKm(vehicleFound);

        return BigDecimal.valueOf(distanceKm * costPerKm);
    }

    private Drivers getNextAvailableDriver(List<Drivers> availableDrivers, Set<Long> assignedDriverIds, int driverIndex) {
        if (driverIndex >= availableDrivers.size()) {
            driverIndex = 0;
        }

        for (int i = driverIndex; i < availableDrivers.size(); i++) {
            Drivers driver = availableDrivers.get(i);
            if (!assignedDriverIds.contains(driver.getId())) {
                return driver;
            }
        }
        return null;
    }

    private double calculateVehicleCostPerKm(Vehicles vehicle) {
        double activationCost = vehicle.getActivationCost().doubleValue();
        double fuelPrice = vehicle.getGasType().getPrice().doubleValue();
        return activationCost * fuelPrice;
    }

    private static class WarehouseDistance {
        private final WarehousesDTO warehouse;
        private final double distance;

        public WarehouseDistance(WarehousesDTO warehouse, double distance) {
            this.warehouse = warehouse;
            this.distance = distance;
        }

        public WarehousesDTO getWarehouse() {
            return warehouse;
        }

        public double getDistance() {
            return distance;
        }
    }
}
