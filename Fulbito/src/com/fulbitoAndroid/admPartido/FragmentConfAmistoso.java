package com.fulbitoAndroid.admPartido;

import com.fulbitoAndroid.fulbito.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentConfAmistoso extends Fragment {

	private String sMac = "HOLA";
	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_config_amistoso, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

    }
    
    public String getPrueba(){
    	return sMac;
    }
}

