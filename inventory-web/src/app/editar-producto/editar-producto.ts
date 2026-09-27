import { Component, inject, signal } from '@angular/core';
import { Producto } from '../models/producto.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductoServicio } from '../services/producto.service';
import { FormsModule } from '@angular/forms';

@Component({
  imports: [FormsModule],
  standalone: true,
  selector: 'app-editar-producto',
  templateUrl: './editar-producto.html',
})
export class EditarProducto {
  id!: number;
  producto = signal<Producto>(new Producto());

  private ruta = inject(ActivatedRoute);
  private productoServicio = inject(ProductoServicio);
  private enrutador = inject(Router);

  ngOnInit(){
    this.id = Number(this.ruta.snapshot.params['id']);
    this.productoServicio.obtenerProductoPorId(this.id).subscribe({
      next: (datos)=> this.producto.set(datos),
      error: (error) => console.log('Error al obtener al producto por Id: ', error)
    });
  }

  onSubmit(){
    this.guardarProducto();
  };

  guardarProducto(){
    this.productoServicio.editarProducto(this.id, this.producto()).subscribe({
      next: () => this.irListarProductos(),
      error: (error) => console.log('Error al guardar producto existente: ', error)
    })
  }

  irListarProductos(){
    this.enrutador.navigate(['/productos']);
  }

}
