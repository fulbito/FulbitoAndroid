package com.fulbitoAndroid.admPartido;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.fulbitoAndroid.clases.Partido;
import com.fulbitoAndroid.clases.PartidoAmistoso;
import com.fulbitoAndroid.fulbito.DatosConfiguracion;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.gestionDB.FulbitoSQLiteHelper;
import com.fulbitoAndroid.gestionDB.PartidoAmistosoDB;
import com.fulbitoAndroid.gestionDB.PartidoDB;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FragmentInfoPartidoDatos extends Fragment{
	
	private Partido cPartido;
	
	private EditText edtTxtNombrePartido;
	private EditText edtTxtFechaHoraPartido;
	private EditText edtTxtLugarPartido;
	private EditText edtTxtCantJugadores;
	private EditText edtTxtVisibilidad;
	private EditText edtTxtTipoPartido;
	
	private Button btnModificar;
	private Button btnEliminar;
	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_partido_datos, container, false);                     
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);  
        
        FragmentInfoPartido fragmentPadre = (FragmentInfoPartido) getParentFragment();
        
        cPartido = fragmentPadre.getDatosPartido();
        
        //Obtenemos los elementos de la interfaz grafica
        
        //EditText
        edtTxtNombrePartido 	= (EditText)getView().findViewById(R.id.edtTxtNombPartido);
        edtTxtFechaHoraPartido 	= (EditText)getView().findViewById(R.id.edtTxtFechaHoraPartido);    
        edtTxtLugarPartido 		= (EditText)getView().findViewById(R.id.edtTxtLugarPartido);
        edtTxtCantJugadores 	= (EditText)getView().findViewById(R.id.edtTxtCantJugadores);    
        edtTxtVisibilidad 		= (EditText)getView().findViewById(R.id.edtTxtVisibilidad);
        edtTxtTipoPartido 		= (EditText)getView().findViewById(R.id.edtTxtTipoPartido);
        
        btnModificar	= (Button)getView().findViewById(R.id.btnModificar);
    	btnEliminar		= (Button)getView().findViewById(R.id.btnEliminar);;
        
        edtTxtNombrePartido.setText(cPartido.getNombre());
        
        String[] separated = cPartido.getFecha().split("-");

        int iYear  = Integer.parseInt(separated[2].trim());
		int iMonth = Integer.parseInt(separated[1].trim()) - 1;
		int iDay   = Integer.parseInt(separated[0].trim());
		
		GregorianCalendar gcCalendar = new GregorianCalendar(iYear, iMonth, iDay);
		String sDiaSemana = gcCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, getResources().getConfiguration().locale);
		sDiaSemana = sDiaSemana.toUpperCase();
        
        edtTxtFechaHoraPartido.setText(sDiaSemana + " " + cPartido.getFecha() + " " + cPartido.getHora() + " Hs.");
        edtTxtLugarPartido.setText(cPartido.getLugar());
        edtTxtCantJugadores.setText(Integer.toString(cPartido.getCantJugadores()));
        
        String sTipoVisibilidad = "";
        sTipoVisibilidad = DatosConfiguracion.sObtenerDescTipoVisibilidad(getActivity(), cPartido.getTipoVisibilidad());
        edtTxtVisibilidad.setText(sTipoVisibilidad);
        
        String sTipoPartido = "";
        String sTipoPartidoAdicional = "";
        
        sTipoPartido = DatosConfiguracion.sObtenerDescTipoPartido(getActivity(), cPartido.getTipoPartido());  
        switch(cPartido.getTipoPartido())
        {
        	case DatosConfiguracion.TP_ID_AMISTOSO:
        		PartidoAmistoso cPartAmistoso = (PartidoAmistoso) cPartido;
        		sTipoPartidoAdicional = DatosConfiguracion.sObtenerDescTipoSeleccion(getActivity(), cPartAmistoso.getTipoSeleccion());
        		sTipoPartido = sTipoPartido.concat(":" + sTipoPartidoAdicional);
        		break;
        	case DatosConfiguracion.TP_ID_DESAFIO_EQ:        		
        		break;
        	case DatosConfiguracion.TP_ID_DESAFIO_USR:
        		break;
        }
                     
        edtTxtTipoPartido.setText(sTipoPartido);
        
        vAgregarEventoBotónAceptar();
        vAgregarEventoBotónEliminar();
    }

    private void vAgregarEventoBotónAceptar(){
    	//Al tocar el boton Aceptar, debemos pasar a la siguiente configuración del partido
    	btnModificar.setOnClickListener(new OnClickListener() 
        {   
        	public void onClick(View v) 
            {           		        		
        		FragmentManager fragmentManager;
				android.support.v4.app.Fragment fragment;				
				//fragment = new FragmentConfPartido();
				fragment = FragmentCrearPartido.newInstance(FragmentCrearPartido.I_MODO_MODIFICAR, cPartido.getId());
				fragmentManager = getActivity().getSupportFragmentManager();				
				android.support.v4.app.FragmentTransaction ftFragmentTransaction = fragmentManager.beginTransaction();				
				ftFragmentTransaction.replace(R.id.loFragmentContainerHome, fragment);
				//Agregamos el fragment anterior a la pila para volver
				ftFragmentTransaction.addToBackStack(null);
				ftFragmentTransaction.commit();	        		
            }
        }); 
    }
        
    private void vAgregarEventoBotónEliminar(){
    	//Al tocar el boton Cancelar, debemos volver al HOME
    	//Al tocar el boton Aceptar, debemos pasar a la siguiente configuración del partido
    	btnEliminar.setOnClickListener(new OnClickListener() 
        {   
        	public void onClick(View v) 
            {           		
        		//Agregamos el fragment para completar los datos de configuración del partido
				FragmentManager fragmentManager;				
				
				fragmentManager = getActivity().getSupportFragmentManager();
				fragmentManager.popBackStack();

				switch(cPartido.getTipoPartido())
				{
					case DatosConfiguracion.TP_ID_AMISTOSO:
						//NO SE HACE NADA
						PartidoAmistosoDB partAmistosoDB = new PartidoAmistosoDB();
						partAmistosoDB.bDeleteById(cPartido.getId());
						break;
					case DatosConfiguracion.TP_ID_DESAFIO_USR:
						//HABRIA QUE BORRAR LA CONFIGURACION DE PARTIDO_DESAFIO_USR
						PartidoDB partidoUsrDB = new PartidoDB();
						partidoUsrDB.bDeletePartidoById(cPartido.getId());
						break;
					case DatosConfiguracion.TP_ID_DESAFIO_EQ:
						//HABRIA QUE BORRAR LA CONFIGURACION DE PARTIDO_DESAFIO_EQUIPO
						PartidoDB partidoEqDB = new PartidoDB();
						partidoEqDB.bDeletePartidoById(cPartido.getId());
						break;
				}							
            }
        }); 
    }
}
