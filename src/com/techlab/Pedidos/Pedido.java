package com.techlab.pedidos;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
  private int id;
  private List<LineaPedido> lineasPedido;
  private double total;
  private static int contadorId = 1;

  public Pedido() {
    this.id = contadorId++;
    this.lineasPedido = new ArrayList<>();
    this.total = 0.0;
  }

  public int getId() { return id; }
  public List<LineaPedido> getLineasPedido() { return lineasPedido; }
  public double getTotal() { return total; }

  public void agregarLinea(LineaPedido linea) {
    lineasPedido.add(linea);
    calcularTotal();
  }

  public void eliminarLinea(int index) {
    if (index >= 0 && index < lineasPedido.size()) {
      lineasPedido.remove(index);
      calcularTotal();
    }
  }

  private void calcularTotal() {
    total = lineasPedido.stream()
        .mapToDouble(LineaPedido::getSubtotal)
        .sum();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Pedido #%d | Total: $%.2f\n", id, total));
    sb.append("Productos:\n");
    for (int i = 0; i < lineasPedido.size(); i++) {
      sb.append(String.format("  %d. %s\n", i + 1, lineasPedido.get(i)));
    }
    return sb.toString();
  }
}