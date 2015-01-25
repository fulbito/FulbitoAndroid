package com.fulbitoAndroid.admPartido;

import com.fulbitoAndroid.clases.Partido;
import com.fulbitoAndroid.clases.PartidoAmistoso;
import com.fulbitoAndroid.fulbito.DatosConfiguracion;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.gestionDB.PartidoAmistosoDB;
import com.fulbitoAndroid.gestionDB.PartidoDB;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

public class FragmentConfAmistoso extends Fragment {

	//Componentes de la interfaz gráfica
	private RadioGroup 	rgTipoSeleccion;
	//Atributos variables
	private int iTipoSeleccion;
	private int iFlagModo;
	//Constantes
	private static final String S_MODO 		= "flag_modo";
	private static final String S_PART_ID 	= "id_partido";
	
	public static FragmentConfAmistoso newInstance(int iParamModo, int iIdPartido) {
		//Creamos una instancia del fragment
		FragmentConfAmistoso f = new FragmentConfAmistoso();
		//Creamos una instancia Bundle, utilizada para pasar los parámetros al Fragment
        Bundle args = new Bundle();
        //Agregamos los parametros
        args.putInt(S_MODO, iParamModo);
        args.putInt(S_PART_ID, iIdPartido);
               
        f.setArguments(args);

        return f;
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_config_amistoso, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        rgTipoSeleccion = (RadioGroup)getView().findViewById(R.id.radGruopTipoSeleccion);
        
        iFlagModo = getArguments().getInt(S_MODO);
		    			
		switch(iFlagModo)
		{
			case FragmentCrearPartido.I_MODO_ALTA:
				//Seteamos el tipo de seleccion default
				iTipoSeleccion = DatosConfiguracion.SELEC_ID_YO_LOS_ARMO;
				break;    					
			case FragmentCrearPartido.I_MODO_MODIFICAR:
				//Obtenemos el tipo de seleccion seteado para el Partido Amistoso a modificar
				int iIdPartido = getArguments().getInt(S_PART_ID);
				PartidoDB cPartidoDB = new PartidoDB();
				Partido cPartido = cPartidoDB.parSelectPartidoById(iIdPartido);
				
				if(cPartido.getTipoPartido() == DatosConfiguracion.TP_ID_AMISTOSO)
				{
					PartidoAmistosoDB cPartAmistosoDB = new PartidoAmistosoDB();
					PartidoAmistoso cPartAmistoso = new PartidoAmistoso();
					cPartAmistoso = cPartAmistosoDB.parSelectPartidoAmistosoById(iIdPartido);
					iTipoSeleccion = cPartAmistoso.getTipoSeleccion();
				}
				else
					iTipoSeleccion = DatosConfiguracion.SELEC_ID_YO_LOS_ARMO;
				break;
		}
        
		switch(iTipoSeleccion)
		{
			case DatosConfiguracion.SELEC_ID_YO_LOS_ARMO:
				rgTipoSeleccion.check(R.id.radioBtnYoLosArmo);
				break;    					
			case DatosConfiguracion.SELEC_ID_PAN_Y_QUESO:
				rgTipoSeleccion.check(R.id.radioBtnPanQueso);    					
				break;
			case DatosConfiguracion.SELEC_ID_DOS_JUGADORES:
				rgTipoSeleccion.check(R.id.radioBtnDosJugadores);				
				break;
			case DatosConfiguracion.SELEC_ID_ALEATORIO:
				rgTipoSeleccion.check(R.id.radioBtnAleatorio);
				break;
		}
        
        vAgregarEventoRadioButton();
    }
    
    public int getTipoSeleccion(){
    	return iTipoSeleccion;
    }
    
    private void vAgregarEventoRadioButton(){
    	rgTipoSeleccion.setOnCheckedChangeListener(
    			new RadioGroup.OnCheckedChangeListener() {
    				public void onCheckedChanged(RadioGroup group, int checkedId) {

    					switch(checkedId) {
    						case R.id.radioBtnYoLosArmo:
    							iTipoSeleccion = DatosConfiguracion.SELEC_ID_YO_LOS_ARMO;
    							break;
    						case R.id.radioBtnPanQueso:
    							iTipoSeleccion = DatosConfiguracion.SELEC_ID_PAN_Y_QUESO;
    							break;
    						case R.id.radioBtnDosJugadores:
    							iTipoSeleccion = DatosConfiguracion.SELEC_ID_DOS_JUGADORES;
    							break;
    						case R.id.radioBtnAleatorio:
    							iTipoSeleccion = DatosConfiguracion.SELEC_ID_ALEATORIO;
    							break;
    					}
    				}
    			});
    }
}

