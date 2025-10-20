package com.techlab.servicios;

import com.techlab.pedidos.Pedido;
import com.techlab.pedidos.LineaPedido;
import com.techlab.productos.Producto;
import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.excepciones.ProductoNoEncontradoException;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {
  private List<Pedido> pedidos;
  private ProductoService productoService;

  public PedidoService(ProductoService productoService) {
    this.pedidos = new ArrayList<>();
    this.productoService = productoService;
  }

  public Pedido crearPedido() {
    Pedido pedido = new Pedido();
    pedidos.add(pedido);
    return pedido;
  }

  public void agregarProductoAPedido(Pedido pedido, int productoId, int cantidad)
      throws ProductoNoEncontradoException, StockInsuficienteException {

    Producto producto = productoService.buscarProductoPorId(productoId);

    if (producto.getStock() < cantidad) {
      throw new StockInsuficienteException(
          String.format("Stock insuficiente. Disponible: %d, Solicitado: %d",
              producto.getStock(), cantidad)
      );
    }

    LineaPedido linea = new LineaPedido(producto, cantidad);
    pedido.agregarLinea(linea);
  }

  public void confirmarPedido(Pedido pedido) throws ProductoNoEncontradoException {
    for (LineaPedido linea : pedido.getLineasPedido()) {
      productoService.actualizarStock(
          linea.getProducto().getId(),
          linea.getCantidad()
      );
    }
  }

  public List<Pedido> listarPedidos() {
    return new ArrayList<>(pedidos);
  }

  public Pedido buscarPedidoPorId(int id) {
    return pedidos.stream()
        .filter(p -> p.getId() == id)
        .findFirst()
        .orElse(null);
  }
}