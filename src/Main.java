package com.techlab;

import com.techlab.servicios.ProductoService;
import com.techlab.servicios.PedidoService;
import com.techlab.productos.Producto;
import com.techlab.pedidos.Pedido;
import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.excepciones.ProductoNoEncontradoException;
import java.util.List;
import java.util.Scanner;

public class Main {
  private static ProductoService productoService = new ProductoService();
  private static PedidoService pedidoService = new PedidoService(productoService);
  private static Scanner scanner = new Scanner(System.in);
  private static Pedido pedidoActual = null;

  public static void main(String[] args) {
    System.out.println("=== SISTEMA DE VENTA DE CENICEROS ===");
    mostrarMenuPrincipal();
  }

  private static void mostrarMenuPrincipal() {
    int opcion;
    do {
      System.out.println("\n=== MENÚ PRINCIPAL ===");
      System.out.println("1. Agregar producto");
      System.out.println("2. Listar productos");
      System.out.println("3. Buscar/Actualizar producto");
      System.out.println("4. Eliminar producto");
      System.out.println("5. Crear un pedido");
      System.out.println("6. Listar pedidos");
      System.out.println("7. Salir");
      System.out.print("Seleccione una opción: ");

      try {
        opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
          case 1 -> agregarProducto();
          case 2 -> listarProductos();
          case 3 -> buscarYActualizarProducto();
          case 4 -> eliminarProducto();
          case 5 -> gestionarPedidos();
          case 6 -> listarPedidos();
          case 7 -> System.out.println("Salir");
          default -> System.out.println("Opción no válida. Intente nuevamente.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Error: Debe ingresar un número válido.");
        opcion = 0;
      }
    } while (opcion != 7);
  }

  private static void agregarProducto() {
    System.out.println("\n--- AGREGAR PRODUCTO ---");
    // Implementación para agregar nuevo producto
    System.out.println("Funcionalidad en desarrollo...");
  }

  private static void listarProductos() {
    System.out.println("\n--- LISTA DE PRODUCTOS ---");
    List<Producto> productos = productoService.listarProductos();
    if (productos.isEmpty()) {
      System.out.println("No hay productos registrados.");
    } else {
      productos.forEach(System.out::println);
    }
  }

