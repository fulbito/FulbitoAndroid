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
        
		FragmentManager fragmentManager;
		android.support.v4.app.Fragment fragment;
		
		fragment = new FragmentInicio();
		fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.loFragmentContainerInicio, fragment).commit();        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }    
}
