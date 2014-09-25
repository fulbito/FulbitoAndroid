package com.fulbitoAndroid.admUsuario;

import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.R;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TabHost;

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
            
      //MODIFICAR PASSWORD
      Button btnPassword = (Button)getView().findViewById(R.id.btnPassword);

      btnPassword.setOnClickListener(new OnClickListener() {
      	@Override
           public void onClick(View v) {
           	}
      	});
      
     
      //MODIFICAR TELEFONO
      EditText edtxtTelefono = (EditText)getView().findViewById(R.id.edtTxtTelefono);
      usrLogueado = SingletonUsuarioLogueado.getUsuarioLogueado();  
      edtxtTelefono.setText(usrLogueado.getTelefono());
      
      
      //MODIFICAR SEXO - VER xml
      RadioGroup rgUsrSexo = (RadioGroup)getView().findViewById(R.id.gruporb);
      rgUsrSexo.clearCheck();
      
      //Traer el sexo con getSexo si no lo tiene establecer M como default.
      if(usrLogueado.getSexo() == "M")
    	  rgUsrSexo.check(R.id.radioButton1);
      else
    	  if(usrLogueado.getSexo() == "F")
    		  rgUsrSexo.check(R.id.radioButton2);
    	  else
    		  rgUsrSexo.check(R.id.radioButton1); //Default
      
      //En caso de modificación del sexo, se genera el listener para el Button Radio.
      rgUsrSexo.setOnCheckedChangeListener(
          new RadioGroup.OnCheckedChangeListener() {
              public void onCheckedChanged(RadioGroup group, int checkedId) {
            	  
            	  switch(checkedId) {
            	  	case R.id.radioButton1:
            	  		usrLogueado.setSexo("M");
            	  		Log.d(TAG, "SEXO M");
            	  		break;
            	  	case R.id.radioButton2:
            	  		usrLogueado.setSexo("F");
            	  		Log.d(TAG, "SEXO F");
            	  		break;
            	  }
              }
          });
      
      //MODIFICAR FECHA DE NACIMIENTO
      
      
      
      
      //GUARDAR CAMBIOS
      Button btnGuardarCambios = (Button)getView().findViewById(R.id.btnGuardarPerfil);

      btnGuardarCambios.setOnClickListener(new OnClickListener() {
      	@Override
           public void onClick(View v) {
           	}
      	});

    }
}
