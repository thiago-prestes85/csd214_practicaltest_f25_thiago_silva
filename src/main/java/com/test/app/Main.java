package com.test.app;

import com.test.repositories.Repository;
import com.test.repositories.impl.InMemoryVehicleRepository;
import com.test.repositories.impl.MySQLVehicleRepository;
import com.test.services.VehicleService;

import java.util.Scanner;

/** Composition Root of the entire application */
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("==== Vehicle Inventory System ====");
        System.out.println("Choose data source:");
        System.out.println("1. In-Memory Repository");
        System.out.println("2. MySQL Repository");
        System.out.print("Your choice: ");

        int choice = 0;
        while (choice != 1 && choice != 2) {
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid. Choose 1 or 2: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            if (choice != 1 && choice != 2) {
                System.out.print("Invalid. Choose 1 or 2: ");
            }
        }

        // Decide which repository implementation to use
        Repository repo;

        if (choice == 1) {
            System.out.println("Using In-Memory Repository.");
            repo = new InMemoryVehicleRepository();
        } else {
            System.out.println("Using MySQL Repository.");
            repo = new MySQLVehicleRepository();
        }

        // Inject repository into service
        VehicleService service = new VehicleService(repo);

        // Inject service into App (console UI)
        App app = new App(service);

        // Start console loop
        app.run();

        // Close MySQL EntityManagerFactory if used
        if (repo instanceof MySQLVehicleRepository mysqlRepo) {
            mysqlRepo.close();
        }

        System.out.println("Application closed.");
    }
}
