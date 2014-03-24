package com.fulbitoAndroid.admUsuario;

import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.fulbito.R.layout;
import com.fulbitoAndroid.fulbito.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class RegistrarUsuarioActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrar_usuario);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registrar_usuario, menu);
		return true;
	}

}
