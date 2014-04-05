package com.fulbitoAndroid.fulbito;

import java.util.ArrayList;

import com.fulbitoAndroid.admUsuario.LoginUsuarioActivity;
import com.fulbitoAndroid.admUsuario.ModUsuarioActivity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.TypedArray;

public class HomeActivity extends Activity {
	ListView lvOpcionesMenuLateral;
	DrawerLayout dlMenuLateral;	
	ActionBarDrawerToggle toggle;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		//Obtenemos los elementos de la interfaz gráfica
		lvOpcionesMenuLateral = (ListView) findViewById(R.id.drawer);
		dlMenuLateral = (DrawerLayout) findViewById(R.id.drawer_layout);		
		
		//Completamos los elementos del menu lateral
		vCrearMenuLateral(lvOpcionesMenuLateral);
					
		/*
		toggle = new ActionBarDrawerToggle(this, dlMenuLateral,  R.drawable.ic_drawer, R.string.app_name, R.string.hello_world ){
			 
			 public void onDrawerClosed(View view) {
				 super.onDrawerClosed(view);
			  //getActionBar().setTitle(getResources().getString(R.string.app_name));
			 //invalidateOptionsMenu();
			 }
			 
			public void onDrawerOpened(View drawerView) {
			 //getActionBar().setTitle("Menu");
			 //invalidateOptionsMenu();
			 }
			};
			
			dlMenuLateral.setDrawerListener(toggle);
			*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	//Completa los items del menú lateral del home
	public void vCrearMenuLateral(ListView lvOpcionesMenuLateral)
	{
		ListMenuAdapter lmaMenuLateralAdapter;
		ArrayList<ItemMenuLateral> alItemsMenuLateral = new ArrayList<ItemMenuLateral>();
		
		//agregamos la imagen del menu lateral
		View header = getLayoutInflater().inflate(R.layout.header_menu_lateral, null);		
		lvOpcionesMenuLateral.addHeaderView(header);
				
		//traemos los arrays iconos y opciones del archivo array.xml
		String[] arrOpciones = getResources().getStringArray(R.array.opciones_menu_lateral);
		TypedArray arrIconosMenuLateral = getResources().obtainTypedArray(R.array.iconos_menu_lateral);
		
		int iLenghtArrOpciones = arrOpciones.length;
		int iLenghtArrIconos = arrIconosMenuLateral.length();
		
		//Validar que los array tengan la misma cantidad de elementos
		if(iLenghtArrOpciones != iLenghtArrIconos)
		{
			//ERROR
		}
		
		//Completamos el array de items con los iconos y opciones del menu
		for(int i=0; i<iLenghtArrOpciones; i++)
		{
			alItemsMenuLateral.add(new ItemMenuLateral(arrOpciones[i], arrIconosMenuLateral.getResourceId(i, -1)));
		}
			
		
		//Seteamos los items del menu
		lmaMenuLateralAdapter = new ListMenuAdapter(this, alItemsMenuLateral);		
		lvOpcionesMenuLateral.setAdapter(lmaMenuLateralAdapter);
		
		//Seteamos la acción a realizar cuando se toca un item del menu lateral
		lvOpcionesMenuLateral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id){
				vMostrarFragment(position);
			}
		});
		
	}
	
	//Dispara un fragment o un activity de acuerdo al item del menu lateral seleccionado
	private void vMostrarFragment(int iOpcionMenu)
	{
		switch(iOpcionMenu)
		{
			case 1:
				Intent intent = new Intent(getApplicationContext(), ModUsuarioActivity.class);
                startActivity(intent);
				break;
		}
	}

}
