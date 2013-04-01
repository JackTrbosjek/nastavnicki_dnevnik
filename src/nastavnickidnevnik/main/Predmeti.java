package nastavnickidnevnik.main;

import java.util.ArrayList;

import nastavnickidnevnik.predmeti.AdapterOcjene;
import nastavnickidnevnik.predmeti.AdapterSati;
import nastavnickidnevnik.predmeti.BazaPredmeti;
import nastavnickidnevnik.predmeti.DialogUnosPredmeta;
import nastavnickidnevnik.predmeti.Predmet;
import nastavnickidnevnik.uspjehUcenici.BazaUspjehUcenici;
import nastavnickidnevnik.uspjehUcenici.Ocjena;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Predmeti extends Activity implements OnClickListener {
	ImageView back,lijevo,desno;
	ListView sati,ocjene;
	LinearLayout headerSati,headerOcjene,dodaj,obrisi;
	BazaPredmeti baza;
	ArrayList<Predmet> popis;
	BazaUspjehUcenici bazaOcjene;
	ArrayList<Ocjena> listOcjene;
	AdapterOcjene ocjeneAdapter;
	AdapterSati satiAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_predmeti);
		inicijalizacija();
		
		baza.otvori();
		popis=baza.dohvatiPredmete();
		baza.zatvori();
		
		bazaOcjene.otvori();
		listOcjene=bazaOcjene.dohvatiOcjene();
		bazaOcjene.zatvori();
		
		//računanje ocjena koje su zapisane kod ucenika i stavljanje na popis u slučaju da već ne postoje neke ocjene na listi
		for(int i =0;i<popis.size();i++){
			int br5=0,br4=0,br3=0,br2=0,br1=0;
			for(int j=0;j<listOcjene.size();j++){
				if(popis.get(i).getId()==listOcjene.get(j).getPredmetId()&&
						popis.get(i).getOdlican()==-1&&
						popis.get(i).getVrloDobar()==-1&&
						popis.get(i).getDobar()==-1&&
						popis.get(i).getDovoljan()==-1&&
						popis.get(i).getNedovoljan()==-1){
					switch(listOcjene.get(j).getOcjena()){
					case 5:
						br5++;
						break;
					case 4:
						br4++;
						break;
					case 3:
						br3++;
						break;
					case 2:
						br2++;
						break;
					case 1:
						br1++;
						break;
					}					
				}
			}
			if(br5!=0||br4!=0||br3!=0||br2!=0||br1!=0){
				popis.get(i).setOdlican(br5+"");
				popis.get(i).setVrloDobar(br4+"");
				popis.get(i).setDobar(br3+"");
				popis.get(i).setDovoljan(br2+"");
				popis.get(i).setNedovoljan(br1+"");
			}
		}
		
		ocjeneAdapter=new AdapterOcjene(this, popis);
		ocjene.setAdapter(ocjeneAdapter);
		
		satiAdapter=new AdapterSati(this, popis);
		sati.setAdapter(satiAdapter);
		
		headerOcjene.setVisibility(View.VISIBLE);
		headerSati.setVisibility(View.GONE);
		
		lijevo.setVisibility(View.INVISIBLE);
	}
	private void inicijalizacija() {
		back=(ImageView)findViewById(R.predmeti.back);
		back.setOnClickListener(this);
		lijevo=(ImageView)findViewById(R.predmeti.lijevo);
		lijevo.setOnClickListener(this);
		desno=(ImageView)findViewById(R.predmeti.desno);
		desno.setOnClickListener(this);
		
		headerOcjene=(LinearLayout)findViewById(R.predmeti.headerOcjene);
		headerSati=(LinearLayout)findViewById(R.predmeti.headerSati);
		
		dodaj=(LinearLayout)findViewById(R.predmeti.dodaj);
		dodaj.setOnClickListener(this);
		obrisi=(LinearLayout)findViewById(R.predmeti.obrisi);
		obrisi.setOnClickListener(this);
		
		sati=(ListView)findViewById(R.predmeti.listSati);
		ocjene=(ListView)findViewById(R.predmeti.listOcjene);
		
		baza=new BazaPredmeti(this);
		bazaOcjene=new BazaUspjehUcenici(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.predmeti.back:
			finish();
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right );
			break;
		case R.predmeti.dodaj:
			new DialogUnosPredmeta(this,ocjeneAdapter,satiAdapter,popis,baza).show();
			break;
		case R.predmeti.obrisi:
			ocjeneAdapter.brisanje();
			satiAdapter.brisanje();
			break;
		case R.predmeti.desno:
			ocjene.setVisibility(View.GONE);
			sati.setVisibility(View.VISIBLE);
			headerOcjene.setVisibility(View.GONE);
			headerSati.setVisibility(View.VISIBLE);
			desno.setVisibility(View.INVISIBLE);
			lijevo.setVisibility(View.VISIBLE);
			satiAdapter.notifyDataSetChanged();
			break;
		case R.predmeti.lijevo:
			sati.setVisibility(View.GONE);
			ocjene.setVisibility(View.VISIBLE);
			headerOcjene.setVisibility(View.VISIBLE);
			headerSati.setVisibility(View.GONE);
			desno.setVisibility(View.VISIBLE);
			lijevo.setVisibility(View.INVISIBLE);
			ocjeneAdapter.notifyDataSetChanged();
			break;
		}
	}
}
