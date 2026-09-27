package org.cjvaldi.inventory.api.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @Column(nullable = false, length = 150)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer existencias;

    // Constructor sin argumentos (obligatorio para Hibernate/JPA)
    public Producto() {
    }

    // Constructor completo (sin id para inserciones)
    public Producto(String descripcion, Double precio, Integer existencias) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencias = existencias;
    }

    // Constructor con todos los atributos
    public Producto(Integer idProducto, String descripcion, Double precio, Integer existencias) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencias = existencias;
    }

    // Getters y Setters
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setId(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getExistencias() {
        return existencias;
    }

    public void setExistencias(Integer existencias) {
        this.existencias = existencias;
    }

    // Override toString
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + idProducto +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", existencias=" + existencias +
                '}';
    }

    // Buenas prácticas en JPA: equals y hashCode basados en la clave primaria
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto producto)) return false;
        return idProducto != null && Objects.equals(idProducto, producto.idProducto);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
