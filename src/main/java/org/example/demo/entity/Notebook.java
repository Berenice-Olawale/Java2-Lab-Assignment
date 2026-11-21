package org.example.demo.entity;

import jakarta.persistence.*;

/** Table `notebook` of the Notebook database (ReNew Ltd.). */
@Entity
@Table(name = "notebook")
public class Notebook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String manufacturer;
    private String type;
    private double display;          // inches
    private int memory;              // MiB
    private int harddisk;            // GB
    private String videocontroller;
    private int price;
    private int pieces;              // in stock

    @ManyToOne
    @JoinColumn(name = "processorid")
    private Processor processor;

    @ManyToOne
    @JoinColumn(name = "opsystemid")
    private OpSystem opsystem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDisplay() {
        return display;
    }

    public void setDisplay(double display) {
        this.display = display;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getHarddisk() {
        return harddisk;
    }

    public void setHarddisk(int harddisk) {
        this.harddisk = harddisk;
    }

    public String getVideocontroller() {
        return videocontroller;
    }

    public void setVideocontroller(String videocontroller) {
        this.videocontroller = videocontroller;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public OpSystem getOpsystem() {
        return opsystem;
    }

    public void setOpsystem(OpSystem opsystem) {
        this.opsystem = opsystem;
    }
}
