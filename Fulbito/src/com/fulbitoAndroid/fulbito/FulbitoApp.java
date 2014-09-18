package com.fulbitoAndroid.fulbito;

import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.gestionDB.SingletonFulbitoDB;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

public class FulbitoApp extends Application{
	public Usuario usrUsuarioLogueado;
	static final String TAG="FulbitoApp";
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d(TAG,"OnCreate del Application Object");
		
		//usrUsuarioLogueado = new Usuario();
		initSingletons();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}
	
	protected void initSingletons()
	{
		SingletonFulbitoDB.initInstance(getApplicationContext());
		SingletonUsuarioLogueado.initInstance(getApplicationContext());
	}	
}
