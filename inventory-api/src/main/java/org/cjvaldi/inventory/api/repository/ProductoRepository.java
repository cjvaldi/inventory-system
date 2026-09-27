package org.cjvaldi.inventory.api.repository;

import org.cjvaldi.inventory.api.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Hereda los métodos CRUD básicos (findAll, findById, save, deleteById, etc.)
   // List<Producto> findByDescriptionContainingIgnoreCase(String descripcion);
}
