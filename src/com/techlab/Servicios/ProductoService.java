package com.techlab.servicios;

import com.techlab.productos.Producto;
import com.techlab.productos.Cenicero;
import com.techlab.productos.CeniceroEspecial;
import com.techlab.excepciones.ProductoNoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoService {
  private List<Producto> productos;

  public ProductoService() {
    this.productos = new ArrayList<>();
    inicializarProductos();
  }

  private void inicializarProductos() {
    // Ceniceros estándar
    agregarProducto(new Cenicero("Cenicero Rosa Clásico", 15.99, 50, "Rosa", "Cerámica", "Redonda"));
    agregarProducto(new Cenicero("Cenicero Azul Marino", 18.50, 30, "Azul Marino", "Vidrio", "Cuadrada"));
    agregarProducto(new Cenicero("Cenicero Verde Cristal", 22.75, 25, "Verde", "Cristal", "Ovalada"));

    // Ceniceros especiales
    agregarProducto(new CeniceroEspecial("Cenicero Dorado Premium", 45.99, 15, "Dorado",
        "Metal", "Hexagonal", true, true, "Pesada"));
    agregarProducto(new CeniceroEspecial("Cenicero Negro Mate", 35.50, 20, "Negro",
        "Silicio", "Redonda", false, true, "Antideslizante"));
  }

  public void agregarProducto(Producto producto) {
    productos.add(producto);
  }

  public List<Producto> listarProductos() {
    return new ArrayList<>(productos);
  }

  public Producto buscarProductoPorId(int id) throws ProductoNoEncontradoException {
    return productos.stream()
        .filter(p -> p.getId() == id)
        .findFirst()
        .orElseThrow(() -> new ProductoNoEncontradoException("Producto con ID " + id + " no encontrado"));
  }

  public List<Producto> buscarProductosPorNombre(String nombre) {
    return productos.stream()
        .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
        .collect(Collectors.toList());
  }

  public void actualizarProducto(int id, double nuevoPrecio, int nuevoStock)
      throws ProductoNoEncontradoException {
    Producto producto = buscarProductoPorId(id);
    if (nuevoPrecio > 0) {
      producto.setPrecio(nuevoPrecio);
    }
    if (nuevoStock >= 0) {
      producto.setStock(nuevoStock);
    }
  }

  public boolean eliminarProducto(int id) {
    return productos.removeIf(p -> p.getId() == id);
  }

  public void actualizarStock(int id, int cantidadVendida) throws ProductoNoEncontradoException {
    Producto producto = buscarProductoPorId(id);
    int nuevoStock = producto.getStock() - cantidadVendida;
    if (nuevoStock < 0) {
      throw new IllegalArgumentException("Stock no puede ser negativo");
    }
    producto.setStock(nuevoStock);
  }
}