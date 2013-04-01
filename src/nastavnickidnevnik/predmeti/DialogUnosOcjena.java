package nastavnickidnevnik.predmeti;

import nastavnickidnevnik.main.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogUnosOcjena extends Dialog implements android.view.View.OnClickListener {
	AdapterOcjene adapter;
	Predmet predmet;
	EditText odlican,vrloDobar,dobar,dovoljan,nedovoljan;
	TextView naslov;
	Button spremi,odustani;
	BazaPredmeti baza;
	LinearLayout info;
	TextView infoText;
	boolean infoFlag;
	public DialogUnosOcjena(Context context, AdapterOcjene ocjeneAdapter,Predmet predmet, BazaPredmeti baza) {
		super(context);
		this.adapter=ocjeneAdapter;
		this.predmet=predmet;
		this.baza=baza;
		infoFlag=false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_predmeti_dialog_unos_ocjene);
		
		inicijalizacija();
		
		naslov.setText("Unos broja ocjena za predmet "+predmet.getIme());
		spremi.setOnClickListener(this);
		odustani.setOnClickListener(this);
		info.setOnClickListener(this);
		
		prikaziPostojeceVrijednosti();
	}

	private void prikaziPostojeceVrijednosti() {
		odlican.setText(predmet.getOdlican()+"");
	}

	private void inicijalizacija() {
		odlican=(EditText)findViewById(R.predmeti.ocjene_dialog_unos_odlican);
		vrloDobar=(EditText)findViewById(R.predmeti.ocjene_dialog_unos_vrlo_dobar);
		dobar=(EditText)findViewById(R.predmeti.ocjene_dialog_unos_dobar);
		dovoljan=(EditText)findViewById(R.predmeti.ocjene_dialog_unos_dovoljan);
		nedovoljan=(EditText)findViewById(R.predmeti.ocjene_dialog_unos_nedovoljan);
		
		naslov=(TextView)findViewById(R.predmeti.ocjene_dialog_unos_naslov);
		
		spremi=(Button)findViewById(R.predmeti.ocjene_dialog_unos_spremi);
		odustani=(Button)findViewById(R.predmeti.ocjene_dialog_unos_odustani);
		
		info=(LinearLayout)findViewById(R.predmeti.ocjene_dialog_unos_info);
		infoText=(TextView)findViewById(R.predmeti.ocjene_dialog_unos_info_text);
	}
	
	@Override
	public void onClick(View v) {
		if(v==spremi){
			baza.otvori();
			if(provjeriText(odlican)){
				String unos=odlican.getText().toString();
				baza.update(predmet.getId(), unos, BazaPredmeti.KEY_OCJENA_ODLICAN);
				predmet.setOdlican(unos);
			}else{
				baza.update(predmet.getId(), "-1", BazaPredmeti.KEY_OCJENA_ODLICAN);
				predmet.setOdlican("-1");
			}
			if(provjeriText(vrloDobar)){
				String unos=vrloDobar.getText().toString();
				baza.update(predmet.getId(), unos, BazaPredmeti.KEY_OCJENA_VRLO_DOBAR);
				predmet.setVrloDobar(unos);
			}else{
				baza.update(predmet.getId(), "-1", BazaPredmeti.KEY_OCJENA_VRLO_DOBAR);
				predmet.setVrloDobar("-1");
			}
			if(provjeriText(dobar)){
				String unos=dobar.getText().toString();
				baza.update(predmet.getId(), unos, BazaPredmeti.KEY_OCJENA_DOBAR);
				predmet.setDobar(unos);
			}else{
				baza.update(predmet.getId(), "-1", BazaPredmeti.KEY_OCJENA_DOBAR);
				predmet.setDobar("-1");
			}
			if(provjeriText(dovoljan)){
				String unos=dovoljan.getText().toString();
				baza.update(predmet.getId(), unos, BazaPredmeti.KEY_OCJENA_DOVOLJAN);
				predmet.setDovoljan(unos);
			}else{
				baza.update(predmet.getId(), "-1", BazaPredmeti.KEY_OCJENA_DOVOLJAN);
				predmet.setDovoljan("-1");
			}
			if(provjeriText(nedovoljan)){
				String unos=nedovoljan.getText().toString();
				baza.update(predmet.getId(), unos, BazaPredmeti.KEY_OCJENA_NEDOVOLJAN);
				predmet.setNedovoljan(unos);
			}else{
				baza.update(predmet.getId(), "-1", BazaPredmeti.KEY_OCJENA_NEDOVOLJAN);
				predmet.setNedovoljan("-1");
			}
			baza.zatvori();
			adapter.notifyDataSetChanged();
			dismiss();
		}
		if(v==odustani){
			cancel();
		}
		if(v==info){
			infoFlag=!infoFlag;
			if(infoFlag){
				infoText.setVisibility(View.VISIBLE);
			}else{
				infoText.setVisibility(View.GONE);
			}
		}
	}
	private boolean provjeriText(EditText provjeri){
		if(provjeri.getText().toString().matches("")){
			return false;
		}
		return true;
	}

}
