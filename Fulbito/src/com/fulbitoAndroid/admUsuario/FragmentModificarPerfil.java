/* ----------------------------------------------------------------------------- 
Nombre: 		FragmentModificarPerfil
Descripci�n:	Clase que controla la interfaz de la secci�n Modificar datos del
				Perfil implementada mediante un fragment que se lanza a partir 
				del Men� Lateral (DrawerLayout) que se encuentra en 
				HomeActivity.java

Log de modificaciones:

Fecha		Autor		Descripci�n
17/04/2014	MAC			Creaci�n
----------------------------------------------------------------------------- */

package com.fulbitoAndroid.admUsuario;

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

public class FragmentModificarPerfil extends Fragment {
	TabHost tbTabHost;
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
        
        //Agregamos la pesta�a de modificar datos de usuario
        vAgregarTab("DATOS", 
        		R.drawable.tab_img_selector_datos, 
        		R.id.tab1);
        
        //Agregamos la pesta�a de modificar foto de usuario
        vAgregarTab("FOTO", 
        		R.drawable.tab_img_selector_foto, 
        		R.id.tab2);
        
        //Agregamos la pesta�a de modificar ubicacion de usuario
        vAgregarTab("UBICACION", 
        		R.drawable.
        		tab_img_selector_ubicacion, 
        		R.id.tab3);
        
        //Agregamos la pesta�a de modificar configuracion de notificaciones de usuario
        vAgregarTab("NOTIFICACION", 
        		R.drawable.tab_img_selector_notificaciones, 
        		R.id.tab4);        
    }
    
    //Agrega pesta�a (TAB) al TabHost
    private void vAgregarTab(final String tag, final int iIconResourceId, final int iContent) {
        //Creamos la vista que se va a utilizar para la pesta�a
    	View tabview = createTabView(tbTabHost.getContext(), iIconResourceId);
    	//Creamos la pesta�a dentro del TabHost
        TabSpec spec = tbTabHost.newTabSpec(tag);
        //Seteamos el layout de contenido que le corresponde a la pesta�a
        spec.setContent(iContent);
        //Seteamos la vista correspondiente a la pesta�a
        spec.setIndicator(tabview);
        //Agregamos finalmente la pesta�a al TabHost
        tbTabHost.addTab(spec);
    }

    //Crea y realiza el Inflate de la vista asociada a una pesta�a
    private static View createTabView(final Context context, final int iResourceId) {
        //Hacemos el inflater del layout
    	View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
    	//Seteamos la imagen asociada a la pesta�a
        ImageView icon = (ImageView) view.findViewById(R.id.tabImage);
        icon.setImageResource(iResourceId);
        return view;
    }
}
