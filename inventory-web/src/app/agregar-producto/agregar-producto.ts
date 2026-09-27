import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Producto } from '../models/producto.model';
import { ProductoServicio } from '../services/producto.service';
import { Router } from '@angular/router';

@Component({
  imports: [FormsModule],
  selector: 'app-agregar-producto',
  templateUrl: './agregar-producto.html',
})
export class AgregarProducto {
  producto: Producto = new Producto();

  private productServicio = inject(ProductoServicio);
  private enrutador = inject(Router);

  onSubmit() {
    this.guardarProducto();
  }

  guardarProducto() {
    this.productServicio.agregarProducto(this.producto).subscribe({
      next: (datos) => this.irListaProductos(),
      error: (error) => console.error('Error al insertar producto:', error),
    });
  }

  irListaProductos() {
    this.enrutador.navigate(['/productos']);
  }
}
