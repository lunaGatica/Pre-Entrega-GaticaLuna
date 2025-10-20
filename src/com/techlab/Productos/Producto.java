package com.techlab.productos;

public abstract class Producto {
  protected int id;
  protected String nombre;
  protected double precio;
  protected int stock;
  protected static int contadorId = 1;

  public Producto(String nombre, double precio, int stock) {
    this.id = contadorId++;
    this.nombre = nombre;
    this.precio = precio;
    this.stock = stock;
  }

  // Getters y Setters
  public int getId() { return id; }
  public String getNombre() { return nombre; }
  public void setNombre(String nombre) { this.nombre = nombre; }
  public double getPrecio() { return precio; }
  public void setPrecio(double precio) { this.precio = precio; }
  public int getStock() { return stock; }
  public void setStock(int stock) { this.stock = stock; }

  public abstract String getTipo();

  @Override
  public String toString() {
    return String.format("ID: %d | %s | Precio: $%.2f | Stock: %d | Tipo: %s",
        id, nombre, precio, stock, getTipo());
  }
}