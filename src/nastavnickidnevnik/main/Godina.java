package nastavnickidnevnik.main;

import java.util.ArrayList;
import java.util.Calendar;

import nastavnickidnevnik.godina.Dani;
import nastavnickidnevnik.godina.GodinaKalendarAdapter;
import nastavnickidnevnik.godina.GodinaListAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Godina extends Activity implements OnClickListener {
	GridView mjesec1, mjesec2;
	TextView naslovM1, naslovM2,  naslovGodina;
	GodinaKalendarAdapter adapter1, adapter2;
	ArrayList<Dani> posebniDani;
	Calendar cal;
	int brojac, mjesec, godina;
	ListView list1, list2;
	ImageView back, prije, poslije;
	TextView legenda1Boja,legenda1,legenda2Boja,legenda2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_godina);
		inicijalizacija();
		dodajDane();
		cal = Calendar.getInstance();
		mjesec = cal.get(Calendar.MONTH);
		godina = cal.get(Calendar.YEAR);
		postaviAdaptereGrid();
		postaviAdaptereList();

	}

	private void postaviAdaptereList() {
		ArrayList<Dani> dani1 = new ArrayList<Dani>();
		ArrayList<Dani> dani2 = new ArrayList<Dani>();
		for (int i = 0; i < posebniDani.size(); i++) {
			if (adapter1.dohvatiMjesec() == posebniDani.get(i).getMjesec()) {
				dani1.add(posebniDani.get(i));
			}
			if (adapter2.dohvatiMjesec() == posebniDani.get(i).getMjesec()) {
				dani2.add(posebniDani.get(i));
			}
		}
		dodavanjeDodatnihBlagdana(dani1,adapter1.dohvatiMjesec(),adapter1.dohvatiGodinu());
		dodavanjeDodatnihBlagdana(dani2,adapter2.dohvatiMjesec(),adapter2.dohvatiGodinu());
		GodinaListAdapter listAdapter1 = new GodinaListAdapter(this, dani1);
		GodinaListAdapter listAdapter2 = new GodinaListAdapter(this, dani2);

		list1.setAdapter(listAdapter1);
		list2.setAdapter(listAdapter2);

	}

	private void postaviAdaptereGrid() {
		adapter1 = new GodinaKalendarAdapter(this, godina, mjesec  + brojac);
		adapter2 = new GodinaKalendarAdapter(this, godina, mjesec +1+ brojac);
		mjesec1.setAdapter(adapter1);
		mjesec2.setAdapter(adapter2);

		naslovM1.setText(adapter1.imeMjeseca());
		naslovM2.setText(adapter2.imeMjeseca());
		if (adapter1.dohvatiGodinu() == adapter2.dohvatiGodinu()) {
			naslovGodina.setText(""+adapter1.dohvatiGodinu());
		}else{
			naslovGodina.setText(adapter1.dohvatiGodinu()+"/"+(adapter1.dohvatiGodinu()+1));
		}
		postaviLegendu(adapter1,legenda1,legenda1Boja);
		postaviLegendu(adapter2,legenda2,legenda2Boja);

	}
	private void postaviLegendu(GodinaKalendarAdapter adapter,
			TextView legenda, TextView legendaBoja) {
		legenda.setVisibility(View.VISIBLE);
		legendaBoja.setVisibility(View.VISIBLE);
		
		int mjesec=adapter.dohvatiMjesec();
		int godina=adapter.dohvatiGodinu();
		
		int ljetoBoja=Color.parseColor("#FFF478");
		String ljeto=" Ljetni praznici";
		int zimaBoja=Color.parseColor("#78E2FF");
		String zima=" Zimski praznici";
		int proljeceBoja=Color.parseColor("#80FC6A");
		String proljece=" Proljetni praznici";
		
		if(godina==2012){
			if(mjesec==11){
				legenda.setText(zima);
				legendaBoja.setBackgroundColor(zimaBoja); return;
			}
			if(mjesec==8){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
		}
		if(godina==2013){
			if(mjesec==0){
				legenda.setText(zima);
				legendaBoja.setBackgroundColor(zimaBoja); return;
			}
			if(mjesec==2){
				legenda.setText(proljece);
				legendaBoja.setBackgroundColor(proljeceBoja); return;
			}
			if(mjesec==5){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==6){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==7){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==8){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==11){
				legenda.setText(zima);
				legendaBoja.setBackgroundColor(zimaBoja); return;
			}
		}
		if(godina==2014){
			if(mjesec==0){
				legenda.setText(zima);
				legendaBoja.setBackgroundColor(zimaBoja); return;
			}
			if(mjesec==3){
				legenda.setText(proljece);
				legendaBoja.setBackgroundColor(proljeceBoja); return;
			}
			if(mjesec==5){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==6){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==7){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==11){
				legenda.setText(zima);
				legendaBoja.setBackgroundColor(zimaBoja); return;
			}
		}
		if(godina==2015){
			if(mjesec==0){
				legenda.setText(zima);
				legendaBoja.setBackgroundColor(zimaBoja); return;
			}
			if(mjesec==3){
				legenda.setText(proljece);
				legendaBoja.setBackgroundColor(proljeceBoja); return;
			}
			if(mjesec==5){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==6){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==7){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==8){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==11){
				legenda.setText(zima);
				legendaBoja.setBackgroundColor(zimaBoja); return;
			}
		}
		if(godina==2016){
			if(mjesec==0){
				legenda.setText(zima);
				legendaBoja.setBackgroundColor(zimaBoja); return;
			}
			if(mjesec==2){
				legenda.setText(proljece);
				legendaBoja.setBackgroundColor(proljeceBoja); return;
			}
			if(mjesec==5){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==6){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==7){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==8){
				legenda.setText(ljeto);
				legendaBoja.setBackgroundColor(ljetoBoja); return;
			}
			if(mjesec==11){
				legenda.setText(zima);
				legendaBoja.setBackgroundColor(zimaBoja); return;
			}
		}
		legenda.setVisibility(View.INVISIBLE);
		legendaBoja.setVisibility(View.INVISIBLE);
	}

	//dodavanje Uskrsa, Uskrsnog pon i tijelova na popis dogaðaja za mjesec
	private void dodavanjeDodatnihBlagdana(ArrayList<Dani> dani, int mjesec,int godina) {
		//2013
		if(godina==2013){
			if(mjesec==2){
				dani.add(new Dani(31,2,"Uskrs"));
			}
			if(mjesec==3){
				dani.add(new Dani(1,3,"Uskrsni ponedjeljak"));
			}
			if(mjesec==4){
				dani.add(new Dani(30,4,"Tijelovo"));
			}
		}
		//2014
		if(godina==2014){
			if(mjesec==3){
				dani.add(new Dani(20,3,"Uskrs"));
				dani.add(new Dani(21,3,"Uskrsni ponedjeljak"));
			}
			if(mjesec==5){
				dani.add(new Dani(19,5,"Tijelovo"));
			}
		}
		//2015
		if(godina==2015){
			if(mjesec==3){
				dani.add(new Dani(5,3,"Uskrs"));
				dani.add(new Dani(6,3,"Uskrsni ponedjeljak"));
			}
			if(mjesec==5){
				dani.add(new Dani(4,5,"Tijelovo"));
			}
		}
		if(godina==2016){
			if(mjesec==2){
				dani.add(new Dani(27,2,"Uskrs"));
				dani.add(new Dani(28,2,"Uskrsni ponedjeljak"));
			}
			if(mjesec==4){
				dani.add(new Dani(26,4,"Tijelovo"));
			}
		}
		}

	private void inicijalizacija() {

		mjesec1 = (GridView) findViewById(R.id.godinaGrid1);
		mjesec2 = (GridView) findViewById(R.id.godinaGrid22);

		list1 = (ListView) findViewById(R.id.godinaList1);
		list2 = (ListView) findViewById(R.id.godinaList2);

		naslovM1 = (TextView) findViewById(R.id.godinaGridMjesec1);
		naslovM2 = (TextView) findViewById(R.id.godinaGridMjesec2);
		
		legenda1Boja=(TextView)findViewById(R.godina.legenda_boja);
		legenda2Boja=(TextView)findViewById(R.godina.legenda2_boja);
		legenda1=(TextView)findViewById(R.godina.legenda);
		legenda2=(TextView)findViewById(R.godina.legenda2);

		brojac = 0;

		back = (ImageView) findViewById(R.id.godinaBack);
		back.setOnClickListener(this);
		prije = (ImageView) findViewById(R.id.godinaPrethodno);
		prije.setOnClickListener(this);
		poslije = (ImageView) findViewById(R.id.godinaIduce);
		poslije.setOnClickListener(this);

		naslovGodina = (TextView) findViewById(R.id.godinaNaslov);

		posebniDani = new ArrayList<Dani>();
	}

	private void dodajDane() {
		// rujan
		posebniDani.add(new Dani(7, 8, "Dan hrvatskih voda"));
		posebniDani.add(new Dani(8, 8,
				"Mala Gospa\nMeðunarodni dan borbe protiv nepismenosti"));
		posebniDani.add(new Dani(16, 8, "Meðunarodni dan zaštite ozona"));
		posebniDani.add(new Dani(18, 8, "Dan HR ratne mornarice"));
		posebniDani.add(new Dani(21, 8, "Meðunarodni dan mira"));
		posebniDani
				.add(new Dani(23, 8, "Dan europske baštine\nprvi dan jeseni"));
		posebniDani.add(new Dani(26, 8,
				"Svjetski dan èistih planina\nSvjetski dan srca"));
		posebniDani.add(new Dani(29, 8, "Dan hrvatske policije"));
		// listopad
		posebniDani.add(new Dani(1, 9, "Svjetski dan ljudskih naselja"));
		posebniDani.add(new Dani(3, 9, "Meðunarodni dan djeteta"));
		posebniDani.add(new Dani(4, 9, "Meðunarodni dan zaštite životinja"));
		posebniDani.add(new Dani(5, 9, "Svjetski dan uèitelja"));
		posebniDani.add(new Dani(8, 9, "Dan neovisnosti"));
		posebniDani.add(new Dani(10, 9, "Dan zahvalnosti za plodove zemlje"));
		posebniDani.add(new Dani(15, 9, "Svjetski dan pješaèenja"));
		posebniDani.add(new Dani(16, 9, "Svjetski dan hrane"));
		posebniDani
				.add(new Dani(17, 9, "Svjetski dan borbe protiv siromaštva"));
		posebniDani.add(new Dani(20, 9, "Dan jabuka"));
		posebniDani.add(new Dani(23, 9, "Hrvatski kao službeni jezik u RH"));
		posebniDani.add(new Dani(24, 9, "Dan Ujedinjenih naroda"));
		posebniDani.add(new Dani(25, 9, "Meðunarodni dan školskih knjižnica"));
		posebniDani.add(new Dani(31, 9, "Meðunarodni dan štednje"));
		// studeni
		posebniDani.add(new Dani(1, 10, "Svi sveti"));
		posebniDani.add(new Dani(2, 10, "Dan spomena na mrtve"));
		posebniDani.add(new Dani(10, 10, "Svjetski dan mlaðeži"));
		posebniDani.add(new Dani(13, 10, "Svjetski dan ljubaznosti"));
		posebniDani.add(new Dani(16, 10, "Meðunarodni dan tolerancije"));
		posebniDani.add(new Dani(17, 10, "Svjetski dan nepušaèa"));
		posebniDani.add(new Dani(18, 10, "Dan sjeæanja na Vukovar"));
		posebniDani.add(new Dani(20, 10, "Svjetski dan djece"));
		posebniDani.add(new Dani(24, 10, "Dan hrvatskog kazališta"));

		// prosinac
		posebniDani.add(new Dani(1, 11, "Dan borbe protiv AIDS-a"));
		posebniDani.add(new Dani(5, 11, "Meðunarodni dan volontera"));
		posebniDani.add(new Dani(3, 11, "Meðunarodni dan invalida"));
		posebniDani.add(new Dani(6, 11, "Sveti Nikola"));
		posebniDani.add(new Dani(7, 11, "Dan knjižnica grada Zagreba"));
		posebniDani.add(new Dani(10, 11, "Dan prava èovjeka"));
		posebniDani.add(new Dani(11, 11, "Dan UNICEF-a"));
		posebniDani.add(new Dani(13, 11, "Sveta Lucija"));
		posebniDani.add(new Dani(21, 11, "prvi dan zime"));
		posebniDani.add(new Dani(25, 11, "Božiæ"));
		posebniDani.add(new Dani(26, 11, "Sveti Stjepan"));
		posebniDani.add(new Dani(31, 11, "Stara godina"));
		// sijeèanj
		posebniDani.add(new Dani(1, 0, "Nova Godina"));
		posebniDani.add(new Dani(10, 0, "Svjetski dan smijeha"));
		posebniDani.add(new Dani(15, 0, "Dan meðunarodnog priznanja RH"));
		// veljaèa
		posebniDani.add(new Dani(2, 1, "Meðunarodni dan zaštite moèvara"));
		posebniDani.add(new Dani(6, 1, "Meðunarodni dan života"));
		posebniDani.add(new Dani(10, 1, "Dan sigurnijeg interneta"));
		posebniDani.add(new Dani(11, 1, "Svjetski dan bolesnika"));
		posebniDani.add(new Dani(14, 1, "Valentinovo"));
		posebniDani.add(new Dani(21, 1, "Meðunarodni dan materinskog jezika"));
		posebniDani.add(new Dani(22, 1,
				"Dan Nacionalne i sveuèilišne knjižnice"));
		// ožujak
		posebniDani.add(new Dani(1, 2, "Svjetski dan civilne zaštite"));
		posebniDani.add(new Dani(8, 2, "Svjetski dan žena"));
		posebniDani.add(new Dani(15, 2, "Meðunarodni dan prava potrošaèa"));
		posebniDani
				.add(new Dani(21, 2,
						"Svjetski dan pjesništva\nSvjetski dan šuma\nprvi dan proljeæa"));
		posebniDani.add(new Dani(22, 2, "Svjetski dan voda"));
		posebniDani.add(new Dani(23, 2, "Svjetski meteorološki dan"));
		posebniDani.add(new Dani(27, 2, "Meðunarodni dan kazališta"));
		// travanj
		posebniDani.add(new Dani(2, 3, "Meðunarodni dan djeèje knjige"));
		posebniDani.add(new Dani(7, 3, "Svjetski dan zdravlja"));
		posebniDani.add(new Dani(22, 3,
				"Dan planeta Zemlje\nDan hrvatske knjige"));
		// svibanj
		posebniDani.add(new Dani(1, 4, "Meðunarodni praznik rada"));
		posebniDani.add(new Dani(3, 4, "Dan sunca"));
		posebniDani.add(new Dani(8, 4,
				"Meðunarodni dan Crvenog križa\nMajèin dan"));
		posebniDani.add(new Dani(9, 4, "Meðunarodni dan pobjede nad fašizmom"));
		posebniDani.add(new Dani(15, 4, "Meðunarodni dan obitelji"));
		posebniDani
				.add(new Dani(22, 4,
						"Svjetski dan biološke raznolikosti\nDan zaštite prirode u Republici Hrvatskoj"));
		posebniDani.add(new Dani(28, 4, "Dan oružanih snaga RH"));
		posebniDani.add(new Dani(31, 4, "Svjetski dan športa\nDan nepušenja"));
		// lipanj
		posebniDani.add(new Dani(4, 5, "Meðunarodni dan nevine djece"));
		posebniDani.add(new Dani(5, 5, "Svjetski dan zaštite okoliša"));
		posebniDani.add(new Dani(8, 5, "Dan zaštite planinske prirode u HR"));
		posebniDani.add(new Dani(22, 5, "Dan antifašistièke borbe"));
		posebniDani.add(new Dani(25, 5, "Dan državnosti"));
		posebniDani.add(new Dani(26, 5, "Dan meðunarodne borbe protiv droge"));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.godinaBack:
			finish();
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right );
			break;
		case R.id.godinaIduce:
			brojac += 2;
			postaviAdaptereGrid();
			postaviAdaptereList();
			break;
		case R.id.godinaPrethodno:
			brojac -= 2;
			postaviAdaptereGrid();
			postaviAdaptereList();
			break;
		}
	}

}
