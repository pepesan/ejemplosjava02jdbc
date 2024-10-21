package com.cursosdedesarrollo.ejemplos;

import java.sql.*;

public class Connect
{
	static Connection CONN = null;
	public final static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	public final static String USERNAME = "root";
	public final static String PASSWORD = "root";
	public final static String URL = "jdbc:mysql://localhost:3306/test";
	private static long LAST_INSERT_ID;
	private final static String TABLE="language";
	private final static String NAME="NAME";
	private final static String DATETIME="last_update";
	private final static String ID="language_ID";
    public static void main (String[] args){
        try
        {
            // Realizar la carga del Driver
            Class.forName (DRIVER_NAME)
            .newInstance ();
			// Establecer la conexión a la BBDD
			// URL, usuario, contraseña
			// devuelve Connection CONN
            CONN = DriverManager
            		.getConnection (URL, USERNAME, PASSWORD);
            System.out.println 
            ("Database CONNection established");

            String sql="SELECT * FROM "+TABLE;
			Statement statement = CONN.createStatement();
            ResultSet result = statement.executeQuery(sql);

            displayResults(result);
			statement = CONN.createStatement();
			result = statement.executeQuery(sql);
			displayResultsGetters(result);
            createData();
			selectData();
			getLanguageById();
            updateData();
            deleteData();
        }
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			System.out.println(e.getClass());
			System.out.println ("Ha tenido un problema con el Servidor SQL");
		}
        catch (Exception e)
        {
        	System.out.println(e.getMessage());
        	System.out.println(e.getCause());
        	System.out.println(e.getClass());
            System.out.println ("Cannot CONNect to database server");
        }
        finally
        {
            if (CONN != null)
            {
                try
                {
                    CONN.close();
                    System.out.println ("Database Connection terminated");
                }
                catch (Exception e) { /* ignore close errors */ }
            }
        }
    }

	private static void selectData() {
		String sql="SELECT * FROM "+TABLE + " Where "+ ID + "=" + LAST_INSERT_ID;
		PreparedStatement prest;
		try{
			prest = CONN.prepareStatement(sql);
			//prest.setLong(1, LAST_INSERT_ID);
			ResultSet result = prest.executeQuery(sql);
			displayResults(result);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	static void createData(){
    	 String sql = "INSERT into "+TABLE+" ("+NAME+","+DATETIME+
				 ") VALUES(?,?)";
    	  PreparedStatement prest;
		try {
			prest = CONN.prepareStatement(sql);
			prest.setString(1, "España");
			prest.setString(2,"2012-02-01 18:00:00" );
			int count = prest.executeUpdate();
			System.out.println("Count: " + count);
			ResultSet rs = null;
			rs = prest.executeQuery("SELECT LAST_INSERT_ID()");
			int autoIncKeyFromFunc = -1;
			if (rs.next()) {
				autoIncKeyFromFunc = rs.getInt(1);
				LAST_INSERT_ID = autoIncKeyFromFunc;
			} else {
				// throw an exception from here
			}
			/*
			ResultSet generatedKeys = null;
	    		generatedKeys = prest.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            LAST_INSERT_ID=generatedKeys.getLong(1);
	            System.out.println(LAST_INSERT_ID + " ID");
	        } else {
	            throw new SQLException("Creating user failed, no generated key obtained.");
	        }

			 */
	    	System.out.println("ID del último insert: "+ autoIncKeyFromFunc);
	    	
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	  

    }
    static void updateData() throws SQLException{
    	String sql = "UPDATE "+TABLE+" SET "+NAME+
				" = ? WHERE "+ID+" = ?";
    	PreparedStatement prest;
		try {
			prest = CONN.prepareStatement(sql);
			prest.setString(1,"ESHpaña");
			prest.setLong(2,LAST_INSERT_ID);
		    int count = prest.executeUpdate();
		    if(count==1){
		    	// ha ido todo guay
				System.out.println("Updating Successfully!");
			}else{
				// no ha ido todo guay
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

    	  

    }
    static void deleteData() throws SQLException{
    	String sql = "DELETE from "+TABLE+" WHERE "+ID+" = ?";
    	PreparedStatement prest;
		try {
			prest = CONN.prepareStatement(sql);
		    prest.setLong(1,LAST_INSERT_ID);
		    prest.executeUpdate();
		    System.out.println("Deleting Successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}


    }
    static void displayResults(ResultSet r) 
    		throws SQLException {
	    	ResultSetMetaData rmeta = r.getMetaData();
	    	int numColumns=rmeta.getColumnCount();
	    	for(int i=1;i<=numColumns;++i) {
	    	 	if(i<numColumns)
	    	  		System.out.print(rmeta.getColumnName(i)+" | ");
				else
	    	   		System.out.println(rmeta.getColumnName(i));
	    	}
	    	while(r.next()){
	    	  for(int i=1;i<=numColumns;++i) {
	    	   if(i<numColumns)
	    	    System.out.print(r.getString(i)+" | ");
	    	   else
	    	    System.out.println(r.getString(i));
	    	  }
	    	}
    }
	static void displayResultsGetters(ResultSet r)
			throws SQLException {
		ResultSetMetaData rmeta = r.getMetaData();
		int numColumns=rmeta.getColumnCount();
		for(int i=1;i<=numColumns;++i) {
			if(i<numColumns)
				System.out.print(rmeta.getColumnName(i)+" | ");
			else
				System.out.println(rmeta.getColumnName(i));
		}
		while(r.next()){
			Boolean conSalida = false;
			String salida = "";
			for(int i=1;i<=numColumns;++i) {
				if(i<numColumns){
					conSalida = true;
				} else{
					conSalida = false;
				}
				switch (i){
					case 1:
						salida = ""+ r.getInt(1);
						break;
					case 2:
						salida = ""+ r.getString(2);
						break;
					case 3:
						salida = ""+ r.getTimestamp(3);
				}
				System.out.print(salida);
				if (conSalida){
					System.out.print(" | ");
				}else {
					System.out.println("");
				}
			}
		}
	}
	public static void getLanguageById() {
		String query = "SELECT "+ID+", "+NAME+", "+DATETIME+" FROM "+TABLE+" WHERE "+ID+" = ?";

		try (PreparedStatement preparedStatement = CONN.prepareStatement(query)) {

			// Establecer el valor del parámetro de la consulta
			preparedStatement.setLong(1, LAST_INSERT_ID);

			// Ejecutar la consulta
			ResultSet resultSet = preparedStatement.executeQuery();

			// Procesar los resultados
			if (resultSet.next()) {
				int id = resultSet.getInt("language_id");
				String name = resultSet.getString("name");
				java.sql.Timestamp lastUpdate = resultSet.getTimestamp("last_update");

				System.out.println("ID: " + id);
				System.out.println("Name: " + name);
				System.out.println("Last Update: " + lastUpdate);
			} else {
				System.out.println("No se encontró el lenguaje con ID: " + LAST_INSERT_ID);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}