package com.cursosdedesarrollo.ejemplos.ejercicios.repositories;

import com.cursosdedesarrollo.ejemplos.ejercicios.domain.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    // findAll (coge registros y los mete en el listado),
    public List<Cliente> findAll() throws SQLException;
    // add (Añade un objeto Cliente como registro),
    public Cliente add(Cliente cliente) throws SQLException;
    // findByID (devuelve un objeto cliente pasando un id)
    public Cliente findByID(Long id) throws SQLException;
    // update (dado un id cambia los datos de un registro),
    public Cliente update(Long id, Cliente cliente) throws SQLException;
    // delete (borra un registro pasando un id),
    public Cliente delete(Long id) throws SQLException;
}
