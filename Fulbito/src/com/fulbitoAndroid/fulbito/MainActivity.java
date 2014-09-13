/* ----------------------------------------------------------------------------- 
Nombre: 		MainActivity
Descripci�n:	Clase que lanza el proceso de inicializaci�n de la 
				aplicaci�n mientras muestra un splash de la aplicaci�n

Log de modificaciones:

Fecha		Autor		Descripci�n
23/03/2014	MAC			Creaci�n
----------------------------------------------------------------------------- */

package com.fulbitoAndroid.fulbito;

import com.fulbitoAndroid.admUsuario.WebServiceLogin;
import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.gestionDB.UsuarioDB;
import com.fulbitoAndroid.herramientas.RespuestaWebService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends Activity {
	static final String TAG="MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d(TAG,"En el OnCreate");
		//la aplicaci�n espera 3 segundos antes de comenzar, mostrando un splash
		//En este activity se deber�a lanzar la inicializaci�n de la aplicaci�n
		Usuario usrLogueado = SingletonUsuarioLogueado.getUsuarioLogueado(getApplicationContext());
		
		if(usrLogueado == null)
		{
			//Si no hay usuario logueado lanzamos InicioActivity  		
	  		final Handler handler = new Handler();
	          handler.postDelayed(new Runnable() {
	              public void run() {
	                  	Intent intent = new Intent(getApplicationContext(), InicioActivity.class);
	                      startActivity(intent);
	                      finish();
	              }
	          }, 3000);
	    }						
	    else
	    {
	    	//Si hay usuario logueado hacemos el logueo automatico
	    	//deberiamos testear la conexion a internet, si no hay conexi�n permiter logueo offline
	    	//Invocamos el Web Service de Login
	    	
	    	WebServiceLogin wsLogin = new WebServiceLogin();
	    	
	    	Usuario usrLogin = new Usuario(usrLogueado);
	    	RespuestaWebService cRespWS = new RespuestaWebService();
	    	if(wsLogin.bLoguearUsuario(usrLogin, cRespWS) == true)
	    	{
	    		//lanzamos HomeActivity  		
		  		final Handler handler = new Handler();
		          handler.postDelayed(new Runnable() {
		              public void run() {
		                  	Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
		                      startActivity(intent);
		                      finish();
		              }
		          }, 3000);
	    	}
	    	else
	    	{
	    		//El webservice envio una respuesta con error
				Toast.makeText(getApplicationContext(), 
						cRespWS.sGetData(), Toast.LENGTH_LONG).show();
				
				//aca deberian lanzarse una interfaz q permita el login manual, recuperar contrase�a o cambiar de usuario				
	    	}	    	
	    }		
	}
}
