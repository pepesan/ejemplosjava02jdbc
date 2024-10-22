package com.cursosdedesarrollo.ejemplos.ejercicios.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class Conexion {
    private Connection connection;
    public final static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    public final static String USERNAME = "root";
    public final static String PASSWORD = "root";
    public final static String URL = "jdbc:mysql://localhost:3306/test";

    public Conexion() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName (DRIVER_NAME)
                .newInstance ();
        connection = DriverManager
                .getConnection (URL, USERNAME, PASSWORD);
    }
    public Conexion(String driverName, String username, String password, String url)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{

        Class.forName(driverName)
            .newInstance();
        connection = DriverManager
            .getConnection(url, username, password);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String toString() {
        return "Conexion{" +
                "connection=" + connection +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conexion conexion = (Conexion) o;
        return Objects.equals(connection, conexion.connection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connection);
    }
}
