package nastavnickidnevnik.kalendar;

import java.util.ArrayList;
import java.util.Calendar;

import nastavnickidnevnik.main.R;
import android.content.Context;
import android.graphics.Color;
import android.util.MonthDisplayHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class KalendarAdapter extends BaseAdapter{
	private Context mContext;
	private Integer[] dani;
	MonthDisplayHelper mdh;
	Calendar cal;
	BazaKalendar baza;
	ArrayList<Unosi> biljeske;

	public KalendarAdapter(Context c) {
		mContext = c;
		cal = Calendar.getInstance();
		mdh = new MonthDisplayHelper(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), 2);

		postaviDane();
		baza= new BazaKalendar(c);
		baza.otvori();
		biljeske=baza.dohvati(mdh.getMonth(), mdh.getYear());
		baza.zatvori();

	}

	public void iduciMjesec() {
		mdh.nextMonth();
		postaviDane();
		baza.otvori();
		biljeske=baza.dohvati(mdh.getMonth(), mdh.getYear());
		baza.zatvori();

	}

	public void prethodniMjesec() {
		mdh.previousMonth();
		postaviDane();
		baza.otvori();
		biljeske=baza.dohvati(mdh.getMonth(), mdh.getYear());
		baza.zatvori();
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
			v = vi.inflate(R.layout.layout_kalendar_dan, null);

		}
		v.setMinimumHeight(70);
		TextView dan;
		dan = (TextView) v.findViewById(R.id.danDatum);
		dan.setText("" + dani[position]);
		dan.setTextColor(Color.parseColor("#756E71"));
		postaviBojuDana(v, dan, position);
		
		ArrayList<TextView> eventButton=new ArrayList<TextView>();
		eventButton.add((TextView)v.findViewById(R.id.btnPrviEvent));
		eventButton.add((TextView)v.findViewById(R.id.btnDrugiEvent));
		eventButton.get(0).setVisibility(View.INVISIBLE);
		eventButton.get(1).setVisibility(View.INVISIBLE);
		
		if (!(position < mdh.getOffset()
				|| position > mdh.getNumberOfDaysInMonth() + mdh.getOffset()
						- 1)) {
			int j=0;
			for(int i =0;i<biljeske.size();i++){
				if(biljeske.get(i).getDan()==dani[position]){
					eventButton.get(j).setText(biljeske.get(i).getNaslov());
					eventButton.get(j).setVisibility(View.VISIBLE);
					j++;if(j>1)break;
				}
			}
		}else{
			v.setClickable(false);
			v.setOnClickListener(null);
		}
		
		return v;
	}
	public void dodajBiljesku(int id,int dan,String naslov,String opis){
		biljeske.add(new Unosi(id,dan,naslov,opis));
	}
	public void obrisiBiljesku(int id){
		for(int i =0;i<biljeske.size();i++){
			if(biljeske.get(i).getID()==id){
				biljeske.remove(i);
			}
		}
		this.notifyDataSetChanged();
		baza.otvori();
		baza.obrisi(id);
		baza.zatvori();
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
	public ArrayList<Unosi> dohvatiBiljeskeZaDan(int position){
		ArrayList<Unosi> vrati= new ArrayList<Unosi>();
		for(int i =0;i<biljeske.size();i++){
			if(biljeske.get(i).getDan()==dohvatiDan(position)){
				vrati.add(biljeske.get(i));
			}
		}
		return vrati;
	}

	// promjena boje za dan koji nije u trenutnom mjesecu
	private void postaviBojuDana(View pozadina, TextView dan, int position) {
		if (position < mdh.getOffset()
				|| position > mdh.getNumberOfDaysInMonth() + mdh.getOffset()
						- 1) {
			pozadina.setBackgroundColor(Color.LTGRAY);
		} else {
			pozadina.setBackgroundColor(provjeriPraznike(position));
			provjeriBlagdane(dan, position);
			// danasnji dan
			if (cal.get(Calendar.DATE) == dohvatiDan(position)
					&& dohvatiMjesec() == cal.get(Calendar.MONTH)
					&& dohvatiGodinu() == cal.get(Calendar.YEAR)) {
				pozadina.setBackgroundColor(Color.parseColor("#FFED2B"));
			}
		}
	}

	private void provjeriBlagdane(TextView dan, int position) {
		if(dohvatiGodinu()==2013&&dohvatiMjesec()==2&&dohvatiDan(position)==31){
			dan.setTextColor(Color.RED);
		}
		if(dohvatiGodinu()==2013&&dohvatiMjesec()==3&&dohvatiDan(position)==1){
			dan.setTextColor(Color.RED);
		}
		if(dohvatiGodinu()==2014&&dohvatiMjesec()==3&&dohvatiDan(position)==20){
			dan.setTextColor(Color.RED);
		}
		if(dohvatiGodinu()==2014&&dohvatiMjesec()==3&&dohvatiDan(position)==21){
			dan.setTextColor(Color.RED);
		}
		if(dohvatiGodinu()==2015&&dohvatiMjesec()==3&&dohvatiDan(position)==5){
			dan.setTextColor(Color.RED);
		}
		if(dohvatiGodinu()==2015&&dohvatiMjesec()==3&&dohvatiDan(position)==6){
			dan.setTextColor(Color.RED);
		}
		if(dohvatiGodinu()==2016&&dohvatiMjesec()==2&&dohvatiDan(position)==27){
			dan.setTextColor(Color.RED);
		}
		if(dohvatiGodinu()==2016&&dohvatiMjesec()==2&&dohvatiDan(position)==28){
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
