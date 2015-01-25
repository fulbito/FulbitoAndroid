package com.fulbitoAndroid.admPartido;

import com.fulbitoAndroid.clases.Partido;
import com.fulbitoAndroid.fulbito.DatosConfiguracion;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.gestionDB.PartidoAmistosoDB;
import com.fulbitoAndroid.gestionDB.PartidoDB;
import com.fulbitoAndroid.herramientas.TabContentVacio;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class FragmentInfoPartido extends Fragment{
	
	TabHost tbTabHost;
	android.support.v4.app.Fragment fragmentTab1;
	android.support.v4.app.Fragment fragmentTab2;
	android.support.v4.app.Fragment fragmentTab3;
	android.support.v4.app.Fragment fragmentTab4;
	android.support.v4.app.FragmentTransaction ft;
	android.support.v4.app.FragmentManager fm;
	//Fragments que incluye la interfaz de información de partidos
	FragmentInfoPartidoDatos 		datosFragment;
	FragmentInfoPartidoEquipos 		equiposFragment;
	FragmentInfoPartidoChat 		chatFragment;
	FragmentInfoPartidoInvitaciones invitacionesFragment;
	//Constantes
	private static final String S_PARTIDO_ID 		= "id_partido";
	private static final String S_TAG_DATOS 		= "DATOS";
	private static final String S_TAG_EQUIPOS 		= "EQUIPOS";
	private static final String S_TAG_CHAT 			= "CHAT";
	private static final String S_TAG_INVITACIONES 	= "INVITACIONES";
	
	String sTagTabAnterior = "";
	
	private Partido cPartido;
	
	public Partido getDatosPartido(){
		return cPartido;
	}
	
	public static FragmentInfoPartido newInstance(int iIdPartido) {
		FragmentInfoPartido f = new FragmentInfoPartido();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        //Pasamos el ID del partido para consultar sus datos en la BD
        args.putInt(S_PARTIDO_ID, iIdPartido);
        
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_partido, container, false);                     
    }
    
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        
        //Obtenemos los datos del partido a mostrar
		PartidoDB cPartidoDB = new PartidoDB();
		
		//Obtenemos los datos del partido
		int iIdPartido = getArguments().getInt(S_PARTIDO_ID);
		cPartido = cPartidoDB.parSelectPartidoById(iIdPartido);
        //Obtenemos los datos particulares dependiendo del tipo de partido
        switch(cPartido.getTipoPartido())
        {
        	case DatosConfiguracion.TP_ID_AMISTOSO:
        		PartidoAmistosoDB cPartidoAmistosoDB = new PartidoAmistosoDB();
        		cPartido = cPartidoAmistosoDB.parSelectPartidoAmistosoById(iIdPartido);
        		break;
        	case DatosConfiguracion.TP_ID_DESAFIO_EQ:
        		cPartido = cPartidoDB.parSelectPartidoById(iIdPartido);        		        		
        		break;
        	case DatosConfiguracion.TP_ID_DESAFIO_USR:
        		cPartido = cPartidoDB.parSelectPartidoById(iIdPartido);
        		break;
        }

        //Obtenemos el control del Tabhost creado en el XML
        tbTabHost = (TabHost)getView().findViewById(android.R.id.tabhost);
        
        //Call setup() before adding tabs if loading TabHost using findViewById()
        tbTabHost.setup();
                        
        //Agregamos la pestaña de modificar datos de usuario
        vAgregarTab(S_TAG_DATOS, 
        		R.drawable.tab_img_selector_datos);
        
        //Agregamos la pestaña de modificar foto de usuario
        vAgregarTab(S_TAG_EQUIPOS, 
        		R.drawable.tab_img_selector_foto);
        
        //Agregamos la pestaña de modificar ubicacion de usuario
        vAgregarTab(S_TAG_CHAT, 
        		R.drawable.
        		tab_img_selector_ubicacion);
        
        //Agregamos la pestaña de modificar configuracion de notificaciones de usuario
        vAgregarTab(S_TAG_INVITACIONES, 
        		R.drawable.tab_img_selector_notificaciones);  
        
        //Se esta trabajando con fragments dentro de un fragment, por lo tanto no debemos usar 
        //getFragmentManager() ya que este obtiene el FragmentManager del activity padre y nosotros
        //debemos laburar con el FragmentManager del fragment padre por eso usamos getChildFragmentManager
        fm =  getChildFragmentManager();
    	ft = fm.beginTransaction();
    	//Añadimos el fragment inicial FragmentInfoPartidoDatos
		ft.add(R.id.realtabcontent_mod_partido, new FragmentInfoPartidoDatos(), S_TAG_DATOS);	
		sTagTabAnterior = S_TAG_DATOS;
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
            	if(sTagTabAnterior == S_TAG_DATOS)
            	{
            		datosFragment = (FragmentInfoPartidoDatos) fm.findFragmentByTag(S_TAG_DATOS);
            		ft.hide(datosFragment);
            		//ft.detach(datosFragment);
            	}
            	
            	if(sTagTabAnterior == S_TAG_EQUIPOS)
            	{
            		equiposFragment = (FragmentInfoPartidoEquipos) fm.findFragmentByTag(S_TAG_EQUIPOS);
            		ft.hide(equiposFragment);
            		//ft.detach(fotoFragment);
            	}
            	
            	if(sTagTabAnterior == S_TAG_CHAT)
            	{
            		chatFragment = (FragmentInfoPartidoChat) fm.findFragmentByTag(S_TAG_CHAT);
            		ft.hide(chatFragment);
            		//ft.detach(ubicacionFragment);
            	}
            	
            	if(sTagTabAnterior == S_TAG_INVITACIONES)
            	{
            		invitacionesFragment = (FragmentInfoPartidoInvitaciones) fm.findFragmentByTag(S_TAG_INVITACIONES);
            		ft.hide(invitacionesFragment);
            		//ft.detach(notificacionesFragment);
            	}
            	
            	//hacemos visible el fragment correspondiente a la pestaña seleccionada
            	if(tabId.equalsIgnoreCase(S_TAG_DATOS))
            	{
            		//buscamos el fragment dentro del FragmentManager
            		datosFragment = (FragmentInfoPartidoDatos) fm.findFragmentByTag(S_TAG_DATOS);
            		if(datosFragment == null)
            			//el fragment NO está dentro del FragmentManager, se lo añade y se lo hace visible con add
            			ft.add(R.id.realtabcontent_mod_partido, new FragmentInfoPartidoDatos(), S_TAG_DATOS);
            		else  
            			//el fragment está dentro del FragmentManager, solo se lo hace visible con attach
            			//ft.attach(datosFragment);
            			ft.show(datosFragment);
            	}
            	
            	if(tabId.equalsIgnoreCase(S_TAG_EQUIPOS))
            	{
            		equiposFragment = (FragmentInfoPartidoEquipos) fm.findFragmentByTag(S_TAG_EQUIPOS);
            		if(equiposFragment == null)
            			ft.add(R.id.realtabcontent_mod_partido, new FragmentInfoPartidoEquipos(), S_TAG_EQUIPOS);
            		else            			
            			//ft.attach(fotoFragment);
            			ft.show(equiposFragment);
            	}
            	
            	if(tabId.equalsIgnoreCase(S_TAG_CHAT))
            	{
            		chatFragment = (FragmentInfoPartidoChat) fm.findFragmentByTag(S_TAG_CHAT);
            		if(chatFragment == null)
            			ft.add(R.id.realtabcontent_mod_partido, new FragmentInfoPartidoChat(), S_TAG_CHAT);
            		else            			
            			//ft.attach(ubicacionFragment);
            			ft.show(chatFragment);
            	}
            	
            	if(tabId.equalsIgnoreCase(S_TAG_INVITACIONES))
            	{
            		invitacionesFragment = (FragmentInfoPartidoInvitaciones) fm.findFragmentByTag(S_TAG_INVITACIONES);
            		if(invitacionesFragment == null)
            			ft.add(R.id.realtabcontent_mod_partido, new FragmentInfoPartidoInvitaciones(), S_TAG_INVITACIONES);
            		else            			
            			//ft.attach(notificacionesFragment);
            			ft.show(invitacionesFragment);
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
