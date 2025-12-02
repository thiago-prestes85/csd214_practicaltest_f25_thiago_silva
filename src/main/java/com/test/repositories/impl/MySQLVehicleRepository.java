package com.test.repositories.impl;

import com.test.entities.VehicleEntity;
import com.test.repositories.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

/**
 * MySQL implementation of the Repository interface using JPA/Hibernate.
 * This repository uses the persistence unit defined in persistence.xml.
 */
public class MySQLVehicleRepository implements Repository<VehicleEntity> {

    private final EntityManagerFactory emf;

    public MySQLVehicleRepository() {
        // Loads the persistence unit "vehiclePU" from persistence.xml
        this.emf = Persistence.createEntityManagerFactory("vehiclePU");
    }

    private EntityManager em() {
        return emf.createEntityManager();
    }

    @Override
    public Optional<VehicleEntity> findById(Long id) {
        EntityManager em = em();
        try {
            return Optional.ofNullable(em.find(VehicleEntity.class, id));
        } finally {
            em.close();
        }
    }

    @Override
    public List<VehicleEntity> findAll() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT v FROM VehicleEntity v", VehicleEntity.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public VehicleEntity save(VehicleEntity entity) {
        EntityManager em = em();

        try {
            em.getTransaction().begin();
            VehicleEntity merged = em.merge(entity); // merge inserts or updates
            em.getTransaction().commit();
            return merged;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to save entity", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        EntityManager em = em();

        try {
            em.getTransaction().begin();
            VehicleEntity entity = em.find(VehicleEntity.class, id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to delete entity with ID: " + id, e);
        } finally {
            em.close();
        }
    }

    /**
     * Ensures MySQL connections (EntityManagerFactory) are released when the app closes.
     */
    public void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
