package com.fulbitoAndroid.gestionDB;

import com.fulbitoAndroid.clases.Usuario;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsuarioDB {
	
	FulbitoSQLiteHelper dbHelper;
	
	public UsuarioDB() {
		dbHelper = SingletonFulbitoDB.getInstance();
	}
	
	public boolean bInsertarUsuario(Usuario usr){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
        	db.execSQL(
        			"INSERT INTO usuario (id, alias, email, password) " +
        			"VALUES (" + 
        			usr.getId() + ", '" + 
        			usr.getAlias() + "', '" + 
        			usr.getEmail() + "', '" + 
        			usr.getPassword() +"')");
        	
            //Cerramos la base de datos
            db.close();
        }

        return true;
	}
	
	public boolean bDeleteUsuario(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
        	//try{
        		db.execSQL("DELETE FROM usuario");
            	
                //Cerramos la base de datos
                db.close();
        	/*}
        	catch(SQLException e){
        		Log.e("UsuarioDB::bDeleteUsuario", e.getMessage());
        	}   */     	
        }

        return true;
	}
	
	public Usuario usrSelectUsuario(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Usuario usr = new Usuario();
		
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
        	String[] COLUMNS = {"id", "alias", "email", "password"};
        	
        	Cursor cursor = 
                    db.query("usuario", // a. table
                    		COLUMNS, // b. column names
                    		null, // c. selections 
                    		null, // d. selections args
                    		null, // e. group by
                    		null, // f. having
                    		null, // g. order by
                    		null); // h. limit
        	
        	if (cursor != null)
        	{
        		cursor.moveToFirst();

                usr.setId(Integer.parseInt(cursor.getString(0)));
                usr.setAlias(cursor.getString(1));
                usr.setEmail(cursor.getString(2));
                usr.setPassword(cursor.getString(3));
                
                cursor.close();
        	}
                    	
            //Cerramos la base de datos
            db.close();
 
        }

        return usr;
	}
	
	public int iGetUsuarioCount(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Usuario usr = new Usuario();
		
		String countQuery = "SELECT * FROM usuario";				
		int iCount = 0;
		
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
        	Cursor cursor = db.rawQuery(countQuery, null);
    		iCount = cursor.getCount();
        	cursor.close();
            db.close();            
        }

        return iCount;
	}

}
