package com.example.menetrend.fragmentek;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.menetrend.Item;
import com.example.menetrend.ListViewAdapter;
import com.example.menetrend.Megallo;
import com.example.menetrend.Megállók;
import com.example.menetrend.R;
import com.example.menetrend.buszDBS.Busz;

public class ListázóFragment extends Fragment {

	public static String TAG = "ListázóFragment";

	private ListView listView;
	TextView tvTest;
	ListViewAdapter adapter;
	LinkedList<Busz> values;
	String tvTestText;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.listazo_fragment, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView = (ListView) getActivity().findViewById(R.id.listView);
		tvTest = (TextView) getActivity().findViewById(R.id.tvTest);
	}

	@Override
	public void onStart() {
		super.onStart();
		try {
			tvTest.setText(tvTestText);
			adapter = new ListViewAdapter(getActivity(),
					getListArrayData(values));
			listView.setAdapter(adapter);

			// időponthoz ugrik
			Calendar cal = Calendar.getInstance();
			for (int i = 0; i < values.size(); i++) {
				int a = Integer
						.parseInt(values.get(i).getIndul().subSequence(0, 2)
								.toString()
								+ ""
								+ values.get(i).getIndul().subSequence(3, 5)
										.toString());
				int b = Integer.parseInt(cal.get(Calendar.HOUR_OF_DAY) + ""
						+ (cal.get(Calendar.MINUTE)<10?"0"+cal.get(Calendar.MINUTE):cal.get(Calendar.MINUTE)));
				Log.v("LISTÁZÓFRAGMENT a+b", a + " + " + b);
				if (a < b) {
					try {
						listView.setSelection(i + 1);
					} catch (Exception ex) {
						listView.setSelection(i);
					}
				}
			}

		} catch (Exception ex) {
		}
	}

	public void állító(String data, LinkedList<Busz> dailyAllBuszList) {

		int honnan = Integer.parseInt(data.split(";")[0]);
		int hova = Integer.parseInt(data.split(";")[1]);

		if (honnan == hova) {
			tvTestText = "ugyanaz a megálló?? nemár....";
		} else {
			String tmp1 = "", tmp2 = "";
			for (int i = 0; i < Megállók.ALL.size(); i++) {
				if (((Megallo) Megállók.ALL.get(i)).getId() == honnan) {
					tmp1 = ((Megallo) Megállók.ALL.get(i)).getNev();
				}
				if (((Megallo) Megállók.ALL.get(i)).getId() == hova) {
					tmp2 = ((Megallo) Megállók.ALL.get(i)).getNev();
				}
			}
			tvTestText = tmp1 + " ->\n" + tmp2;
			// SÁMSON-DEBRECEN ÚTVONAL
			if ((honnan == 1415 ? 14 : honnan) < ((hova == 1415) ? 14 : hova)) {
				honnanhova(honnan, hova, dailyAllBuszList, true);
				values = dailyAllBuszList;
			} else {
				// DEBRECEN-SÁMSON ÚTVONAL
				honnanhova(honnan, hova, dailyAllBuszList, false);
				values = dailyAllBuszList;
			}
		}
	}

	private ArrayList<Item> getListArrayData(List<Busz> values) {

		ArrayList<Item> ret = new ArrayList<Item>();
		for (Busz busz : values) {
			String item2 = "";
			if ("DB".equals(busz.getHonnan())) {
				if ("HS".equals(busz.getHova())) {
					item2 += "Debrecen - Hajdúsámson (Kiscsere)";
				} else {
					if ("HK".equals(busz.getHova())) {
						item2 += "Debrecen - Hajdúsámson (Szűcs utca)";
					} else {
						if ("NA".equals(busz.getHova())) {
							item2 += "Debrecen - Nyíradony (Kiscsere)";
						} else {
							if ("SZ".equals(busz.getHova())) {
								item2 += "Debrecn - Nyíradony (Kiscsere)";
							} else {
								item2 += "Sámsonkert, Szűcs utca - Debrecen";
							}
						}
					}
				}
			} else {
				if ("HS".equals(busz.getHonnan())) {
					item2 += "Hajdúsámson - Debrecen (Kiscsere)";
				} else {
					if ("HK".equals(busz.getHonnan())) {

						item2 += "Hajdúsámson -Debrecen (Szűcs utca)";
					} else {
						if ("NA".equals(busz.getHonnan())) {
							item2 += "Nyíradony - Debrecen (Kiscsere)";
						} else {
							if ("SZ".equals(busz.getHonnan())) {
								item2 += "Szakolykert - Debrecen(Kiscsere)";
							} else {
								item2 += "Sámsonkert, Szűcs utca - Debrecen";
							}
						}
					}
				}
			}
			ret.add(new Item(busz.getIndul(), item2));
		}
		return ret;
	}

	// REFAKTORIZÁLNI!!!!!!!!!!!!
	private void honnanhova(int honnan_id, int hova_id,
			LinkedList<Busz> values, boolean sámsonDebrecen) {
		//először törli ami fölösleges mert a másik irányba megy
		if (sámsonDebrecen) {
			// ami DB-ből indul delete
			for (int i = 0; i < values.size(); i++) {
				if ("DB".equals(((Busz) values.get(i)).getHonnan())) {
					values.remove(i);
					i--;
				}
			}
		} else {
			// ami NEM DB-ből indul delete
			for (int i = 0; i < values.size(); i++) {
				if ("DB".equals(((Busz) values.get(i)).getHova())) {
					values.remove(i);
					i--;
				}
			}
		}
		
		boolean nincsbenne = true;
		Megállók megállók = new Megállók();
		int menetido = 0;
		for (Megallo megallo : megállók.HS_DB) {
			if (megallo.getId() == honnan_id) {
				menetido = megallo.getIdo();
				for (Megallo megallo2 : megállók.HS_DB) {
					if (megallo2.getId() == hova_id) {
						nincsbenne = false;
						break;
					}
				}
			}
		}
		if (nincsbenne) {
			for (int i = 0; i < values.size(); i++) {
				if ("HS".equals(values.get(i).getHonnan())
						|| "HS".equals(values.get(i).getHova())) {
					values.remove(i);
					i--;
				}
			}
		} else {
			for (int i = 0; i < values.size(); i++) {
				if ("HS".equals(values.get(i).getHonnan())
						|| "HS".equals(values.get(i).getHova())) {
					if (sámsonDebrecen) {
						values.get(i).setIndul(
								menetidőHozzáadás(values.get(i), menetido));
					} else {

						values.get(i).setIndul(
								menetidőHozzáadás(values.get(i), megállók.HS_DB
										.getLast().getIdo() - menetido));
					}
				}
			}
		}
		nincsbenne = true;
		for (Megallo megallo : megállók.NA_DB) {
			if (megallo.getId() == honnan_id) {
				menetido = megallo.getIdo();
				for (Megallo megallo2 : megállók.NA_DB) {
					if (megallo2.getId() == hova_id) {
						nincsbenne = false;
						break;
					}
				}
			}
		}
		if (nincsbenne) {
			for (int i = 0; i < values.size(); i++) {
				if ("NA".equals(values.get(i).getHonnan())
						|| "NA".equals(values.get(i).getHova())) {
					values.remove(i);
					i--;
				}
			}
		} else {
			for (int i = 0; i < values.size(); i++) {
				if ("NA".equals(values.get(i).getHonnan())
						|| "NA".equals(values.get(i).getHova())) {
					if (sámsonDebrecen) {
						values.get(i).setIndul(
								menetidőHozzáadás(values.get(i), menetido));
					} else {

						values.get(i).setIndul(
								menetidőHozzáadás(values.get(i), megállók.NA_DB
										.getLast().getIdo() - menetido));
					}
				}
			}
		}

		nincsbenne = true;
		for (Megallo megallo : megállók.SZ_DB) {
			if (megallo.getId() == honnan_id) {
				menetido = megallo.getIdo();
				for (Megallo megallo2 : megállók.SZ_DB) {
					if (megallo2.getId() == hova_id) {
						nincsbenne = false;
						break;
					}
				}
			}
		}
		if (nincsbenne) {
			for (int i = 0; i < values.size(); i++) {
				if ("SZ".equals(values.get(i).getHonnan())
						|| "SZ".equals(values.get(i).getHova())) {
					values.remove(i);
					i--;
				}
			}
		} else {
			for (int i = 0; i < values.size(); i++) {
				if ("SZ".equals(values.get(i).getHonnan())
						|| "SZ".equals(values.get(i).getHova())) {
					if (sámsonDebrecen) {
						values.get(i).setIndul(
								menetidőHozzáadás(values.get(i), menetido));
					} else {

						values.get(i).setIndul(
								menetidőHozzáadás(values.get(i), megállók.SZ_DB
										.getLast().getIdo() - menetido));
					}
				}
			}
		}

		nincsbenne = true;
		for (Megallo megallo : megállók.SK_DB) {
			if (megallo.getId() == honnan_id) {
				menetido = megallo.getIdo();
				for (Megallo megallo2 : megállók.SK_DB) {
					if (megallo2.getId() == hova_id) {
						nincsbenne = false;
						break;
					}
				}
			}
		}
		if (nincsbenne) {
			for (int i = 0; i < values.size(); i++) {
				if ("SK".equals(values.get(i).getHonnan())
						|| "SK".equals(values.get(i).getHova())) {
					values.remove(i);
					i--;
				}
			}
		} else {
			for (int i = 0; i < values.size(); i++) {
				if ("SK".equals(values.get(i).getHonnan())
						|| "SK".equals(values.get(i).getHova())) {
					if (sámsonDebrecen) {
						values.get(i).setIndul(
								menetidőHozzáadás(values.get(i), menetido));
					} else {

						values.get(i).setIndul(
								menetidőHozzáadás(values.get(i), megállók.SK_DB
										.getLast().getIdo() - menetido));
					}
				}
			}
		}

		/**/

		nincsbenne = true;
		for (Megallo megallo : megállók.HK_DB) {
			if (megallo.getId() == honnan_id) {
				menetido = megallo.getIdo();
				for (Megallo megallo2 : megállók.HK_DB) {
					if (megallo2.getId() == hova_id) {
						nincsbenne = false;
						break;
					}
				}
			}
		}
		if (nincsbenne) {
			for (int i = 0; i < values.size(); i++) {
				if ("HK".equals(values.get(i).getHonnan())
						|| "HK".equals(values.get(i).getHova())) {
					values.remove(i);
					i--;
				}
			}
		} else {
			for (int i = 0; i < values.size(); i++) {
				if ("HK".equals(values.get(i).getHonnan())
						|| "HK".equals(values.get(i).getHova())) {

					if (sámsonDebrecen) {
						values.get(i).setIndul(
								menetidőHozzáadás(values.get(i), menetido));
					} else {

						values.get(i).setIndul(
								menetidőHozzáadás(values.get(i), megállók.HK_DB
										.getLast().getIdo() - menetido));
					}
				}
			}
		}

		
	}

	private String menetidőHozzáadás(Busz busz, int menetido) {
		int ora = Integer
				.parseInt(busz.getIndul().subSequence(0, 2).toString());
		int perc = Integer.parseInt(busz.getIndul().subSequence(3, 5)
				.toString());

		ora = ora + (perc + menetido) / 60;
		perc = (perc + menetido) % 60;

		return (ora < 10 ? "0" + ora : ora) + ":"
				+ (perc == 0 ? perc + "0" : (perc < 10 ? "0" + perc : perc));

	}

}
