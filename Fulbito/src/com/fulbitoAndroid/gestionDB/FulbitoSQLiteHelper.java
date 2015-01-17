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
    /////////////////////////////////////////////////////////////
    // TABLA USUARIO
    static final String TABLA_USUARIO = "usuario";
    // CAMPOS TABLA USUARIO
    static final String USUARIO_ID 			= "id";
    static final String USUARIO_ALIAS 		= "alias";
    static final String USUARIO_EMAIL 		= "email";
    static final String USUARIO_PASSWORD	= "password";
    static final String USUARIO_FOTO 		= "foto";
	/////////////////////////////////////////////////////////////
    // TABLA DATOS_OPC_USUARIO
    static final String TABLA_DAT_OPC_USR = "datos_opc_usuario";
    // CAMPOS TABLA USUARIO
    static final String DAT_OPC_USR_ID 			= "id_usuario";
    static final String DAT_OPC_USR_FECHA_NAC	= "fecha_nacimiento";
    static final String DAT_OPC_USR_UBICACION	= "ubicacion";
    static final String DAT_OPC_USR_LATITUD		= "latitud";
    static final String DAT_OPC_USR_LONGITUD 	= "longitud";
    static final String DAT_OPC_USR_SEXO		= "sexo";
    static final String DAT_OPC_USR_TELEFONO	= "telefono";
    static final String DAT_OPC_USR_RADIO_BUSQ	= "radio_busq_partido";
	/////////////////////////////////////////////////////////////
    // TABLA PARTIDO
    static final String TABLA_PARTIDO = "partido";
    // CAMPOS TABLA PARTIDO
    static final String PARTIDO_ID 			= "id";
    static final String PARTIDO_NOMBRE 		= "nombre";
    static final String PARTIDO_FECHA 		= "fecha";
    static final String PARTIDO_HORA		= "hora";
    static final String PARTIDO_CANT_JUG	= "cant_jugadores";
    static final String PARTIDO_LUGAR		= "lugar";
    static final String PARTIDO_ID_USR_ADM	= "id_usuario_adm";
	/////////////////////////////////////////////////////////////    
    // TABLA TIPO_PARTIDO
    static final String TABLA_TIPO_PARTIDO = "tipo_partido";
    // CAMPOS TABLA TIPO_PARTIDO
    static final String TIPO_PARTIDO_ID 	= "id";
    static final String TIPO_PARTIDO_DESC 	= "descripcion";
	/////////////////////////////////////////////////////////////
    // TABLA TIPO_VISIBILIDAD
    static final String TABLA_TIPO_VISIBILIDAD = "tipo_visibilidad";
    // CAMPOS TABLA TIPO_VISIBILIDAD
    static final String TIPO_VISIBILIDAD_ID 	= "id";
    static final String TIPO_VISIBILIDAD_DESC 	= "descripcion";
	/////////////////////////////////////////////////////////////
    // TABLA PARTIDO_AMISTOSO
    static final String TABLA_PART_AMISTOSO = "partido_amistoso";
    // CAMPOS TABLA PARTIDO_AMISTOSO
    static final String PART_AMISTOSO_PART_ID 			= "id_partido";
    static final String PART_AMISTOSO_TIPO_SELEC 	= "id_tipo_seleccion";
	/////////////////////////////////////////////////////////////
    // TABLA TIPO_SELECCION
    static final String TABLA_TIPO_SELECCION = "tipo_seleccion";
    // CAMPOS TABLA PARTIDO_AMISTOSO
    static final String TIPO_SELECCION_ID 		= "id_partido";
    static final String TIPO_SELECCION_DESC 	= "id_tipo_seleccion";
	/////////////////////////////////////////////////////////////
    // TABLA PARTIDO_AMISTOSO_PAN_QUESO
    static final String TABLA_PAN_QUESO = "partido_amistoso_pan_queso";
    // CAMPOS TABLA PARTIDO_AMISTOSO
    static final String PAN_QUESO_ID 		= "id_partido";
    static final String PAN_QUESO_JUG_1 	= "id_usuario_selec_1";
    static final String PAN_QUESO_JUG_2 	= "id_usuario_selec_2";
	/////////////////////////////////////////////////////////////
    // TABLA JUGADOR
	static final String TABLA_JUGADOR = "jugador";
	// CAMPOS TABLA JUGADOR
	static final String JUGADOR_ID_USUARIO	= "id_usuario";
	static final String JUGADOR_ID_PARTIDO 	= "id_partido";
	static final String JUGADOR_ID_EQUIPO 	= "id_equipo";
	static final String JUGADOR_GOLES 		= "goles";
	/////////////////////////////////////////////////////////////
	// TABLA PARTIDO_DESAFIO_USUARIO
	static final String TABLA_DESAFIO_USR = "partido_desafio_usuario";
	// CAMPOS TABLA JUGADOR
	static final String DESAFIO_USR_ID_PARTIDO			= "id_partido";
	static final String DESAFIO_USR_ID_USR_DESAFIADO 	= "id_usuario_desafiado";
	static final String DESAFIO_USR_ID_USR_DESAFIANTE 	= "id_usuario_desafiante";
	/////////////////////////////////////////////////////////////
	// TABLA PARTIDO_DESAFIO_EQUIPO
	static final String TABLA_DESAFIO_EQ = "partido_desafio_equipo";
	// CAMPOS TABLA JUGADOR
	static final String DESAFIO_EQ_ID_PARTIDO	= "id_partido";
		/////////////////////////////////////////////////////////////
	//Sentencia SQL para crear la tabla USUARIO
    private static final String S_CREATE_USUARIO = 
    		"CREATE TABLE " + TABLA_USUARIO + 
    		" ( " +
    			USUARIO_ID 			+ "	INTEGER, " +
    			USUARIO_ALIAS 		+ "	TEXT, " +
    			USUARIO_EMAIL 		+ "	TEXT, " +
    			USUARIO_PASSWORD 	+ "	TEXT, " +
    			USUARIO_FOTO 		+ "	TEXT, " +
    			"PRIMARY KEY (" + USUARIO_ID + ") " +
    		" ) ";
    //Sentencia SQL para crear la tabla DATOS_OPC_USUARIO
    private static final String S_CREATE_DAT_OPC_USR = 
    		"CREATE TABLE " + TABLA_DAT_OPC_USR + 
    		" ( " +
    			DAT_OPC_USR_ID 			+ "	INTEGER, " +
    			DAT_OPC_USR_FECHA_NAC	+ "	TEXT, " +
    			DAT_OPC_USR_UBICACION	+ "	TEXT, " +
    			DAT_OPC_USR_LATITUD 	+ "	TEXT, " +
    			DAT_OPC_USR_LONGITUD	+ "	TEXT," +
    			DAT_OPC_USR_SEXO		+ "	TEXT," +
    			DAT_OPC_USR_TELEFONO	+ "	TEXT," +
    			DAT_OPC_USR_RADIO_BUSQ	+ "	INTEGER, " +
    			"PRIMARY KEY (" + DAT_OPC_USR_ID + "), " +
    			"FOREIGN KEY(" + DAT_OPC_USR_ID +") REFERENCES " + TABLA_USUARIO + "(" + USUARIO_ID + ")" +
    		" ) ";
    //Sentencia SQL para crear la tabla PARTIDO
    private static final String S_CREATE_PARTIDO = 
    		"CREATE TABLE " + TABLA_PARTIDO + 
    		" ( " +
    			PARTIDO_ID 			+ "	INTEGER, " +
    			PARTIDO_NOMBRE 		+ "	TEXT, " +
    			PARTIDO_FECHA 		+ "	TEXT, " +
    			//PARTIDO_HORA		+ "	INTEGER, " +
    			PARTIDO_HORA		+ "	TEXT, " +
    			PARTIDO_LUGAR		+ "	TEXT, " +
    			PARTIDO_CANT_JUG 	+ "	INTEGER, " +
    			PARTIDO_ID_USR_ADM	+ "	INTEGER, " +
    			"PRIMARY KEY (" + PARTIDO_ID + "), " +
    			"FOREIGN KEY(" + PARTIDO_ID_USR_ADM +") REFERENCES " + TABLA_USUARIO + "(" + USUARIO_ID + ")" +
    		" ) ";
    
    //Sentencia SQL para dropear la tabla de usuario
    private static final String S_DROP_USUARIO 		= "DROP TABLE IF EXISTS " + TABLA_USUARIO;
    private static final String S_DROP_DAT_OPC_USR 	= "DROP TABLE IF EXISTS " + TABLA_DAT_OPC_USR;
    private static final String S_DROP_PARTIDO 		= "DROP TABLE IF EXISTS " + TABLA_PARTIDO;
 
    public FulbitoSQLiteHelper(Context contexto) {
        super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(S_CREATE_USUARIO);
        db.execSQL(S_CREATE_DAT_OPC_USR);
        db.execSQL(S_CREATE_PARTIDO);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.
 
        //Se eliminan las versiones anteriores de las tablas
        db.execSQL(S_DROP_USUARIO);
        db.execSQL(S_DROP_DAT_OPC_USR);
        db.execSQL(S_DROP_PARTIDO);
 
        //Se crean las nuevas versiones de las tablas
        db.execSQL(S_CREATE_USUARIO);
        db.execSQL(S_CREATE_DAT_OPC_USR);
        db.execSQL(S_CREATE_PARTIDO);
    }
}
