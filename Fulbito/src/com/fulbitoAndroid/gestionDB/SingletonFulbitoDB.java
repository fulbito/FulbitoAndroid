package com.fulbitoAndroid.gestionDB;

import com.fulbitoAndroid.gestionDB.FulbitoSQLiteHelper;

import android.content.Context;

public class SingletonFulbitoDB {

	private static FulbitoSQLiteHelper bd;     
   
	public static void initInstance(Context contexto)
	{
		if (bd == null)
		{
			//Creamos la instancia
			bd = new FulbitoSQLiteHelper(contexto);		
		}
	}

	public static FulbitoSQLiteHelper getInstance()
	{
		//Retornamos la instancia
		return bd;
	}
 /*
    public Cursor ejecutarConsulta(String sNombreTabla, String[] sArrayCampos, String sArrayFiltros, String[] sArrayValoresFiltros) {    	
    	Cursor cursor = bd.query(sNombreTabla, sArrayCampos, sArrayFiltros, sArrayValoresFiltros, null, null, null, null);    	
    	return cursor;
    }
    
    public Cursor ejecutarConsulta(String sQuery) {
    	Cursor cursor = bd.rawQuery(sQuery, null);    	
    	return cursor;
    }
    */
}
