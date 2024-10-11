package com.scaffolding.optimization.Services.solver;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.scaffolding.optimization.database.Entities.Response.AssigmentResponseWrapper;
import com.scaffolding.optimization.database.Entities.models.*;
import com.scaffolding.optimization.database.dtos.*;
import com.scaffolding.optimization.database.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimplexOptimizationService {

    private static final Logger logger = LoggerFactory.getLogger(SimplexOptimizationService.class);
    private static final String latitude = "14.729235";
    private static final String longitude = "-90.643291";
    private final String API_KEY = "AIzaSyDXpDUEBYsQPagnH8poxVZdmswBGkMzL08";
    private GeoApiContext geoApiContext;

    private final WarehousesRepository warehousesRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final VehiclesRepository vehiclesRepository;
    private final DriversRepository driversRepository;
    private final AssignmentsRepository assignmentsRepository;
    private final OrdersRepository ordersRepository;
    private final WarehouseProductRepository warehouseProductRepository;

    public SimplexOptimizationService(WarehousesRepository warehousesRepository, OrderDetailRepository orderDetailRepository,
                                      VehiclesRepository vehiclesRepository, DriversRepository driversRepository,
                                      AssignmentsRepository assignmentsRepository, OrdersRepository ordersRepository,
                                      WarehouseProductRepository warehouseProductRepository) {
        this.warehousesRepository = warehousesRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.vehiclesRepository = vehiclesRepository;
        this.driversRepository = driversRepository;
        this.assignmentsRepository = assignmentsRepository;
        this.ordersRepository = ordersRepository;
        this.warehouseProductRepository = warehouseProductRepository;
        this.geoApiContext = new GeoApiContext.Builder().apiKey(API_KEY).build();
    }

    @Transactional(readOnly = true)
    public List<AssigmentResponseWrapper> assignWarehousesAndVehiclesForOrders() {
        List<Orders> orders = getOrdersForToday();
        List<Vehicles> vehicles = getVehicles();
        List<AssigmentResponseWrapper> responseWrappers = new ArrayList<>();

        for (Orders order : orders) {
            // Cargar los detalles de la orden usando el repositorio (método correcto)
            List<OrderDetail> orderDetails = getOrderDetailsByOrder(order);

            Vehicles assignedVehicle = findSuitableVehicleForOrder(order, orderDetails, vehicles);

            if (assignedVehicle != null) {
                // Asignar múltiples bodegas si es necesario
                List<Warehouses> assignedWarehouses = assignWarehousesForOrder(orderDetails, assignedVehicle);

                if (!assignedWarehouses.isEmpty()) {
                    // Pasar la lista de bodegas al construir el AssigmentResponseWrapper
                    AssigmentResponseWrapper responseWrapper = buildAssignmentResponse(order, assignedWarehouses, assignedVehicle);
                    responseWrappers.add(responseWrapper);
                    logger.info("Assigned warehouses {} to order {}", assignedWarehouses.stream().map(Warehouses::getId).collect(Collectors.toList()), order.getId());
                }
            } else {
                logger.warn("No suitable vehicle found for order {}", order.getId());
            }
        }

        return responseWrappers;
    }

    @Transactional(readOnly = true)
    public List<OrderDetail> getOrderDetailsByOrder(Orders order) {
        // Cargar los detalles de la orden a través del repositorio
        return orderDetailRepository.findByOrder(order);
    }

    @Transactional(readOnly = true)
    public List<Warehouses> assignWarehousesForOrder(List<OrderDetail> orderDetails, Vehicles vehicle) {
        List<Warehouses> assignedWarehouses = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            long productId = orderDetail.getProduct().getId();
            int requiredQuantity = orderDetail.getQuantity();
            List<WarehouseProduct> warehouseProducts = getWarehouseProductsByProduct(productId);

            Map<Warehouses, String> distancesToWarehouses = calculateDistancesToWarehouses();
            List<Warehouses> suitableWarehouses = new ArrayList<>();

            // Calcular el costo por kilómetro para el vehículo actual
            double costPerKm = calculateVehicleCostPerKm(vehicle);

            for (WarehouseProduct warehouseProduct : warehouseProducts) {
                Warehouses warehouse = warehouseProduct.getWarehouse();
                int availableQuantity = warehouseProduct.getPartialStock();
                String distanceStr = distancesToWarehouses.get(warehouse);

                if (distanceStr == null || distanceStr.equals("Distance not available")) {
                    continue;
                }

                // Convertir la distancia a kilómetros
                double distanceKm = extractDistanceInKm(distanceStr);
                // Calcular el costo total del viaje a esta bodega
                double totalCost = distanceKm * costPerKm;

                if (availableQuantity > 0) {
                    suitableWarehouses.add(warehouse);

                    if (availableQuantity >= requiredQuantity) {
                        assignedWarehouses.add(warehouse);
                        logger.info("Assigned warehouse {} with cost {} for product {}", warehouse.getId(), totalCost, productId);
                        break; // Si esta bodega tiene suficiente, no necesitamos buscar más
                    } else {
                        assignedWarehouses.add(warehouse);
                        requiredQuantity -= availableQuantity;  // Reducir la cantidad restante a recoger
                        logger.info("Partial assignment to warehouse {} with cost {} for product {}", warehouse.getId(), totalCost, productId);
                    }
                }
            }

            if (requiredQuantity > 0) {
                logger.warn("Insufficient stock for product {}. Remaining quantity: {}", productId, requiredQuantity);
            }
        }

        return assignedWarehouses;
    }

    // Método que calcula el costo por kilómetro basado en el consumo del vehículo y el precio del combustible
    public double calculateVehicleCostPerKm(Vehicles vehicle) {
        double activationCost = vehicle.getActivationCost().doubleValue();
        double fuelPrice = vehicle.getGasType().getPrice().doubleValue();
        return activationCost * fuelPrice;
    }

    @Transactional(readOnly = true)
    public List<Orders> getOrdersForToday() {
        LocalDate today = LocalDate.now();
        return ordersRepository.findAll().stream()
                .filter(order -> order.getOrderDate().toLocalDateTime().toLocalDate().equals(today)
                        && order.getAssignment() == null)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Map<Warehouses, String> calculateDistancesToWarehouses() {
        List<Orders> orders = getOrdersForToday();
        List<OrderDetail> orderDetails = getOrderDetailsByOrders(orders);
        Set<Long> productIds = orderDetails.stream()
                .map(orderDetail -> orderDetail.getProduct().getId())
                .collect(Collectors.toSet());

        List<Warehouses> warehouses = getWarehouseProducts(new ArrayList<>(productIds)).stream()
                .map(WarehouseProduct::getWarehouse)
                .toList();

        Map<Warehouses, String> distances = new HashMap<>();
        String[] origins = {latitude + "," + longitude};
        String[] destinations = warehouses.stream()
                .map(warehouse -> warehouse.getLatitude() + "," + warehouse.getLongitude())
                .toArray(String[]::new);

        logger.info("Origin: " + Arrays.toString(origins));
        logger.info("Destinations: " + Arrays.toString(destinations));

        try {
            DistanceMatrix distanceMatrix = DistanceMatrixApi.getDistanceMatrix(geoApiContext, origins, destinations).await();
            for (int i = 0; i < warehouses.size(); i++) {
                DistanceMatrixElement element = distanceMatrix.rows[0].elements[i];
                if (element.status == DistanceMatrixElementStatus.OK) {
                    distances.put(warehouses.get(i), element.distance.humanReadable);
                } else {
                    logger.warn("Distance not available for warehouse: " + warehouses.get(i).getId());
                    distances.put(warehouses.get(i), "Distance not available");
                }
            }
        } catch (Exception e) {
            logger.error("Error calculating distances: " + e.getMessage(), e);
        }

        return distances;
    }

    @Transactional(readOnly = true)
    public List<Vehicles> getVehicles() {
        return vehiclesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<WarehouseProduct> getWarehouseProductsByProduct(Long productId) {
        return warehouseProductRepository.findByProductIdIn(Collections.singletonList(productId));
    }

    @Transactional(readOnly = true)
    public List<WarehouseProduct> getWarehouseProducts(List<Long> productIds) {
        return warehouseProductRepository.findByProductIdIn(productIds);
    }

    @Transactional(readOnly = true)
    public Vehicles findSuitableVehicleForOrder(Orders order, List<OrderDetail> orderDetails, List<Vehicles> vehicles) {
        double orderWeight = calculateOrderWeight(order, orderDetails);
        BigDecimal orderWeightBigDecimal = BigDecimal.valueOf(orderWeight);
        List<Vehicles> sortedVehicles = vehicles.stream()
                .sorted(Comparator.comparing(Vehicles::getMaxWeight))
                .toList();

        for (Vehicles vehicle : sortedVehicles) {
            if (vehicle.getMaxWeight().compareTo(orderWeightBigDecimal) >= 0 &&
                    vehicle.getCurrentWeight().add(orderWeightBigDecimal).compareTo(vehicle.getMaxWeight()) <= 0) {
                return vehicle;
            }
        }

        return null;
    }

    private double calculateOrderWeight(Orders order, List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .mapToDouble(orderDetail -> orderDetail.getQuantity() * orderDetail.getProduct().getWeight().doubleValue())
                .sum();
    }

    public List<OrderDetail> getOrderDetailsByOrders(List<Orders> order) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Orders o : order) {
            orderDetails.addAll(orderDetailRepository.findByOrder(o));
        }
        return orderDetails;
    }

    public double extractDistanceInKm(String distanceStr) {
        return Double.parseDouble(distanceStr.replace(" km", "").replace(",", "."));
    }

    // Método para construir AssigmentResponseWrapper con múltiples almacenes
    public AssigmentResponseWrapper buildAssignmentResponse(Orders order, List<Warehouses> warehouses, Vehicles vehicle) {
        AssigmentResponseWrapper responseWrapper = new AssigmentResponseWrapper();

        // Convertir Orders a OrdersDTO
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setId(order.getId());
        ordersDTO.setCustomerId(order.getCustomer().getId());
        ordersDTO.setOrderDate(order.getOrderDate());
        ordersDTO.setDeliveryDate(order.getDeliveryDate());
        ordersDTO.setStatusId(order.getStatus().getId());
        ordersDTO.setTotal(order.getTotal());
        ordersDTO.setDeleted(order.getDeleted());

        // Asignar dirección
        AddressesDTO addressDTO = new AddressesDTO();
        addressDTO.setId(order.getAddress().getId());
        addressDTO.setName(order.getAddress().getName());
        addressDTO.setLatitude(order.getAddress().getLatitude());
        addressDTO.setLongitude(order.getAddress().getLongitude());
        ordersDTO.setAddress(addressDTO);

        // Asignar detalles del pedido cargados de forma explícita
        List<OrderDetailDTO> orderDetailDTOList = getOrderDetailsByOrder(order).stream().map(orderDetail -> {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setOrderId(orderDetail.getOrder().getId());
            orderDetailDTO.setProductId(orderDetail.getProduct().getId());
            orderDetailDTO.setLineTotal(orderDetail.getLineTotal());
            orderDetailDTO.setQuantity(orderDetail.getQuantity());
            return orderDetailDTO;
        }).collect(Collectors.toList());
        ordersDTO.setOrderDetails(orderDetailDTOList);

        // Convertir la lista de Warehouses a WarehousesDTO y asignarla
        List<WarehousesDTO> warehousesDTOList = warehouses.stream().map(warehouse -> {
            WarehousesDTO warehousesDTO = new WarehousesDTO();
            warehousesDTO.setId(warehouse.getId());
            warehousesDTO.setAddress(warehouse.getAddress());
            warehousesDTO.setLongitude(warehouse.getLongitude());
            warehousesDTO.setLatitude(warehouse.getLatitude());
            warehousesDTO.setSupplierId(warehouse.getSupplier().getId());
            return warehousesDTO;
        }).collect(Collectors.toList());

        VehiclesDTO vehiclesDTO = new VehiclesDTO();
        vehiclesDTO.setId(vehicle.getId());
        vehiclesDTO.setMaxWeight(vehicle.getMaxWeight());
        vehiclesDTO.setMinWeight(vehicle.getMinWeight());
        vehiclesDTO.setDescription(vehicle.getDescription());
        vehiclesDTO.setLicensePlate(vehicle.getLicensePlate());
        vehiclesDTO.setStopLimit(vehicle.getStopLimit());
        vehiclesDTO.setActivationCost(vehicle.getActivationCost());
        vehiclesDTO.setDeleted(vehicle.getDeleted());
        vehiclesDTO.setGasTypeId(vehicle.getGasType().getId());

        // Asignar los DTOs en el wrapper
        responseWrapper.setOrder(ordersDTO);
        responseWrapper.setWarehouses(warehousesDTOList); // Asignar la lista de bodegas
        responseWrapper.setVehicle(vehiclesDTO);

        return responseWrapper;
    }

    public double getDistanceBetweenLocations(String[] origins, String[] destinations) {
        try {
            // Usamos la API de Google Maps para calcular la distancia entre dos ubicaciones
            DistanceMatrix distanceMatrix = DistanceMatrixApi.getDistanceMatrix(geoApiContext, origins, destinations).await();
            DistanceMatrixElement element = distanceMatrix.rows[0].elements[0];

            if (element.status == DistanceMatrixElementStatus.OK) {
                return extractDistanceInKm(element.distance.humanReadable);
            } else {
                logger.warn("Could not calculate distance between locations. Status: " + element.status);
                return 0;
            }
        } catch (Exception e) {
            logger.error("Error calculating distance between locations: " + e.getMessage(), e);
            return 0;
        }
    }
}
