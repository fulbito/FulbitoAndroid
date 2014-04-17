/* ----------------------------------------------------------------------------- 
Nombre: 		InicioActivity
Descripción:	Clase que controla la interfaz de inicio de la aplicación en la 
				que se puede elegir entre loguearse o registrarse

Log de modificaciones:

Fecha		Autor		Descripción
17/04/2014	MAC			Creación
----------------------------------------------------------------------------- */

package com.fulbitoAndroid.fulbito;

import com.fulbitoAndroid.fulbito.R;

import android.os.Bundle;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class InicioActivity extends FragmentActivity {
	//Metodos publicos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        
        //Lanzamos el fragment FragmentInicio que contiene la botonera principal de la interfaz de inicio
		FragmentManager fragmentManager;
		android.support.v4.app.Fragment fragment;
		android.support.v4.app.FragmentTransaction ftFragmentTransaction;
		
		fragment = new FragmentInicio();
		fragmentManager = getSupportFragmentManager();
		ftFragmentTransaction = fragmentManager.beginTransaction();
		ftFragmentTransaction.replace(R.id.loFragmentContainerInicio, fragment);
		ftFragmentTransaction.commit();        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }    
}
