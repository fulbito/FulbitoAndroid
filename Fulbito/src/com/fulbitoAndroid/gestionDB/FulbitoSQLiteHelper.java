package com.fulbitoAndroid.gestionDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FulbitoSQLiteHelper extends SQLiteOpenHelper {
 	
	// Logcat tag
    private static final String LOG = "FulbitoSQLiteHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "FulbitoDB";
    
    //Sentencia SQL para crear la tabla de usuario
    private static final String S_CREATE_USUARIO = "CREATE TABLE usuario (id INTEGER, alias TEXT, email TEXT, foto TEXT, password TEXT)";
    //Sentencia SQL para dropear la tabla de usuario
    private static final String S_DROP_USUARIO = "DROP TABLE IF EXISTS usuario";
 
    public FulbitoSQLiteHelper(Context contexto) {
        super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creaci�n de la tabla
        db.execSQL(S_CREATE_USUARIO);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aqu� utilizamos directamente la opci�n de
        //      eliminar la tabla anterior y crearla de nuevo vac�a con el nuevo formato.
        //      Sin embargo lo normal ser� que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este m�todo deber�a ser m�s elaborado.
 
        //Se elimina la versi�n anterior de la tabla
        db.execSQL(S_DROP_USUARIO);
 
        //Se crea la nueva versi�n de la tabla
        db.execSQL(S_CREATE_USUARIO);
    }
}
