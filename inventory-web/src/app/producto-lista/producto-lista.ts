import { Component, inject, OnInit, signal } from '@angular/core';
import { Producto } from '../models/producto.model';
import { ProductoServicio } from '../services/producto.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-producto-lista',
  standalone: true,
  imports: [],
  templateUrl: './producto-lista.html',
})
export class ProductoLista implements OnInit {
  // Tipado estricto: evita inferencia de tipo 'never[]'
  productos = signal<Producto[]>([]);
  //  productos!: Producto[];

  private readonly productoServicio = inject(ProductoServicio);
  private enrutador = inject(Router);

  ngOnInit(): void {
    this.obtenerProductos();
  }

  private obtenerProductos(): void {
    this.productoServicio.obtenerProductosLista().subscribe({
      next: (datos) => this.productos.set(datos),
      error: (error) => console.error('Error al conectar con la API REST:', error)
    });
  }

  editarProducto(id: number) {
    this.enrutador.navigate(['editar-producto', id]);
  }

  eliminarProducto(id: number) {
    this.productoServicio.eliminarProducto(id).subscribe({
      next: (datos)=> this.obtenerProductos(),
      error: (error) => console.log('Error al eliminar producto: ',error)
    })
  }

}