package com.fulbitoAndroid.herramientas;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.fulbitoAndroid.clases.Usuario;

public class CoDecJSON {
	
	//constructor
	public CoDecJSON(){}
	
	//Obtiene la respuesta del webservice
	public String sDecodificarRespuesta(String sRespuestaJSON)
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
			// TODO Auto-generated catch block
			Log.e("CoDecJSON::vDecodificarRespuesta", e.getMessage());
		}
		
		return sError;
	}
	
	//Obtiene la data del webservice
	public String sDecodificarData(String sRespuestaJSON)
	{
		JSONObject jsonRespuesta;
		String sData = "";
		
		//Obtenemos la data del Webservice
		try 
		{
			jsonRespuesta = new JSONObject(sRespuestaJSON);
			sData 	= jsonRespuesta.getString("data");
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			Log.e("CoDecJSON::sDecodificarData", e.getMessage());
		}
		
		return sData;
	}
	
	//Recibe un objeto Usuario y codifica el JSON que necesita el WebService de Login
	public JSONObject jsonCodificarJSON_Login(Usuario cUsrLogin)
	{
		JSONObject cJSON_UsrLogin = new JSONObject();
		try
		{
			cJSON_UsrLogin.put("correo", cUsrLogin.getEmail());
			cJSON_UsrLogin.put("clave", cUsrLogin.getPassword());
		}
		catch(JSONException e){
			Log.e("CoDecJSON::CodificarJSON_Login", e.getMessage());
		}		
							
		return cJSON_UsrLogin;
	}
	
	//Decodifica los datos de usuario que recibimos del WebService de Login
	public Usuario usrDecodificarJSON_Login(String sData)
	{
		JSONObject usuarioJSON;
		String sId;
		String sAlias;
		String sCorreo;
		Usuario usrUsuarioLogin = new Usuario();
		
		try 
		{
			usuarioJSON = new JSONObject(sData);
			//Parseamos los datos del usuario
			sId = usuarioJSON.getString("id");
			sAlias = usuarioJSON.getString("alias");
			sCorreo = usuarioJSON.getString("mail");

			usrUsuarioLogin.setId(Integer.parseInt(sId));
			usrUsuarioLogin.setAlias(sAlias);
			usrUsuarioLogin.setEmail(sCorreo);
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		return usrUsuarioLogin;
	}
}
