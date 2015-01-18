package com.fulbitoAndroid.admPartido;

import java.util.Calendar;
import java.util.Locale;

import com.fulbitoAndroid.admUsuario.FragmentLogin;
import com.fulbitoAndroid.admUsuario.FragmentRegistrar;
import com.fulbitoAndroid.clases.Partido;
import com.fulbitoAndroid.fulbito.R;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

public class FragmentCrearPartido extends Fragment {
	
	//Atributos privados de la clase
	
	//INICIO - Componentes de la interfaz gráfica
	private EditText edtTxtNombrePartido;
	private EditText edtTxtFechaPartido;
	private EditText edtTxtHoraPartido;
	private EditText edtTxtLugarPartido;
	
	private ImageButton imgBtnModificarFecha;
	private ImageButton imgBtnModificarHora;	
	private ImageButton imgBtnAceptar;
	private ImageButton imgBtnCancelar;	
	
	private Spinner spCantJugadores;
	
	private RadioGroup rgTipoVisibilidad;
	
	private RadioButton radBtnPrivado;
	private RadioButton radBtnPublico;
	// FIN - Componentes de la interfaz gráfica
	
	static final String NOMBRE_PARTIDO = "Desafio_";
	static final String HORA_PARTIDO = "21:00";
	
	private Partido cPartidoNuevo;
	int iTipoVisibilidad;
	
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spCantJugadoresItems, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item_list);
        spCantJugadores.setAdapter(adapter);
        //RadioGroup
        rgTipoVisibilidad = (RadioGroup)getView().findViewById(R.id.radGroupTipoVis);        
        rgTipoVisibilidad.check(R.id.radioBtnPrivado); //Default   
        iTipoVisibilidad = getActivity().getResources().getInteger(R.integer.id_tv_privado);
                		
		//Seteamos los valores por default
        
        //Seteamos la fecha
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        String sFechaHoy = DateFormat.format("dd-MM-yyyy", cal).toString();
        edtTxtFechaPartido.setText(sFechaHoy);
        //Seteamos el nombre del partido
        String sNombrePartido = NOMBRE_PARTIDO + sFechaHoy;
		edtTxtNombrePartido.setText(sNombrePartido);
		//Seteamos la hora del partido
		edtTxtHoraPartido.setText(HORA_PARTIDO);
		
        //seteamos el evento OnClick de los botones                                 
        vAgregarEventoBotónAceptar();
        vAgregarEventoBotónCancelar();	  
        vAgregarEventoBotónModificarFecha();
        vAgregarEventoBotónModificarHora();
        vAgregarEventoRadioButton();
    }
    
    private void vAgregarEventoBotónAceptar(){
    	//Al tocar el boton Aceptar, debemos pasar a la siguiente configuración del partido
    	imgBtnAceptar.setOnClickListener(new OnClickListener() 
        {   
        	public void onClick(View v) 
            {   
        		//Creamos una instancia de Partido para cargar los datos del nuevo Partido creado
        		cPartidoNuevo = new Partido();
        		
        		cPartidoNuevo.setNombre(edtTxtNombrePartido.getText().toString());
        		//Revisar en que formato guardar la fecha y la hora
        		cPartidoNuevo.setFecha(edtTxtFechaPartido.getText().toString());        		
        		cPartidoNuevo.setHora(edtTxtHoraPartido.getText().toString());
        		cPartidoNuevo.setLugar(edtTxtLugarPartido.getText().toString());
        		cPartidoNuevo.setCantJugadores(Integer.parseInt(spCantJugadores.getSelectedItem().toString()));        		
        		cPartidoNuevo.setTipoVisibilidad(iTipoVisibilidad);
        		
        		//Agregamos el fragment para completar los datos de configuración del partido
				FragmentManager fragmentManager;
				android.support.v4.app.Fragment fragment;				
				//fragment = new FragmentConfPartido();
				fragment = FragmentConfPartido.newInstance(cPartidoNuevo);
				fragmentManager = getActivity().getSupportFragmentManager();				
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
				
				if(edtTxtNombrePartido.getText().toString().substring(0, 8).equals("Desafio_"))
				{
					edtTxtNombrePartido.setText("Desafio_" + sFecha);
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
                }, iHour, iFecha, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
    						
        }
        });
    }

    private void vAgregarEventoRadioButton(){
    	rgTipoVisibilidad.setOnCheckedChangeListener(
    			new RadioGroup.OnCheckedChangeListener() {
    				public void onCheckedChanged(RadioGroup group, int checkedId) {
    	  
    					switch(checkedId) {
    						case R.id.radioBtnPrivado:
    							iTipoVisibilidad = getActivity().getResources().getInteger(R.integer.id_tv_privado);
    							break;
    						case R.id.radioBtnPublico:
    							iTipoVisibilidad = getActivity().getResources().getInteger(R.integer.id_tv_publico);
    							break;
    					}
    				}
    			});
    }
}

