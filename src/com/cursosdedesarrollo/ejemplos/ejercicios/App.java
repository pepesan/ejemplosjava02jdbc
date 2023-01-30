package com.cursosdedesarrollo.ejemplos.ejercicios;

import com.cursosdedesarrollo.ejemplos.ejercicios.db.Conexion;
import com.cursosdedesarrollo.ejemplos.ejercicios.domain.Cliente;
import com.cursosdedesarrollo.ejemplos.ejercicios.repositories.ClienteDAO;
import com.cursosdedesarrollo.ejemplos.ejercicios.repositories.ClienteDAODDBBImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public final static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    public final static String USERNAME = "root";
    public final static String PASSWORD = "root";
    public final static String URL = "jdbc:mysql://localhost:3306/";
    public final static String DATABASE = "test";
    public static void main(String[] args) {
        try {
            Conexion conexion = new Conexion();
            System.out.println("Conection created!");
            System.out.println(conexion);
            conexion.getConnection().close();
            System.out.println("Conection closed");
            conexion = new Conexion(DRIVER_NAME, USERNAME, PASSWORD, URL+DATABASE);
            System.out.println("Conection created!");
            System.out.println(conexion);
            // Consulta en la BBDD
            // Crea un objeto del Cliente DAO
            ClienteDAO clienteDAO = new ClienteDAODDBBImpl(conexion);
            // Obtener un listado de datos
            List<Cliente> listado = clienteDAO.findAll();
            System.out.println(listado);
            // Crear un objeto en la bbdd
            Cliente cliente = new Cliente();
            cliente.setName("Natalia");
            cliente.setDir("Madrid");
            cliente.setTlf("912345678");
            cliente = clienteDAO.add(cliente);
            System.out.println(cliente);
            // Obtener un dato de la bbdd por su ID
            cliente = clienteDAO.findByID(cliente.getId());
            System.out.println(cliente);

            // Modificar un objeto en la bbdd
            // modificamos el objeto del cliente
            cliente.setName("Natalia de la Hoz");
            cliente.setDir("Toledo");
            cliente.setTlf("92456789");
            cliente = clienteDAO.update(cliente.getId(), cliente);
            System.out.println(cliente);

            // Borrado del objeto por su ID
            cliente = clienteDAO.delete(cliente.getId());
            System.out.println(cliente);
            conexion.getConnection().close();
            System.out.println("Conection closed");

            // Convertir a Stream
            System.out.println(listado);
            Stream<Cliente> stream =listado.stream();
            // Uso de Lambdas o Arrow Functions
            // ForEach para imprimir uno a uno
            stream.forEach(System.out::println);
            //Filter
            stream =listado.stream();
            stream
                .filter(c -> c.getDir().equals("Toledo"))
                .forEach(System.out::println);
            // Map
            stream =listado.stream();
            stream
                .map(c -> c.getName())
                .forEach(System.out::println);
            // Collect
            stream =listado.stream();
            List<Cliente> litadoFiltrado =
                    stream
                            .filter(c -> c.getDir().equals("Toledo"))
                            .collect(Collectors.toList());
            System.out.println(litadoFiltrado);
            // All together
            stream =listado.stream();
            stream
                // filtramos los clientes de Toledo
                .filter(c -> c.getDir().equals("Toledo"))
                // cogemos sólo los nombres
                .map(c -> c.getName())
                // con un foreach los imprimimos
                .forEach(System.out::println);
            System.out.println("fin del stream");



        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException");
            System.out.println(e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("InstantiationException");
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException");
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQLException");
            System.out.println(e.getMessage());
        }

    }
}
