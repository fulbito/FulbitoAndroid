package com.fulbitoAndroid.admUsuario;

import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ConnectTimeoutException;

import android.content.Context;
import android.util.Log;

import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.FulbitoException;
import com.fulbitoAndroid.fulbito.WebServiceFulbito;
import com.fulbitoAndroid.herramientas.CoDecJSON;
import com.fulbitoAndroid.herramientas.CodificadorNameValuePair;
import com.fulbitoAndroid.herramientas.RespuestaWebService;

public class WebServiceUsuario extends WebServiceFulbito{
		//WebService Login
		protected static final String S_WEBSERVICE_LOGIN 		= "login/ingresar";
		public static final String S_WS_LOGIN_PAR_CORREO 		= "correo";
		public static final String S_WS_LOGIN_PAR_CLAVE 		= "clave";
		//WebService Registrar
		private static final String S_WEBSERVICE_REGISTRAR		= "login/registrar_usuario";
		public static final String S_WS_REGISTRAR_PAR_ALIAS		= "alias";
		public static final String S_WS_REGISTRAR_PAR_CORREO 	= "email";
		public static final String S_WS_REGISTRAR_PAR_CLAVE 	= "password";
				
		public WebServiceUsuario(Context context){
			super(context);
		}
		
		public Result bLoguearUsuario(Usuario cUsrLogin, RespuestaWebService cRespWS) throws FulbitoException{
			
			String sError = "";
			String sData = "";
			boolean bConexion = isOnline();
			if(bConexion == false)
			{
				//No hay conexión a internet
				return Result.NO_CONNECTION;
			}
			
			//Generamos el parametro a enviar al WebService
			CodificadorNameValuePair cCodNVP = new CodificadorNameValuePair();
			List<NameValuePair> listaParametros = cCodNVP.CodificarNVP_Login(cUsrLogin);
			//Invocamos el WebService
			vSetWebservice(S_WEBSERVICE_LOGIN);
			
			try {
				vEjecutarWebservice(listaParametros, cRespWS);
			} catch (SocketTimeoutException e) {
				Log.e("WebServiceLogin::bLoguearUsuario", e.getMessage());
				return Result.NO_CONNECTION;
			} catch (ConnectTimeoutException e) {
				Log.e("WebServiceLogin::bLoguearUsuario", e.getMessage());
				return Result.NO_CONNECTION;
			}
			
			//Procesamos la respuesta
			sError = cRespWS.sGetError();
			sData = cRespWS.sGetData();
			
			if(sError.equalsIgnoreCase(S_RESP_ERROR))
			{
				//El webservice envio una respuesta con error
				String sMsjError = sObtenerMsjError(Integer.parseInt(sData));
				cRespWS.vSetData(sMsjError);
				return Result.ERROR;
			}
			else
			{
				//El webservice envio una respuesta valida con los datos del usuario logueado  
				CoDecJSON cCodJSON = new CoDecJSON();
				
				Usuario usrJSON = cCodJSON.usrDecodificarJSON_Login(sData);
				usrJSON.setPassword(cUsrLogin.getPassword());
				
				cUsrLogin.vCopiar(usrJSON);
				//Se activa el modo de conexión ONLINE
				SingletonUsuarioLogueado.setModoConexion(SingletonUsuarioLogueado.ModoConexion.ONLINE);
				
				return Result.OK;
			}
			
		}

		public Result bRegistrarUsuario(Usuario cUsrRegistrar, RespuestaWebService cRespWS) throws FulbitoException{
			
			String sError = "";
			String sData = "";
			
			boolean bConexion = isOnline();
			if(bConexion == false)
			{
				//No hay conexión a internet
				return Result.NO_CONNECTION;
			}
			
			//Generamos el parametro a enviar al WebService
			CodificadorNameValuePair cCodNVP = new CodificadorNameValuePair();
	    	List<NameValuePair> listaParametros = cCodNVP.CodificarNVP_Registrar(cUsrRegistrar);
	    	//Invocamos el WebService
	    	vSetWebservice(S_WEBSERVICE_REGISTRAR);
	    	
	    	try {
				vEjecutarWebservice(listaParametros, cRespWS);
			} catch (SocketTimeoutException e) {
				Log.e("WebServiceLogin::bRegistrarUsuario", e.getMessage());
				return Result.NO_CONNECTION;
			} catch (ConnectTimeoutException e) {
				Log.e("WebServiceLogin::bRegistrarUsuario", e.getMessage());
				return Result.NO_CONNECTION;
			}
	    	
	    	//Procesamos la respuesta
	    	sError = cRespWS.sGetError();
	    	sData = cRespWS.sGetData();
	    	
	    	if(sError.equalsIgnoreCase(S_RESP_ERROR))
			{
	    		//El webservice envio una respuesta con error
				String sMsjError = sObtenerMsjError(Integer.parseInt(sData));
				cRespWS.vSetData(sMsjError);
	    		return Result.ERROR;
			}
	    	else
			{
				//El webservice envio una respuesta valida con los datos del usuario logueado  
	    		CoDecJSON cCodJSON = new CoDecJSON();
	    		
				Usuario usrJSON = cCodJSON.usrDecodificarJSON_Registrar(sData);
				
				cUsrRegistrar.setId(usrJSON.getId());
				
				//Se activa el modo de conexión ONLINE
				SingletonUsuarioLogueado.setModoConexion(SingletonUsuarioLogueado.ModoConexion.ONLINE);
				
				return Result.OK;
			}
		}
}
