package com.fulbitoAndroid.fulbito;

import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fulbitoAndroid.herramientas.CoDecJSON;
import com.fulbitoAndroid.herramientas.RespuestaWebService;
import com.fulbitoAndroid.herramientas.WebService;

public class WebServiceFulbito {
	//Definicion de constantes de los webservices de fulbito
	protected static final String S_WEBSERVICE_URL 			= "http://www.fulbitoweb.com.ar/index.php/";
	protected static final String S_RESP_ERROR 				= "true";
	protected static final String S_RESP_OK 				= "false";		
	
	private String sWebService = "";
	private Context context;
	
	public static enum Result {
		   NO_CONNECTION,
		   OK,
		   ERROR
	}
	
	public WebServiceFulbito(Context context){
		this.context = context;
	}
	
	protected void vSetWebservice(String sWebService){
		this.sWebService = sWebService;
	}
	
	protected void vEjecutarWebservice(List<NameValuePair> listaParametros, RespuestaWebService cRespWS) throws FulbitoException{
		//Invocamos el WebService en modo POST   	
    	WebService webService = new WebService(S_WEBSERVICE_URL);
    	String sRespuesta = webService.sWebPost(this.sWebService, listaParametros);

		//Invocamos el WebService en modo GET
		//String sRespuesta = webService.sWebGet(getString(R.string.webservice_login), listaParametros);
    	
    	//Sacar cuando arreglen WebService de Login (MAC)
    	sRespuesta = sRespuesta.substring(sRespuesta.indexOf("{"));
    	
    	//Obtenemos la respueta JSON del WebService
    	CoDecJSON cCodJSON = new CoDecJSON();
    	
    	cRespWS.vSetError(cCodJSON.sDecodificarRespuesta(sRespuesta));
    	cRespWS.vSetData(cCodJSON.sDecodificarData(sRespuesta));
	}
	
	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo != null && netInfo.isConnected()) 
		{
			/*if(netInfo.getTypeName () == "WIFI")
			{
				Log.d(TAG, "EXISTE CONEXIÓN - WIFI");
			}
			else
			{
				Log.d(TAG, "EXISTE CONEXIÓN - MOBILE");
			}*/
			return true;
		}

		//Log.d(TAG, "NO EXISTE CONEXIÓN");
		return false;
	}
	
}
