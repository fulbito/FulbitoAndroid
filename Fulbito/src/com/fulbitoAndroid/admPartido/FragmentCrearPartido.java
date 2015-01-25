package com.fulbitoAndroid.admPartido;

import java.util.Calendar;
import java.util.Locale;

import com.fulbitoAndroid.clases.Partido;
import com.fulbitoAndroid.fulbito.DatosConfiguracion;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.gestionDB.PartidoDB;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

public class FragmentCrearPartido extends Fragment {

	//Componentes de la interfaz gráfica
	private EditText 	edtTxtNombrePartido;
	private EditText 	edtTxtFechaPartido;
	private EditText 	edtTxtHoraPartido;
	private EditText 	edtTxtLugarPartido;	
	private ImageButton imgBtnModificarFecha;
	private ImageButton imgBtnModificarHora;	
	private ImageButton imgBtnAceptar;
	private ImageButton imgBtnCancelar;		
	private Spinner 	spCantJugadores;	
	private RadioGroup 	rgTipoVisibilidad;
	
	//Constantes
	private static final String S_PARTIDO_ID 		= "id_partido";
	private static final String S_MODO	 			= "flag_modo";
	private static final String S_NOMBRE_PARTIDO 	= "Desafio_"; // Debería depender del idioma
	private static final String S_HORA_PARTIDO 	 	= "21:00";	
	public static final int 	I_CANT_JUGADORES	= 5;
	public static final int 	I_MODO_ALTA		 	= 0;
	public static final int 	I_MODO_MODIFICAR 	= 1;
	
	//Atributos variables
	ArrayAdapter<CharSequence> adapterCantJug;
	private Partido cPartidoNuevo;	
	int iFlagModo;
	
	//Método contructor de la clase utlizado para inicializar los parámetros pasados al Fragment 
	public static FragmentCrearPartido newInstance(int iParamFlagModo, int iIdPartido) {
		//Creamos una instancia del fragment
		FragmentCrearPartido f = new FragmentCrearPartido();
        //Creamos una instancia Bundle, utilizada para pasar los parámetros al Fragment
		Bundle args = new Bundle();
        //Agregamos los parametros
		args.putInt(S_MODO, iParamFlagModo);
		
        switch(iParamFlagModo)
        {
        	case I_MODO_ALTA:
        		//En caso que el Fragment haya sido invocado para crear un nuevo partido
        		//no debe cargarse ningún dato
        		break;
        	case I_MODO_MODIFICAR:
        		//En caso que el Fragment haya sido invocado para modificar un partido
        		//deben cargarse los datos del partido a modificar
        		args.putInt(S_PARTIDO_ID, iIdPartido);                
        		break;
        }
        
        f.setArguments(args);

        return f;
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crear_partido, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        
        //Obtenemos los elementos de la interfaz grafica
        
        //EditText
        edtTxtNombrePartido = (EditText)getView().findViewById(R.id.edtTxtNombPartido);
        edtTxtFechaPartido 	= (EditText)getView().findViewById(R.id.edtTxtFechaPartido);    
        edtTxtHoraPartido 	= (EditText)getView().findViewById(R.id.edtTxtHoraPartido);    
        edtTxtLugarPartido 	= (EditText)getView().findViewById(R.id.edtTxtUbicPartido);
        //Botones
        imgBtnModificarFecha = (ImageButton)getView().findViewById(R.id.btnModificarFecha);
        imgBtnModificarHora = (ImageButton)getView().findViewById(R.id.btnModificarHora);
        imgBtnAceptar = (ImageButton)getView().findViewById(R.id.btnAceptar);
        imgBtnCancelar = (ImageButton)getView().findViewById(R.id.btnCancelar);
        //Spinner
        spCantJugadores = (Spinner)getView().findViewById(R.id.spCantJugadores);        
        adapterCantJug = ArrayAdapter.createFromResource(getActivity(), R.array.spCantJugadoresItems, R.layout.spinner_item);
        adapterCantJug.setDropDownViewResource(R.layout.spinner_item_list);
        spCantJugadores.setAdapter(adapterCantJug);
        //RadioGroup
        rgTipoVisibilidad = (RadioGroup)getView().findViewById(R.id.radGroupTipoVis);
                		
		//Cargamos los datos a mostrar a la interfaz
        vCargarDatosInterfaz();
        		
        //seteamos el evento OnClick de los botones                                 
        vAgregarEventoBotónAceptar();
        vAgregarEventoBotónCancelar();	  
        vAgregarEventoBotónModificarFecha();
        vAgregarEventoBotónModificarHora();
    }
    
