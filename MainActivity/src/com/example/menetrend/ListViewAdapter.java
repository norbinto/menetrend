package com.example.menetrend;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

@SuppressLint("ViewHolder") public class ListViewAdapter extends ArrayAdapter<Item> {
	private Context context;
	private ArrayList<Item> itemsArrayList;

	public ListViewAdapter(Context context, ArrayList<Item> itemsArrayList) {
		super(context, R.layout.listview_row, itemsArrayList);
		
		this.context = context;
		this.itemsArrayList = itemsArrayList;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {

		// 1. Create inflater
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// 2. Get rowView from inflater
		View rowView = inflater.inflate(R.layout.listview_row, parent, false);

		// 3. Get the two text view from the rowView
		TextView labelView = (TextView) rowView.findViewById(R.id.textView1);
		TextView leftView = (TextView) rowView.findViewById(R.id.textView2);

		// 4. Set the text for textView
		labelView.setText(itemsArrayList.get(position).getCim());
		leftView.setText(itemsArrayList.get(position).getMegallo());
		// rightView.setText(itemsArrayList.get(position).getDescription());

		// 5. return rowView
		return rowView;
	}
}
