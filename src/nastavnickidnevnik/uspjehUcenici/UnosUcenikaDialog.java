package nastavnickidnevnik.uspjehUcenici;

import java.util.ArrayList;
import java.util.Collections;

import nastavnickidnevnik.main.R;
import nastavnickidnevnik.popisUcenika.BazaPopisUcenika;
import nastavnickidnevnik.popisUcenika.Ucenik;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UnosUcenikaDialog extends Dialog implements android.view.View.OnClickListener {
	EditText redniBroj,ime,prezime;
	Button spremi,odustani;
	Context context;
	int intRedniBroj;
	String sIme,sPrezime;
	AdapterExpandableList adapter;
	BazaPopisUcenika baza;
	ArrayList<Ucenik> popis;
	public UnosUcenikaDialog(Context context,AdapterExpandableList adapter,ArrayList<Ucenik> popis,BazaPopisUcenika baza) {
		super(context);
		this.context=context;
		this.baza=baza;
		this.adapter=adapter;
		this.popis=popis;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_popis_ucenika_unos_ucenika_dialog);
		inicijalizacija();
	}

	private void inicijalizacija() {
		redniBroj=(EditText)findViewById(R.popisUcenika.unos_ucenika_dialog_redni_broj);
		ime=(EditText)findViewById(R.popisUcenika.unos_ucenika_dialog_ime);
		prezime=(EditText)findViewById(R.popisUcenika.unos_ucenika_dialog_prezime);
		
		spremi=(Button)findViewById(R.popisUcenika.unos_ucenika_dialog_spremi);
		odustani=(Button)findViewById(R.popisUcenika.unos_ucenika_dialog_odustani);
		spremi.setOnClickListener(this);
		odustani.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.popisUcenika.unos_ucenika_dialog_spremi:
			if(!upisanRB()){
				Toast.makeText(context, "Niste unjeli redni broj ili nije broj",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if(!upisanoIme()){
				Toast.makeText(context, "Niste unjeli ime",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if(!upisanoPrezime()){
				Toast.makeText(context, "Niste unjeli prezime",
						Toast.LENGTH_SHORT).show();
				break;
			}
			spremiPodatke();
			dismiss();
			break;
		case R.popisUcenika.unos_ucenika_dialog_odustani:
			cancel();
			break;
		}
	}
	
	private void spremiPodatke() {
		baza.otvori();
		long bazaID=baza.dodaj(intRedniBroj, sIme, sPrezime);
		baza.zatvori();
		popis.add(new Ucenik(bazaID,intRedniBroj, sIme, sPrezime));
		Collections.sort(popis);
		adapter.notifyDataSetChanged();
	}

	public boolean upisanRB(){
		if(redniBroj.getText().toString().matches("")){
			return false;
		}
		try { 
	        intRedniBroj=Integer.parseInt(redniBroj.getText().toString()); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
		return true;
	}
	public boolean upisanoIme(){
		if(ime.getText().toString().matches("")){
			return false;
		}
		sIme=ime.getText().toString();
		return true;
	}
	public boolean upisanoPrezime(){
		if(prezime.getText().toString().matches("")){
			return false;
		}
		sPrezime=prezime.getText().toString();
		return true;
	}

}
