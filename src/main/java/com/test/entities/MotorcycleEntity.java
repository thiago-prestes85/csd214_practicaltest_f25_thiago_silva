package com.test.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "motorcycles")
public class MotorcycleEntity extends VehicleEntity {

    @Column(nullable = false)
    private boolean hasSidecar;

    public MotorcycleEntity() {}

    public MotorcycleEntity(String make, String model, int year, boolean hasSidecar) {
        super(make, model, year);
        this.hasSidecar = hasSidecar;
    }

    public boolean isHasSidecar() {
        return hasSidecar;
    }

    public void setHasSidecar(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }
}
