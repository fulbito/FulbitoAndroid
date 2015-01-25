package com.fulbitoAndroid.gestionDB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.fulbitoAndroid.clases.Partido;
import com.fulbitoAndroid.clases.PartidoAmistoso;

public class PartidoAmistosoDB extends PartidoDB{
	
	public PartidoAmistosoDB() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public boolean bInsertar(PartidoAmistoso partido){		
		boolean bResult = true;
		
        //Insertamos el partido
    	Partido aux = partido;
    	bResult = bInsertarPartido(aux);
        
        if(bResult == true)
        	bResult = bInsertarPartidoAmistoso(partido);

        return bResult;
	}
	
	public boolean bUpdate(PartidoAmistoso partido){		
		boolean bResult = true;
		
        //Updateamos el partido
    	Partido aux = partido;
    	bResult = bUpdatePartido(aux);
        
        if(bResult == true)
        	bResult = bUpdatePartidoAmistoso(partido);

        return bResult;
	}
	
	public boolean bDeleteById(int iIdPartido){		
		boolean bResult = true;
		
        //Updateamos el partido
    	bResult = bDeletePartidoAmistosoById(iIdPartido);
        
        if(bResult == true)
        	bResult = bDeletePartidoById(iIdPartido);

        return bResult;
	}
	
    //Metodo para insertar un registro en la tabla PARTIDO_AMISTOSO
	public boolean bInsertarPartidoAmistoso(PartidoAmistoso partido){		
		boolean bResult = true;
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db == null)
        {
        	Log.e("PartidoAmistosoDB:bInsertarPartidoAmistoso", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
        //Realizamos el insert
        if(bResult == true)
        {
        	ContentValues values = new ContentValues();
        	        	
        	values.put(FulbitoSQLiteHelper.PART_AMISTOSO_PART_ID, partido.getId());
        	values.put(FulbitoSQLiteHelper.PART_AMISTOSO_TIPO_SELEC, partido.getTipoSeleccion());

        	try{        	
        		db.insertOrThrow(FulbitoSQLiteHelper.TABLA_PART_AMISTOSO, null, values);
        	}
        	catch(SQLiteConstraintException e)
        	{
        		//Fallo el insert por Constraint PRIMARY KEY, entonces hacemos un update
        		bResult = bUpdatePartidoAmistoso(partido);
        	}
        	catch(SQLException e)
        	{
        		Log.e("PartidoAmistosoDB:bInsertarPartidoAmistoso", 
        				"Error al insertar en tabla [" + FulbitoSQLiteHelper.TABLA_PART_AMISTOSO + "]" +
        				e.getMessage()
        		);
        		bResult = false;
        	}
        	
            //Cerramos la base de datos
            db.close();
        }

        return bResult;
	}
	
	//Metodo para updatear un registro en la tabla PARTIDO_AMISTOSO
	public boolean bUpdatePartidoAmistoso(PartidoAmistoso partido){
		
		boolean bResult = true;
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db == null)
        {
        	Log.e("PartidoAmistosoDB:bUpdatePartidoAmistoso", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
        //Realizamos el insert
        if(bResult == true)
        {
        	ContentValues values = new ContentValues();
            
        	values.put(FulbitoSQLiteHelper.PART_AMISTOSO_TIPO_SELEC, partido.getTipoSeleccion());
        	
        	String FILTROS = FulbitoSQLiteHelper.PART_AMISTOSO_PART_ID + " = " + Integer.toString(partido.getId());
        	
        	db.update(FulbitoSQLiteHelper.TABLA_PART_AMISTOSO, values, FILTROS, null);        	

            //Cerramos la base de datos
            db.close();
        }

        return bResult;
	}
		
	//Metodo para eliminar un registro de la tabla PARTIDO_AMISTOSO filtrando por ID
	public boolean bDeletePartidoAmistosoById(int iIdPartido){
		boolean bResult = true;
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db == null)
        {
        	Log.e("PartidoAmistosoDB:bDeletePartidoById", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
        //Si hemos abierto correctamente la base de datos
        if(bResult == true)
        {        
        	String FILTROS = FulbitoSQLiteHelper.PART_AMISTOSO_PART_ID + " = " + Integer.toString(iIdPartido);
        	
        	db.delete(FulbitoSQLiteHelper.TABLA_PART_AMISTOSO, FILTROS, null);
			
			//Cerramos la base de datos
			db.close(); 
        }

        return bResult;
	}
	
	//Metodo para obtener un registro de la tabla PARTIDO filtrando por ID
	public PartidoAmistoso parSelectPartidoAmistosoById(int iIdPartido){
		boolean bResult = true;
		
		PartidoAmistoso partido = new PartidoAmistoso();
		
		//Obtenemos el acceso a la base de datos
		SQLiteDatabase db = dbHelper.getWritableDatabase();
        if(db == null)
        {
        	Log.e("PartidoAmistosoDB:usrSelectPartidoAmistosoById", "La base de datos no fue abierta correctamente");
        	bResult = false;
        }
        
      //Si hemos abierto correctamente la base de datos
        if(bResult == true)
        {
        	SQLiteQueryBuilder qbQuery = new SQLiteQueryBuilder();
        	
        	//Asignamos las tablas que se acceden
        	qbQuery.setTables(
        			FulbitoSQLiteHelper.TABLA_PARTIDO + 
        			" INNER JOIN " + 
    				FulbitoSQLiteHelper.TABLA_PART_AMISTOSO + 
    				" ON (" +
    				FulbitoSQLiteHelper.TABLA_PARTIDO + "." +FulbitoSQLiteHelper.PARTIDO_ID +
    				" = " + 
    				FulbitoSQLiteHelper.TABLA_PART_AMISTOSO + "." +FulbitoSQLiteHelper.PART_AMISTOSO_PART_ID +
    				")" +
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
        			FulbitoSQLiteHelper.TABLA_USUARIO + "." + FulbitoSQLiteHelper.USUARIO_FOTO,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_ID_TIPO_PART,
        			FulbitoSQLiteHelper.TABLA_PARTIDO + "." + FulbitoSQLiteHelper.PARTIDO_ID_TIPO_VISIB,
        			FulbitoSQLiteHelper.TABLA_PART_AMISTOSO + "." + FulbitoSQLiteHelper.PART_AMISTOSO_TIPO_SELEC
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
        		//partido.setHora(cursor.getInt(3));
        		partido.setHora(cursor.getString(3));
        		partido.setLugar(cursor.getString(4));
        		partido.setCantJugadores(cursor.getInt(5));
        		//Asignamos los datos del usuario administrados del partido        		
        		partido.getUsuarioAdm().setId(cursor.getInt(6));
        		partido.getUsuarioAdm().setAlias(cursor.getString(7));
        		partido.getUsuarioAdm().setEmail(cursor.getString(8));                
        		partido.getUsuarioAdm().setFoto(cursor.getString(9));
        		
        		partido.setTipoPartido(cursor.getInt(10));
        		partido.setTipoVisibilidad(cursor.getInt(11));        		
        		partido.setTipoSeleccion(cursor.getInt(12));
                
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

}
