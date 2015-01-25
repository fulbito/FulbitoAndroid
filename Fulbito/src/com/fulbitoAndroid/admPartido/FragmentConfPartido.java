package com.fulbitoAndroid.admPartido;


import com.fulbitoAndroid.clases.Partido;
import com.fulbitoAndroid.clases.PartidoAmistoso;
import com.fulbitoAndroid.fulbito.DatosConfiguracion;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.gestionDB.PartidoAmistosoDB;
import com.fulbitoAndroid.gestionDB.PartidoDB;

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
	
	//Componentes de la interfaz gráfica
	private Spinner 		spTipoPartido;	
	private LinearLayout 	loContenedorFragment;	
	private ImageButton 	imgBtnAceptar;
	private ImageButton 	imgBtnCancelar;	
	
	private int iItemAnterior = -1;
	private int iTipoPartido;
	private int iFlagModo;
	
	//Constantes
	private static final String S_MODO	 			= "flag_modo";
	private static final String S_TAG_AMISTOSO 		= "AMISTOSO";
	private static final String S_TAG_DESAFIO_USR 	= "DESAFIO_USR";
	private static final String S_TAG_DESAFIO_EQ 	= "DESAFIO_EQ";
	
	//Método contructor de la clase utlizado para inicializar los parámetros pasados al Fragment 
	public static FragmentConfPartido newInstance(int iParamFlagModo, Partido cParamPartido) {
		//Creamos una instancia del fragment
		FragmentConfPartido f = new FragmentConfPartido();
        //Creamos una instancia Bundle, utilizada para pasar los parámetros al Fragment
		Bundle args = new Bundle();
        //Agregamos los parametros
		args.putInt(S_MODO, iParamFlagModo);
		//Cargamos en los parametros los datos del partido desde el Fragment FragmentrearPartido
		cParamPartido.vAgregarPartidoEnBundle(args);
		
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
        
        //Seteamos los eventos OnClick
        vAgregarEventoSpinnerTipoPartido();
        vAgregarEventoBotónAceptar();
        vAgregarEventoBotónCancelar();
        
        //Cargamos los datos del partido a la interfaz gráfica
        vCargarDatosInterfaz();
    }
    
    private void vCargarDatosInterfaz(){
    	
        //getString(key, defValue) was added in API 12. Use getString(key), as this will return null if the key doesn't exist.    	
        iFlagModo = getArguments().getInt(S_MODO);
        
        switch(iFlagModo)
        {
        	case FragmentCrearPartido.I_MODO_ALTA:
        		//Seteamos el tipo de partido default
        		iTipoPartido = DatosConfiguracion.TP_ID_AMISTOSO;
        		break;
        	case FragmentCrearPartido.I_MODO_MODIFICAR:
        		//Obtenemos los datos del partido a modificar desde la BD
				Partido cPartido = Partido.vObtenerPartidoDeBundle(getArguments());
        		//Seteamos el tipo de partido del partido a modificar
        		iTipoPartido = cPartido.getTipoPartido();        		
        		spTipoPartido.setSelection(iTipoPartido - 1);
        		break;
        }
        //Actualizamos la interfaz
        spTipoPartido.setSelection(iTipoPartido - 1);
    }

    //Método encargado de cargar el fragment indicado de acuerdo al tipo de partido seleccionado en el Spinner
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
    				case DatosConfiguracion.TP_ID_AMISTOSO:    					
                		ft.hide(fm.findFragmentByTag(S_TAG_AMISTOSO));
    					break;
    				case DatosConfiguracion.TP_ID_DESAFIO_USR:
    					ft.hide(fm.findFragmentByTag(S_TAG_DESAFIO_USR));
    					break;
    				case DatosConfiguracion.TP_ID_DESAFIO_EQ:
    					ft.hide(fm.findFragmentByTag(S_TAG_DESAFIO_EQ));
    					break;
    			}
    	    	    			    			
    	    	Partido cPartido = Partido.vObtenerPartidoDeBundle(getArguments());
    	    	
    			switch(pos)
    			{
    				case (DatosConfiguracion.TP_ID_AMISTOSO-1):    					
                		if(fm.findFragmentByTag(S_TAG_AMISTOSO) == null)
                			//el fragment NO está dentro del FragmentManager, se lo añade y se lo hace visible con add
                			//Aca en vez de hacer "new FragmentConfAmistoso()" debo hacer un new instance
                			ft.add(R.id.loContenedorTipoPartido, FragmentConfAmistoso.newInstance(iFlagModo, cPartido.getId()), S_TAG_AMISTOSO);
                		else  
                			//el fragment está dentro del FragmentManager, solo se lo hace visible con show
                			ft.show(fm.findFragmentByTag(S_TAG_AMISTOSO));
                		iItemAnterior = DatosConfiguracion.TP_ID_AMISTOSO;
                		iTipoPartido = DatosConfiguracion.TP_ID_AMISTOSO;
    					break;
    				case (DatosConfiguracion.TP_ID_DESAFIO_USR-1):
    					if(fm.findFragmentByTag(S_TAG_DESAFIO_USR) == null)
                			//el fragment NO está dentro del FragmentManager, se lo añade y se lo hace visible con add
                			ft.add(R.id.loContenedorTipoPartido, new FragmentConfDesafioUsr(), S_TAG_DESAFIO_USR);
                		else  
                			//el fragment está dentro del FragmentManager, solo se lo hace visible con show
                			ft.show(fm.findFragmentByTag(S_TAG_DESAFIO_USR));
    					iItemAnterior = DatosConfiguracion.TP_ID_DESAFIO_USR;
    					iTipoPartido = DatosConfiguracion.TP_ID_DESAFIO_USR;
    					break;
    				case (DatosConfiguracion.TP_ID_DESAFIO_EQ-1):
    					if(fm.findFragmentByTag(S_TAG_DESAFIO_EQ) == null)
                			//el fragment NO está dentro del FragmentManager, se lo añade y se lo hace visible con add
                			ft.add(R.id.loContenedorTipoPartido, new FragmentConfDesafioEq(), S_TAG_DESAFIO_EQ);
                		else  
                			//el fragment está dentro del FragmentManager, solo se lo hace visible con show
                			ft.show(fm.findFragmentByTag(S_TAG_DESAFIO_EQ));
    					iItemAnterior = DatosConfiguracion.TP_ID_DESAFIO_EQ;
    					iTipoPartido = DatosConfiguracion.TP_ID_DESAFIO_EQ;
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
        		Partido cPartidoNuevo = new Partido();
        		
        		switch(iItemAnterior)
    			{
    				case DatosConfiguracion.TP_ID_AMISTOSO:
    					//Analizar como resolver cuando se cambia de partido amistoso a otro tipo de partido, y viceversa.
    					//Hay que eliminar de la base de datos el tipo anterior
    					cPartidoNuevo = vProcesarPartidoAmistoso();
    					break;
    				case DatosConfiguracion.TP_ID_DESAFIO_USR:
    					cPartidoNuevo = vProcesarPartidoDesafioUsr();
    					break;
    				case DatosConfiguracion.TP_ID_DESAFIO_EQ:
    					cPartidoNuevo = vProcesarPartidoDesafioEq();
    					break;
    			}

                //Agregamos el fragment para completar los datos de configuración del partido
        		FragmentManager fragmentManager;
        		android.support.v4.app.Fragment fragment;				
        		//fragment = new FragmentInfoPartido();
        		fragment = FragmentInfoPartido.newInstance(cPartidoNuevo.getId());
        		fragmentManager = getActivity().getSupportFragmentManager();
        		
        		//Quitamos del BackStack los fragments ConfPartido y CrearPartido
        		fragmentManager.popBackStack();
        		fragmentManager.popBackStack();
        		
        		if(iFlagModo == FragmentCrearPartido.I_MODO_MODIFICAR)
        			fragmentManager.popBackStack();
        		
        		android.support.v4.app.FragmentTransaction ftFragmentTransaction = fragmentManager.beginTransaction();				
        		ftFragmentTransaction.replace(R.id.loFragmentContainerHome, fragment);
        		//Agregamos el fragment anterior a la pila para volver				
        		ftFragmentTransaction.addToBackStack(null);
        		ftFragmentTransaction.commit();                
            }
        }); 
    }
    
    private PartidoAmistoso vProcesarPartidoAmistoso(){
    	
    	android.support.v4.app.FragmentManager fm = getChildFragmentManager();
    	//Obtenemos el tipo de seleccion del fragment hijo FragmentConfAmistoso 
		FragmentConfAmistoso f = (FragmentConfAmistoso) fm.findFragmentByTag(S_TAG_AMISTOSO);
		int iTipoSeleccion = f.getTipoSeleccion();
		
		PartidoAmistosoDB cPartidoAmistosoDB = new PartidoAmistosoDB();
		PartidoAmistoso cPartidoNuevo = new PartidoAmistoso();

		//Obtenenemos los datos cargados en el fragment FragmentCrearPartido
		Partido cPartido = Partido.vObtenerPartidoDeBundle(getArguments());
		
		//Si el tipo de partido nuevo es diferente al anterior debemos tomar una acción, por el momento solo esta la lógice de Partido Amistoso
		if(cPartido.getTipoPartido() != iTipoPartido)
		{
			switch(cPartido.getTipoPartido())
			{
				case DatosConfiguracion.TP_ID_AMISTOSO:
					//NO SE HACE NADA
					break;
				case DatosConfiguracion.TP_ID_DESAFIO_USR:
					//HABRIA QUE BORRAR LA CONFIGURACION DE PARTIDO_DESAFIO_USR
					break;
				case DatosConfiguracion.TP_ID_DESAFIO_EQ:
					//HABRIA QUE BORRAR LA CONFIGURACION DE PARTIDO_DESAFIO_EQUIPO
					break;
			}
		}
		
		//Copiamos los datos al Partido Amistoso nuevo
		cPartidoNuevo.vCopiar(cPartido);
		//Seteamos los datos cargados en este fragment (FragmentConfPartido)
		int iTipoPartidoAnterior = cPartidoNuevo.getTipoPartido();
		cPartidoNuevo.setTipoPartido(iTipoPartido);
		cPartidoNuevo.setTipoSeleccion(iTipoSeleccion);
				
		switch(iFlagModo)
        {
        	case FragmentCrearPartido.I_MODO_ALTA:        		
        		//Provisorio: El Id lo debe setear el servidor
        		PartidoDB partidoDB = new PartidoDB();
        		cPartidoNuevo.setId(partidoDB.iSelectNextPartidoId());
				//Insertamos el nuevo Partido
				cPartidoAmistosoDB.bInsertar(cPartidoNuevo);
        		break;
        	case FragmentCrearPartido.I_MODO_MODIFICAR:
        		if(iTipoPartidoAnterior != DatosConfiguracion.TP_ID_AMISTOSO)
        		{
        			cPartidoAmistosoDB.bInsertarPartidoAmistoso(cPartidoNuevo);
        		}
        		//Hacemos el UPDATE en la BD
        		cPartidoAmistosoDB.bUpdate(cPartidoNuevo);
        		break;
        }
				
		return cPartidoNuevo;
    }

    private Partido vProcesarPartidoDesafioUsr(){
    	//TO DO
		PartidoDB partidoDB = new PartidoDB();
		//Obtenenemos los datos cargados en el fragment FragmentCrearPartido
		Partido cPartido = Partido.vObtenerPartidoDeBundle(getArguments());
		
		//Si el tipo de partido nuevo es diferente al anterior debemos tomar una acción, por el momento solo esta la lógice de Partido Amistoso
		if(cPartido.getTipoPartido() != iTipoPartido)
		{
			switch(cPartido.getTipoPartido())
			{
				case DatosConfiguracion.TP_ID_AMISTOSO:
					PartidoAmistosoDB cPartidoAmistosoDB = new PartidoAmistosoDB();
					cPartidoAmistosoDB.bDeletePartidoAmistosoById(cPartido.getId());
					break;
				case DatosConfiguracion.TP_ID_DESAFIO_USR:
					//HABRIA QUE BORRAR LA CONFIGURACION DE PARTIDO_DESAFIO_USR
					break;
				case DatosConfiguracion.TP_ID_DESAFIO_EQ:
					//HABRIA QUE BORRAR LA CONFIGURACION DE PARTIDO_DESAFIO_EQUIPO
					break;
			}
		}

		//Seteamos los datos cargados en este fragment (FragmentConfPartido)
		cPartido.setTipoPartido(iTipoPartido);
				
		switch(iFlagModo)
        {
        	case FragmentCrearPartido.I_MODO_ALTA:        		
        		//Provisorio: El Id lo debe setear el servidor        		
        		cPartido.setId(partidoDB.iSelectNextPartidoId());
				//Insertamos el nuevo Partido
        		partidoDB.bInsertarPartido(cPartido);
        		break;
        	case FragmentCrearPartido.I_MODO_MODIFICAR:
        		//Hacemos el UPDATE en la BD
        		partidoDB.bUpdatePartido(cPartido);
        		break;
        }
				
		return cPartido;
    }
    
    private Partido vProcesarPartidoDesafioEq(){
    	//TO DO
		PartidoDB partidoDB = new PartidoDB();
		//Obtenenemos los datos cargados en el fragment FragmentCrearPartido
		Partido cPartido = Partido.vObtenerPartidoDeBundle(getArguments());

		//Si el tipo de partido nuevo es diferente al anterior debemos tomar una acción, por el momento solo esta la lógice de Partido Amistoso
			if(cPartido.getTipoPartido() != iTipoPartido)
			{
				switch(cPartido.getTipoPartido())
				{
					case DatosConfiguracion.TP_ID_AMISTOSO:
						PartidoAmistosoDB cPartidoAmistosoDB = new PartidoAmistosoDB();
						cPartidoAmistosoDB.bDeletePartidoAmistosoById(cPartido.getId());
						break;
					case DatosConfiguracion.TP_ID_DESAFIO_USR:
						//HABRIA QUE BORRAR LA CONFIGURACION DE PARTIDO_DESAFIO_USR
						break;
					case DatosConfiguracion.TP_ID_DESAFIO_EQ:
						//HABRIA QUE BORRAR LA CONFIGURACION DE PARTIDO_DESAFIO_EQUIPO
						break;
				}
			}
		
		//Seteamos los datos cargados en este fragment (FragmentConfPartido)
		cPartido.setTipoPartido(iTipoPartido);
				
		switch(iFlagModo)
        {
        	case FragmentCrearPartido.I_MODO_ALTA:
        		
        		//Provisorio: El Id lo debe setear el servidor        		
        		cPartido.setId(partidoDB.iSelectNextPartidoId());
				//Insertamos el nuevo Partido
        		partidoDB.bInsertarPartido(cPartido);
        		break;
        	case FragmentCrearPartido.I_MODO_MODIFICAR:
        		//Hacemos el UPDATE en la BD
        		partidoDB.bUpdatePartido(cPartido);
        		break;
        }
				
		return cPartido;
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

