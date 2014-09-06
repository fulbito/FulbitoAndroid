package com.fulbitoAndroid.herramientas;

import org.json.JSONException;
import org.json.JSONObject;
import com.fulbitoAndroid.clases.Usuario;


public class CodificadorJSON {
	//constructor
	public CodificadorJSON(){}
	//Recibe un objeto Usuario y codifica el JSON que necesita el WebService de Login
	public JSONObject CodificarJSON_Login(Usuario cUsrLogin)
	{
		JSONObject cJSON_UsrLogin = new JSONObject();
		try{
			cJSON_UsrLogin.put("correo", cUsrLogin.getEmail());
			cJSON_UsrLogin.put("clave", cUsrLogin.getPassword());
		}
		catch(JSONException e){
			//Log.e("sWebPost", "JSONException : " + e);
		}		
		
					
		
		return cJSON_UsrLogin;
	}
	
}
