package com.fulbitoAndroid.admPartido;


import com.fulbitoAndroid.fulbito.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class FragmentConfPartido extends Fragment {
	
	//Atributos privados de la clase
	private Spinner spTipoPartido;
	
	private LinearLayout loContenedorFragment;
	
	private ImageButton imgBtnAceptar;
	private ImageButton imgBtnCancelar;	
	
	private int iItemAnterior = -1;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_config_partido, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        
        //Obtenemos los elementos de la interfaz grafica
        spTipoPartido = (Spinner)getView().findViewById(R.id.spTipoPartido);
        
        loContenedorFragment = (LinearLayout)getView().findViewById(R.id.loContenedorTipoPartido);
        
        imgBtnAceptar = (ImageButton)getView().findViewById(R.id.btnAceptar);
        imgBtnCancelar = (ImageButton)getView().findViewById(R.id.btnCancelar);
        
        vAgregarEventoSpinnerTipoPartido();
    }
    
    private void vAgregarEventoSpinnerTipoPartido(){
    	spTipoPartido.setOnItemSelectedListener(new OnItemSelectedListener() {
    		@Override
    		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
    			
    			android.support.v4.app.FragmentTransaction ft;
    			android.support.v4.app.FragmentManager fm;
    			
    			fm =  getChildFragmentManager();
    	    	ft = fm.beginTransaction();
    	    	
    	    	switch(iItemAnterior)
    			{
    				case 0:    					
                		ft.hide(fm.findFragmentByTag("AMISTOSO"));
    					break;
    				case 1:
    					ft.hide(fm.findFragmentByTag("DESAFIO_USR"));
    					break;
    				case 2:
    					ft.hide(fm.findFragmentByTag("DESAFIO_EQ"));
    					break;
    			}
    	    	
    			    			
    			switch(pos)
    			{
    				case 0:    					
                		if(fm.findFragmentByTag("AMISTOSO") == null)
                			//el fragment NO está dentro del FragmentManager, se lo añade y se lo hace visible con add
                			ft.add(R.id.loContenedorTipoPartido, new FragmentConfAmistoso(), "AMISTOSO");
                		else  
                			//el fragment está dentro del FragmentManager, solo se lo hace visible con show
                			ft.show(fm.findFragmentByTag("AMISTOSO"));
                		iItemAnterior = 0;
    					break;
    				case 1:
    					if(fm.findFragmentByTag("DESAFIO_USR") == null)
                			//el fragment NO está dentro del FragmentManager, se lo añade y se lo hace visible con add
                			ft.add(R.id.loContenedorTipoPartido, new FragmentConfDesafioUsr(), "DESAFIO_USR");
                		else  
                			//el fragment está dentro del FragmentManager, solo se lo hace visible con show
                			ft.show(fm.findFragmentByTag("DESAFIO_USR"));
    					iItemAnterior = 1;
    					break;
    				case 2:
    					if(fm.findFragmentByTag("DESAFIO_EQ") == null)
                			//el fragment NO está dentro del FragmentManager, se lo añade y se lo hace visible con add
                			ft.add(R.id.loContenedorTipoPartido, new FragmentConfDesafioEq(), "DESAFIO_EQ");
                		else  
                			//el fragment está dentro del FragmentManager, solo se lo hace visible con show
                			ft.show(fm.findFragmentByTag("DESAFIO_EQ"));
    					iItemAnterior = 2;
    					break;
    			}
    			
    			//Commiteamos los cambios
    			ft.commit();
    		}
    		@Override
    		public void onNothingSelected(AdapterView<?> arg0){
    			
    		}
		});
    }
}

