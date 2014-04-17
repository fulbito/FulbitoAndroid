package com.fulbitoAndroid.admUsuario;

import com.fulbitoAndroid.fulbito.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentModPerfilUbicacion extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mod_perfil_ubicacion, container, false);                     
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
    }
}
