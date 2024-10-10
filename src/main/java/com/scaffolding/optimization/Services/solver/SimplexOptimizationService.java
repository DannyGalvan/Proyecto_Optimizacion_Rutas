package com.scaffolding.optimization.Services.solver;


import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.scaffolding.optimization.database.Entities.models.*;
import com.scaffolding.optimization.database.repositories.*;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimplexOptimizationService {

    private static final Logger logger = LoggerFactory.getLogger(SimplexOptimizationService.class);
    private static final double COST_PER_KM = 5.0;
    private final String API_KEY = "AIzaSyDXpDUEBYsQPagnH8poxVZdmswBGkMzL08";
    private Set<Warehouses> warehouses;
    private List<Orders> orders;
    private Set<Long> productIds;

    private final WarehousesRepository warehousesRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final VehiclesRepository vehiclesRepository;
    private final DriversRepository driversRepository;
    private final AssignmentsRepository assignmentsRepository;

    private final OrdersRepository ordersRepository;

    private final WarehouseProductRepository warehouseProductRepository;
    private GeoApiContext geoApiContext;

    public SimplexOptimizationService(WarehousesRepository warehousesRepository, OrderDetailRepository orderDetailRepository, VehiclesRepository vehiclesRepository, DriversRepository driversRepository, AssignmentsRepository assignmentsRepository, OrdersRepository ordersRepository, WarehouseProductRepository warehouseProductRepository) {
        this.warehousesRepository = warehousesRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.vehiclesRepository = vehiclesRepository;
        this.driversRepository = driversRepository;
        this.assignmentsRepository = assignmentsRepository;
        this.ordersRepository = ordersRepository;
        this.warehouseProductRepository = warehouseProductRepository;
        this.geoApiContext = new GeoApiContext.Builder()
             .apiKey(API_KEY)
             .build();
//        warehouses = getWarehouses(productIds);
    }


    @Transactional(readOnly = true)
    public List<Orders> getOrdersForToday() {
        // Obtener la fecha de hoy sin horas
        LocalDate today = LocalDate.now();

        // Filtrar las órdenes cuyo campo orderDate sea hoy
        return ordersRepository.findAll().stream()
                .filter(order -> order.getOrderDate().toLocalDateTime().toLocalDate().equals(today))
                .collect(Collectors.toList());
    }



//    @Transactional(readOnly = true)
//    public Set<Warehouses> getWarehouses(Set<Long> productIds) {
//        List<WarehouseProduct> warehouseProducts = warehouseProductRepository.findByProductIdIn(new ArrayList<>(productIds));
//
//        Set<Long> warehouseIds = warehouseProducts.stream()
//                .map(warehouseProduct -> warehouseProduct.getWarehouse().getId())
//                .collect(Collectors.toSet());
//
//        return warehousesRepository.findAll().stream()
//                .filter(warehouse -> warehouseIds.contains(warehouse.getId()))
//                .collect(Collectors.toSet());
//    }

    @Transactional(readOnly = true)
    public List<Vehicles> getVehicles() {
        return vehiclesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Drivers> getAvailableDrivers() {
        return driversRepository.findDriversByCurrentTime(new Time(System.currentTimeMillis()));
    }

//    public String minimizeCost() {
//       int[][] cost = generarMatrizCostos(new ArrayList<>(warehouses), orders);
//       int[] supply = warehouses.stream()
//                .mapToInt(warehouse -> warehouse.getWarehouseProducts().stream()
//                        .mapToInt(WarehouseProduct::getPartialStock)
//                        .sum())
//                .toArray();
//
//        int[] demand = orders.stream()
//                .flatMap(order -> order.getOrderDetails().stream())
//                .mapToInt(OrderDetail::getQuantity)
//                .toArray();
//        return resolverSimplex(cost, supply, demand);
//    }

//    private int[][] generarMatrizCostos(List<Warehouses> bodegas, List<Orders> ordenes) {
//
//        int[][] cost = new int[bodegas.size()][ordenes.size()];
//
//        for (int i = 0; i < bodegas.size(); i++) {
//            for (int j = 0; j < ordenes.size(); j++) {
//                cost[i][j] = calculateCost(bodegas.get(i), ordenes.get(j));
//            }
//        }
//
//        return cost;
//    }

//    private int calculateCost(Warehouses warehouse, Orders order) {
//        try {
//            String origin = warehouse.getLatitude() + "," + warehouse.getLongitude();
//            String destination = order.getAddress().getLatitude() + "," + order.getAddress().getLongitude();
//
//            // Verificar si las coordenadas se muestran correctamente
//            System.out.println("Origin: " + origin);
//            System.out.println("Destination: " + destination);
//
//            DistanceMatrix result = DistanceMatrixApi.getDistanceMatrix(geoApiContext,
//                            new String[]{origin},
//                            new String[]{destination})
//                    .await();
//
//            DistanceMatrixElement element = result.rows[0].elements[0];
//
//            // Verificar si el elemento contiene información de distancia
//            if (element.status != DistanceMatrixElementStatus.OK) {
//                System.out.println("Error en la respuesta de la API: " + element.status);
//                return -1;
//            }
//
//            long distanceInMeters = element.distance.inMeters;
//            double distanceInKm = distanceInMeters / 1000.0;
//            return (int) (distanceInKm * COST_PER_KM);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }


//
//
//
//
//    private String resolverSimplex(int[][] cost, int[] supply, int[] demand) {
//        int numBodegas = supply.length;
//        int numClientes = demand.length;
//
//        // Validación: suma de suministros debe ser igual a la suma de demandas
//        int totalSupply = Arrays.stream(supply).sum();
//        int totalDemand = Arrays.stream(demand).sum();
//        if (totalSupply != totalDemand) {
//            return "Suma de suministros y demandas no coinciden.";
//        }
//
//        Model model = new Model("Simplex Cost Minimization");
//
//        // Crear variables de decisión (envíos entre bodegas y clientes)
//        IntVar[][] envio = new IntVar[numBodegas][numClientes];
//        for (int i = 0; i < numBodegas; i++) {
//            for (int j = 0; j < numClientes; j++) {
//                envio[i][j] = model.intVar("envio_" + i + "_" + j, 0, Math.min(supply[i], demand[j]));
//            }
//        }
//
//        // Restricción: La cantidad enviada desde cada bodega no puede exceder el suministro disponible
//        for (int i = 0; i < numBodegas; i++) {
//            model.sum(envio[i], "<=", supply[i]).post();
//        }
//
//        // Restricción: La cantidad recibida por cada cliente debe satisfacer su demanda exacta
//        for (int j = 0; j < numClientes; j++) {
//            IntVar[] productosCliente = new IntVar[numBodegas];
//            for (int i = 0; i < numBodegas; i++) {
//                productosCliente[i] = envio[i][j];
//            }
//            model.sum(productosCliente, "=", demand[j]).post();
//        }
//
//        // Función objetivo: Minimizar el costo total
//        IntVar totalCost = model.intVar("totalCost", 0, IntVar.MAX_INT_BOUND); // Definir un límite mayor
//        IntVar[] costs = new IntVar[numBodegas * numClientes];
//        int idx = 0;
//        for (int i = 0; i < numBodegas; i++) {
//            for (int j = 0; j < numClientes; j++) {
//                costs[idx] = envio[i][j].mul(cost[i][j]).intVar();
//                idx++;
//            }
//        }
//        model.sum(costs, "=", totalCost).post();
//
//        // Definir el objetivo de minimización
//        model.setObjective(Model.MINIMIZE, totalCost);
//
//        // Resolver el modelo
//        Solver solver = model.getSolver();
//        if (solver.solve()) {
//            StringBuilder resultado = new StringBuilder("Costo total mínimo: " + totalCost.getValue() + "\n");
//            for (int i = 0; i < numBodegas; i++) {
//                for (int j = 0; j < numClientes; j++) {
//                    resultado.append("Bodega ").append(i).append(" a Cliente ").append(j).append(": ")
//                            .append(envio[i][j].getValue()).append(" productos\n");
//                }
//            }
//            return resultado.toString();
//        } else {
//            return "No se encontró solución.";
//        }
//    }


    @Transactional(readOnly = true)
    public Map<Orders, String> assignVehiclesToOrders() {

        List<Orders> orders = getOrdersForToday();
        List<Vehicles> vehicles = getVehicles();
        Map<Orders, String> orderVehicleMap = new HashMap<>();

        for (Orders order : orders) {
            double orderWeight = calculateOrderWeight(order);
            Vehicles assignedVehicle = findSuitableVehicle(order, orderWeight, vehicles);

            if (assignedVehicle != null) {
                List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);

                // Construir la cadena con los detalles de la orden y el vehículo asignado
                StringBuilder detailsStringBuilder = new StringBuilder();
                detailsStringBuilder.append("Order ID: ").append(order.getId()).append("\n");
                detailsStringBuilder.append("Customer: ").append(order.getCustomer().getFirstName())
                        .append(" ").append(order.getCustomer().getLastName()).append("\n");
                detailsStringBuilder.append("Delivery Address: ").append(order.getAddress().getName()).append("\n");
                detailsStringBuilder.append("Delivery Date: ").append(order.getDeliveryDate()).append("\n");
                detailsStringBuilder.append("Status: ").append(order.getStatus().getName()).append("\n");
                detailsStringBuilder.append("Vehicle: ").append(assignedVehicle.getLicensePlate()).append(", Max Weight: ")
                        .append(assignedVehicle.getMaxWeight()).append("\n");
                detailsStringBuilder.append("Order Details:\n");

                // Agregar detalles de productos
                for (OrderDetail detail : orderDetails) {
                    detailsStringBuilder.append(" - Product: ").append(detail.getProduct().getName())
                            .append(", Quantity: ").append(detail.getQuantity())
                            .append(", Line Total: ").append(detail.getLineTotal()).append("\n");
                }

                // Añadir al mapa
                orderVehicleMap.put(order, detailsStringBuilder.toString());

                logger.debug("Assigned vehicle {} to order {}", assignedVehicle.getId(), order.getId());
            } else {
                logger.debug("No suitable vehicle found for order {}", order.getId());
            }
        }

        if (orderVehicleMap.isEmpty()) {
            logger.debug("No vehicles assigned to any orders");
        }

        return orderVehicleMap;
    }



    private double calculateOrderWeight(Orders order) {
        Map<Orders, OrderDetail> orderDetails = new HashMap<>();
        List<OrderDetail> orderDetailsList = orderDetailRepository.findByOrder(order);
        for (OrderDetail orderDetail : orderDetailsList) {
            orderDetails.put(order, orderDetail);
        }

        return orderDetails.values().stream()
                .mapToDouble(orderDetail -> orderDetail.getQuantity() * orderDetail.getProduct().getWeight().doubleValue())
                .sum();

    }

    private Vehicles findSuitableVehicle(Orders order, double orderWeight, List<Vehicles> vehicles) {
        for (Vehicles vehicle : vehicles) {
            BigDecimal orderWeightBigDecimal = BigDecimal.valueOf(orderWeight);
            if (vehicle.getMaxWeight().compareTo(orderWeightBigDecimal) >= 0 &&
                    vehicle.getCurrentWeight().add(orderWeightBigDecimal).compareTo(vehicle.getMaxWeight()) <= 0) {
                return vehicle;
            }
        }
        return null;
    }
}
