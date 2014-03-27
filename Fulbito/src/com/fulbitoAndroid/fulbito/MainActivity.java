/* ----------------------------------------------------------------------------- 
Nombre: 		MainActivity
Descripci�n:	Clase que contiene lanza el proceso de inicializaci�n de la 
				aplicaci�n mientras muestra un splash de la aplicaci�n

Log de modificaciones:

Fecha		Autor		Descripci�n
23/03/2014	MAC			Creaci�n
----------------------------------------------------------------------------- */

package com.fulbitoAndroid.fulbito;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.fulbitoAndroid.admUsuario.LoginUsuarioActivity;

public class MainActivity extends Activity {
	static final String TAG="MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d(TAG,"En el OnCreate");
		//la aplicaci�n espera 3 segundos antes de comenzar, mostrando un splash
		//En este activity se deber�a lanzar la inicializaci�n de la aplicaci�n
		final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                	Intent intent = new Intent(getApplicationContext(), LoginUsuarioActivity.class);
                    startActivity(intent);
                    finish();
            }
        }, 3000);		
	}
}
