package com.test.app;

import com.test.entities.CarEntity;
import com.test.entities.MotorcycleEntity;
import com.test.entities.VehicleEntity;
import com.test.services.VehicleService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Console-based UI for the Vehicle Inventory Management System.
 * Handles all user interaction and delegates business logic to VehicleService.
 */
public class App {

    private final VehicleService service;
    private final Scanner scanner = new Scanner(System.in);

    public App(VehicleService service) {
        this.service = service;
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> addVehicle();
                case 2 -> listVehicles();
                case 3 -> updateVehicle();
                case 4 -> deleteVehicle();
                case 5 -> {
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n===== VEHICLE INVENTORY MENU =====");
        System.out.println("1. Add Vehicle");
        System.out.println("2. List All Vehicles");
        System.out.println("3. Update Vehicle");
        System.out.println("4. Delete Vehicle");
        System.out.println("5. Quit");
    }

    // ========== ADD ==========
    private void addVehicle() {
        System.out.println("\nAdd a: ");
        System.out.println("1. Car");
        System.out.println("2. Motorcycle");

        int type = readInt("Choose type: ");

        String make = readString("Make: ");
        String model = readString("Model: ");
        int year = readInt("Year: ");

        if (type == 1) {
            int doors = readInt("Number of doors: ");
            CarEntity car = service.createCar(make, model, year, doors);
            System.out.println("Car created with ID: " + car.getId());
        } else if (type == 2) {
            boolean hasSidecar = readBoolean("Has sidecar (true/false): ");
            MotorcycleEntity m = service.createMotorcycle(make, model, year, hasSidecar);
            System.out.println("Motorcycle created with ID: " + m.getId());
        } else {
            System.out.println("Invalid type.");
        }
    }

    // ========== LIST ==========
    private void listVehicles() {
        List<VehicleEntity> list = service.getAllVehicles();

        if (list.isEmpty()) {
            System.out.println("No vehicles in inventory.");
            return;
        }

        System.out.println("\n--- INVENTORY ---");
        for (VehicleEntity v : list) {
            System.out.println(formatVehicle(v));
        }
    }

    private String formatVehicle(VehicleEntity v) {
        String type = (v instanceof CarEntity) ? "Car" : "Motorcycle";
        String extra = (v instanceof CarEntity)
                ? "Doors: " + ((CarEntity) v).getNumDoors()
                : "Has Sidecar: " + ((MotorcycleEntity) v).isHasSidecar();

        return String.format("ID: %d | %s | %s %s %d | %s",
                v.getId(), type, v.getMake(), v.getModel(), v.getYear(), extra);
    }

    // ========== UPDATE ==========
    private void updateVehicle() {
        Long id = (long) readInt("Enter vehicle ID to update: ");
        Optional<VehicleEntity> opt = service.findVehicleById(id);

        if (opt.isEmpty()) {
            System.out.println("Vehicle not found.");
            return;
        }

        VehicleEntity v = opt.get();

        String make = readString("New Make (" + v.getMake() + "): ");
        String model = readString("New Model (" + v.getModel() + "): ");
        int year = readInt("New Year (" + v.getYear() + "): ");

        v.setMake(make);
        v.setModel(model);
        v.setYear(year);

        if (v instanceof CarEntity car) {
            int doors = readInt("New Number of doors (" + car.getNumDoors() + "): ");
            car.setNumDoors(doors);
        } else if (v instanceof MotorcycleEntity moto) {
            boolean hasSidecar = readBoolean("New sidecar value (" + moto.isHasSidecar() + "): ");
            moto.setHasSidecar(hasSidecar);
        }

        service.updateVehicle(v);
        System.out.println("Vehicle updated successfully.");
    }

    // ========== DELETE ==========
    private void deleteVehicle() {
        Long id = (long) readInt("Enter vehicle ID to delete: ");
        service.deleteVehicle(id);
        System.out.println("Vehicle deleted (if it existed).");
    }

    // ========== INPUT HELPERS ==========
    private int readInt(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Try again: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private String readString(String msg) {
        System.out.print(msg);
        return scanner.next();
    }

    private boolean readBoolean(String msg) {
        System.out.print(msg);
        return Boolean.parseBoolean(scanner.next());
    }
}
