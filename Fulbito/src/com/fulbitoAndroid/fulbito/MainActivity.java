/* ----------------------------------------------------------------------------- 
Nombre: 		MainActivity

Descripción:	

Clase que lanza el proceso de inicialización de la aplicación mientras muestra 
un splash.
El proceso de inicialización es el siguiente:
1) Consultamos si hay registrado un usuario logueado
2) Si hay usuario logueado intentamos loguear automaticamente y se lanza el home
de la aplicación
	a) Si hay conexion a internet, se loguea en modo ONLINE, actualizando los
	datos del usuario.
	b) Si no hay conexion a internet, se loguea en modo OFFLINE
3) Si no hay usuario logueado, se lanza la inerfaz de inicio de la aplciación.
4) En caso de error de logueo, se lanza la inerfaz de inicio de la aplciación.

Log de modificaciones:

Fecha		Autor		Descripción
23/03/2014	MAC			Creación
----------------------------------------------------------------------------- */

package com.fulbitoAndroid.fulbito;

import com.fulbitoAndroid.admUsuario.WebServiceUsuario;
import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.SingletonUsuarioLogueado.ModoConexion;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.herramientas.RespuestaWebService;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
		
	Intent intent = new Intent();
	
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

		private Usuario usrLogueado 	= null;
		private int iAccionSiguiente 	= 0;
		private String sError 			= "";		
		Long lMilisegundosInicio 		= 0l;
		Long lMilisegundosFin 			= 0l;
		private static final int I_INICIAR_LOGIN 		= 1;
		private static final int I_INICIAR_HOME 		= 2;
		private static final int I_INICIAR_RELOGIN 		= 3;
		private static final int I_INICIAR_HOME_OFFLINE = 4;		

	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        //Aca debemos poner el bloque de codigo a ejecutar antes de lanzar 
	        //el hilo que realiza la inicialización de Fulbito (Se ejecuta en el hilo principal)       
	    }

	    @Override
	    protected Void doInBackground(Void... arg0) {
	    	//Obtenemos el tiempo al inicio de la tarea de inicializacion
	    	lMilisegundosInicio = System.currentTimeMillis();
	    	//Obtenemos los datos del usuario logueado
	    	usrLogueado = SingletonUsuarioLogueado.getUsuarioLogueado();
	    	
	    	if(usrLogueado == null)
	    	{
	    		//No hay un usuario logueado
	    		iAccionSiguiente = I_INICIAR_LOGIN;
	    	}
	    	else
	    	{
	    		//Hay usuario logueado
	    		WebServiceUsuario wsLogin = new WebServiceUsuario(getApplicationContext());
		    	//Se realiza el logueo automatico
		    	Usuario usrLogin = new Usuario(usrLogueado);
		    	RespuestaWebService cRespWS = new RespuestaWebService();
		    	
		    	try{
			    	switch(wsLogin.bLoguearUsuario(usrLogin, cRespWS))
			    	{
			    		case OK:
			    			//el logueo automatico fue exitoso		    
			    			//Seteamos los datos del usuario logueado
			    			SingletonUsuarioLogueado.actualizarUsuarioLogueado(usrLogin);			    			
				    		iAccionSiguiente = I_INICIAR_HOME;
			    			break;
			    		case NO_CONNECTION:
			    			//logueo offline
			    			sError = getString(R.string.errMsjLoginOffline);
			    			SingletonUsuarioLogueado.setModoConexion(ModoConexion.OFFLINE);
			    			iAccionSiguiente = I_INICIAR_HOME_OFFLINE;
			    			break;
			    		case ERROR:
			    			//el logueo automatico no fue exitoso
			    			//eliminamos la información de usuario logueado
			    			SingletonUsuarioLogueado.eliminarUsuarioLogueado();
				    		sError = cRespWS.sGetData();
				    		iAccionSiguiente = I_INICIAR_RELOGIN;
			    			break;		    		
			    	}
		    	}
		    	catch(FulbitoException feException)
		    	{
		    		//Hubo error al invocar el WebService de Login
		    		sError = getString(R.string.errMsjLogin);		    		
		    		iAccionSiguiente = I_INICIAR_RELOGIN;
		    	}
	    	}	    			    	    

	        return null;
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);
			
	        //Aca se ubica el bloque de codigo a ejecutar luego de que finaliza 
	        //el hilo que realiza la inicialización de Fulbito (Se ejecuta en el hilo principal)
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
	        	case I_INICIAR_HOME_OFFLINE:
	        		//No hay conexion a internet
	        		Toast.makeText(getApplicationContext(), 
							sError, Toast.LENGTH_LONG).show();
	        		intent = new Intent(getApplicationContext(), HomeActivity.class);				
					break;
	        }
	        
	        //Obtenemos el tiempo al final de la tarea de inicializacion
	        lMilisegundosFin = System.currentTimeMillis();
	        
	        long lTotalMilisegundos = lMilisegundosFin - lMilisegundosInicio;
	        
	        //Bloque de codigo para asegurar que el tiempo minimo del splash activo sea de 3000 milisegundos
	        Handler handler = new Handler();
	        
	        handler.postDelayed(new Runnable(){
				            @Override
				            public void run(){
				            	startActivity(intent);	    	        	       	     	        	
				    	        finish();
				    	        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				            }
				        }, 
				        3000 - lTotalMilisegundos
	        		);
	    }

	}
}
