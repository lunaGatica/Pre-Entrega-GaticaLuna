package com.techlab.productos;

public class Cenicero extends Producto {
  private String color;
  private String material;
  private String forma;

  public Cenicero(String nombre, double precio, int stock, String color, String material, String forma) {
    super(nombre, precio, stock);
    this.color = color;
    this.material = material;
    this.forma = forma;
  }

  public String getColor() { return color; }
  public void setColor(String color) { this.color = color; }
  public String getMaterial() { return material; }
  public void setMaterial(String material) { this.material = material; }
  public String getForma() { return forma; }
  public void setForma(String forma) { this.forma = forma; }

  @Override
  public String getTipo() {
    return "Cenicero Redondo";
  }

  @Override
  public String toString() {
    return String.format("ID: %d | %s | Precio: $%.2f | Stock: %d | Color: %s | Material: %s | Forma: %s",
        id, nombre, precio, stock, color, material, forma);
  }
}