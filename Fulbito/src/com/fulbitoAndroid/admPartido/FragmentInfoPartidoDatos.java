package com.fulbitoAndroid.admPartido;

import com.fulbitoAndroid.clases.Partido;
import com.fulbitoAndroid.fulbito.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentInfoPartidoDatos extends Fragment{
	
	private Partido cPartido;
	
	//INICIO - Componentes de la interfaz gráfica
	private EditText edtTxtNombrePartido;
	private EditText edtTxtFechaHoraPartido;
	private EditText edtTxtLugarPartido;
	private EditText edtTxtCantJugadores;
	private EditText edtTxtVisibilidad;
	private EditText edtTxtTipoPartido;
	
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
        
        edtTxtNombrePartido.setText(cPartido.getNombre());
        edtTxtFechaHoraPartido.setText(cPartido.getFecha() + " " + cPartido.getHora() + " Hs.");
        edtTxtLugarPartido.setText(cPartido.getLugar());
        edtTxtCantJugadores.setText(Integer.toString(cPartido.getCantJugadores()));
        
        String sTipoVisibilidad = "";
        int iTipoVisPublico = getActivity().getResources().getInteger(R.integer.id_tv_publico);
        int iTipoVisPrivado = getActivity().getResources().getInteger(R.integer.id_tv_privado);
        
        switch(cPartido.getTipoVisibilidad())
        {
        	case 1:
        		sTipoVisibilidad = getActivity().getResources().getString(R.string.txtPartidoPublico);
        		break;
        	case 2:
        		sTipoVisibilidad = getActivity().getResources().getString(R.string.txtPartidoPrivado);
        		break;
        }
        
        edtTxtVisibilidad.setText(sTipoVisibilidad);
        
        String sTipoPartido = "";
        
        String[] arrTiposPartidos = getResources().getStringArray(R.array.spTipoPartidoItems);
        
        if(cPartido.getTipoPartido()>0)
        	edtTxtTipoPartido.setText(arrTiposPartidos[cPartido.getTipoPartido()-1]);        
    }

}
