/* ----------------------------------------------------------------------------- 
Nombre: 		UsuarioDB
Descripción:	Clase que maneja el acceso a la BD para mantener los datos de la 
				tabla USUARIO

Log de modificaciones:

Fecha		Autor		Descripción
13/11/2014	MAC			Creación
----------------------------------------------------------------------------- */
package com.fulbitoAndroid.gestionDB;

import java.util.ArrayList;
import java.util.List;

import com.fulbitoAndroid.clases.Usuario;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

public class UsuarioDB {
	
	FulbitoSQLiteHelper dbHelper;

	public UsuarioDB() {
		dbHelper = SingletonFulbitoDB.getInstance();
	}
	
	//Metodo para insertar un registro en la tabla USUARIO
	public boolean bInsertarUsuario(Usuario usr){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
        	ContentValues values = new ContentValues();
            
        	values.put(FulbitoSQLiteHelper.USUARIO_ID		, usr.getId());
        	values.put(FulbitoSQLiteHelper.USUARIO_ALIAS	, usr.getAlias());
        	values.put(FulbitoSQLiteHelper.USUARIO_EMAIL	, usr.getEmail());
        	values.put(FulbitoSQLiteHelper.USUARIO_PASSWORD	, usr.getPassword());
        	values.put(FulbitoSQLiteHelper.USUARIO_FOTO		, usr.getFoto());
        	        	
        	db.insert(FulbitoSQLiteHelper.TABLA_USUARIO, null, values);
        	
        	//Van a ocurrir muchas situaciones en la que se quiera insertar un USUARIO ya insertado
        	//por eso se usa el metodo insertWithOnConflict para ignorar cuando ocurra este tipo de conflicto
        	//db.insertWithOnConflict(FulbitoSQLiteHelper.TABLA_USUARIO, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        	/*
        	db.execSQL(
        			"INSERT INTO " + FulbitoSQLiteHelper.TABLA_USUARIO +
        			" ( " +
        				FulbitoSQLiteHelper.USUARIO_ID 			+ ", " +
        				FulbitoSQLiteHelper.USUARIO_ALIAS 		+ ", " +
        				FulbitoSQLiteHelper.USUARIO_EMAIL 		+ ", " +
        				FulbitoSQLiteHelper.USUARIO_PASSWORD 	+ ", " +
        				FulbitoSQLiteHelper.USUARIO_FOTO 		+ 
        			" ) " + 
        			"VALUES" +
        			" ( " + 
        				usr.getId() + 
        				", " + 
        				"'" + usr.getAlias() + "'" + 
        				", " + 
        				"'" + usr.getEmail() + "'" +
        				", " + 
        				"'" + usr.getPassword() + "'" +
        				", " + 
        				"'" + usr.getFoto() + "'" +
        			")");
        	*/
            //Cerramos la base de datos
            db.close();
        }

