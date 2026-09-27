package org.cjvaldi.inventory.api.service;

import org.cjvaldi.inventory.api.entity.Producto;

import java.util.List;

public interface IProductoService {

    List<Producto> listarProductos();

    Producto buscarProductoPorId(Integer idProducto);

    Producto guardarProducto(Producto producto);

    void eliminarProductoPorId(Integer idProducto);
}