    private void vCargarDatosInterfaz(){
    	
    	//Creamos una instancia de Partido para cargar los datos del Partido nuevo/a modificar
    	cPartidoNuevo = new Partido();
    	iFlagModo = getArguments().getInt(S_MODO);
    			
		String sNombre 	= "";
		String sFecha 	= "";
		String sHora 	= "";
		String sLugar 	= "";
		int iTipoVisibilidad = 0;
		int iCantJugadores = 0;
		    			
		switch(iFlagModo)
		{
			case I_MODO_ALTA:
				//Obtenemos la fecha de hoy
		        Calendar cal = Calendar.getInstance(Locale.getDefault());
		        String sFechaHoy = DateFormat.format("dd-MM-yyyy", cal).toString();
		        //Datos por default
				sNombre = S_NOMBRE_PARTIDO + sFechaHoy;
				sFecha 	= sFechaHoy;
				sHora 	= S_HORA_PARTIDO;
				sLugar 	= "";
				iCantJugadores = I_CANT_JUGADORES;
				iTipoVisibilidad = DatosConfiguracion.TV_ID_PRIVADO;
				break;    					
			case I_MODO_MODIFICAR:
				//Obtenemos los datos del partido a modificar desde la BD
				PartidoDB cPartidoDB = new PartidoDB();
				int iIdPartido = getArguments().getInt(S_PARTIDO_ID);
				cPartidoNuevo = cPartidoDB.parSelectPartidoById(iIdPartido);
				//Seteamos los datos del partido
				sNombre 			= cPartidoNuevo.getNombre();
				sFecha 				= cPartidoNuevo.getFecha(); 
				sHora 				= cPartidoNuevo.getHora();
				sLugar 				= cPartidoNuevo.getLugar();
				iCantJugadores 		= cPartidoNuevo.getCantJugadores();
				iTipoVisibilidad 	= cPartidoNuevo.getTipoVisibilidad();
				break;
		}
		
        //Seteamos el nombre del partido
		edtTxtNombrePartido.setText(sNombre);
		//Seteamos la fecha del partido
        edtTxtFechaPartido.setText(sFecha);
		//Seteamos la hora del partido
		edtTxtHoraPartido.setText(sHora);
		//Seteamos el lugar del partido
		edtTxtLugarPartido.setText(sLugar);
		//Seteamos la cantidad de jugadores
		int iPosition = adapterCantJug.getPosition(Integer.toString(iCantJugadores));
		spCantJugadores.setSelection(iPosition);
		//Seteamos la visibilidad del partido
		switch(iTipoVisibilidad) {
		case DatosConfiguracion.TV_ID_PRIVADO:
			rgTipoVisibilidad.check(R.id.radioBtnPrivado);
			break;
		case DatosConfiguracion.TV_ID_PUBLICO:
			rgTipoVisibilidad.check(R.id.radioBtnPublico);
			break;
		}
    }
    
