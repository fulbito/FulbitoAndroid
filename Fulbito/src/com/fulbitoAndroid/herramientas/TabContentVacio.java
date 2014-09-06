package com.fulbitoAndroid.herramientas;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;
 
public class TabContentVacio implements TabContentFactory{
    private Context mContext;
 
    public TabContentVacio(Context context){
        mContext = context;
    }
 
    @Override
    public View createTabContent(String tag) {
        View v = new View(mContext);
        return v;
    }
}
