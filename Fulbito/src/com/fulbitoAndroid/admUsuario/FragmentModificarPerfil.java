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


import java.util.List;

import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.herramientas.TabContentVacio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class FragmentModificarPerfil extends Fragment {
	TabHost tbTabHost;
	android.support.v4.app.Fragment fragmentTab1;
	android.support.v4.app.Fragment fragmentTab2;
	android.support.v4.app.Fragment fragmentTab3;
	android.support.v4.app.Fragment fragmentTab4;
	android.support.v4.app.FragmentTransaction ft;
	android.support.v4.app.FragmentManager fm;
	
	FragmentModPerfilDatos datosFragment;
	FragmentModPerfilFoto fotoFragment;
	FragmentModPerfilUbicacion ubicacionFragment;
	FragmentModPerfilNotificaciones notificacionesFragment;
	
	public String sUbicacion = "";
	
	String sTagTabAnterior = "";
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mod_perfil, container, false);                     
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	
	   //Si este fragement no llamo a un StartActivityForResult, lo tira a hacia los otros fragments
	   List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
            	//if(String.valueOf(requestCode).substring(0,1)=="2" && fragment.get() == "FOTO")
            	//{
            		fragment.onActivityResult(requestCode, resultCode, data);
            	//}
            }
        }
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
        		R.drawable.tab_img_selector_datos);
        
        //Agregamos la pestaña de modificar foto de usuario
        vAgregarTab("FOTO", 
        		R.drawable.tab_img_selector_foto);
        
        //Agregamos la pestaña de modificar ubicacion de usuario
        vAgregarTab("UBICACION", 
        		R.drawable.
        		tab_img_selector_ubicacion);
        
        //Agregamos la pestaña de modificar configuracion de notificaciones de usuario
        vAgregarTab("NOTIFICACION", 
        		R.drawable.tab_img_selector_notificaciones);  
        
        //Se esta trabajando con fragments dentro de un fragment, por lo tanto no debemos usar 
        //getFragmentManager() ya que este obtiene el FragmentManager del activity padre y nosotros
        //debemos laburar con el FragmentManager del fragment padre por eso usamos getChildFragmentManager
        fm =  getChildFragmentManager();
    	ft = fm.beginTransaction();
    	//Añadimos el fragment inicial FragmentModPerfilDatos
		ft.add(R.id.realtabcontent, new FragmentModPerfilDatos(), "DATOS");	
		sTagTabAnterior = "DATOS";
		//Commiteamos los cambios
		ft.commit();
		
        //Definimos el Tab Change Listener event. Este se invoca cuando cambia el Tab, es decir
		//cuando seleccionamos una pestaña. Lo que se realizará dentro de el es cambiar al 
		//fragment correspondiente a la pestaña seleccionada, sacando el anterior fragment activo
		//y activando el correspondiente
        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
        	 
            @Override
            public void onTabChanged(String tabId) {

            	ft = fm.beginTransaction();
            	//sacamos el fragment activo
            	if(sTagTabAnterior == "DATOS")
            	{
            		datosFragment = (FragmentModPerfilDatos) fm.findFragmentByTag("DATOS");
            		ft.hide(datosFragment);
            		//ft.detach(datosFragment);
            	}
            	
            	if(sTagTabAnterior == "FOTO")
            	{
            		fotoFragment = (FragmentModPerfilFoto) fm.findFragmentByTag("FOTO");
            		ft.hide(fotoFragment);
            		//ft.detach(fotoFragment);
            	}
            	
            	if(sTagTabAnterior == "UBICACION")
            	{
            		ubicacionFragment = (FragmentModPerfilUbicacion) fm.findFragmentByTag("UBICACION");
            		ft.hide(ubicacionFragment);
            		//ft.detach(ubicacionFragment);
            	}
            	
            	if(sTagTabAnterior == "NOTIFICACION")
            	{
            		notificacionesFragment = (FragmentModPerfilNotificaciones) fm.findFragmentByTag("NOTIFICACION");
            		ft.hide(notificacionesFragment);
            		//ft.detach(notificacionesFragment);
            	}
            	
            	//hacemos visible el fragment correspondiente a la pestaña seleccionada
            	if(tabId.equalsIgnoreCase("DATOS"))
            	{
            		//buscamos el fragment dentro del FragmentManager
            		datosFragment = (FragmentModPerfilDatos) fm.findFragmentByTag("DATOS");
            		if(datosFragment == null)
            			//el fragment NO está dentro del FragmentManager, se lo añade y se lo hace visible con add
            			ft.add(R.id.realtabcontent, new FragmentModPerfilDatos(), "DATOS");
            		else  
            			//el fragment está dentro del FragmentManager, solo se lo hace visible con attach
            			//ft.attach(datosFragment);
            			ft.show(datosFragment);
            	}
            	
            	if(tabId.equalsIgnoreCase("FOTO"))
            	{
            		fotoFragment = (FragmentModPerfilFoto) fm.findFragmentByTag("FOTO");
            		if(fotoFragment == null)
            			ft.add(R.id.realtabcontent, new FragmentModPerfilFoto(), "FOTO");
            		else            			
            			//ft.attach(fotoFragment);
            			ft.show(fotoFragment);
            	}
            	
            	if(tabId.equalsIgnoreCase("UBICACION"))
            	{
            		ubicacionFragment = (FragmentModPerfilUbicacion) fm.findFragmentByTag("UBICACION");
            		if(ubicacionFragment == null)
            			ft.add(R.id.realtabcontent, new FragmentModPerfilUbicacion(), "UBICACION");
            		else            			
            			//ft.attach(ubicacionFragment);
            			ft.show(ubicacionFragment);
            	}
            	
            	if(tabId.equalsIgnoreCase("NOTIFICACION"))
            	{
            		notificacionesFragment = (FragmentModPerfilNotificaciones) fm.findFragmentByTag("NOTIFICACION");
            		if(notificacionesFragment == null)
            			ft.add(R.id.realtabcontent, new FragmentModPerfilNotificaciones(), "NOTIFICACION");
            		else            			
            			//ft.attach(notificacionesFragment);
            			ft.show(notificacionesFragment);
            	}
            	
                ft.commit();
                
                sTagTabAnterior = tabId;
            }
        };
        
        //seteamos el tabChangeListener
        tbTabHost.setOnTabChangedListener(tabChangeListener);
                        
    }
    
    //Agrega pestaña (TAB) al TabHost
    private void vAgregarTab(final String tag, final int iIconResourceId/*, final int iContent*/) {
        //Creamos la vista que se va a utilizar para la pestaña
    	View tabview = createTabView(tbTabHost.getContext(), iIconResourceId);
    	//Creamos la pestaña dentro del TabHost
        TabSpec spec = tbTabHost.newTabSpec(tag);
        //Seteamos un contenido vacio para la pestaña, luego se le asignará un fragment
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
