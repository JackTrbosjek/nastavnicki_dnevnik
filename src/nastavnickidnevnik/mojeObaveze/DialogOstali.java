package nastavnickidnevnik.mojeObaveze;

import java.util.ArrayList;

import nastavnickidnevnik.main.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DialogOstali extends Dialog implements OnClickListener {
	Button spremi,odustani;
	EditText sati;
	TextView naslov;
	BazaObaveze baza;
	ArrayList<Obaveza> obaveze;
	int redniBroj;
	String vrsta;
	int satiInt;
	Obaveza trenutna;
	public DialogOstali(Context context, BazaObaveze baza, ArrayList<Obaveza> obaveze,int redniBroj,String vrsta) {
		super(context);
		this.baza=baza;
		this.obaveze=obaveze;
		this.redniBroj=redniBroj;
		this.vrsta=vrsta;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_moje_obaveze_ostali);
		inicijalizacija();
		naslov.setText(vrsta);
		
		//ako već postoje vrijednosti stavljanje u edit text tih vrijednsoti
		trenutna= dohvatiObavezu(redniBroj,"ostali");
		if(trenutna!=null){
			sati.setText(trenutna.getSati()+"");
		}
	}

	private void inicijalizacija() {
		naslov=(TextView)findViewById(R.mojeObaveze.dialog_ostali_naslov);
		
		sati=(EditText)findViewById(R.mojeObaveze.dialog_ostali_sati);
		
		spremi=(Button)findViewById(R.mojeObaveze.dialog_ostali_spremi);
		spremi.setOnClickListener(this);
		
		odustani=(Button)findViewById(R.mojeObaveze.dialog_ostali_odustani);
		odustani.setOnClickListener(this);
	}
	
	//funcija koja pretražuje ArrayList obaveza i vraća obavezu za zadane paramtere
	private Obaveza dohvatiObavezu(int redniBr, String naziv) {
		for (int i = 0; i < obaveze.size(); i++) {
			if (obaveze.get(i).getRedniBroj() == redniBr
					&& obaveze.get(i).getNaziv().equalsIgnoreCase(naziv)) {
				return obaveze.get(i);
			}
		}
		return null;
	}

	@Override
	public void onClick(View v) {
		if(v==spremi){
			if(provjeriSate()){
				baza.otvori();
				baza.updateObaveza(redniBroj, "ostali", vrsta, "ništa a nisam napravio drugi konstruktor",satiInt);
				baza.zatvori();
				obaveze.add(new Obaveza(233,vrsta,redniBroj,"ostali", "ništa a nisam napravio drugi konstruktor",satiInt));
				if(trenutna!=null){
					obaveze.remove(trenutna);
				}
				dismiss();
			}else{
				Toast.makeText(getContext(), "Niste unjeli dobre vrijednosti u polja", Toast.LENGTH_SHORT).show();
			}
		}
		if(v==odustani){
			cancel();
		}
	}

	private boolean provjeriSate(){
		if(sati.getText().toString().matches("")){
			return false;
		}else{
			try {
			    satiInt=Integer.parseInt(sati.getText().toString());
			  } catch (NumberFormatException e) {
			    return false;
			  }
		}
		return true;
	}
}
