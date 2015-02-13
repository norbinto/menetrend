package com.example.menetrend.fragmentek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
	public Button btnCsere;

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
		btnCsere = (Button) getActivity().findViewById(R.id.btnCsere);
		setCurrentDateOnView();

		btnOk.setOnClickListener(this);
		btnCsere.setOnClickListener(this);
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
		Dátumkiírás();
	}

	public void setCurrentDateOnView() {

		Calendar c = Calendar.getInstance();
		// set current date into datepicker
		dpDate.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						Dátumkiírás();
					}
				});

	}

	public void Dátumkiírás() {
		Calendar sCalendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.");

		try {
			sCalendar
					.setTime(sdf.parse((dpDate.getYear())
							+ "."
							+ ((dpDate.getMonth() + 1) + "."
									+ dpDate.getDayOfMonth() + ".")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK,
				Calendar.LONG, Locale.getDefault());
		tvDate.setText(sdf.format(sCalendar.getTime()) + " " + dayLongName);

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
		case R.id.btnOk:
			comm.üzenet(((Megallo) snHonnan.getSelectedItem()).getId() + ";"
					+ ((Megallo) snHova.getSelectedItem()).getId());
			break;

		case R.id.btnCsere:
			int tmp = snHonnan.getSelectedItemPosition();
			snHonnan.setSelection(snHova.getSelectedItemPosition());
			snHova.setSelection(tmp);
			break;
		default:
			break;
		}

	}

	public int getYear() {
		return dpDate.getYear();
	}

	public int getMonth() {
		return dpDate.getMonth();
	}

	public int getDay() {
		return dpDate.getDayOfMonth();
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
