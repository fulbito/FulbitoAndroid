/* ----------------------------------------------------------------------------- 
Nombre: 		PartidoDB
Descripción:	Clase que maneja el acceso a la BD para mantener los datos de la 
				tabla PARTIDO

Log de modificaciones:

Fecha		Autor		Descripción
17/11/2014	MAC			Creación
----------------------------------------------------------------------------- */
package com.fulbitoAndroid.gestionDB;

import java.util.ArrayList;
import java.util.List;

import com.fulbitoAndroid.clases.Partido;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class PartidoDB {
	
	FulbitoSQLiteHelper dbHelper;

	public PartidoDB() {
		dbHelper = SingletonFulbitoDB.getInstance();
	}
	
	//Metodo para insertar un registro en la tabla USUARIO
	public boolean bInsertarPartido(Partido partido){		
		boolean bResult = true;
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db == null)
        {
        	Log.e("PartidoDB:bInsertarPartido", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
        //Realizamos el insert
        if(bResult == true)
        {
        	ContentValues values = new ContentValues();

        	values.put(FulbitoSQLiteHelper.PARTIDO_ID			, partido.getId());
        	values.put(FulbitoSQLiteHelper.PARTIDO_NOMBRE		, partido.getNombre());
        	values.put(FulbitoSQLiteHelper.PARTIDO_FECHA		, partido.getFecha());
        	values.put(FulbitoSQLiteHelper.PARTIDO_HORA			, partido.getHora());
        	values.put(FulbitoSQLiteHelper.PARTIDO_CANT_JUG		, partido.getCantJugadores());
        	values.put(FulbitoSQLiteHelper.PARTIDO_LUGAR		, partido.getLugar());
        	values.put(FulbitoSQLiteHelper.PARTIDO_ID_USR_ADM	, partido.getUsuarioAdm().getId());

        	try{        	
        		db.insertOrThrow(FulbitoSQLiteHelper.TABLA_PARTIDO, null, values);
        	}
        	catch(SQLiteConstraintException e)
        	{
        		//Fallo el insert por Constraint PRIMARY KEY, entonces hacemos un update
        		bResult = bUpdatePartido(partido);
        	}
        	catch(SQLException e)
        	{
        		Log.e("PartidoDB:bInsertarPartido", "Error al insertar en tabla " + FulbitoSQLiteHelper.TABLA_PARTIDO);
        		bResult = false;
        	}
        	
            //Cerramos la base de datos
            db.close();
        }

        return bResult;
	}
	
	//Metodo para updatear un registro en la tabla PARTIDO
	public boolean bUpdatePartido(Partido partido){
		
		boolean bResult = true;
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db == null)
        {
        	Log.e("PartidoDB:bUpdatePartido", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
        //Realizamos el insert
        if(bResult == true)
        {
        	ContentValues values = new ContentValues();
            
        	values.put(FulbitoSQLiteHelper.PARTIDO_NOMBRE		, partido.getNombre());
        	values.put(FulbitoSQLiteHelper.PARTIDO_FECHA		, partido.getFecha());
        	values.put(FulbitoSQLiteHelper.PARTIDO_HORA			, partido.getHora());
        	values.put(FulbitoSQLiteHelper.PARTIDO_CANT_JUG		, partido.getCantJugadores());
        	values.put(FulbitoSQLiteHelper.PARTIDO_LUGAR		, partido.getLugar());
        	values.put(FulbitoSQLiteHelper.PARTIDO_ID_USR_ADM	, partido.getUsuarioAdm().getId());
        	
        	String FILTROS = FulbitoSQLiteHelper.PARTIDO_ID + " = " + Integer.toString(partido.getId());
        	
        	db.update(FulbitoSQLiteHelper.TABLA_PARTIDO, values, FILTROS, null);        	

            //Cerramos la base de datos
            db.close();
        }

        return bResult;
	}

	//Metodo para eliminar toda la tabla PARTIDO
	public boolean bDeleteAllPartido(){
		boolean bResult = true;
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db == null)
        {
        	Log.e("PartidoDB:bDeleteAllPartido", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
        //Si hemos abierto correctamente la base de datos
        if(bResult == true)
        {
        	db.delete(FulbitoSQLiteHelper.TABLA_PARTIDO, null, null);
        	
			//Cerramos la base de datos
			db.close();
        }

        return bResult;
	}
	
	//Metodo para eliminar un registro de la tabla PARTIDO filtrando por ID
	public boolean bDeletePartidoById(int iIdPartido){
		boolean bResult = true;
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db == null)
        {
        	Log.e("PartidoDB:bDeletePartidoById", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
        //Si hemos abierto correctamente la base de datos
        if(bResult == true)
        {        
        	String FILTROS = FulbitoSQLiteHelper.PARTIDO_ID + " = " + Integer.toString(iIdPartido);
        	
        	db.delete(FulbitoSQLiteHelper.TABLA_PARTIDO, FILTROS, null);
			
			//Cerramos la base de datos
			db.close(); 
        }

        return bResult;
	}
	
	//Metodo para eliminar lo partidos de un USUARIO en particular
	public boolean bDeletePartidosByUsrId(int iIdUsuario){
		boolean bResult = true;
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db == null)
        {
        	Log.e("PartidoDB:bDeletePartidosByUsrId", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
        //Si hemos abierto correctamente la base de datos
        if(bResult == true)
        {        
        	String FILTROS = FulbitoSQLiteHelper.PARTIDO_ID_USR_ADM + " = " + Integer.toString(iIdUsuario);
        	
        	db.delete(FulbitoSQLiteHelper.TABLA_PARTIDO, FILTROS, null);
			
			//Cerramos la base de datos
			db.close(); 
        }

        return bResult;
	}	

	//Metodo para obtener un registro de la tabla PARTIDO filtrando por ID
	public Partido usrSelectPartidoById(int iIdPartido){
		boolean bResult = true;
		
		Partido partido = new Partido();
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db == null)
        {
        	Log.e("PartidoDB:usrSelectPartidoById", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
        //Si hemos abierto correctamente la base de datos
        if(bResult == true)
        {
        	SQLiteQueryBuilder qbQuery = new SQLiteQueryBuilder();
        	
        	//Asignamos las tablas que se acceden
        	qbQuery.setTables(
        			FulbitoSQLiteHelper.TABLA_PARTIDO + 
        			" LEFT JOIN " + 
    				FulbitoSQLiteHelper.TABLA_USUARIO + 
    				" ON (" +
    				FulbitoSQLiteHelper.TABLA_PARTIDO + "." +FulbitoSQLiteHelper.PARTIDO_ID_USR_ADM +
    				" = " + 
    				FulbitoSQLiteHelper.TABLA_USUARIO + "." +FulbitoSQLiteHelper.USUARIO_ID +
    				")"
    			);
        	
        	//Analizar si es necesario obtener los datos opcionales del usuario, en ese caso deberiamos agregar 
        	//un JOIN mas
        	String sCampos[] = {
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_ID,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_NOMBRE,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_FECHA,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_HORA,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_LUGAR,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_CANT_JUG,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_ID_USR_ADM,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_ALIAS,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_EMAIL,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_FOTO
        		};
        	
        	String FILTROS = FulbitoSQLiteHelper.TABLA_PARTIDO + 
        			"." +
        			FulbitoSQLiteHelper.PARTIDO_ID + 
    				" = " + 
    				Integer.toString(iIdPartido);
        	
        	Cursor cursor = qbQuery.query(db, sCampos, FILTROS, null, null, null, null);
        	        	
        	if (cursor != null && cursor.getCount() != 0)
        	{
        		cursor.moveToFirst();

                //Asignamos los datos del partido
        		partido.setId(cursor.getInt(0));
        		partido.setNombre(cursor.getString(1));
        		partido.setFecha(cursor.getString(2));
        		partido.setHora(cursor.getInt(3));
        		partido.setLugar(cursor.getString(4));
        		partido.setCantJugadores(cursor.getInt(5));
        		//Asignamos los datos del usuario administrados del partido        		
        		partido.getUsuarioAdm().setId(cursor.getInt(6));
        		partido.getUsuarioAdm().setAlias(cursor.getString(7));
        		partido.getUsuarioAdm().setEmail(cursor.getString(8));                
        		partido.getUsuarioAdm().setFoto(cursor.getString(9));
                
                cursor.close();
        	}
        	else
        	{
        		partido = null;
        	}
                    	
            //Cerramos la base de datos
            db.close();
        }

        return partido;
	}

	//Metodo para obtener todos los registros de la tabla PARTIDO de un usuario en particular
	public List<Partido> partSelectAllPartidoByUsrAdm(int iIdUsuarioAdm){
		boolean bResult = true;
		List<Partido> listaPartidos = new ArrayList<Partido>();
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db == null)
        {
        	Log.e("PartidoDB:partSelectAllPartidoByUsrAdm", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
        //Si hemos abierto correctamente la base de datos
        if(bResult == true)	
        {        	        
        	SQLiteQueryBuilder qbQuery = new SQLiteQueryBuilder();
        	
        	//Asignamos las tablas que se acceden
        	qbQuery.setTables(
        			FulbitoSQLiteHelper.TABLA_PARTIDO + 
        			" LEFT JOIN " + 
    				FulbitoSQLiteHelper.TABLA_USUARIO + 
    				" ON (" +
    				FulbitoSQLiteHelper.TABLA_PARTIDO + "." +FulbitoSQLiteHelper.PARTIDO_ID_USR_ADM +
    				" = " + 
    				FulbitoSQLiteHelper.TABLA_USUARIO + "." +FulbitoSQLiteHelper.USUARIO_ID +
    				")"
    			);
        	
        	//Analizar si es necesario obtener los datos opcionales del usuario, en ese caso deberiamos agregar 
        	//un JOIN mas
        	String sCampos[] = {
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_ID,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_NOMBRE,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_FECHA,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_HORA,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_LUGAR,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_CANT_JUG,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_ID_USR_ADM,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_ALIAS,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_EMAIL,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_FOTO
        		};
        	
        	String FILTROS = FulbitoSQLiteHelper.TABLA_PARTIDO + 
        			"." +
        			FulbitoSQLiteHelper.PARTIDO_ID_USR_ADM + 
    				" = " + 
    				Integer.toString(iIdUsuarioAdm);
        	
        	Cursor cursor = qbQuery.query(db, sCampos, FILTROS, null, null, null, null);                	
        	
        	if (cursor != null)
        	{
        		if(cursor .moveToFirst()) 
        		{
                    while (cursor.isAfterLast() == false) 
                    {
                    	Partido partido = new Partido();
                    	
                    	//Asignamos los datos del partido
                		partido.setId(cursor.getInt(0));
                		partido.setNombre(cursor.getString(1));
                		partido.setFecha(cursor.getString(2));
                		partido.setHora(cursor.getInt(3));
                		partido.setLugar(cursor.getString(4));
                		partido.setCantJugadores(cursor.getInt(5));
                		//Asignamos los datos del usuario administrados del partido        		
                		partido.getUsuarioAdm().setId(cursor.getInt(6));
                		partido.getUsuarioAdm().setAlias(cursor.getString(7));
                		partido.getUsuarioAdm().setEmail(cursor.getString(8));                
                		partido.getUsuarioAdm().setFoto(cursor.getString(9));
                    	
                        listaPartidos.add(partido);
                        cursor.moveToNext();
                    }
                }
                
                cursor.close();
        	}
        	else
        	{
        		listaPartidos = null;
        	}
                    	
            //Cerramos la base de datos
            db.close();
 
        }

        return listaPartidos;
	}

	//Metodo para obtener todos los registros de la tabla PARTIDO
	public List<Partido> partSelectAllPartido(){
		boolean bResult = true;
		List<Partido> listaPartidos = new ArrayList<Partido>();
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db == null)
        {
        	Log.e("PartidoDB:partSelectAllPartido", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
        //Si hemos abierto correctamente la base de datos
        if(bResult == true)	
        {        	        
        	SQLiteQueryBuilder qbQuery = new SQLiteQueryBuilder();
        	
        	//Asignamos las tablas que se acceden
        	qbQuery.setTables(
        			FulbitoSQLiteHelper.TABLA_PARTIDO + 
        			" LEFT JOIN " + 
    				FulbitoSQLiteHelper.TABLA_USUARIO + 
    				" ON (" +
    				FulbitoSQLiteHelper.TABLA_PARTIDO + "." +FulbitoSQLiteHelper.PARTIDO_ID_USR_ADM +
    				" = " + 
    				FulbitoSQLiteHelper.TABLA_USUARIO + "." +FulbitoSQLiteHelper.USUARIO_ID +
    				")"
    			);
        	
        	//Analizar si es necesario obtener los datos opcionales del usuario, en ese caso deberiamos agregar 
        	//un JOIN mas
        	String sCampos[] = {
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_ID,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_NOMBRE,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_FECHA,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_HORA,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_LUGAR,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_CANT_JUG,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_ID_USR_ADM,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_ALIAS,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_EMAIL,
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_FOTO
        		};	        		        
        	
        	Cursor cursor = qbQuery.query(db, sCampos, null, null, null, null, null);                	
        	
        	if (cursor != null)
        	{
        		if(cursor .moveToFirst()) 
        		{
                    while (cursor.isAfterLast() == false) 
                    {
                    	Partido partido = new Partido();
                    	
                    	//Asignamos los datos del partido
                		partido.setId(cursor.getInt(0));
                		partido.setNombre(cursor.getString(1));
                		partido.setFecha(cursor.getString(2));
                		partido.setHora(cursor.getInt(3));
                		partido.setLugar(cursor.getString(4));
                		partido.setCantJugadores(cursor.getInt(5));
                		//Asignamos los datos del usuario administrados del partido        		
                		partido.getUsuarioAdm().setId(cursor.getInt(6));
                		partido.getUsuarioAdm().setAlias(cursor.getString(7));
                		partido.getUsuarioAdm().setEmail(cursor.getString(8));                
                		partido.getUsuarioAdm().setFoto(cursor.getString(9));
                    	
                        listaPartidos.add(partido);
                        cursor.moveToNext();
                    }
                }
                
                cursor.close();
        	}
        	else
        	{
        		listaPartidos = null;
        	}
                    	
            //Cerramos la base de datos
            db.close();
 
        }

        return listaPartidos;
	}
}
