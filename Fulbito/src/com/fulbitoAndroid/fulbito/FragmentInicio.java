/* ----------------------------------------------------------------------------- 
Nombre: 		FragmentInicio
Descripción:	Clase que controla la botonera principal de la interfaz de inicio
				en la que podemos elegir entre loguearse y regitrarse

Log de modificaciones:

Fecha		Autor		Descripción
17/04/2014	MAC			Creación
----------------------------------------------------------------------------- */

package com.fulbitoAndroid.fulbito;

import com.fulbitoAndroid.admUsuario.FragmentLogin;
import com.fulbitoAndroid.admUsuario.FragmentRegistrar;
import com.fulbitoAndroid.fulbito.R;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
 
public class FragmentInicio extends Fragment {
	
	TextView txtVwLogin;
	Button btnIngresar;
	Button btnRegistrar;
	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        //Obtenemos los controles de la interfaz gráfica
        txtVwLogin = (TextView) getView().findViewById(R.id.txtVwLogin);
        btnIngresar = (Button) getView().findViewById(R.id.btnInicioIngresar);
        btnRegistrar = (Button) getView().findViewById(R.id.btnInicioRegistrar);
    	
        //Seteamos el tipo de fuente a los TextView txtVwLogin y txtVwRegistrar
    	Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Black.ttf");                       
        txtVwLogin.setTypeface(typeFace);        
        
        TextView txtVwRegistrar = (TextView) getView().findViewById(R.id.txtVwRegistrar);       
        txtVwRegistrar.setTypeface(typeFace);
        
        //seteamos el evento OnClick del botón btnIngresar                                 
        vAgregarEventoBotónIngresar();
        vAgregarEventoBotónRegistrar();		
    }
    
    private void vAgregarEventoBotónIngresar(){
    	btnIngresar.setOnClickListener(new OnClickListener() 
        {   
        	public void onClick(View v) 
            {   
        		//Agregamos el fragment para completar los datos de usuario y loguearse
				FragmentManager fragmentManager;
				android.support.v4.app.Fragment fragment;				
				fragment = new FragmentLogin();
				fragmentManager = getActivity().getSupportFragmentManager();				
				android.support.v4.app.FragmentTransaction ftFragmentTransaction = fragmentManager.beginTransaction();				
				ftFragmentTransaction.replace(R.id.loFragmentContainerInicio, fragment);
				//Agregamos el fragment anterior a la pila para volver
				ftFragmentTransaction.addToBackStack(null);
				ftFragmentTransaction.commit();				
            }
        }); 
    }
    private void vAgregarEventoBotónRegistrar(){
    	btnRegistrar.setOnClickListener(new OnClickListener() 
		{   
			public void onClick(View v) 
			{   
	
				//Agregamos el fragment para completar los datos de usuario y registrarse
				FragmentManager fragmentManager;
				android.support.v4.app.Fragment fragment;				
				fragment = new FragmentRegistrar();
				fragmentManager = getActivity().getSupportFragmentManager();				
				android.support.v4.app.FragmentTransaction ftFragmentTransaction = fragmentManager.beginTransaction();				
				ftFragmentTransaction.replace(R.id.loFragmentContainerInicio, fragment);
				//Agregamos el fragment anterior a la pila para volver
				ftFragmentTransaction.addToBackStack(null);
				ftFragmentTransaction.commit();		
			}
		});
    }
}

