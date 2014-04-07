package com.fulbitoAndroid.fulbito;

import java.util.ArrayList;

import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.admUsuario.ModUsuarioActivity;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;



public class HomeActivity extends ActionBarActivity {
	ListView lvOpcionesMenuLateral;
	DrawerLayout dlMenuLateral;	
	ActionBarDrawerToggle mDrawerToggle;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		//Obtenemos los elementos de la interfaz gráfica
		lvOpcionesMenuLateral = (ListView) findViewById(R.id.drawer);
		dlMenuLateral = (DrawerLayout) findViewById(R.id.drawer_layout);		
		
		//Completamos los elementos del menu lateral
		vCrearMenuLateral(lvOpcionesMenuLateral);
		
		//Activamos el ActionBar y lo vinculamos al DrawerLayout (menu lateral)
		getSupportActionBar().setTitle(R.string.app_name);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(
			this, 
			dlMenuLateral,  
			R.drawable.ic_drawer, 
			R.string.app_name, 
			R.string.txtMenuLateral ) 
		{		 
			public void onDrawerClosed(View view) 
			{
				getSupportActionBar().setTitle(R.string.app_name);
				//invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
			 
			public void onDrawerOpened(View drawerView) 
			{
				getSupportActionBar().setTitle(R.string.txtMenuLateral);
				//invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
          
		dlMenuLateral.setDrawerListener(mDrawerToggle);
		
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
	
	//Sobrecargas de metodos para que funcione el ActionBar
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = dlMenuLateral.isDrawerOpen(lvOpcionesMenuLateral);
       // menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        /*case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;*/
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

}
