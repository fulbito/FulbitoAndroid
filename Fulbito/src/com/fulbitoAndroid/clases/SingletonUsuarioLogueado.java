package com.fulbitoAndroid.clases;

import com.fulbitoAndroid.gestionDB.DatOpcUsrDB;
import com.fulbitoAndroid.gestionDB.UsuarioDB;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SingletonUsuarioLogueado {
	private static Usuario usrLogueado;
	private static Context context;

	public static enum ModoConexion {
		   ONLINE,
		   OFFLINE
	}
	
	private static ModoConexion mModoConexion = ModoConexion.OFFLINE;
	
	private static final String SH_PREF_NOMBRE 			= "InfoUsuario";
	private static final String KEY_ID 					= "ID";
	private static final String KEY_EMAIL 				= "EMAIL";
	private static final String KEY_PASSWORD 			= "PASSWORD";
	private static final String KEY_MODO_CONEXION 		= "MODO_CONEXION";
	   
	public static ModoConexion getModoConexion(){
		SharedPreferences prefs =
			     context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
			 
		int iModoConexion = prefs.getInt(KEY_MODO_CONEXION, -1);
		
		switch(iModoConexion)
		{
			case 0:
				mModoConexion = ModoConexion.OFFLINE;
				break;
			case 1:
				mModoConexion = ModoConexion.ONLINE;
				break;
		}
		
		return mModoConexion;
	}
	
	public static void setModoConexion(ModoConexion modoConexion){
		int iModoConexion = 0;
		
		mModoConexion = modoConexion;	
		
		switch(modoConexion)
		{
			case OFFLINE:
				iModoConexion = 0;
				break;
			case ONLINE:
				iModoConexion = 1;
				break;
		}
		
		SharedPreferences prefs = context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(KEY_MODO_CONEXION, iModoConexion);
		editor.commit();
	}
	
	public static void initInstance(Context pContext)
	{
		if (usrLogueado == null)
		{
			//Creamos la instancia
			context = pContext;
			usrLogueado = new Usuario();
			setModoConexion(SingletonUsuarioLogueado.ModoConexion.OFFLINE);
		}
	}

	public static Usuario getUsuarioLogueado()
	{
		SharedPreferences prefs =
			     context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
			 
		int iId = prefs.getInt(KEY_ID, -1);
		
		if(iId != -1)
		{
			usrLogueado.setId(iId);
			usrLogueado.setEmail(prefs.getString(KEY_EMAIL, ""));
			usrLogueado.setPassword(prefs.getString(KEY_PASSWORD, ""));
			
			UsuarioDB usrDB = new UsuarioDB();
			usrLogueado = usrDB.usrSelectDatosUsuarioById(iId); 	
						
			return usrLogueado;			
		}
		else
		{
			return null;
		}
	}
	
	public static void registrarUsuarioLogueado(Usuario usrUsuario)
	{
		SharedPreferences prefs = context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(KEY_ID, usrUsuario.getId());
		editor.putString(KEY_EMAIL, usrUsuario.getEmail());
		editor.putString(KEY_PASSWORD, usrUsuario.getPassword());

		//Insertamos los datos del usuario logueado en la tabla USUARIO
		UsuarioDB usrDB = new UsuarioDB();
		usrDB.bInsertarUsuario(usrUsuario);
		//Insertamos los datos opcionales del usuario logueado en la tabla DATOS_OPC_USUARIO
		DatOpcUsrDB datOpcDB = new DatOpcUsrDB();
		datOpcDB.bInsertarDatOpcUsr(usrUsuario);
				
		editor.commit();		
	}
	
	public static void actualizarUsuarioLogueado(Usuario usrUsuario)
	{
		SharedPreferences prefs = context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(KEY_ID, usrUsuario.getId());
		editor.putString(KEY_EMAIL, usrUsuario.getEmail());
		editor.putString(KEY_PASSWORD, usrUsuario.getPassword());

		//Insertamos los datos del usuario logueado en la tabla USUARIO
		UsuarioDB usrDB = new UsuarioDB();
		usrDB.bUpdateUsuario(usrUsuario);
		//Insertamos los datos opcionales del usuario logueado en la tabla DATOS_OPC_USUARIO
		DatOpcUsrDB datOpcDB = new DatOpcUsrDB();
		datOpcDB.bInsertarDatOpcUsr(usrUsuario);
				
		editor.commit();		
	}
	
	public static void eliminarUsuarioLogueado()
	{
		SharedPreferences prefs = context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(KEY_ID, -1);
		editor.putString(KEY_EMAIL, "");
		editor.putString(KEY_PASSWORD, "");
		
		//evaluar la eliminación de los datos del usuario logueado en la BD
		
    	editor.commit();		
	}	
}
