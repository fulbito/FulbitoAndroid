package com.fulbitoAndroid.herramientas;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.FulbitoException;

public class CoDecJSON {
	protected static final String S_NULL 				= "null";
	//constructor
	public CoDecJSON(){}
	
	//Obtiene la respuesta del webservice
	public String sDecodificarRespuesta(String sRespuestaJSON) throws FulbitoException
	{
		JSONObject jsonRespuesta;
		String sError = "";
		
		//Obtenemos la respuesta del Webservice
		try 
		{
			jsonRespuesta = new JSONObject(sRespuestaJSON);
			sError 	= jsonRespuesta.getString("error");
		} 
		catch (JSONException e) 
		{
			//Lanzamos una excepcion
			throw new FulbitoException("CoDecJSON::sDecodificarRespuesta", e.getMessage());
		}
		
		return sError;
	}
	
	//Obtiene la data del webservice
	public String sDecodificarData(String sRespuestaJSON) throws FulbitoException
	{
		JSONObject jsonRespuesta;
		String sData = "";
		
		//Obtenemos la data del Webservice
		try 
		{
			jsonRespuesta = new JSONObject(sRespuestaJSON);
			//hay veces en caso de exito, no se manda el valor "data"
			if(jsonRespuesta.length() > 1)
				sData 	= jsonRespuesta.getString("data");
		} 
		catch (JSONException e) 
		{
			//Lanzamos una excepcion
			throw new FulbitoException("CoDecJSON::sDecodificarData", e.getMessage());
		}
		
		return sData;
	}
	
	//Recibe un objeto Usuario y codifica el JSON que necesita el WebService de Login
	public JSONObject jsonCodificarJSON_Login(Usuario cUsrLogin) throws FulbitoException
	{
		JSONObject cJSON_UsrLogin = new JSONObject();
		try
		{
			cJSON_UsrLogin.put("correo", cUsrLogin.getEmail());
			cJSON_UsrLogin.put("clave", cUsrLogin.getPassword());
		}
		catch (JSONException e) 
		{
			//Lanzamos una excepcion
			throw new FulbitoException("CoDecJSON::jsonCodificarJSON_Login", e.getMessage());
		}		
							
		return cJSON_UsrLogin;
	}
	
	//Decodifica los datos de usuario que recibimos del WebService de Login
	public Usuario usrDecodificarJSON_Login(String sData) throws FulbitoException
	{
		JSONObject usuarioJSON;
		String sId				= "";
		String sAlias			= "";
		String sCorreo			= "";
		String sRadioBusqueda	= "";
		String sTelefono		= "";
		String sSexo			= "";
		String sLongitud		= "";
		String sLatitud			= "";
		String sUbicacion		= "";
		String sFechaNacimiento = "";
		
		Usuario usrUsuarioLogin = new Usuario();
		
		try 
		{
			usuarioJSON = new JSONObject(sData);
			//Parseamos los datos del usuario
			sId = usuarioJSON.getString("id");
			sAlias = usuarioJSON.getString("alias");
			sCorreo = usuarioJSON.getString("mail");
			sRadioBusqueda = usuarioJSON.getString("radio_busqueda_partido");
			sTelefono = usuarioJSON.getString("telefono");
			sSexo = usuarioJSON.getString("sexo");
			sLongitud = usuarioJSON.getString("longitud");
			sLatitud = usuarioJSON.getString("latitud");
			sUbicacion = usuarioJSON.getString("ubicacion");
			sFechaNacimiento = usuarioJSON.getString("fecha_nacimiento");			
			
			//Asignamos los datos obtenidos a un objeto usuario
			usrUsuarioLogin.setId(Integer.parseInt(sId));
			usrUsuarioLogin.setAlias(sAlias);
			usrUsuarioLogin.setEmail(sCorreo);
			
			if(!sRadioBusqueda.equalsIgnoreCase(S_NULL))
				usrUsuarioLogin.setRadioBusqueda(Integer.parseInt(sRadioBusqueda));
			
			if(!sTelefono.equalsIgnoreCase(S_NULL))
				usrUsuarioLogin.setTelefono(sTelefono);
			
			if(!sSexo.equalsIgnoreCase(S_NULL))
				usrUsuarioLogin.setSexo(sSexo);
			
			if(!sLongitud.equalsIgnoreCase(S_NULL))
				usrUsuarioLogin.setUbicacionLongitud(sLongitud);
			
			if(!sLatitud.equalsIgnoreCase(S_NULL))
				usrUsuarioLogin.setUbicacionLatitud(sLatitud);
			
			if(!sUbicacion.equalsIgnoreCase(S_NULL))
				usrUsuarioLogin.setUbicacion(sUbicacion);
			
			if(!sFechaNacimiento.equalsIgnoreCase(S_NULL))
				usrUsuarioLogin.setFechaNacimiento(sFechaNacimiento);
		} 
		catch (JSONException e) 
		{
			//Lanzamos una excepcion
			throw new FulbitoException("CoDecJSON::jsonCodificarJSON_Login", e.getMessage());
		}	
		
		return usrUsuarioLogin;
	}
	
	//Decodifica los datos de usuario que recibimos del WebService de Login
	public Usuario usrDecodificarJSON_Registrar(String sData) throws FulbitoException
	{
		JSONObject usuarioJSON;
		String sId;
		
		Usuario usrUsuarioLogin = new Usuario();
		
		try 
		{
			usuarioJSON = new JSONObject(sData);
			//Parseamos los datos del usuario
			sId = usuarioJSON.getString("id");

			usrUsuarioLogin.setId(Integer.parseInt(sId));
		} 
		catch (JSONException e) 
		{
			//Lanzamos una excepcion
			throw new FulbitoException("CoDecJSON::jsonCodificarJSON_Login", e.getMessage());
		}	
		
		return usrUsuarioLogin;
	}
}
