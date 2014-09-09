/* ----------------------------------------------------------------------------- 
Nombre: 		InicioActivity
Descripción:	Clase que controla la interfaz Home de la aplicación, a ella se 
				llega luego de que el usuario se loguee correctamente.
				Contiene un menu lateral (DrawerLayout) con las opciones principales
				de la aplicación

Log de modificaciones:

Fecha		Autor		Descripción
17/04/2014	MAC			Creación
----------------------------------------------------------------------------- */
package com.fulbitoAndroid.fulbito;


import java.util.ArrayList;

import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.gestionDB.UsuarioDB;
import com.fulbitoAndroid.herramientas.ItemMenuLateral;
import com.fulbitoAndroid.herramientas.ListMenuAdapter;
import com.fulbitoAndroid.admPartido.FragmentCrearPartido;
import com.fulbitoAndroid.admUsuario.FragmentModificarPerfil;
import com.fulbitoAndroid.admUsuario.ModUsuarioActivity;
import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;

public class HomeActivity extends ActionBarActivity {
	ListView lvOpcionesMenuLateral;
	DrawerLayout dlLayoutPrincipal;
	ActionBarDrawerToggle mDrawerToggle;	
	LinearLayout lloMenuLateral;
	
	Usuario usrUsuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		//Obtenemos los elementos de la interfaz gráfica
		lvOpcionesMenuLateral = (ListView) findViewById(R.id.drawer);
		dlLayoutPrincipal = (DrawerLayout) findViewById(R.id.drawer_layout);
		lloMenuLateral = (LinearLayout) findViewById(R.id.loDrawerContainer);
		
		//Completamos los elementos del menu lateral
		vCrearMenuLateral(lvOpcionesMenuLateral);
		
		usrUsuario = SingletonUsuarioLogueado.getInstance();
		
		//Activamos el ActionBar y lo vinculamos al DrawerLayout (menu lateral)	
		vAgregarActionBar();
		
		
	}
	
	//Completa los items del menú lateral del home
	private void vCrearMenuLateral(ListView lvOpcionesMenuLateral)
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
				dlLayoutPrincipal.closeDrawers();
				vMostrarFragment(position);
			}
		});
		
	}
	
	private void vAgregarActionBar(){
		//getSupportActionBar().setTitle(R.string.app_name);
		getSupportActionBar().setTitle(usrUsuario.getAlias());
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(
			this, 
			dlLayoutPrincipal,  
			R.drawable.ic_drawer, 
			R.string.app_name, 
			R.string.txtMenuLateral ) 
		{		 
			public void onDrawerClosed(View view) 
			{
				//getSupportActionBar().setTitle(R.string.app_name);
				getSupportActionBar().setTitle(usrUsuario.getAlias());
				//invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
			 
			public void onDrawerOpened(View drawerView) 
			{
				getSupportActionBar().setTitle(R.string.txtMenuLateral);
				//invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
          
		dlLayoutPrincipal.setDrawerListener(mDrawerToggle);
	}
	
	//Dispara un fragment o un activity de acuerdo al item del menu lateral seleccionado
	private void vMostrarFragment(int iOpcionMenu)
	{
		FragmentManager fragmentManager;
		android.support.v4.app.Fragment fragment;
		android.support.v4.app.FragmentTransaction ftFragmentTransaction;
		
		fragmentManager = getSupportFragmentManager();
				
    	//Limpiamos el BackStack Fragment
    	for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
    		fragmentManager.popBackStack();        
    	}
		
		switch(iOpcionMenu)
		{			
			case 1:
		        //Agregamos el fragment Modificar Perfil
				fragment = new FragmentModificarPerfil();								
				ftFragmentTransaction = fragmentManager.beginTransaction();
				ftFragmentTransaction.replace(R.id.loFragmentContainer, fragment);		        																								
				//Agregamos el fragment anterior a la pila para volver
				ftFragmentTransaction.addToBackStack(null);
				ftFragmentTransaction.commit();			        
		        break;
			case 2:
				fragment = new FragmentCrearPartido();				
				ftFragmentTransaction = fragmentManager.beginTransaction();
				ftFragmentTransaction.replace(R.id.loFragmentContainer, fragment);		        																								
				//Agregamos el fragment anterior a la pila para volver
				ftFragmentTransaction.addToBackStack(null);
				ftFragmentTransaction.commit();
				break;
			case 3:
				Intent intent = new Intent(getApplicationContext(), ModUsuarioActivity.class);
                startActivity(intent);
				break;
			case 4:
			    UsuarioDB usrDB = new UsuarioDB();
			    usrDB.bDeleteUsuario();
			    
			    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);           
			    //intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    startActivity(intent2);
			    finish();
				break;
		}
	}
	
	//Sobrecargas de metodos para que funcione el ActionBar
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }	

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = dlMenuLateral.isDrawerOpen(lvOpcionesMenuLateral);
    	boolean drawerOpen = dlLayoutPrincipal.isDrawerOpen(lloMenuLateral);
       // menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    //Método para setear la acción a realizar cuando se hace click sobre u icono del ActionBar
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
        case R.id.menu_item_1:
        	FragmentManager fm = getSupportFragmentManager();
        	//sacamos todos los fragments del stackpara volver al HOME
        	for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
        	    fm.popBackStack();        
        	}
        	return true;
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
