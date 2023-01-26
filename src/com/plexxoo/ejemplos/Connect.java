package com.plexxoo.ejemplos;

import java.sql.*;

public class Connect
{
	static Connection CONN = null;
	public final static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	public final static String USERNAME = "root";
	public final static String PASSWORD = "root";
	public final static String URL = "jdbc:mysql://localhost:3306/sakila";
	private static long LAST_INSERT_ID;
	private static String TABLE="language";
	private static String NAME="NAME";
	private static String DATETIME="last_update";
	private static String ID="language_ID";
    public static void main (String[] args)
    {
        

        try
        {
            
            Class.forName (DRIVER_NAME)
            .newInstance ();
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
                    CONN.close ();
                    System.out.println ("Database Connection terminated");
                }
                catch (Exception e) { /* ignore close errors */ }
            }
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
	    	System.out.println(autoIncKeyFromFunc + "ID del último insert");
	    	
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
}