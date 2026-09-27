package org.cjvaldi.inventory.api.service;

import org.cjvaldi.inventory.api.entity.Producto;
import org.cjvaldi.inventory.api.exception.RecursoNoEncontradoExcepcion;
import org.cjvaldi.inventory.api.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas Unitarias para ProductoService")
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto productoBase;

    @BeforeEach
    void setUp() {
        productoBase = new Producto(1, "Teclado Mecanico", 85.50, 15);
    }

    @Nested
    @DisplayName("Metodo: listarProductos")
    class ListarProductosTests {

        @Test
        @DisplayName("Debe retornar la lista completa de productos disponibles")
        void listarProductos_DebeRetornarColeccion() {
            // Arrange (Given)
            Producto productoDos = new Producto(2, "Raton Inalambrico", 25.00, 30);
            when(productoRepository.findAll()).thenReturn(List.of(productoBase, productoDos));

            // Act (When)
            List<Producto> resultado = productoService.listarProductos();

            // Assert (Then)
            assertThat(resultado).isNotNull().hasSize(2);
            assertThat(resultado.get(0).getDescripcion()).isEqualTo("Teclado Mecanico");
            verify(productoRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Debe retornar una lista vacia cuando no hay registros")
        void listarProductos_CuandoNoHayDatos_DebeRetornarListaVacia() {
            when(productoRepository.findAll()).thenReturn(List.of());

            List<Producto> resultado = productoService.listarProductos();

            assertThat(resultado).isNotNull().isEmpty();
            verify(productoRepository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("Metodo: buscarProductoPorId")
    class BuscarProductoPorIdTests {

        @Test
        @DisplayName("Debe retornar el producto cuando el identificador existe")
        void buscarProductoPorId_CuandoExiste_DebeRetornarProducto() {
            // Arrange
            when(productoRepository.findById(1)).thenReturn(Optional.of(productoBase));

            // Act
            Producto resultado = productoService.buscarProductoPorId(1);

            // Assert
            assertThat(resultado).isNotNull();
            assertThat(resultado.getIdProducto()).isEqualTo(1);
            assertThat(resultado.getDescripcion()).isEqualTo("Teclado Mecanico");
            verify(productoRepository, times(1)).findById(1);
        }

        @Test
        @DisplayName("Debe lanzar RecursoNoEncontradoExcepcion cuando el ID no existe")
        void buscarProductoPorId_CuandoNoExiste_DebeLanzarExcepcion() {
            // Arrange
            when(productoRepository.findById(99)).thenReturn(Optional.empty());

            // Act & Assert
            assertThatThrownBy(() -> productoService.buscarProductoPorId(99))
                    .isInstanceOf(RecursoNoEncontradoExcepcion.class)
                    .hasMessageContaining("No se encontró el producto con id: 99");

            verify(productoRepository, times(1)).findById(99);
        }
    }

    @Nested
    @DisplayName("Metodo: guardarProducto")
    class GuardarProductoTests {

        @Test
        @DisplayName("Debe persistir y retornar la entidad de producto guardada")
        void guardarProducto_DebeRetornarEntidadPersistida() {
            // Arrange
            when(productoRepository.save(any(Producto.class))).thenReturn(productoBase);

            // Act
            Producto resultado = productoService.guardarProducto(productoBase);

            // Assert
            assertThat(resultado).isNotNull();
            assertThat(resultado.getIdProducto()).isEqualTo(1);
            verify(productoRepository, times(1)).save(productoBase);
        }
    }

    @Nested
    @DisplayName("Metodo: eliminarProductoPorId")
    class EliminarProductoPorIdTests {

        @Test
        @DisplayName("Debe invocar el borrado en el repositorio con el identificador dado")
        void eliminarProductoPorId_DebeInvocarRepositorio() {
            // Arrange: Simulamos que el producto sí existe al buscarlo
            when(productoRepository.findById(1)).thenReturn(Optional.of(productoBase));
            doNothing().when(productoRepository).delete(productoBase); // o deleteById(1) según tu método

            // Act
            productoService.eliminarProductoPorId(1);

            // Assert: Verificamos que se consultó y se borró
            verify(productoRepository, times(1)).findById(1);
            verify(productoRepository, times(1)).delete(productoBase); // o deleteById(1)
        }
    }
}