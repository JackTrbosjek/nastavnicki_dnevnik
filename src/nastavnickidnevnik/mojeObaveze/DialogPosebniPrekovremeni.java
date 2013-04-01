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

public class DialogPosebniPrekovremeni extends Dialog implements OnClickListener {
	Button spremi,odustani;
	EditText sati,naziv;
	TextView naslov;
	BazaObaveze baza;
	ArrayList<Obaveza> obaveze;
	int redniBroj;
	String vrsta,opis;
	int satiInt;
	Obaveza trenutna;
	public DialogPosebniPrekovremeni(Context context, BazaObaveze baza, ArrayList<Obaveza> obaveze,int redniBroj,String vrsta, String opis) {
		super(context);
		this.baza=baza;
		this.obaveze=obaveze;
		this.redniBroj=redniBroj;
		this.vrsta=vrsta;
		this.opis=opis;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_moje_obaveze_posebni_prekovremeni);
		inicijalizacija();
		naslov.setText(vrsta);
		naziv.setText(vrsta);
		//ako veæ postoje vrijednosti stavljanje u edit text tih vrijednsoti
		trenutna= dohvatiObavezu(redniBroj,opis);
		if(trenutna!=null){
			sati.setText(trenutna.getSati()+"");
		}
	}

	private void inicijalizacija() {
		naslov=(TextView)findViewById(R.mojeObaveze.dialog_posebni_prekovremeni_naslov);
		
		sati=(EditText)findViewById(R.mojeObaveze.dialog_posebni_prekovremeni_sati);
		naziv=(EditText)findViewById(R.mojeObaveze.dialog_posebni_prekovremeni_naziv);
		
		spremi=(Button)findViewById(R.mojeObaveze.dialog_posebni_prekovremeni_spremi);
		spremi.setOnClickListener(this);
		
		odustani=(Button)findViewById(R.mojeObaveze.dialog_posebni_prekovremeni_odustani);
		odustani.setOnClickListener(this);
	}
	
	//funcija koja pretraÅ¾uje ArrayList obaveza i vraÄ‡a obavezu za zadane paramtere
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
			if(provjeriSate()&&provjeriNaziv()){
				vrsta=naziv.getText().toString();
				baza.otvori();
				baza.updateObaveza(redniBroj, opis, vrsta, "ništa a nisam napravio drugi konstruktor",satiInt);
				baza.zatvori();
				obaveze.add(new Obaveza(233,vrsta,redniBroj,opis, "ništa a nisam napravio drugi konstruktor",satiInt));
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
	private boolean provjeriNaziv(){
		if(naziv.getText().toString().matches("")){
			return false;
		}
		return true;
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
