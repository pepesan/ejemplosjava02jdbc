package com.cursosdedesarrollo.ejemplos.ejercicios.repositories;

import com.cursosdedesarrollo.ejemplos.ejercicios.db.Conexion;
import com.cursosdedesarrollo.ejemplos.ejercicios.domain.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ClienteDAODDBBImpl implements ClienteDAO{

    private static final String TABLE = "clientes";
    private static final String NAME = "name";
    private static final String DIR = "dir";
    private static final String TLF = "tlf";
    private static final String ID = "id";
    private Conexion conexion;

    public ClienteDAODDBBImpl(Conexion conexion){
        this.conexion = conexion;
    }
    @Override
    public List<Cliente> findAll() throws SQLException {
        String sql="SELECT * FROM "+TABLE;
        Statement statement = this.conexion.getConnection().createStatement();
        ResultSet r = statement.executeQuery(sql);
        List<Cliente> listado = new LinkedList<>();
        Cliente cliente;
        while(r.next()){
            cliente = new Cliente();
            cliente.setId(r.getLong(1));
            cliente.setName(r.getString(2));
            cliente.setDir(r.getString(3));
            cliente.setTlf(r.getString(4));
            listado.add(cliente);
        }
        return listado;
    }

    @Override
    public Cliente add(Cliente cliente) throws SQLException {
        String sql = "INSERT into "+TABLE+" ("+NAME+","+DIR+","+TLF+
                ") VALUES(?,?,?)";
        PreparedStatement prest;
        prest = this.conexion.getConnection().prepareStatement(sql);
        prest.setString(1, cliente.getName());
        prest.setString(2,cliente.getDir());
        prest.setString(3,cliente.getTlf());
        int count = prest.executeUpdate();
        System.out.println("count: "+ count);
        ResultSet rs = null;
        rs = prest.executeQuery("SELECT LAST_INSERT_ID()");
        if (rs.next()) {
            System.out.println("inserted correctly");
            cliente.setId(rs.getLong(1));
        } else {
            System.out.println("Not inserted correctly");
            cliente.setId(-1L);
        }
        return cliente;
    }

    @Override
    public Cliente findByID(Long id) throws SQLException {
        // consulta en la bbdd buscando por ID
        String sql = "SELECT * FROM "+TABLE+" WHERE "+ID+" = ?";
        PreparedStatement prest;
        prest = this.conexion.getConnection().prepareStatement(sql);
        prest.setLong(1, id);
        ResultSet r = prest.executeQuery();
        // rellenar un objeto de Cliente con los datos de la bbdd
        Cliente cliente = new Cliente();
        if(r.next()){
            cliente.setId(r.getLong(1));
            cliente.setName(r.getString(2));
            cliente.setDir(r.getString(3));
            cliente.setTlf(r.getString(4));
        }
        // devolver el objeto cliente
        return cliente;
    }

    @Override
    public Cliente update(Long id, Cliente cliente) throws SQLException {
        String sql = "UPDATE "+TABLE+" SET "+NAME+
                " = ?, "+DIR+" = ?, "+TLF+" = ? WHERE "+ID+" = ?";
        PreparedStatement prest;
        prest = this.conexion.getConnection().prepareStatement(sql);
        prest.setString(1, cliente.getName());
        prest.setString(2,cliente.getDir());
        prest.setString(3,cliente.getTlf());
        prest.setLong(4,id);
        int count = prest.executeUpdate();
        System.out.println("count: " + count);
        if (count==1){
            cliente.setId(id);
        }else{
            cliente.setId(-1L);
        }
        return cliente;
    }

    @Override
    public Cliente delete(Long id) throws SQLException {
        Cliente cliente = findByID(id);
        if (cliente.getId()>0){
            String sql = "DELETE from "+TABLE+" WHERE "+ID+" = ?";
            PreparedStatement prest;
            prest = this.conexion.getConnection().prepareStatement(sql);
            prest.setLong(1,id);
            int count = prest.executeUpdate();
            if (count>0){
                System.out.println("Deleting Successfully!");
            }else{
                cliente = new Cliente();
            }
        }
        return cliente;
    }
}
