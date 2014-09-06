package com.fulbitoAndroid.admUsuario;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.herramientas.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class FragmentModPerfilUbicacion extends Fragment{
	
	FragmentManager fmFragmentManager;
	SupportMapFragment mMapFragment;
	GoogleMap map;
	Handler handler;
	LocationListener locationListener;
	EditText edtTxtDireccion;
	//Button btnBuscar;
	ImageButton btnBuscar;
	
    Usuario usrUsuario;
    //Geocoder myLocation;
    
    Location mCurrentLocation;
    
    Circle mCircle;
    SeekBar seekBar;
    TextView seekBarValue;
    
    static final String TAG="FragmentModPerfilUbicacion";
    	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mod_perfil_ubicacion, container, false);                     
    }
    
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);                
        
        edtTxtDireccion = (EditText) getView().findViewById(R.id.edtTxtDireccion);                    
        btnBuscar = (ImageButton) getView().findViewById(R.id.button1);
        //Agregamos el google map fragment
        fmFragmentManager = getChildFragmentManager();        
        mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = fmFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.loMapContainerModPerfilUbicacion, mMapFragment);
        fragmentTransaction.commit();        
        
        //Se utiliza el handler porque el mapa tarda un poco en cargarse y el getMap() puede tirar null y terminar la aplicación con excepcion
        handler = new Handler();
        handler.postDelayed(new Runnable() {
        	@Override
        	public void run() 
        	{
        		map = mMapFragment.getMap();
        		if(map != null) 
        		{
        			//habilitamos en el mapa el botón que marca nuestra ubicacion actual
        			map.setMyLocationEnabled(true);
        			//seteamos la accion al hacer click en el boton "mi ubicacion actual"
        			map.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener() {
        		        @Override
        		        public boolean onMyLocationButtonClick() {
        		        	//Obtenemos la ubicacion actual
        		        	vObtenerUbicacionActual();
        		            return true;
        		        }
        		    });
        			
        			//Obtenemos la ubicacion actual al iniciar la interfaz de modificar ubicacion
		        	vObtenerUbicacionActual();        			
        		}
        		else 
        		{
        			handler.postDelayed(this, 500);
        		}
        	}
        }, 500);       
        
        btnBuscar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
	        {   				
	        	String sDireccion = edtTxtDireccion.getText().toString();
	        	vBuscarDireccion(sDireccion);						
	        }
        });
        
        vSetearSeekBar();
    }    

    public void vSetearSeekBar(){
        seekBar = (SeekBar)getView().findViewById(R.id.seekBarRadioUbicacion);
        seekBarValue = (TextView)getView().findViewById(R.id.txtKmRadios);

        seekBar.incrementProgressBy(1000);
        seekBar.setMax(30000);        
        
        seekBar.setProgress(3000);        
        seekBarValue.setText(Integer.toString(seekBar.getProgress()/1000) + " km");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            	mCircle.setRadius(progress);
            	seekBarValue.setText(Integer.toString(progress/1000) + " Km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    
    public void vObtenerUbicacionActual(){
    	GPSTracker gps = new GPSTracker(getActivity());

		//Verificamos que se pudo obtener la ubicación actual    
		if(gps.canGetLocation())
		{       
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();

			vDibujarMarcador(latitude, longitude);
			
			Location location = gps.getLocation();
			vObtenerDireccion(location);
		}
		else
		{
			// no se pudo obtener la ubicacion actual
			// el GPS o internet no estan habilitados
			// preguntamos si el usuario quiere habilitar el GPS
			gps.showSettingsAlert();
		}		
		return;
    }
    
    private void vDibujarMarcador(double latitude, double longitude){
    	map.clear();
    	LatLng currentPosition = new LatLng(latitude, longitude);    	
    	
    	double radiusInMeters = 3000.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill        
        
        CircleOptions circleOptions = new CircleOptions().center(currentPosition).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mCircle = map.addCircle(circleOptions);
        
        seekBar.setProgress(3000);
    	
    	map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,15));
        // Zoom in, animating the camera.
    	map.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
    	map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);    
    	map.addMarker(new MarkerOptions().position(currentPosition));    	
	}
    
    public void vObtenerDireccion(Location location) {
    	(new ObtenerDireccionTask(getActivity())).execute(location);
    } 
    
    public void vBuscarDireccion(String sDireccion) {
    	(new GetLatLongTask(getActivity())).execute(sDireccion);
    }
    
    /**
     * Subclase de AsyncTask que invoca a getFromLocation() en
     * background.
     * La definición de clase tiene estos tipos genéricos: 
     * Location - Un objeto que contiene la ubicación actual. 
     * Void - indica que las unidades de avance no se utilizan 
     * String - String que contiene la direccion a retornar en el OnPostExecute()
     */
     class ObtenerDireccionTask extends
             AsyncTask<Location, Void, String> {
         
    	 Context mContext;
         
         public ObtenerDireccionTask(Context context) {
             super();
             mContext = context;
         }

         /**
          * Obtiene la direccion de una ubicación a partir de 
          * una latitud y longitud
          *
          * @params params Uno o mas objetos Location
          * @return Un string que retorna la direccion relacionada 
          * con los puntos latitud y longitud pasados por parametro
          */
         @Override
         protected String doInBackground(Location... params) {
             
        	 Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
             // Get the current location from the input parameter list
             Location loc = params[0];
             // Create a list to contain the result address
             List<Address> addresses = null;
             try {
                 /*
                  * Return 1 address.
                  */            	 
            	 addresses = geocoder.getFromLocation(loc.getLatitude(),
                         loc.getLongitude(), 1);
             } catch (IOException e1) {
            	 Log.e(TAG, "IO Exception in getFromLocation()");
            	 e1.printStackTrace();
            	 return ("IO Exception trying to get address");
             } catch (IllegalArgumentException e2) {
            	// Error message to post in the log
                 String errorString = "Illegal arguments " +
                         Double.toString(loc.getLatitude()) +
                         " , " +
                         Double.toString(loc.getLongitude()) +
                         " passed to address service";
             Log.e("LocationSampleActivity", errorString);
             e2.printStackTrace();
             return errorString;
             }
             // If the reverse geocode returned an address
             if (addresses != null && addresses.size() > 0) {
                 // Get the first address
                 Address address = addresses.get(0);
                 /*
                  * Format the first line of address (if available),
                  * city, and country name.
                  */
                 String addressText = String.format(
                         "%s, %s, %s",
                         // If there's a street address, add it
                         address.getMaxAddressLineIndex() > 0 ?
                                 address.getAddressLine(0) : "",
                         // Locality is usually a city
                         address.getLocality(),
                         // The country of the address
                         address.getCountryName());
                 // Return the text
                 return addressText;
             } else {
                 return "No address found";
             }
         }
         
         /**
          * A method that's called once doInBackground() completes. Turn
          * off the indeterminate activity indicator and set
          * the text of the UI element that shows the address. If the
          * lookup failed, display the error message.
          */
         @Override
         protected void onPostExecute(String address) {
             // Display the results of the lookup.             
             edtTxtDireccion.setText(address);
         }

     }
     
     /*
      * Subclase de AsyncTask que invoca a getFromLocationName() en
      * background.
      * La definición de clase tiene estos tipos genéricos: 
      * Location - Un objeto que contiene la ubicación actual. 
      * Void - indica que las unidades de avance no se utilizan 
      * String - String que contiene la direccion a retornar en el OnPostExecute()
      */
      class GetLatLongTask extends
              AsyncTask<String, Void, Address> {
          Context mContext;
          public GetLatLongTask(Context context) {
              super();
              mContext = context;
          }

          /**
           * Get a Geocoder instance, get the latitude and longitude
           * look up the address, and return it
           *
           * @params params One or more Location objects
           * @return A string containing the address of the current
           * location, or an empty string if no address can be found,
           * or an error message
           */
          @Override
          protected Address doInBackground(String... params) {
              Geocoder geocoder =
                      new Geocoder(mContext, Locale.getDefault());
              // Get the current location from the input parameter list
              String sDireccion = params[0];
              // Create a list to contain the result address
              List<Address> addresses = null;
              try {
                  /*
                   * Return 1 address.
                   */
             	 addresses = geocoder.getFromLocationName(sDireccion, 1);
                  /*addresses = geocoder.getFromLocation(ubicacion.latitude,
                 		 ubicacion.latitude, 1);*/
              } catch (IOException e1) {
              Log.e("LocationSampleActivity",
                      "IO Exception in getFromLocation()");
              e1.printStackTrace();
              return null;
              } catch (IllegalArgumentException e2) {
             	// Error message to post in the log
                  String errorString = "Illegal arguments " +
                          sDireccion +
                          " passed to address service";
              Log.e("LocationSampleActivity", errorString);
              e2.printStackTrace();
              return null;
              }
              // If the reverse geocode returned an address
              if (addresses != null && addresses.size() > 0) {
                  // Get the first address
                  Address address = addresses.get(0);
                  /*
                   * Format the first line of address (if available),
                   * city, and country name.
                   */
                  

                  // Return the text
                  return address;
              } else {
                  return null;
              }
          }
          
          /**
           * A method that's called once doInBackground() completes. Turn
           * off the indeterminate activity indicator and set
           * the text of the UI element that shows the address. If the
           * lookup failed, display the error message.
           */
          @Override
          protected void onPostExecute(Address address) {
        	 
              // Display the results of the lookup.
         	if(address != null){
         		String addressText = String.format(
                        "%s, %s, %s",
                        // If there's a street address, add it
                        address.getMaxAddressLineIndex() > 0 ?
                                address.getAddressLine(0) : "",
                        // Locality is usually a city
                        address.getLocality(),
                        // The country of the address
                        address.getCountryName());
                        
                        vDibujarMarcador(address.getLatitude(), address.getLongitude());
                  
                  edtTxtDireccion.setText(addressText);
         	}         	
          }
      }
}


