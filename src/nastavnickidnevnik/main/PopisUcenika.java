package nastavnickidnevnik.main;

import java.util.ArrayList;
import java.util.Random;

import nastavnickidnevnik.popisUcenika.BazaPopisUcenika;
import nastavnickidnevnik.popisUcenika.PopisUcenikaAdapter;
import nastavnickidnevnik.popisUcenika.Ucenik;
import nastavnickidnevnik.popisUcenika.UnosUcenikaDialog;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PopisUcenika extends Activity implements OnClickListener {
	ListView listaUcenika;
	BazaPopisUcenika baza;
	PopisUcenikaAdapter adapter;
	ArrayList<Ucenik> ucenici;
	LinearLayout dodaj, obrisi, randomUcenik;
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popis_ucenika);
		inicijalizacija();

		baza.otvori();
		ucenici = baza.dohvatiUcenike();
		baza.zatvori();
		adapter = new PopisUcenikaAdapter(this, ucenici);
		listaUcenika.setAdapter(adapter);

	}

	private void inicijalizacija() {
		listaUcenika = (ListView) findViewById(R.popisUcenika.list);
		baza = new BazaPopisUcenika(this);

		dodaj = (LinearLayout) findViewById(R.popisUcenika.dodaj);
		dodaj.setOnClickListener(this);
		obrisi = (LinearLayout) findViewById(R.popisUcenika.obrisi);
		obrisi.setOnClickListener(this);
		randomUcenik = (LinearLayout) findViewById(R.popisUcenika.nasumicni_odabir);
		randomUcenik.setOnClickListener(this);
		back = (ImageView) findViewById(R.popisUcenika.back);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.popisUcenika.dodaj:
			new UnosUcenikaDialog(this, adapter).show();
			break;
		case R.popisUcenika.nasumicni_odabir:
			nasumicniOdabirUcenika();
			break;
		case R.popisUcenika.back:
			finish();
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right );
			break;
		case R.popisUcenika.obrisi:
			pokaziButtoneZaBrisanje();
			break;
		}
	}

	private void pokaziButtoneZaBrisanje() {
		adapter.BRISANJE = !adapter.BRISANJE;
		adapter.notifyDataSetChanged();
	}

	private void nasumicniOdabirUcenika() {
		if (adapter.getCount() > 1) {
			int randomInt = new Random().nextInt(adapter.getCount());
			adapter.RANDOM = randomInt;
			adapter.notifyDataSetChanged();
			listaUcenika.smoothScrollToPosition(randomInt);
		}
	}

}
