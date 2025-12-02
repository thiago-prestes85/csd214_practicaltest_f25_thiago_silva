package com.test.repositories.impl;

import com.test.entities.VehicleEntity;
import com.test.repositories.Repository;

import java.util.*;

/**
 * In-memory implementation of the Repository interface.
 * Stores all VehicleEntity objects inside a HashMap.
 * This repository does NOT use JPA or any database.
 */
public class InMemoryVehicleRepository implements Repository<VehicleEntity> {

    private final Map<Long, VehicleEntity> store = new HashMap<>();
    private long idCounter = 1L;

    @Override
    public Optional<VehicleEntity> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<VehicleEntity> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public VehicleEntity save(VehicleEntity entity) {
        // If entity has no ID, generate one
        if (entity.getId() == null) {
            try {
                // Using reflection because id in VehicleEntity is private with no setter
                var field = VehicleEntity.class.getDeclaredField("id");
                field.setAccessible(true);
                field.set(entity, idCounter++);
            } catch (Exception e) {
                throw new RuntimeException("Failed to set ID on VehicleEntity", e);
            }
        }

        store.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
