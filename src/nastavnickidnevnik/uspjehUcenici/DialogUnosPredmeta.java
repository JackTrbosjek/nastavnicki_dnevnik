package nastavnickidnevnik.uspjehUcenici;

import java.util.ArrayList;

import nastavnickidnevnik.main.R;
import nastavnickidnevnik.predmeti.BazaPredmeti;
import nastavnickidnevnik.predmeti.Predmet;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class DialogUnosPredmeta extends Dialog implements android.view.View.OnClickListener {
	EditText naziv;
	Button spremi,odustani;
	ArrayList<Predmet> popis;
	BazaPredmeti baza;
	AdapterExpandableList adapter;
	public DialogUnosPredmeta(Context context, AdapterExpandableList adapter,ArrayList<Predmet> popis, BazaPredmeti baza) {
		super(context);
		this.adapter=adapter;
		this.popis=popis;
		this.baza=baza;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_predmeti_dialog_unos_predmeta);
		
		inicijalizacija();
		
	}

	private void inicijalizacija() {
		naziv=(EditText)findViewById(R.predmeti.dialog_naziv);
		
		spremi=(Button)findViewById(R.predmeti.dialog_spremi);
		spremi.setOnClickListener(this);
		
		odustani=(Button)findViewById(R.predmeti.dialog_odustani);
		odustani.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==spremi){
			baza.otvori();
			long id=baza.dodajPredmet(naziv.getText().toString());
			baza.zatvori();
			popis.add(new Predmet(id,naziv.getText().toString()));
			adapter.notifyDataSetChanged();
			dismiss();
		}
		if(v==odustani){
			cancel();
		}
	}

}
