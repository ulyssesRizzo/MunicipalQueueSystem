package com.municipal.model;

import java.util.Date;

public class Ticket {
    private int id;
    private String dependencia;
    private String descripcion;
    private String estado;
    private Date fecha; // Campo fecha

    // Constructor
    public Ticket() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getDependencia() { return dependencia; }
    public void setDependencia(String dependencia) { this.dependencia = dependencia; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public Date getFecha() { return fecha; } // Getter para fecha
    public void setFecha(Date fecha) { this.fecha = fecha; } // Setter para fecha
}