    private void vAgregarEventoBotónAceptar(){
    	//Al tocar el boton Aceptar, debemos pasar a la siguiente configuración del partido
    	imgBtnAceptar.setOnClickListener(new OnClickListener() 
        {   
        	public void onClick(View v) 
            {           		        		            	
            	//Cargamos los datos del partido
        		cPartidoNuevo.setNombre(edtTxtNombrePartido.getText().toString());        		
        		cPartidoNuevo.setFecha(edtTxtFechaPartido.getText().toString()); //Revisar en que formato guardar la fecha y la hora       		
        		cPartidoNuevo.setHora(edtTxtHoraPartido.getText().toString());
        		cPartidoNuevo.setLugar(edtTxtLugarPartido.getText().toString());
        		cPartidoNuevo.setCantJugadores(Integer.parseInt(spCantJugadores.getSelectedItem().toString()));        		
        		switch(rgTipoVisibilidad.getCheckedRadioButtonId())
        		{
					case R.id.radioBtnPrivado:
						cPartidoNuevo.setTipoVisibilidad(DatosConfiguracion.TV_ID_PRIVADO);
						break;
					case R.id.radioBtnPublico:
						cPartidoNuevo.setTipoVisibilidad(DatosConfiguracion.TV_ID_PUBLICO);
						break;
        		}       		
        		
        		//Agregamos el fragment para completar los datos de configuración del partido
				FragmentManager fragmentManager;
				android.support.v4.app.Fragment fragment;
				//Al fragment le pasamos los datos del partido creado/modificado
				fragment 		= FragmentConfPartido.newInstance(iFlagModo, cPartidoNuevo);
				fragmentManager = getActivity().getSupportFragmentManager();
				//Añadimos el fragment
				android.support.v4.app.FragmentTransaction ftFragmentTransaction = fragmentManager.beginTransaction();				
				ftFragmentTransaction.replace(R.id.loFragmentContainerHome, fragment);
				//Agregamos el fragment anterior a la pila para volver
				ftFragmentTransaction.addToBackStack(null);
				ftFragmentTransaction.commit();				
            }
        }); 
    }
        
    private void vAgregarEventoBotónCancelar(){
    	//Al tocar el boton Cancelar, debemos volver al HOME
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
    
    private void vAgregarEventoBotónModificarFecha(){
    	
    	final OnDateSetListener odsl = new OnDateSetListener()
    	{  
    		public void onDateSet(DatePicker arg0, int iSelectYear, int iSelectMonth, int iSelectDay) 
			{
				String strSelectDay;
				String strSelectMonth;
				String sFecha;
				
				if(iSelectDay < 10)
					strSelectDay  = "0" + Integer.toString(iSelectDay);
				else
					strSelectDay = Integer.toString(iSelectDay);
				
				if(iSelectMonth < 10)
					strSelectMonth  = "0" + Integer.toString(iSelectMonth + 1);
				else
					strSelectMonth = Integer.toString(iSelectMonth + 1);
				
				sFecha = strSelectDay + "-" + strSelectMonth + "-" + iSelectYear;
				
				edtTxtFechaPartido.setText(sFecha);
				
				if(edtTxtNombrePartido.getText().toString().substring(0, 8).equals(S_NOMBRE_PARTIDO))
				{
					edtTxtNombrePartido.setText(S_NOMBRE_PARTIDO + sFecha);
				}
			} 
        };
    	
    	imgBtnModificarFecha.setOnClickListener(new OnClickListener()
        {
    		public void onClick(View arg0) {

			String[] arrFechaSeparada = edtTxtFechaPartido.getText().toString().split("-");

			int iDay = Integer.parseInt(arrFechaSeparada[0].trim());
			int iMonth = Integer.parseInt(arrFechaSeparada[1].trim()) - 1;
			int iYear = Integer.parseInt(arrFechaSeparada[2].trim());
  
    		DatePickerDialog datePickDiag = new DatePickerDialog(getActivity(), odsl, iYear, iMonth, iDay);
    		datePickDiag.show();
        }
        });
    }
    
    private void vAgregarEventoBotónModificarHora(){
    	
    	imgBtnModificarHora.setOnClickListener(new OnClickListener()
        {
    		public void onClick(View arg0) {

    			String[] arrHoraSeparada = edtTxtHoraPartido.getText().toString().split(":");

    			int iHour = Integer.parseInt(arrHoraSeparada[0].trim());
    			int iFecha = Integer.parseInt(arrHoraSeparada[1].trim());

                TimePickerDialog mTimePicker;
                
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker view, int hourOfDay,
							int minute) {
						String sHora;
						String sMinuto;
						
						// TODO Auto-generated method stub
						if(hourOfDay < 10)
							sHora  = "0" + Integer.toString(hourOfDay);
						else
							sHora = Integer.toString(hourOfDay);
						
						if(minute < 10)
							sMinuto  = "0" + Integer.toString(minute);
						else
							sMinuto = Integer.toString(minute);
						
						edtTxtHoraPartido.setText(sHora + ":" + sMinuto);
						
					}
                }, iHour, iFecha, true); //Yes 24 hour time

                mTimePicker.show();    					
        }
        });
    }
    
}

