package org.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Table `opsystem` of the Notebook database. */
@Entity
@Table(name = "opsystem")
public class OpSystem {
    @Id
    private int id;
    private String osname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOsname() {
        return osname;
    }

    public void setOsname(String osname) {
        this.osname = osname;
    }
}
