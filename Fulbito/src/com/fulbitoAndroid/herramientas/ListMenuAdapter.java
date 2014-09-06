package com.fulbitoAndroid.herramientas;

import java.util.ArrayList;

import com.fulbitoAndroid.fulbito.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListMenuAdapter extends BaseAdapter{
	private Activity activity;
	ArrayList<ItemMenuLateral> arrItems;
	
	public ListMenuAdapter(Activity activity, ArrayList<ItemMenuLateral> arrItems)
	{
		super();
		this.activity = activity;
		this.arrItems = arrItems;
	}
	
	@Override 
	public Object getItem(int iPosition)
	{
		return arrItems.get(iPosition);
	}
	
	public int getCount()
	{
		return arrItems.size();
	}
	
	@Override
	public long getItemId(int iPosition)
	{
		return iPosition;
	}
	
	public static class Fila
	{
		TextView tvTituloItem;
		ImageView ivIcono;
	}

	public View getView(int iPosition, View vConvertView, ViewGroup vgParent)
	{	
		
		Fila fView = new Fila();;
		
		if(vConvertView == null)
		{
			LayoutInflater liInflator = activity.getLayoutInflater();
			vConvertView = liInflator.inflate(R.layout.item_menu_lateral, null);
		}
		
		ItemMenuLateral itm = arrItems.get(iPosition);
		
		if(itm != null)
		{
			fView.tvTituloItem = (TextView) vConvertView.findViewById(R.id.tvItem);
  			fView.tvTituloItem.setText(itm.getTitulo());
  			fView.ivIcono = (ImageView) vConvertView.findViewById(R.id.imgItem);
  			fView.ivIcono.setImageResource(itm.getIcono());
  			vConvertView.setTag(fView);
		}		
		else
		{
			fView = (Fila) vConvertView.getTag();
		}
		return vConvertView;
	}
}
