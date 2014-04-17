/* ----------------------------------------------------------------------------- 
Nombre: 		FragmentModificarPerfil
Descripción:	Clase que controla la interfaz de la sección Modificar datos del
				Perfil implementada mediante un fragment que se lanza a partir 
				del Menú Lateral (DrawerLayout) que se encuentra en 
				HomeActivity.java

Log de modificaciones:

Fecha		Autor		Descripción
17/04/2014	MAC			Creación
----------------------------------------------------------------------------- */

package com.fulbitoAndroid.admUsuario;

import com.fulbitoAndroid.clases.TabContentVacio;
import com.fulbitoAndroid.fulbito.FragmentInicio;
import com.fulbitoAndroid.fulbito.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabHost.TabContentFactory;

public class FragmentModificarPerfil extends Fragment {
	TabHost tbTabHost;
	android.support.v4.app.Fragment fragmentTab1;
	android.support.v4.app.Fragment fragmentTab2;
	android.support.v4.app.Fragment fragmentTab3;
	android.support.v4.app.Fragment fragmentTab4;
	android.support.v4.app.FragmentTransaction ft;
	android.support.v4.app.FragmentManager fm;
	String sTagTabAnterior = "";
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mod_perfil, container, false);                     
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        //Obtenemos el control del Tabhost creado en el XML
        tbTabHost = (TabHost)getView().findViewById(android.R.id.tabhost);
        
        //Call setup() before adding tabs if loading TabHost using findViewById()
        tbTabHost.setup();
        
        
        
        //Agregamos la pestaña de modificar datos de usuario
        vAgregarTab("DATOS", 
        		R.drawable.tab_img_selector_datos/*, 
        		R.id.tab1*/);
        
        //Agregamos la pestaña de modificar foto de usuario
        vAgregarTab("FOTO", 
        		R.drawable.tab_img_selector_foto/*, 
        		R.id.tab2*/);
        
        //Agregamos la pestaña de modificar ubicacion de usuario
        vAgregarTab("UBICACION", 
        		R.drawable.
        		tab_img_selector_ubicacion/*, 
        		R.id.tab3*/);
        
        //Agregamos la pestaña de modificar configuracion de notificaciones de usuario
        vAgregarTab("NOTIFICACION", 
        		R.drawable.tab_img_selector_notificaciones/*, 
        		R.id.tab4*/);  
        
        fm =  getActivity().getSupportFragmentManager();
    	ft = fm.beginTransaction();
        fragmentTab1 = new FragmentModPerfilDatos();
		ft.add(R.id.realtabcontent, new FragmentModPerfilDatos(), "DATOS");
		//ft.commit();
		fragmentTab2 = new FragmentModPerfilFoto();	
		ft.add(R.id.realtabcontent, new FragmentModPerfilFoto(), "FOTO");
		//ft.commit();
		fragmentTab3 = new FragmentModPerfilUbicacion();
		ft.add(R.id.realtabcontent, new FragmentModPerfilUbicacion(), "UBICACION");
		//ft.commit();
		fragmentTab4 = new FragmentModPerfilNotificaciones();
		ft.add(R.id.realtabcontent, new FragmentModPerfilNotificaciones(), "NOTIFICACION");
		//ft.commit();
        ft.attach(fragmentTab1);
        sTagTabAnterior = "DATOS";
        ft.commit();
        /** Defining Tab Change Listener event. This is invoked when tab is changed */
        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
        	 
            @Override
            public void onTabChanged(String tabId) {

            	FragmentModPerfilDatos datosFragment = (FragmentModPerfilDatos) fm.findFragmentByTag("DATOS");
            	FragmentModPerfilFoto fotoFragment = (FragmentModPerfilFoto) fm.findFragmentByTag("FOTO");
            	FragmentModPerfilUbicacion ubicacionFragment = (FragmentModPerfilUbicacion) fm.findFragmentByTag("UBICACION");
            	FragmentModPerfilNotificaciones notificacionesFragment = (FragmentModPerfilNotificaciones) fm.findFragmentByTag("NOTIFICACION");
            	
            	ft = fm.beginTransaction();

            	if(sTagTabAnterior == "DATOS")
            	{
            		ft.detach(datosFragment);
            	}
            	
            	if(sTagTabAnterior == "FOTO")
            	{
            		ft.detach(fotoFragment);
            	}
            	
            	if(sTagTabAnterior == "UBICACION")
            	{
            		ft.detach(ubicacionFragment);
            	}
            	
            	if(sTagTabAnterior == "NOTIFICACION")
            	{
            		ft.detach(notificacionesFragment);
            	}
            	
            	if(tabId.equalsIgnoreCase("DATOS"))
            	{
            		ft.attach(datosFragment);
            	}
            	
            	if(tabId.equalsIgnoreCase("FOTO"))
            	{
            		ft.attach(fotoFragment);
            	}
            	
            	if(tabId.equalsIgnoreCase("UBICACION"))
            	{
            		ft.attach(ubicacionFragment);
            	}
            	
            	if(tabId.equalsIgnoreCase("NOTIFICACION"))
            	{
            		ft.attach(notificacionesFragment);
            	}
            	
                ft.commit();
                
                sTagTabAnterior = tabId;
            }
        };
        
        /** Setting tabchangelistener for the tab */
        tbTabHost.setOnTabChangedListener(tabChangeListener);
    }
    
    //Agrega pestaña (TAB) al TabHost
    private void vAgregarTab(final String tag, final int iIconResourceId/*, final int iContent*/) {
        //Creamos la vista que se va a utilizar para la pestaña
    	View tabview = createTabView(tbTabHost.getContext(), iIconResourceId);
    	//Creamos la pestaña dentro del TabHost
        TabSpec spec = tbTabHost.newTabSpec(tag);
        //Seteamos el layout de contenido que le corresponde a la pestaña
        //spec.setContent(iContent);
        spec.setContent(new TabContentVacio(tbTabHost.getContext()));
        
        //Seteamos la vista correspondiente a la pestaña
        spec.setIndicator(tabview);
        //Agregamos finalmente la pestaña al TabHost
        tbTabHost.addTab(spec);
        
    }

    //Crea y realiza el Inflate de la vista asociada a una pestaña
    private static View createTabView(final Context context, final int iResourceId) {
        //Hacemos el inflater del layout
    	View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
    	//Seteamos la imagen asociada a la pestaña
        ImageView icon = (ImageView) view.findViewById(R.id.tabImage);
        icon.setImageResource(iResourceId);
        return view;
    }
}
