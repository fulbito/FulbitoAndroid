/* ----------------------------------------------------------------------------- 
Nombre: 		MainActivity
Descripción:	Clase que lanza el proceso de inicialización de la 
				aplicación mientras muestra un splash de la aplicación

Log de modificaciones:

Fecha		Autor		Descripción
23/03/2014	MAC			Creación
----------------------------------------------------------------------------- */

package com.fulbitoAndroid.fulbito;

import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.gestionDB.UsuarioDB;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends Activity {
	static final String TAG="MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d(TAG,"En el OnCreate");
		//la aplicación espera 3 segundos antes de comenzar, mostrando un splash
		//En este activity se debería lanzar la inicialización de la aplicación
		
		//Consultamos si hay un usuario de aplicación logueado
		/*UsuarioDB usrDB = new UsuarioDB();
		
		int iExisteUsuarioLogueado = usrDB.iGetUsuarioCount();		
		
		if(iExisteUsuarioLogueado != 0)
		{
			Usuario usr = usrDB.usrSelectUsuario();
			Usuario usrLogueado = SingletonUsuarioLogueado.getInstance();
			usrLogueado.setId(usr.getId());
			usrLogueado.setAlias(usr.getAlias());
			usrLogueado.setEmail(usr.getEmail());
			usrLogueado.setPassword(usr.getPassword());
		}
		*/
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
	    	//Si hay usuario logueado lanzamos HomeActivity  		
	  		final Handler handler = new Handler();
	          handler.postDelayed(new Runnable() {
	              public void run() {
	                  	Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
	                      startActivity(intent);
	                      finish();
	              }
	          }, 3000);
	    }		
	}
	
	private boolean bExisteUsuarioLogueado(){
    //Consultamos si hay un usuario de aplicación logueado
		UsuarioDB usrDB = new UsuarioDB();
		
		int iExisteUsuarioLogueado = usrDB.iGetUsuarioCount();		
		boolean bExiste = false;
		if(iExisteUsuarioLogueado != 0)
		{
			Usuario usr = usrDB.usrSelectUsuario();
			Usuario usrLogueado = SingletonUsuarioLogueado.getInstance();
			usrLogueado.setId(usr.getId());
			usrLogueado.setAlias(usr.getAlias());
			usrLogueado.setEmail(usr.getEmail());
			usrLogueado.setPassword(usr.getPassword());
			
			bExiste = true;
		}
		else
		{
		  bExiste = false;
		}
    
    return bExiste;
  }
}
