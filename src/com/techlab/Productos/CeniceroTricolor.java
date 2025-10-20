package com.techlab.productos;

public class CeniceroTricolor extends Cenicero {
  private boolean contieneDiseño;
  private boolean esFluorescente;
  private String tipoBase;

  public CeniceroTricolor(String nombre, double precio, int stock, String color,
      String material, String forma, boolean contieneDiseño,
      boolean esFluorescente, String tipoBase) {
    super(nombre, precio, stock, color, material, forma);
    this.contieneDiseño = contieneDiseño;
    this.esFluorescente = esFluorescente;
    this.tipoBase = tipoBase;
  }

  public boolean isContieneDiseño() { return contieneDiseño; }
  public boolean isEsFluorescente() { return esFluorescente; }
  public String getTipoBase() { return tipoBase; }

  @Override
  public String getTipo() {
    return "Cenicero Tricolor";
  }

  @Override
  public String toString() {
    String diseño = contieneDiseño ? "Con diseño" : "Sin diseño";
    String fluorescente = esFluorescente ? "Fluorescente" : "Normal";
    return super.toString() + String.format(" | %s | %s | Base: %s",
        diseño, fluorescente, tipoBase);
  }
}