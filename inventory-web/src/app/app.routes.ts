import { Routes } from '@angular/router';
import { ProductoLista } from './producto-lista/producto-lista';
import { AgregarProducto } from './agregar-producto/agregar-producto';
import { EditarProducto } from './editar-producto/editar-producto';

export const routes: Routes = [
    {path: 'productos', component: ProductoLista},
    {path: '', redirectTo: 'productos', pathMatch:'full'},
    {path: 'agregar-producto', component: AgregarProducto},
    {path: 'editar-producto/:id', component: EditarProducto}
];
