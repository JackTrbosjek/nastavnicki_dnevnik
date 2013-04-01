package nastavnickidnevnik.godina;

import java.util.ArrayList;
import java.util.Calendar;

import nastavnickidnevnik.kalendar.Unosi;
import nastavnickidnevnik.main.R;
import android.content.Context;
import android.graphics.Color;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GodinaKalendarAdapter extends BaseAdapter {
	private Context mContext;
	private Integer[] dani;
	MonthDisplayHelper mdh;
	Calendar cal;
	ArrayList<Unosi> biljeske;

	public GodinaKalendarAdapter(Context c, int godina, int mjesec) {
		mContext = c;
		cal = Calendar.getInstance();
		mdh = new MonthDisplayHelper(godina, mjesec, 2);
		postaviDane();
	}
	public boolean areAllItemsEnabled()
	{
	    return false;
	}

	public boolean isEnabled(int position)
	{
	    return false;
	}
	public void iduciMjesec() {
		mdh.nextMonth();
		postaviDane();
	}

	public void prethodniMjesec() {
		mdh.previousMonth();
		postaviDane();
	}

	// postavljanje dana u integer array
	private void postaviDane() {
		int brojRedova = mdh.getRowOf(mdh.getNumberOfDaysInMonth());
		dani = new Integer[100];
		int broj = 0;
		for (int j = 0; j <= brojRedova; j++)
			for (int i = 0; i < 7; i++)
				dani[broj++] = mdh.getDayAt(j, i);
	}

	public int getCount() {
		int broj = 7 * (mdh.getRowOf(mdh.getNumberOfDaysInMonth()) + 1);
		return broj;

	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.layout_godina_dan, null);

		}
		TextView dan;
		dan = (TextView) v.findViewById(R.id.godinaViewDan);
		dan.setText("" + dani[position]);
		dan.setTextColor(Color.parseColor("#756E71"));
		postaviBojuDana(v, dan, position);

		return v;
	}

	public int dohvatiDan(int position) {
		return dani[position];
	}

	public int dohvatiMjesec() {
		return mdh.getMonth();
	}

	public int dohvatiGodinu() {
		return mdh.getYear();
	}

	// promjena boje za dan koji nije u trenutnom mjesecu
	private void postaviBojuDana(View pozadina, TextView dan, int position) {
		if (position < mdh.getOffset()
				|| position > mdh.getNumberOfDaysInMonth() + mdh.getOffset()
						- 1) {
			pozadina.setBackgroundColor(Color.LTGRAY);
		} else {
			int backgroudColor=provjeriPraznike(position);
			pozadina.setBackgroundColor(backgroudColor);
			provjeriBlagdane(dan, position);
			// danasnji dan
			if (cal.get(Calendar.DATE) == dohvatiDan(position)
					&& dohvatiMjesec() == cal.get(Calendar.MONTH)
					&& dohvatiGodinu() == cal.get(Calendar.YEAR)) {
				pozadina.setBackgroundColor(Color.parseColor("#FFED2B"));
			}
		}
	}
	public String imeMjeseca(){
		int mjesec=dohvatiMjesec();
		switch(mjesec){
		case 0: return "Sijeèanj"; 
		case 1: return "Veljaèa";
		case 2: return "Ožujak"; 
		case 3: return "Travanj";
		case 4: return "Svibanj";
		case 5: return "Lipanj";
		case 6: return "Srpanj";
		case 7: return "Kolovoz";
		case 8: return "Rujan";
		case 9: return "Listopad";
		case 10: return "Studeni";
		case 11: return "Prosinac";
		}
		return null;
	}

	private void provjeriBlagdane(TextView dan, int position) {
		if (dohvatiGodinu() == 2013 && dohvatiMjesec() == 2
				&& dohvatiDan(position) == 31) {
			dan.setTextColor(Color.RED);
			return;
		}
		if (dohvatiGodinu() == 2013 && dohvatiMjesec() == 3
				&& dohvatiDan(position) == 1) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiGodinu() == 2014 && dohvatiMjesec() == 3
				&& dohvatiDan(position) == 20) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiGodinu() == 2014 && dohvatiMjesec() == 3
				&& dohvatiDan(position) == 21) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiGodinu() == 2015 && dohvatiMjesec() == 3
				&& dohvatiDan(position) == 5) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiGodinu() == 2015 && dohvatiMjesec() == 3
				&& dohvatiDan(position) == 6) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiGodinu() == 2016 && dohvatiMjesec() == 2
				&& dohvatiDan(position) == 27) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiGodinu() == 2016 && dohvatiMjesec() == 2
				&& dohvatiDan(position) == 28) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiMjesec() == 10 && dohvatiDan(position) == 1) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiMjesec() == 9 && dohvatiDan(position) == 8) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiMjesec() == 11 && dohvatiDan(position) == 25) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiMjesec() == 11 && dohvatiDan(position) == 26) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiMjesec() == 0 && dohvatiDan(position) == 1) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiMjesec() == 0 && dohvatiDan(position) == 6) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiMjesec() == 4 && dohvatiDan(position) == 1) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiMjesec() == 5 && dohvatiDan(position) == 22) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiMjesec() == 5 && dohvatiDan(position) == 25) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiMjesec() == 7 && dohvatiDan(position) == 5) {
			dan.setTextColor(Color.RED);
		}
		if (dohvatiMjesec() == 7 && dohvatiDan(position) == 15) {
			dan.setTextColor(Color.RED);
		}
	}

	private int provjeriPraznike(int position) {
		// ljetni #FFF478
		// zimski #78E2FF
		// proljetni #80FC6A
		String LJETO = "#FFF478";
		String ZIMA = "#78E2FF";
		String PROLJECE = "#80FC6A";
		// skolska godina 2012/2013
		if (dohvatiGodinu() == 2012) {
			if (dohvatiMjesec() == 8 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 2)
				return Color.parseColor(LJETO);
			if (dohvatiMjesec() == 11 && dohvatiDan(position) >= 24
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(ZIMA);
		}
		if (dohvatiGodinu() == 2013) {
			if (dohvatiMjesec() == 0 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 13)
				return Color.parseColor(ZIMA);
			if (dohvatiMjesec() == 2 && dohvatiDan(position) >= 25
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(PROLJECE);
			if (dohvatiMjesec() == 5 && dohvatiDan(position) >= 17
					&& dohvatiDan(position) <= 30)
				return Color.parseColor(LJETO);
			if (dohvatiMjesec() == 6 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(LJETO);
			if (dohvatiMjesec() == 7 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(LJETO);
		}

		// skolska godina 2013/2014
		if (dohvatiGodinu() == 2013) {
			if (dohvatiMjesec() == 8 && dohvatiDan(position) == 1)
				return Color.parseColor(LJETO);
			if (dohvatiMjesec() == 11 && dohvatiDan(position) >= 23
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(ZIMA);
		}
		if (dohvatiGodinu() == 2014) {
			if (dohvatiMjesec() == 0 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 12)
				return Color.parseColor(ZIMA);
			if (dohvatiMjesec() == 3 && dohvatiDan(position) >= 14
					&& dohvatiDan(position) <= 21)
				return Color.parseColor(PROLJECE);
			if (dohvatiMjesec() == 5 && dohvatiDan(position) >= 16
					&& dohvatiDan(position) <= 30)
				return Color.parseColor(LJETO);
			if (dohvatiMjesec() == 6 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(LJETO);
			if (dohvatiMjesec() == 7 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(LJETO);
		}

		// skolska godina 2014/2015
		if (dohvatiGodinu() == 2014) {
			if (dohvatiMjesec() == 11 && dohvatiDan(position) >= 22
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(ZIMA);
		}
		if (dohvatiGodinu() == 2015) {
			if (dohvatiMjesec() == 0 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 11)
				return Color.parseColor(ZIMA);
			if (dohvatiMjesec() == 3 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 6)
				return Color.parseColor(PROLJECE);
			if (dohvatiMjesec() == 5 && dohvatiDan(position) >= 15
					&& dohvatiDan(position) <= 30)
				return Color.parseColor(LJETO);
			if (dohvatiMjesec() == 6 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(LJETO);
			if (dohvatiMjesec() == 7 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(LJETO);
		}

		// skolska godina 2015/2016
		if (dohvatiGodinu() == 2015) {
			if (dohvatiMjesec() == 8 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 6)
				return Color.parseColor(LJETO);
			if (dohvatiMjesec() == 11 && dohvatiDan(position) >= 21
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(ZIMA);
		}
		if (dohvatiGodinu() == 2016) {
			if (dohvatiMjesec() == 0 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 10)
				return Color.parseColor(ZIMA);
			if (dohvatiMjesec() == 2 && dohvatiDan(position) >= 21
					&& dohvatiDan(position) <= 28)
				return Color.parseColor(PROLJECE);
			if (dohvatiMjesec() == 5 && dohvatiDan(position) >= 20
					&& dohvatiDan(position) <= 30)
				return Color.parseColor(LJETO);
			if (dohvatiMjesec() == 6 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(LJETO);
			if (dohvatiMjesec() == 7 && dohvatiDan(position) >= 1
					&& dohvatiDan(position) <= 31)
				return Color.parseColor(LJETO);
		}
		return Color.WHITE;
	}

}
