/* ----------------------------------------------------------------------------- 
Nombre: 		MainActivity
Descripción:	Clase que contiene lanza el proceso de inicialización de la 
				aplicación mientras muestra un splash de la aplicación

Log de modificaciones:

Fecha		Autor		Descripción
23/03/2014	MAC			Creación
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
		//la aplicación espera 3 segundos antes de comenzar, mostrando un splash
		//En este activity se debería lanzar la inicialización de la aplicación
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
