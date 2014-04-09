package com.fulbitoAndroid.admUsuario;

import com.fulbitoAndroid.fulbito.HomeActivity;
import com.fulbitoAndroid.fulbito.R;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 
public class FragmentLogin extends Fragment {
 
	//Atributos privados de la clase
	private EditText edtTextCorreo;
	private EditText edtTextContrasena;
	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
 
        //Obtenemos los controles EditText
    	edtTextCorreo = (EditText) getView().findViewById(R.id.edtTxtEmailLogin);
    	edtTextContrasena = (EditText) getView().findViewById(R.id.edtTxtContrasenaLogin);    	
    	
    	//Seteamos el tipo de fuente a los TextView txtVwLogin y txtVwRegistrar
    	Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "Helvetica-Black-SemiBold.ttf");
        
        TextView txtVwLogin = (TextView) getView().findViewById(R.id.txtVwLogin);        
        txtVwLogin.setTypeface(typeFace);        
        
        TextView txtTextOlvidoContrasena = (TextView) getView().findViewById(R.id.txtVwOlvidoContrasena);
        txtTextOlvidoContrasena.setTypeface(typeFace); 
        
      //seteamos el evento OnClick del botón btnIngresar
        Button btnIngresar = (Button) getView().findViewById(R.id.btnIngresar);
        
        btnIngresar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
            {   
                Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                    startActivity(intent);      
            }
        });                         
    }
}

