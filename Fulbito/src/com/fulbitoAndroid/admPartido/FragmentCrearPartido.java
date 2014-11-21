package com.fulbitoAndroid.admPartido;

import java.util.Calendar;
import java.util.Locale;

import com.fulbitoAndroid.admUsuario.FragmentLogin;
import com.fulbitoAndroid.admUsuario.FragmentRegistrar;
import com.fulbitoAndroid.fulbito.R;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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
import android.widget.Spinner;

public class FragmentCrearPartido extends Fragment {
	
	//Atributos privados de la clase
	private EditText edtTxtNombrePartido;
	private EditText edtTxtFechaPartido;
	private EditText edtTxtHoraPartido;
	private EditText edtTxtLugarPartido;
	
	private ImageButton imgBtnModificarFecha;
	private ImageButton imgBtnModificarHora;	
	private ImageButton imgBtnAceptar;
	private ImageButton imgBtnCancelar;	
	
	private Spinner spCantJugadores;
	
	private RadioButton radBtnPrivado;
	private RadioButton radBtnPublico;
	
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
        edtTxtNombrePartido = (EditText)getView().findViewById(R.id.edtTxtNombPartido);
        edtTxtFechaPartido = (EditText)getView().findViewById(R.id.edtTxtFechaPartido);    
        edtTxtHoraPartido = (EditText)getView().findViewById(R.id.edtTxtHoraPartido);    
        edtTxtLugarPartido = (EditText)getView().findViewById(R.id.edtTxtUbicPartido);
        
        imgBtnModificarFecha = (ImageButton)getView().findViewById(R.id.btnModificarFecha);
        imgBtnModificarHora = (ImageButton)getView().findViewById(R.id.btnModificarHora);
        imgBtnAceptar = (ImageButton)getView().findViewById(R.id.btnAceptar);
        imgBtnCancelar = (ImageButton)getView().findViewById(R.id.btnCancelar);
        
        spCantJugadores = (Spinner)getView().findViewById(R.id.spCantJugadores);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spCantJugadoresItems, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item_list);
        spCantJugadores.setAdapter(adapter);
        
        radBtnPrivado = (RadioButton)getView().findViewById(R.id.radioBtnPrivado);
        radBtnPublico = (RadioButton)getView().findViewById(R.id.radioBtnPublico);
        
        Calendar cal = Calendar.getInstance(Locale.getDefault());		
        String sFechaHoy = DateFormat.format("dd-MM-yyyy", cal).toString();
		
		String sNombrePartido = "Desafio_" + sFechaHoy;
		
		edtTxtNombrePartido.setText(sNombrePartido);
		edtTxtFechaPartido.setText(sFechaHoy);
		edtTxtHoraPartido.setText("21:00");
		
		radBtnPrivado.setChecked(true);	
		
        //seteamos el evento OnClick del botón btnIngresar                                 
        vAgregarEventoBotónAceptar();
        vAgregarEventoBotónCancelar();	  
        vAgregarEventoBotónModificarFecha();
    }
    
    private void vAgregarEventoBotónAceptar(){
    	imgBtnAceptar.setOnClickListener(new OnClickListener() 
        {   
        	public void onClick(View v) 
            {   
        		//Agregamos el fragment para completar los datos de usuario y loguearse
				FragmentManager fragmentManager;
				android.support.v4.app.Fragment fragment;				
				fragment = new FragmentConfPartido();
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

    }
    
    private void vAgregarEventoBotónModificarFecha(){
    	
    	final OnDateSetListener odsl = new OnDateSetListener()
    	{  
    		public void onDateSet(DatePicker arg0, int iSelectYear, int iSelectMonth, int iSelectDay) 
			{
				String strSelectDay;
				String sFecha;
				
				if(iSelectDay < 10)
					strSelectDay  = "0" + Integer.toString(iSelectDay);
				else
					strSelectDay = Integer.toString(iSelectDay);
				
				sFecha = strSelectDay + "-" + (iSelectMonth + 1) + "-" + iSelectYear;
				
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
			int iMonth = Integer.parseInt(arrFechaSeparada[1].trim());
			int iYear = Integer.parseInt(arrFechaSeparada[2].trim());
  
    		DatePickerDialog datePickDiag = new DatePickerDialog(getActivity(), odsl, iYear, iMonth, iDay);
    		datePickDiag.show();
        }
        });
    }
    
    private void vAgregarEventoBotónModificarHora(){

    }
}

