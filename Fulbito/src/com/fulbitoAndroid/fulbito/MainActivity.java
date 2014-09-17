/* ----------------------------------------------------------------------------- 
Nombre: 		MainActivity
Descripción:	Clase que lanza el proceso de inicialización de la 
				aplicación mientras muestra un splash de la aplicación

Log de modificaciones:

Fecha		Autor		Descripción
23/03/2014	MAC			Creación
----------------------------------------------------------------------------- */

package com.fulbitoAndroid.fulbito;

import com.fulbitoAndroid.admUsuario.WebServiceLogin;
import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.WebServiceFulbito.Result;
import com.fulbitoAndroid.herramientas.RespuestaWebService;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
		
	
	static final String TAG="MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Se lanza el splash de la aplicacion mientras se realiza la inicialización en el 
		//AsyncTask InitFulbito
		setContentView(R.layout.activity_main);
		
		Log.d(TAG,"En el OnCreate");
		
		//Se lanza la inicialización de la aplicación				
		new InitFulbito().execute();				               
	}
	
	private class InitFulbito extends AsyncTask<Void, Void, Void> {

		private Usuario usrLogueado;
		private int iAccionSiguiente = 0;
		private String sError = "";		
		Long lMilisegundosInicio;
		Long lMilisegundosFin;
		private static final int I_INICIAR_LOGIN = 1;
		private static final int I_INICIAR_HOME = 2;
		private static final int I_INICIAR_RELOGIN = 3;

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        // before making http calls         
	    }

	    @Override
	    protected Void doInBackground(Void... arg0) {
	    	//Obtenemos el tiempo al inicio de la tarea de inicializacion
	    	lMilisegundosInicio = System.currentTimeMillis();
	    	//Obtenemos los datos del usuario logueado
	    	usrLogueado = SingletonUsuarioLogueado.getUsuarioLogueado(getApplicationContext());
	    	
	    	if(usrLogueado == null)
	    	{
	    		//No hay un usuario logueado
	    		iAccionSiguiente = I_INICIAR_LOGIN;
	    	}
	    	else
	    	{
	    		//Hay usuario logueado
	    		WebServiceLogin wsLogin = new WebServiceLogin(getApplicationContext());
		    	//Se realiza el logueo automatico
		    	Usuario usrLogin = new Usuario(usrLogueado);
		    	RespuestaWebService cRespWS = new RespuestaWebService();
		    	
		    	switch(wsLogin.bLoguearUsuario(usrLogin, cRespWS))
		    	{
		    		case OK:
		    			//el logueo automatico fue exitoso
			    		iAccionSiguiente = I_INICIAR_HOME;
		    			break;
		    		case NO_CONNECTION:
		    			//logueo offline
		    			break;
		    		case ERROR:
		    			//el logueo automatico no fue exitoso
			    		sError = cRespWS.sGetData();
			    		iAccionSiguiente = I_INICIAR_RELOGIN;
		    			break;		    		
		    	}
	    	}	    			    	    

	        return null;
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);
	        
	        Intent intent = new Intent();
			
	        switch(iAccionSiguiente)
	        {
	        	case I_INICIAR_LOGIN:
	        		//No hay un usuario logueado
	        		intent = new Intent(getApplicationContext(), InicioActivity.class);		        			        	
		        	break;
	        	case I_INICIAR_HOME:
	        		//Se ejecuto el login automatico de manera exitosa
	        		intent = new Intent(getApplicationContext(), HomeActivity.class);				
					break;
	        	case I_INICIAR_RELOGIN:
	        		//El webservice envio una respuesta con error por usuario/contraseña invalido
					Toast.makeText(getApplicationContext(), 
							sError, Toast.LENGTH_LONG).show();
					intent = new Intent(getApplicationContext(), InicioActivity.class);		        		        	
		        	break;
	        }
	        
	        lMilisegundosFin = System.currentTimeMillis();
	        
	        long lTotalMilisegundos = lMilisegundosFin - lMilisegundosInicio;
	        
	        //Bloque de codigo para asegurar que el tiempo minimo del splash activo se de 3000 milisegundos
	        if(lTotalMilisegundos < 3000)
	        {
	        	try 
	        	{
					Thread.sleep(3000 - lTotalMilisegundos);					
				} 
	        	catch (InterruptedException e) 
	        	{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }	        
	        	        
	        startActivity(intent);
	        finish();	        	           
	    }

	}
}
