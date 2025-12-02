package com.test.services;

import com.test.entities.CarEntity;
import com.test.entities.MotorcycleEntity;
import com.test.entities.VehicleEntity;
import com.test.repositories.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Service layer that contains all business logic related to vehicles.
 * It depends ONLY on the Repository interface (NOT on MySQL or InMemory).
 * This guarantees Inversion of Control and Dependency Injection.
 */
public class VehicleService {

    private final Repository<VehicleEntity> repository;

    // Constructor dependency injection
    public VehicleService(Repository<VehicleEntity> repository) {
        this.repository = repository;
    }

    // ========== CREATE ==========
    public CarEntity createCar(String make, String model, int year, int numDoors) {
        CarEntity car = new CarEntity(make, model, year, numDoors);
        return (CarEntity) repository.save(car);
    }

    public MotorcycleEntity createMotorcycle(String make, String model, int year, boolean hasSidecar) {
        MotorcycleEntity m = new MotorcycleEntity(make, model, year, hasSidecar);
        return (MotorcycleEntity) repository.save(m);
    }

    // ========== READ ==========
    public List<VehicleEntity> getAllVehicles() {
        return repository.findAll();
    }

    public Optional<VehicleEntity> findVehicleById(Long id) {
        return repository.findById(id);
    }

    // ========== UPDATE ==========
    public VehicleEntity updateVehicle(VehicleEntity updated) {
        return repository.save(updated); // merge handles update
    }

    // ========== DELETE ==========
    public void deleteVehicle(Long id) {
        repository.deleteById(id);
    }
}