  private static void buscarYActualizarProducto() {
    System.out.println("\n--- BUSCAR Y ACTUALIZAR PRODUCTO ---");
    System.out.print("Ingrese ID o nombre del producto: ");
    String busqueda = scanner.nextLine();

    try {
      int id = Integer.parseInt(busqueda);
      Producto producto = productoService.buscarProductoPorId(id);
      System.out.println("Producto encontrado: " + producto);
      mostrarMenuActualizacion(producto);
    } catch (NumberFormatException e) {
      List<Producto> productos = productoService.buscarProductosPorNombre(busqueda);
      if (productos.isEmpty()) {
        System.out.println("No se encontraron productos.");
      } else {
        productos.forEach(System.out::println);
      }
    } catch (ProductoNoEncontradoException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private static void mostrarMenuActualizacion(Producto producto) {
    System.out.print("¿Desea actualizar este producto? (s/n): ");
    String respuesta = scanner.nextLine();

    if (respuesta.equalsIgnoreCase("s")) {
      try {
        System.out.print("Nuevo precio (actual: " + producto.getPrecio() + "): ");
        String precioStr = scanner.nextLine();
        double nuevoPrecio = precioStr.isEmpty() ? producto.getPrecio() : Double.parseDouble(precioStr);

        System.out.print("Nuevo stock (actual: " + producto.getStock() + "): ");
        String stockStr = scanner.nextLine();
        int nuevoStock = stockStr.isEmpty() ? producto.getStock() : Integer.parseInt(stockStr);

        productoService.actualizarProducto(producto.getId(), nuevoPrecio, nuevoStock);
        System.out.println("Producto actualizado correctamente.");
      } catch (NumberFormatException e) {
        System.out.println("Error: Formato de número inválido.");
      } catch (ProductoNoEncontradoException e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }

  private static void eliminarProducto() {
    System.out.println("\n--- ELIMINAR PRODUCTO ---");
    System.out.print("Ingrese ID del producto a eliminar: ");

    try {
      int id = Integer.parseInt(scanner.nextLine());
      Producto producto = productoService.buscarProductoPorId(id);
      System.out.println("Producto a eliminar: " + producto);

      System.out.print("¿Está seguro? (s/n): ");
      String confirmacion = scanner.nextLine();

      if (confirmacion.equalsIgnoreCase("s")) {
        if (productoService.eliminarProducto(id)) {
          System.out.println("Producto eliminado correctamente.");
        } else {
          System.out.println("Error al eliminar el producto.");
        }
      }
    } catch (NumberFormatException e) {
      System.out.println("Error: Debe ingresar un ID válido.");
    } catch (ProductoNoEncontradoException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private static void gestionarPedidos() {
    System.out.println("\n--- GESTIÓN DE PEDIDOS ---");

    if (pedidoActual == null) {
      pedidoActual = pedidoService.crearPedido();
      System.out.println("Nuevo pedido creado (#" + pedidoActual.getId() + ")");
    }

    int opcion;
    do {
      System.out.println("\nPedido actual: #" + pedidoActual.getId());
      System.out.println("1. Agregar producto al pedido");
      System.out.println("2. Ver pedido actual");
      System.out.println("3. Confirmar pedido");
      System.out.println("4. Cancelar pedido");
      System.out.println("5. Volver al menú principal");
      System.out.print("Seleccione una opción: ");

      try {
        opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
          case 1 -> agregarProductoAlPedido();
          case 2 -> verPedidoActual();
          case 3 -> confirmarPedido();
          case 4 -> cancelarPedido();
          case 5 -> { pedidoActual = null; return; }
          default -> System.out.println("Opción no válida.");
        }
      } catch (NumberFormatException e) {
        System.out.println("Error: Debe ingresar un número válido.");
        opcion = 0;
      }
    } while (opcion != 5);
  }

  private static void agregarProductoAlPedido() {
    System.out.println("\n--- AGREGAR PRODUCTO AL PEDIDO ---");
    listarProductos();

    try {
      System.out.print("Ingrese ID del producto: ");
      int productoId = Integer.parseInt(scanner.nextLine());

      System.out.print("Ingrese cantidad: ");
      int cantidad = Integer.parseInt(scanner.nextLine());

      pedidoService.agregarProductoAPedido(pedidoActual, productoId, cantidad);
      System.out.println("Producto agregado al pedido correctamente.");

    } catch (NumberFormatException e) {
      System.out.println("Error: Formato de número inválido.");
    } catch (ProductoNoEncontradoException | StockInsuficienteException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private static void verPedidoActual() {
    if (pedidoActual.getLineasPedido().isEmpty()) {
      System.out.println("El pedido está vacío.");
    } else {
      System.out.println(pedidoActual);
    }
  }

  private static void confirmarPedido() {
    if (pedidoActual.getLineasPedido().isEmpty()) {
      System.out.println("No puede confirmar un pedido vacío.");
      return;
    }

    try {
      System.out.println("Resumen del pedido:");
      System.out.println(pedidoActual);

      System.out.print("¿Confirmar pedido? (s/n): ");
      String confirmacion = scanner.nextLine();

      if (confirmacion.equalsIgnoreCase("s")) {
        pedidoService.confirmarPedido(pedidoActual);
        System.out.println("Pedido confirmado correctamente.");
        pedidoActual = null;
      }
    } catch (ProductoNoEncontradoException e) {
      System.out.println("Error al confirmar pedido: " + e.getMessage());
    }
  }

  private static void cancelarPedido() {
    System.out.print("¿Está seguro de cancelar el pedido? (s/n): ");
    String confirmacion = scanner.nextLine();

    if (confirmacion.equalsIgnoreCase("s")) {
      pedidoActual = null;
      System.out.println("Pedido cancelado.");
    }
  }

  private static void listarPedidos() {
    System.out.println("\n--- LISTA DE PEDIDOS ---");
    List<Pedido> pedidos = pedidoService.listarPedidos();

    if (pedidos.isEmpty()) {
      System.out.println("No hay pedidos registrados.");
    } else {
      pedidos.forEach(System.out::println);
    }
  }
}