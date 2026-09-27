package org.cjvaldi.inventory.api.controller;

import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/inventory-app/productos")   // http://localhost:8080/inventory-app/productos
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    // 1. Obtener todos los productos (GET /inventory-app/productos)
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = this.productoService.listarProductos();
        logger.info("Productos obtenidos: {}", productos.size());
        return ResponseEntity.ok(productos);
    }

    // 2. Obtener producto por ID (GET /inventory-app/productos/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable("id") Integer id) {
        Producto producto = this.productoService.buscarProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    // 3. Crear nuevo producto (POST /inventory-app/productos)
    @PostMapping
    public ResponseEntity<Producto> agregarProducto(@Valid @RequestBody Producto producto) {
        logger.info("Producto a agregar: {}", producto);
        producto.setIdProducto(null);
        Producto nuevoProducto = this.productoService.guardarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // 4. Actualizar producto existente (PUT /inventory-app/productos/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable("id") Integer id,
            @Valid @RequestBody Producto productoDetalles) {

        Producto productoExistente = this.productoService.buscarProductoPorId(id);

        productoExistente.setDescripcion(productoDetalles.getDescripcion());
        productoExistente.setPrecio(productoDetalles.getPrecio());
        productoExistente.setExistencias(productoDetalles.getExistencias());

        Producto productoActualizado = this.productoService.guardarProducto(productoExistente);
        logger.info("Producto actualizado: {}", productoActualizado);
        return ResponseEntity.ok(productoActualizado);
    }

    // 5. Eliminar producto (DELETE /inventory-app/productos/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable("id") Integer id) {
        // Verificamos existencia para disparar 404 si no existe antes de eliminar
        this.productoService.buscarProductoPorId(id);
        this.productoService.eliminarProductoPorId(id);
        logger.info("Producto eliminado con id: {}", id);

        Map<String, Boolean> respuesta = Collections.singletonMap("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
