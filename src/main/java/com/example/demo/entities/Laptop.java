package com.example.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "laptos")
public class Laptop {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private String screen;
    private String hdd;
    private String ram;

    //Constructores

    public Laptop() {}

    public Laptop(Long id, String brand, String model, String screen, String hdd, String ram) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.screen = screen;
        this.hdd = hdd;
        this.ram = ram;
    }
    //Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }


    //toString

    @Override
    public String toString() {
        return "Laptop{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", screen='" + screen + '\'' +
                ", hdd='" + hdd + '\'' +
                ", ram='" + ram + '\'' +
                '}';
    }
}
