package com.fulbitoAndroid.admUsuario;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fulbitoAndroid.fulbito.R;

import android.app.Activity;
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
 
public class FragmentRegistrar extends Fragment {
 
	//Atributos privados de la clase
	private static final String URL_REGISTRAR_USR = "http://www.fulbitoweb.com.ar/procesaRegistracion.php?alias=%1$s&email=%2$s&password=%3$s&registrar=true";
	private EditText edtTextAlias;
	private EditText edtTextCorreo;
	private EditText edtTextContrasena;
	private EditText edtTextConfirmarContrasena;
	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registrar, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
 
        //Obtenemos los controles EditText
        edtTextAlias = (EditText) getView().findViewById(R.id.edtTxtAlias);
    	edtTextCorreo = (EditText) getView().findViewById(R.id.edtTxtEmailRegistrar);
    	edtTextContrasena = (EditText) getView().findViewById(R.id.edtTxtContrasenaRegistrar);
    	edtTextConfirmarContrasena = (EditText) getView().findViewById(R.id.edtTxtConfirmarContrasena);
    	
    	//Seteamos el tipo de fuente a los TextView txtVwLogin y txtVwRegistrar
    	Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "Helvetica-Black-SemiBold.ttf");
        
        TextView txtVwRegistrar = (TextView) getView().findViewById(R.id.txtVwRegistrar);       
        txtVwRegistrar.setTypeface(typeFace);
        
        Button btnRegistrar = (Button) getView().findViewById(R.id.btnRegistrar);
        
        btnRegistrar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
            {   
	        	/*HttpPost httpPost = new HttpPost(String.format(URL_REGISTRAR_USR,
	        			edtTextAlias.getText().toString(),
	        			edtTextCorreo.getText().toString(),
	        			edtTextContrasena.getText().toString()));*/
	        	
	        	HttpGet httpget = new HttpGet(String.format(URL_REGISTRAR_USR,
	        			edtTextAlias.getText().toString(),
	        			edtTextCorreo.getText().toString(),
	        			edtTextContrasena.getText().toString()));
	        	
	        	HttpResponse response;
	        	HttpClient httpClient = new DefaultHttpClient();
	        	
        		try {
					response = httpClient.execute(httpget);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });        
    }
}
