package com.fulbitoAndroid.admPartido;

import java.util.ArrayList;
import java.util.List;

import com.fulbitoAndroid.clases.Partido;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.gestionDB.PartidoDB;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentMisPartidos extends Fragment{
	
	private ListView lvMisPartidos;
	private ArrayAdapter<String> listAdapter;
	@Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mis_partidos, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        
        lvMisPartidos = (ListView) getView().findViewById( R.id.mainListView );  
        
        //Obtenemos todos mis partidos (Aca deberían ser los partidos que cree y los que participo)
		PartidoDB partidoDB = new PartidoDB();

		List<Partido> lMisPartidos = new ArrayList<Partido>();
		
		lMisPartidos = partidoDB.partSelectAllPartido();
		
		// Create ArrayAdapter using the planet list.  
	    listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.fila_list_view);  
	      
	    // Add more planets. If you passed a String[] instead of a List<String>   
	    // into the ArrayAdapter constructor, you must not add more items.   
	    // Otherwise an exception will occur. 
	    for(int i=0; i < lMisPartidos.size(); i++)
	    {
	    	listAdapter.add(lMisPartidos.get(i).getNombre());
	    } 
	      
	    // Set the ArrayAdapter as the ListView's adapter.  
	    lvMisPartidos.setAdapter( listAdapter ); 
    }

}
