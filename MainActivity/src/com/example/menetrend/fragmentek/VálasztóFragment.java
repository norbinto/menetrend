package com.example.menetrend.fragmentek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
	public DatePicker dpDate;
	public TextView tvDate;

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
		dpDate = (DatePicker) getActivity().findViewById(R.id.dpDate);
		tvDate = (TextView) getActivity().findViewById(R.id.tvDate);

		btnOk.setOnClickListener(this);
		snHonnan.setOnItemSelectedListener(this);
		snHova.setOnItemSelectedListener(this);
		dpDate.setOnClickListener(this);

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
		switch (v.getId()) {
		case R.id.dpDate:
			dpDateOnDateChange();
			break;
		case R.id.button1:
			comm.üzenet(((Megallo) snHonnan.getSelectedItem()).getId() + ";"
					+ ((Megallo) snHova.getSelectedItem()).getId());
			break;

		default:
			break;
		}

	}

	public void dpDateOnDateChange() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date d = null;
		try {
			d = sdf.parse((dpDate.getYear() - 1900) + "-" + dpDate.getMonth()
					+ "-" + dpDate.getDayOfMonth());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SimpleDateFormat sdfDateTime = new SimpleDateFormat(
				"dd EEEE" );
		String dateStr = sdfDateTime.format(d);

		tvDate.setText(dateStr);
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
