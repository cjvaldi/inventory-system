package org.cjvaldi.inventory.api.service;

import org.cjvaldi.inventory.api.entity.Producto;
import org.cjvaldi.inventory.api.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;

    // Inyección de dependencias por constructor (inmutable y testable)
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return this.productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto buscarProductoPorId(Integer idProducto) {
        return this.productoRepository.findById(idProducto).orElse(null);
    }

    @Override
    @Transactional
    public Producto guardarProducto(Producto producto) {
        return this.productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void eliminarProductoPorId(Integer idProducto) {
        if(!productoRepository.existsById(idProducto)){
            throw new NoSuchElementException("No existe producto con id: "+idProducto);
        }
        this.productoRepository.deleteById(idProducto);
    }
}