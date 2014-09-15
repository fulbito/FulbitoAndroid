package com.fulbitoAndroid.clases;

import android.content.Context;
import android.content.SharedPreferences;

public class SingletonUsuarioLogueado {
	private static Usuario usrLogueado;     
	
	private static final String SH_PREF_NOMBRE 			= "InfoUsuario";
	private static final String KEY_ID 					= "ID";
	private static final String KEY_ALIAS 				= "ALIAS";
	private static final String KEY_EMAIL 				= "EMAIL";
	private static final String KEY_PASSWORD 			= "PASSWORD";
	private static final String KEY_PATH_FOTO 			= "PATH_FOTO";
	private static final String KEY_FECHA_NACIMIENTO 	= "FECHA_NACIMIENTO";
	private static final String KEY_UBICACION_DESC 		= "UBICACION_DESC";
	private static final String KEY_UBICACION_LAT 		= "UBICACION_LAT";
	private static final String KEY_UBICACION_LONG 		= "UBICACION_LONG";
	private static final String KEY_SEXO 				= "SEXO";
	private static final String KEY_TELEFONO 			= "TELEFONO";
	private static final String KEY_RADIO_BUSQUEDA 		= "RADIO_BUSQUEDA";	
	   
	public static void initInstance()
	{
		if (usrLogueado == null)
		{
			//Creamos la instancia
			usrLogueado = new Usuario();		
		}
	}

	public static Usuario getUsuarioLogueado(Context context)
	{
		SharedPreferences prefs =
			     context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
			 
		int iId = prefs.getInt(KEY_ID, -1);
		
		if(iId != -1)
		{
			usrLogueado.setId(iId);
			usrLogueado.setAlias(prefs.getString(KEY_ALIAS, ""));
			usrLogueado.setEmail(prefs.getString(KEY_EMAIL, ""));
			usrLogueado.setPassword(prefs.getString(KEY_PASSWORD, ""));
			usrLogueado.setFoto(prefs.getString(KEY_PATH_FOTO, ""));			
			usrLogueado.setUbicacion(prefs.getString(KEY_UBICACION_DESC, ""));
			usrLogueado.setUbicacionLatitud(prefs.getString(KEY_UBICACION_LAT, ""));
			usrLogueado.setUbicacionLongitud(prefs.getString(KEY_UBICACION_LONG, ""));
			usrLogueado.setFechaNacimiento(prefs.getString(KEY_FECHA_NACIMIENTO, ""));
			usrLogueado.setRadioBusqueda(prefs.getFloat(KEY_RADIO_BUSQUEDA, 0));
			usrLogueado.setSexo(prefs.getString(KEY_SEXO, ""));
			usrLogueado.setTelefono(prefs.getString(KEY_TELEFONO, ""));

			return usrLogueado;			
		}
		else
		{
			return null;
		}
	}
	
	public static void registrarUsuarioLogueado(Usuario usrUsuario, Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(KEY_ID, usrUsuario.getId());
		editor.putString(KEY_ALIAS, usrUsuario.getAlias());
		editor.putString(KEY_EMAIL, usrUsuario.getEmail());
		editor.putString(KEY_PASSWORD, usrUsuario.getPassword());
		editor.putString(KEY_PATH_FOTO, usrUsuario.getFoto());		
		editor.putString(KEY_UBICACION_DESC, usrUsuario.getUbicacion());
		editor.putString(KEY_UBICACION_LAT, usrUsuario.getUbicacionLatitud());
		editor.putString(KEY_UBICACION_LONG, usrUsuario.getUbicacionLongitud());
		editor.putString(KEY_FECHA_NACIMIENTO, usrUsuario.getFechaNacimiento());
		editor.putFloat(KEY_RADIO_BUSQUEDA, usrUsuario.getRadioBusqueda());
		editor.putString(KEY_SEXO, usrUsuario.getSexo());
		editor.putString(KEY_TELEFONO, usrUsuario.getTelefono());

		editor.commit();		
	}
	
	public static void eliminarUsuarioLogueado(Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(KEY_ID, -1);
		editor.putString(KEY_ALIAS, "");
		editor.putString(KEY_EMAIL, "");
		editor.putString(KEY_PASSWORD, "");
		editor.putString(KEY_PATH_FOTO, "");
		editor.putString(KEY_UBICACION_DESC, "");
		editor.putString(KEY_UBICACION_LAT, "");
		editor.putString(KEY_UBICACION_LONG, "");
		editor.putString(KEY_FECHA_NACIMIENTO, "");
		editor.putFloat(KEY_RADIO_BUSQUEDA, 0);
		editor.putString(KEY_SEXO, "");
		editor.putString(KEY_TELEFONO, "");
		
		editor.commit();		
	}
	
	public static void modificarAlias(String sAlias, Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(KEY_ALIAS, sAlias);
		editor.commit();		
	}
	
	public static void modificarEmail(String sEmail, Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(KEY_EMAIL, sEmail);
		editor.commit();		
	}
	
	public static void modificarPassword(String sPassword, Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(KEY_PASSWORD, sPassword);
		editor.commit();		
	}
	
	public static void modificarPathFoto(String sPathFoto, Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(KEY_PATH_FOTO, sPathFoto);
		editor.commit();		
	}

	public static void modificarUbicacionDesc(String sUbicacionDesc, Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(SH_PREF_NOMBRE, Context.MODE_PRIVATE);
		
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(KEY_UBICACION_DESC, sUbicacionDesc);
		editor.commit();		
	}
}
