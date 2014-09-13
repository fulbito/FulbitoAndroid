package com.fulbitoAndroid.fulbito;

import java.util.List;

import org.apache.http.NameValuePair;

import com.fulbitoAndroid.herramientas.CoDecJSON;
import com.fulbitoAndroid.herramientas.RespuestaWebService;
import com.fulbitoAndroid.herramientas.WebService;

public class WebServiceFulbito {
	//Definicion de constantes de los webservices de fulbito
	protected static final String S_WEBSERVICE_URL 			= "http://www.fulbitoweb.com.ar/index.php/";
	protected static final String S_RESP_ERROR 				= "true";
	protected static final String S_RESP_OK 				= "false";		
	
	private String sWebService = "";
	
	protected void vSetWebservice(String sWebService){
		this.sWebService = sWebService;
	}
	
	protected void vEjecutarWebservice(List<NameValuePair> listaParametros, RespuestaWebService cRespWS){
		//Invocamos el WebService en modo POST   	
    	WebService webService = new WebService(S_WEBSERVICE_URL);
    	String sRespuesta = webService.sWebPost(this.sWebService, listaParametros);

		//Invocamos el WebService en modo GET
		//String sRespuesta = webService.sWebGet(getString(R.string.webservice_login), listaParametros);
    	
    	//Obtenemos la respueta JSON del WebService
    	CoDecJSON cCodJSON = new CoDecJSON();
    	
    	cRespWS.vSetError(cCodJSON.sDecodificarRespuesta(sRespuesta));
    	cRespWS.vSetData(cCodJSON.sDecodificarData(sRespuesta));
	}
	
}
