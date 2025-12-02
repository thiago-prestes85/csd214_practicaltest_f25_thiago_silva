package com.test.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class CarEntity extends VehicleEntity {

    @Column(nullable = false)
    private int numDoors;

    public CarEntity() {}

    public CarEntity(String make, String model, int year, int numDoors) {
        super(make, model, year);
        this.numDoors = numDoors;
    }

    public int getNumDoors() {
        return numDoors;
    }

    public void setNumDoors(int numDoors) {
        this.numDoors = numDoors;
    }
}
