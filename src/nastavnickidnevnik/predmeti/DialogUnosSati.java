package nastavnickidnevnik.predmeti;

import nastavnickidnevnik.main.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogUnosSati extends Dialog implements android.view.View.OnClickListener {
	AdapterSati adapter;
	Predmet predmet;
	EditText planirano,ostvareno,dopunska,strucno,nestrucno;
	TextView naslov;
	Button spremi,odustani;
	BazaPredmeti baza;
	public DialogUnosSati(Context context, AdapterSati ocjeneAdapter,Predmet predmet, BazaPredmeti baza) {
		super(context);
		this.adapter=ocjeneAdapter;
		this.predmet=predmet;
		this.baza=baza;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_predmeti_dialog_unos_sati);
		
		inicijalizacija();
		
		naslov.setText("Unos broja sati za predmet "+predmet.getIme());
		postaviVrijednostiAkoPostoje();
		spremi.setOnClickListener(this);
		odustani.setOnClickListener(this);
	}
	private void postaviVrijednostiAkoPostoje(){
		if(predmet.getSatiPlanirano()!=-1){
			planirano.setText(predmet.getSatiPlanirano()+"");
		}
		if(predmet.getSatiOstvareno()!=-1){
			ostvareno.setText(predmet.getSatiOstvareno()+"");
		}
		if(predmet.getSatiDopunska()!=-1){
			dopunska.setText(predmet.getSatiDopunska()+"");
		}
		if(predmet.getSatiStrucno()!=-1){
			strucno.setText(predmet.getSatiStrucno()+"");
		}
		if(predmet.getSatiNeStrucno()!=-1){
			nestrucno.setText(predmet.getSatiNeStrucno()+"");
		}
	}

	private void inicijalizacija() {
		planirano=(EditText)findViewById(R.predmeti.sati_dialog_unos_planirano);
		ostvareno=(EditText)findViewById(R.predmeti.sati_dialog_unos_ostvareno);
		dopunska=(EditText)findViewById(R.predmeti.sati_dialog_unos_dopunska);
		strucno=(EditText)findViewById(R.predmeti.sati_dialog_unos_strucno);
		nestrucno=(EditText)findViewById(R.predmeti.sati_dialog_unos_nestrucno);
		
		naslov=(TextView)findViewById(R.predmeti.sati_dialog_unos_naslov);
		
		spremi=(Button)findViewById(R.predmeti.sati_dialog_unos_spremi);
		odustani=(Button)findViewById(R.predmeti.sati_dialog_unos_odustani);
	}

	@Override
	public void onClick(View v) {
		if(v==spremi){
			baza.otvori();
			if(provjeriText(planirano)){
				String unos=planirano.getText().toString();
				baza.update(predmet.getId(), unos, BazaPredmeti.KEY_SATI_PLANIRANO);
				predmet.setSatiPlanirano(unos);
			}else{
				baza.update(predmet.getId(), "-1", BazaPredmeti.KEY_SATI_PLANIRANO);
				predmet.setSatiPlanirano("-1");
			}
			if(provjeriText(ostvareno)){
				String unos=ostvareno.getText().toString();
				baza.update(predmet.getId(), unos, BazaPredmeti.KEY_SATI_OSTVARENO);
				predmet.setSatiOstvareno(unos);
			}else{
				baza.update(predmet.getId(), "-1", BazaPredmeti.KEY_SATI_OSTVARENO);
				predmet.setSatiOstvareno("-1");
			}
			if(provjeriText(dopunska)){
				String unos=dopunska.getText().toString();
				baza.update(predmet.getId(), unos, BazaPredmeti.KEY_SATI_DOPUNSKA);
				predmet.setSatiDopunska(unos);
			}else{
				baza.update(predmet.getId(), "-1", BazaPredmeti.KEY_SATI_DOPUNSKA);
				predmet.setSatiDopunska("-1");
			}
			if(provjeriText(strucno)){
				String unos=strucno.getText().toString();
				baza.update(predmet.getId(), unos, BazaPredmeti.KEY_SATI_STRUCNO);
				predmet.setSatiStrucno(unos);
			}else{
				baza.update(predmet.getId(), "-1", BazaPredmeti.KEY_SATI_STRUCNO);
				predmet.setSatiStrucno("-1");
			}
			if(provjeriText(nestrucno)){
				String unos=nestrucno.getText().toString();
				baza.update(predmet.getId(), unos, BazaPredmeti.KEY_SATI_NESTRUCNO);
				predmet.setSatiNeStrucno(unos);
			}else{
				baza.update(predmet.getId(), "-1", BazaPredmeti.KEY_SATI_NESTRUCNO);
				predmet.setSatiNeStrucno("-1");
			}
			baza.zatvori();
			adapter.notifyDataSetChanged();
			dismiss();
		}
		if(v==odustani){
			cancel();
		}
	}
	private boolean provjeriText(EditText provjeri){
		if(provjeri.getText().toString().matches("")){
			return false;
		}
		return true;
	}

}
