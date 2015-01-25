package com.fulbitoAndroid.admPartido;

import java.util.ArrayList;
import java.util.List;

import com.fulbitoAndroid.clases.Partido;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.gestionDB.PartidoDB;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentMisPartidos extends Fragment{
	
	//Componentes de la interfaz gráfica
	private ListView lvMisPartidos;
	//Atributos variables 
	private List<Partido> listMisPartidos = new ArrayList<Partido>();
	private ArrayAdapter<String> listPartidosAdapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mis_partidos, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        
    	super.onActivityCreated(state);
        
    	//Obtenemos los componentes de la interfaz
        lvMisPartidos = (ListView) getView().findViewById(R.id.mainListView);  
        
        //Obtenemos todos mis partidos (Aca deberían ser los partidos que cree y los que participo)
		PartidoDB partidoDB = new PartidoDB();				
		listMisPartidos = partidoDB.partSelectAllPartido();
		
		//Creamos el ArrayAdapter usando la lista de mis partidos.  
	    listPartidosAdapter = new ArrayAdapter<String>(getActivity(), R.layout.fila_list_view);  
	      
	    //Agregamos los nombres de los partidos
	    for(int i=0; i < listMisPartidos.size(); i++)
	    {
	    	listPartidosAdapter.add(listMisPartidos.get(i).getNombre());
	    } 
	      
	    //Seteamos el ArrayAdapter al ListView.  
	    lvMisPartidos.setAdapter( listPartidosAdapter ); 
	    	    
	    lvMisPartidos.setOnItemClickListener( 
	    		new OnItemClickListener() {
	    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			        	//Agregamos el fragment para completar los datos de configuración del partido
						FragmentManager fragmentManager;
						android.support.v4.app.Fragment fragment;				
						//fragment = new FragmentInfoPartido();
						fragment = FragmentInfoPartido.newInstance(listMisPartidos.get(arg2).getId());
						fragmentManager = getActivity().getSupportFragmentManager();
		
						android.support.v4.app.FragmentTransaction ftFragmentTransaction = fragmentManager.beginTransaction();				
						ftFragmentTransaction.replace(R.id.loFragmentContainerHome, fragment);
						//Agregamos el fragment anterior a la pila para volver				
						ftFragmentTransaction.addToBackStack(null);
						ftFragmentTransaction.commit();
	    			}
	    		});
	    
    }

}
