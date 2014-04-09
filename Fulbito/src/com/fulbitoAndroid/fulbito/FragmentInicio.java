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
	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

    	//Seteamos el tipo de fuente a los TextView txtVwLogin y txtVwRegistrar
    	Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "Helvetica-Black-SemiBold.ttf");
        
        TextView txtVwLogin = (TextView) getView().findViewById(R.id.txtVwLogin);        
        txtVwLogin.setTypeface(typeFace);        
        
        TextView txtVwRegistrar = (TextView) getView().findViewById(R.id.txtVwRegistrar);       
        txtVwRegistrar.setTypeface(typeFace);
        
      //seteamos el evento OnClick del botón btnIngresar
        Button btnIngresar = (Button) getView().findViewById(R.id.btnIngresar);
        
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
        
		Button btnRegistrar = (Button) getView().findViewById(R.id.btnRegistrar);

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