        return true;
	}
	
	//Metodo para eliminar toda la tabla USUARIO
	public boolean bDeleteAllUsuario(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
        	db.delete(FulbitoSQLiteHelper.TABLA_USUARIO, null, null);

        	//db.execSQL("DELETE FROM usuario");
        	
			//Cerramos la base de datos
			db.close();
        }

        return true;
	}
	
	//Metodo para eliminar un registro de la tabla USUARIO filtrando por ID
	public boolean bDeleteUsuarioById(int iIdUsuario){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {        
        	String FILTROS = FulbitoSQLiteHelper.USUARIO_ID + " = " + Integer.toString(iIdUsuario);
        	
        	db.delete(FulbitoSQLiteHelper.TABLA_USUARIO, FILTROS, null);
			
			//Cerramos la base de datos
			db.close(); 
        }

        return true;
	}
	
	//Metodo para obtener un registro de la tabla USUARIO filtrando por ID
	public Usuario usrSelectUsuarioById(int iIdUsuario){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Usuario usr = new Usuario();
		
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {        	        
        	String[] COLUMNAS = {
        			FulbitoSQLiteHelper.USUARIO_ID, 
        			FulbitoSQLiteHelper.USUARIO_ALIAS, 
        			FulbitoSQLiteHelper.USUARIO_EMAIL, 
        			FulbitoSQLiteHelper.USUARIO_PASSWORD,
        			FulbitoSQLiteHelper.USUARIO_FOTO
        		};
        	
        	String FILTROS = FulbitoSQLiteHelper.USUARIO_ID + " = " + Integer.toString(iIdUsuario);
        	
        	Cursor cursor = 
                    db.query(
                    		FulbitoSQLiteHelper.TABLA_USUARIO, // a. table
                    		COLUMNAS, // b. column names
                    		FILTROS, // c. selections 
                    		null, // d. selections args
                    		null, // e. group by
                    		null, // f. having
                    		null, // g. order by
                    		null // h. limit
                    	); 
        	
        	if (cursor != null)
        	{
        		cursor.moveToFirst();

                usr.setId(Integer.parseInt(cursor.getString(0)));
                usr.setAlias(cursor.getString(1));
                usr.setEmail(cursor.getString(2));
                usr.setPassword(cursor.getString(3));
                usr.setFoto(cursor.getString(4));
                
                cursor.close();
        	}
                    	
            //Cerramos la base de datos
            db.close();
 
        }

        return usr;
	}
	
	//Metodo para obtener todos los registros de la tabla USUARIO
	public List<Usuario> usrSelectAllUsuario(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();		
		
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {        	        
        	String[] COLUMNAS = {
        			FulbitoSQLiteHelper.USUARIO_ID, 
        			FulbitoSQLiteHelper.USUARIO_ALIAS, 
        			FulbitoSQLiteHelper.USUARIO_EMAIL, 
        			FulbitoSQLiteHelper.USUARIO_PASSWORD,
        			FulbitoSQLiteHelper.USUARIO_FOTO
        		};        	
        	
        	Cursor cursor = 
                    db.query(
                    		FulbitoSQLiteHelper.TABLA_USUARIO, // a. table
                    		COLUMNAS, // b. column names
                    		null, // c. selections 
                    		null, // d. selections args
                    		null, // e. group by
                    		null, // f. having
                    		null, // g. order by
                    		null // h. limit
                    	);                 	
        	
        	if (cursor != null)
        	{
        		if(cursor .moveToFirst()) 
        		{
                    while (cursor.isAfterLast() == false) 
                    {
                    	Usuario usr = new Usuario();
                    	
                    	usr.setId(Integer.parseInt(cursor.getString(0)));
                        usr.setAlias(cursor.getString(1));
                        usr.setEmail(cursor.getString(2));
                        usr.setPassword(cursor.getString(3));
                        usr.setFoto(cursor.getString(4));
                    	
                        listaUsuarios.add(usr);
                        cursor.moveToNext();
                    }
                }
                
                cursor.close();
        	}
                    	
            //Cerramos la base de datos
            db.close();
 
        }

        return listaUsuarios;
	}
	
	//Metodo para retornar la cantidad de registros de la tabla USUARIO
	public int iGetUsuarioCount(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		String countQuery = "SELECT 1 FROM " + FulbitoSQLiteHelper.TABLA_USUARIO;				
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
	
	//Metodo para updatear un registro en la tabla USUARIO
	public boolean bUpdateUsuario(Usuario usr){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		 
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
        	ContentValues values = new ContentValues();
            
        	values.put(FulbitoSQLiteHelper.USUARIO_ALIAS	, usr.getAlias());
        	values.put(FulbitoSQLiteHelper.USUARIO_EMAIL	, usr.getEmail());
        	values.put(FulbitoSQLiteHelper.USUARIO_PASSWORD	, usr.getPassword());
        	values.put(FulbitoSQLiteHelper.USUARIO_FOTO		, usr.getFoto());
        	
        	String FILTROS = FulbitoSQLiteHelper.USUARIO_ID + " = " + Integer.toString(usr.getId());
        	
        	db.update(FulbitoSQLiteHelper.TABLA_USUARIO, values, FILTROS, null);        	

            //Cerramos la base de datos
            db.close();
        }

        return true;
	}
	
	//Metodo para obtener todos los datos de un usuario (USUARIO + DATOS_OPC_USUARIO)
	public Usuario usrSelectDatosUsuarioById(int iIdUsuario){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Usuario usr = new Usuario();
		
        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {        	        
        	
        	SQLiteQueryBuilder qbQuery = new SQLiteQueryBuilder();
        	//Asignamos las tablas que se acceden
        	qbQuery.setTables(
        			FulbitoSQLiteHelper.TABLA_USUARIO + 
        			" JOIN " + 
    				FulbitoSQLiteHelper.TABLA_DAT_OPC_USR + 
    				" ON (" +
    				FulbitoSQLiteHelper.USUARIO_ID +
    				" = " + 
    				FulbitoSQLiteHelper.DAT_OPC_USR_ID +
    				")"
    			);
        	        	
        	String sCampos[] = {
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_ID,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_ALIAS,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_EMAIL,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_PASSWORD,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_FOTO,
        			FulbitoSQLiteHelper.TABLA_DAT_OPC_USR + "." + FulbitoSQLiteHelper.DAT_OPC_USR_FECHA_NAC,
        			FulbitoSQLiteHelper.TABLA_DAT_OPC_USR + "." + FulbitoSQLiteHelper.DAT_OPC_USR_UBICACION,
        			FulbitoSQLiteHelper.TABLA_DAT_OPC_USR + "." + FulbitoSQLiteHelper.DAT_OPC_USR_LATITUD,
        			FulbitoSQLiteHelper.TABLA_DAT_OPC_USR + "." + FulbitoSQLiteHelper.DAT_OPC_USR_LONGITUD,
        			FulbitoSQLiteHelper.TABLA_DAT_OPC_USR + "." + FulbitoSQLiteHelper.DAT_OPC_USR_SEXO,
        			FulbitoSQLiteHelper.TABLA_DAT_OPC_USR + "." + FulbitoSQLiteHelper.DAT_OPC_USR_TELEFONO,
        			FulbitoSQLiteHelper.TABLA_DAT_OPC_USR + "." + FulbitoSQLiteHelper.DAT_OPC_USR_RADIO_BUSQ
        		};
        	
        	String FILTROS = FulbitoSQLiteHelper.USUARIO_ID + " = " + Integer.toString(iIdUsuario);
        	
        	Cursor cursor = qbQuery.query(db, sCampos, FILTROS, null, null, null, null);
        	        	
        	if (cursor != null)
        	{
        		cursor.moveToFirst();

                usr.setId(Integer.parseInt(cursor.getString(0)));
                usr.setAlias(cursor.getString(1));
                usr.setEmail(cursor.getString(2));
                usr.setPassword(cursor.getString(3));
                usr.setFoto(cursor.getString(4));                
                usr.setFechaNacimiento(cursor.getString(5));
                usr.setUbicacion(cursor.getString(6));
                usr.setUbicacionLatitud(cursor.getString(7));
                usr.setUbicacionLongitud(cursor.getString(8));
                usr.setSexo(cursor.getString(9));
                usr.setTelefono(cursor.getString(10));
                usr.setRadioBusqueda(cursor.getInt(11));
                
                cursor.close();
        	}
                    	
            //Cerramos la base de datos
            db.close();
 
        }

        return usr;
	}
}
