package com.fulbitoAndroid.fulbito;

import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fulbitoAndroid.herramientas.CoDecJSON;
import com.fulbitoAndroid.herramientas.RespuestaWebService;
import com.fulbitoAndroid.herramientas.WebService;

public class WebServiceFulbito {
	//Definicion de constantes de los webservices de fulbito
	protected static final String S_WEBSERVICE_URL 			= "http://www.fulbitoweb.com/index.php/";
	protected static final String S_RESP_ERROR 				= "true";
	protected static final String S_RESP_OK 				= "false";
	
	private static final String S_KEY_PARAM_ORIGEN			= "origen";
	private static final String S_VAL_PARAM_ORIGEN 			= "android";
	
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
	
	protected void vEjecutarWebservice(List<NameValuePair> listaParametros, RespuestaWebService cRespWS) 
			throws FulbitoException, SocketTimeoutException, ConnectTimeoutException{
		//Invocamos el WebService en modo POST   	
    	WebService webService = new WebService(S_WEBSERVICE_URL);
    	
    	//Le agregamos la variable para indicarle al servidor que se invoca el WS desde Android (MAC 27/09/14)
    	listaParametros.add(new BasicNameValuePair(S_KEY_PARAM_ORIGEN, S_VAL_PARAM_ORIGEN));
    	
    	String sRespuesta = webService.sWebPost(this.sWebService, listaParametros);

		//Invocamos el WebService en modo GET
		//String sRespuesta = webService.sWebGet(getString(R.string.webservice_login), listaParametros);
    	
    	//Obtenemos la respueta JSON del WebService
    	CoDecJSON cCodJSON = new CoDecJSON();
    	
    	cRespWS.vSetError(cCodJSON.sDecodificarRespuesta(sRespuesta));
    	cRespWS.vSetData(cCodJSON.sDecodificarData(sRespuesta));
	}
	
	protected String sObtenerMsjError(int iCodigoError){
		//traemos el array de mensajes de errores del archivo array.xml
		String[] arrMsjsError = this.context.getResources().getStringArray(R.array.msjs_error_webservices);
		String sMsjError = "";
		
		if(arrMsjsError.length >= iCodigoError)
			sMsjError = arrMsjsError[iCodigoError-1];
		
		return sMsjError;
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
