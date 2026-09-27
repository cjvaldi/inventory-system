package org.cjvaldi.inventory.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @Column(nullable = false)
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser superior a 0")
    private Double precio;

    @Column(nullable = false)
    @NotNull(message = "Las existencias son obligatorias")
    @Min(value = 0, message = "Las existencias no pueden ser negativas")
    private Integer existencias;

    public Producto() {
    }

    public Producto(String descripcion, Double precio, Integer existencias) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencias = existencias;
    }

    public Producto(Integer idProducto, String descripcion, Double precio, Integer existencias) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.existencias = existencias;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
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

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", existencias=" + existencias +
                '}';
    }

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