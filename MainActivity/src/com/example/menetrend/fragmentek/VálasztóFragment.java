package com.example.menetrend.fragmentek;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.menetrend.Communicator;
import com.example.menetrend.Megallo;
import com.example.menetrend.Megállók;
import com.example.menetrend.R;

public class VálasztóFragment extends Fragment implements OnClickListener,
		android.widget.AdapterView.OnItemSelectedListener {

	public static String TAG = "VálasztóFragment";

	public TextView tvHonnan;
	public TextView tvHova;
	public Spinner snHonnan;
	public Spinner snHova;
	public Button btnOk;
	public Communicator comm;

	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.valaszto_fragment, container, false);

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		tvHonnan = (TextView) getActivity().findViewById(R.id.tvHonnan);
		tvHova = (TextView) getActivity().findViewById(R.id.tvHova);
		snHonnan = (Spinner) getActivity().findViewById(R.id.snHonnan);
		snHova = (Spinner) getActivity().findViewById(R.id.snHova);
		btnOk = (Button) getActivity().findViewById(R.id.btnOk);
		comm = (Communicator) getActivity();

		btnOk.setOnClickListener(this);
		snHonnan.setOnItemSelectedListener(this);
		snHova.setOnItemSelectedListener(this);

		@SuppressWarnings("unchecked")
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				Megállók.ALL);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		snHonnan.setAdapter(dataAdapter);
		snHova.setAdapter(dataAdapter);
		
		
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		snHonnan.setSelection(15);
		snHova.setSelection(0);
	}

	@Override
	public void onClick(View v) {
		comm.üzenet(((Megallo) snHonnan.getSelectedItem()).getId() + ";"
				+ ((Megallo) snHova.getSelectedItem()).getId());
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
