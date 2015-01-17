package com.fulbitoAndroid.admPartido;


import com.fulbitoAndroid.clases.Partido;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.gestionDB.PartidoDB;
import com.fulbitoAndroid.gestionDB.UsuarioDB;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class FragmentConfPartido extends Fragment {
	
	//Atributos privados de la clase
	private Spinner spTipoPartido;
	
	private LinearLayout loContenedorFragment;
	
	private ImageButton imgBtnAceptar;
	private ImageButton imgBtnCancelar;	
	
	private int iItemAnterior = -1;
	
	private Partido cPartidoNuevo;
	
	//TIPOS DE PARTIDO
	private static final int TP_AMISTOSO = 0;
	private static final int TP_DESAFIO_USR = 1;
	private static final int TP_DESAFIO_EQ = 2;
	
	public static FragmentConfPartido newInstance(Partido cParamPartidoNuevo) {
		FragmentConfPartido f = new FragmentConfPartido();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        
        args.putString("nombre", cParamPartidoNuevo.getNombre());
        args.putString("fecha", cParamPartidoNuevo.getFecha());
        args.putString("hora", cParamPartidoNuevo.getHora());
        args.putString("lugar", cParamPartidoNuevo.getLugar());
        args.putInt("cant_jug", cParamPartidoNuevo.getCantJugadores());
        
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_config_partido, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        
        //Obtenemos los elementos de la interfaz grafica
        spTipoPartido = (Spinner)getView().findViewById(R.id.spTipoPartido);
        
        loContenedorFragment = (LinearLayout)getView().findViewById(R.id.loContenedorTipoPartido);
        
        imgBtnAceptar = (ImageButton)getView().findViewById(R.id.btnAceptar);
        imgBtnCancelar = (ImageButton)getView().findViewById(R.id.btnCancelar);
        
        vAgregarEventoSpinnerTipoPartido();
        
        //seteamos el evento OnClick del botón btnIngresar                                 
        vAgregarEventoBotónAceptar();
        vAgregarEventoBotónCancelar();               
    }
    
    private void vAgregarEventoSpinnerTipoPartido(){
    	spTipoPartido.setOnItemSelectedListener(new OnItemSelectedListener() {
    		@Override
    		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
    			
    			android.support.v4.app.FragmentTransaction ft;
    			android.support.v4.app.FragmentManager fm;
    			
    			fm =  getChildFragmentManager();
    	    	ft = fm.beginTransaction();
    	    	
    	    	switch(iItemAnterior)
    			{
    				case TP_AMISTOSO:    					
                		ft.hide(fm.findFragmentByTag("AMISTOSO"));
    					break;
    				case TP_DESAFIO_USR:
    					ft.hide(fm.findFragmentByTag("DESAFIO_USR"));
    					break;
    				case TP_DESAFIO_EQ:
    					ft.hide(fm.findFragmentByTag("DESAFIO_EQ"));
    					break;
    			}
    	    	    			    			
    			switch(pos)
    			{
    				case TP_AMISTOSO:    					
                		if(fm.findFragmentByTag("AMISTOSO") == null)
                			//el fragment NO está dentro del FragmentManager, se lo añade y se lo hace visible con add
                			ft.add(R.id.loContenedorTipoPartido, new FragmentConfAmistoso(), "AMISTOSO");
                		else  
                			//el fragment está dentro del FragmentManager, solo se lo hace visible con show
                			ft.show(fm.findFragmentByTag("AMISTOSO"));
                		iItemAnterior = TP_AMISTOSO;
    					break;
    				case TP_DESAFIO_USR:
    					if(fm.findFragmentByTag("DESAFIO_USR") == null)
                			//el fragment NO está dentro del FragmentManager, se lo añade y se lo hace visible con add
                			ft.add(R.id.loContenedorTipoPartido, new FragmentConfDesafioUsr(), "DESAFIO_USR");
                		else  
                			//el fragment está dentro del FragmentManager, solo se lo hace visible con show
                			ft.show(fm.findFragmentByTag("DESAFIO_USR"));
    					iItemAnterior = TP_DESAFIO_USR;
    					break;
    				case TP_DESAFIO_EQ:
    					if(fm.findFragmentByTag("DESAFIO_EQ") == null)
                			//el fragment NO está dentro del FragmentManager, se lo añade y se lo hace visible con add
                			ft.add(R.id.loContenedorTipoPartido, new FragmentConfDesafioEq(), "DESAFIO_EQ");
                		else  
                			//el fragment está dentro del FragmentManager, solo se lo hace visible con show
                			ft.show(fm.findFragmentByTag("DESAFIO_EQ"));
    					iItemAnterior = TP_DESAFIO_EQ;
    					break;
    			}
    			
    			//Commiteamos los cambios
    			ft.commit();
    		}
    		@Override
    		public void onNothingSelected(AdapterView<?> arg0){
    			
    		}
		});
    }

    private void vAgregarEventoBotónAceptar(){
    	//Al tocar el boton Aceptar, debemos pasar a la siguiente configuración del partido
    	imgBtnAceptar.setOnClickListener(new OnClickListener() 
        {   
        	public void onClick(View v) 
            {   
        		cPartidoNuevo = new Partido();
                
                cPartidoNuevo.setNombre(getArguments().getString("nombre", ""));
                cPartidoNuevo.setFecha(getArguments().getString("fecha", ""));
                cPartidoNuevo.setHora(getArguments().getString("hora", ""));
                cPartidoNuevo.setLugar(getArguments().getString("lugar", ""));
                cPartidoNuevo.setCantJugadores(getArguments().getInt("cant_jug", 0));
                
                android.support.v4.app.FragmentManager fm;
    			
    			fm =  getChildFragmentManager();
                
                switch(iItemAnterior)
    			{
    				case TP_AMISTOSO:    					
                		FragmentConfAmistoso f = (FragmentConfAmistoso) fm.findFragmentByTag("AMISTOSO");
                		String prueba;
                		prueba = f.getPrueba();                		
    					break;
    				case TP_DESAFIO_USR:
    					
    					break;
    				case TP_DESAFIO_EQ:
    					
    					break;
    			}
                
                //Insertamos los datos del usuario logueado en la tabla USUARIO
				PartidoDB partidoDB = new PartidoDB();
				partidoDB.bInsertarPartido(cPartidoNuevo);
            }
        }); 
    }
    
    
    private void vAgregarEventoBotónCancelar(){
    	//Al tocar el boton Cancelar, debemos volver al HOME
    	//Al tocar el boton Aceptar, debemos pasar a la siguiente configuración del partido
    	imgBtnCancelar.setOnClickListener(new OnClickListener() 
        {   
        	public void onClick(View v) 
            {   
        		//Agregamos el fragment para completar los datos de configuración del partido
				FragmentManager fragmentManager;				
				
				fragmentManager = getActivity().getSupportFragmentManager();
				fragmentManager.popBackStack();					
            }
        }); 
    }
}

