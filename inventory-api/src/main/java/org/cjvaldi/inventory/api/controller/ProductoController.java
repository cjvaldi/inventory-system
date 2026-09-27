package org.cjvaldi.inventory.api.controller;

import org.cjvaldi.inventory.api.entity.Producto;
import org.cjvaldi.inventory.api.service.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/inventory-app")   // http://localhost:8080/inventory-app
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    // 1. Obtener todos los productos (GET /inventory-app/productos)
    @GetMapping
    public ResponseEntity<?> listarProductos() {
        List<Producto>productos = this.productoService.listarProductos();
        logger.info("Productos obtenidos: {}", productos.size());
        return ResponseEntity.ok(productos);
    }

    // 2. Obtener producto por ID (GET /inventory-app/productos/{id})
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductoPorId(@PathVariable("id") Integer id) {
        Producto producto = this.productoService.buscarProductoPorId(id);
        if (producto == null) {
            logger.warn("Producto no encontrado con id: {}", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    // 3. Crear nuevo producto (POST /inventory-app/productos)
    @PostMapping
    public ResponseEntity<?> agregarProducto(@RequestBody Producto producto) {
        logger.info("Producto a agregar: {}", producto);
        // Usa setId() que es el método existente en tu clase Producto
        producto.setId(null);
        Producto nuevoProducto = this.productoService.guardarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // 4. Actualizar producto existente (PUT /inventory-app/productos/{id})
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(
            @PathVariable("id") Integer id,
            @RequestBody Producto productoDetalles) {

        Producto productoExistente = this.productoService.buscarProductoPorId(id);
        if (productoExistente == null) {
            logger.warn("No se puede actualizar. Producto no encontrado con id: {}", id);
            return ResponseEntity.notFound().build();
        }

        productoExistente.setDescripcion(productoDetalles.getDescripcion());
        productoExistente.setPrecio(productoDetalles.getPrecio());
        productoExistente.setExistencias(productoDetalles.getExistencias());

        Producto productoActualizado = this.productoService.guardarProducto(productoExistente);
        logger.info("Producto actualizado: {}", productoActualizado);
        return ResponseEntity.ok(productoActualizado);
    }

    // 5. Eliminar producto (DELETE /inventory-app/productos/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable("id") Integer id) {
        try {
            this.productoService.eliminarProductoPorId(id);
            logger.info("Producto eliminado con id: {}", id);

            Map<String, Boolean> respuesta = Collections.singletonMap("eliminado", Boolean.TRUE);
            return ResponseEntity.ok(respuesta);
        } catch (NoSuchElementException e) {
            logger.warn("No se pudo eliminar: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}