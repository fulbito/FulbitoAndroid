package com.fulbitoAndroid.admUsuario;

import java.util.Calendar;
import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.R;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FragmentModPerfilDatos extends Fragment {
	private static Usuario usrLogueado;
	private static final String TAG = "FragmentModPerfilDatos";	
	
	@Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mod_perfil_datos, container, false);                     
    }
	 
	@Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
      
      //MODIFICAR ALIAS
      EditText edtxtAlias = (EditText)getView().findViewById(R.id.edtTxtAlias); 
      usrLogueado = SingletonUsuarioLogueado.getUsuarioLogueado();  
      edtxtAlias.setText(usrLogueado.getAlias());
   
      //MODIFICAR FECHA DE NACIMIENTO
      EditText edtxtFechaNacimiento = (EditText) getView().findViewById(R.id.edtFechaNacimiento);
      
      if(!TextUtils.isEmpty(usrLogueado.getFechaNacimiento()))
      {
    	  String[] separated = usrLogueado.getFechaNacimiento().split("-");

    	  String strYear  = separated[0].trim();
    	  String strMonth = separated[1].trim();
    	  String strDay   = separated[2].trim();
    	  
    	  edtxtFechaNacimiento.setText(strDay + "-" + strMonth + "-" + strYear);  
      }
      
      final OnDateSetListener odsl = new OnDateSetListener()
      {  
    	  public void onDateSet(DatePicker arg0, int iSelectYear, int iSelectMonth, int iSelectDay) 
    	  {
    		  // TODO Auto-generated method stub
    		  EditText edtxtFechaNacimiento = (EditText) getView().findViewById(R.id.edtFechaNacimiento);
    		  String strSelectDay;
    		  if(iSelectDay < 10)
    			  strSelectDay  = "0" + Integer.toString(iSelectDay);
    		  else
    			  strSelectDay = Integer.toString(iSelectDay);
    		  edtxtFechaNacimiento.setText(strSelectDay + "-" + (iSelectMonth + 1) + "-" + iSelectYear);
    	  } 
      };
      
      ImageButton btnElegirFechaNacimiento = (ImageButton)getView().findViewById(R.id.btnFechaNacimiento);
      btnElegirFechaNacimiento.setOnClickListener(new OnClickListener()
      {
    	  public void onClick(View arg0) {
		  Calendar cal = Calendar.getInstance();
		  DatePickerDialog datePickDiag = new DatePickerDialog(getActivity(), odsl, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		  datePickDiag.show();
    	  }
      });

	//MODIFICAR TELEFONO
	EditText edtxtTelefono = (EditText)getView().findViewById(R.id.edtTxtTelefono);
	usrLogueado = SingletonUsuarioLogueado.getUsuarioLogueado();  
	edtxtTelefono.setText(usrLogueado.getTelefono());
           
	//MODIFICAR SEXO
	RadioGroup rgUsrSexo = (RadioGroup)getView().findViewById(R.id.gruporb);
	rgUsrSexo.clearCheck();
	  
	if(usrLogueado.getSexo() == "h")
	  rgUsrSexo.check(R.id.radioButton1);
	else
	  if(usrLogueado.getSexo() == "m")
		  rgUsrSexo.check(R.id.radioButton2);
	  else
		  rgUsrSexo.check(R.id.radioButton1); //Default
      
    //En caso de modificación del sexo, se genera el listener para el Button Radio.
     rgUsrSexo.setOnCheckedChangeListener(
      new RadioGroup.OnCheckedChangeListener() {
          public void onCheckedChanged(RadioGroup group, int checkedId) {
        	  
        	  switch(checkedId) {
        	  	case R.id.radioButton1:
        	  		usrLogueado.setSexo("m");
        	  		break;
        	  	case R.id.radioButton2:
        	  		usrLogueado.setSexo("h");
        	  		break;
        	  }
          }
      });
      
	  //MODIFICAR PASSWORD - ml - falta terminar
	  Button btnPassword = (Button)getView().findViewById(R.id.btnPassword);
	
	  btnPassword.setOnClickListener(new OnClickListener() {
	  	@Override
	       public void onClick(View v) {
	       	}
	  	});
      
      //GUARDAR CAMBIOS - ml - falta terminar
      Button btnGuardarCambios = (Button)getView().findViewById(R.id.btnGuardarPerfil);

      btnGuardarCambios.setOnClickListener(new OnClickListener() {
      	@Override
           public void onClick(View v) {
           	}
      	});

    }
}
