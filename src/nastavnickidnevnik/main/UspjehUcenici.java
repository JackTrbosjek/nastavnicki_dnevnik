package nastavnickidnevnik.main;

import java.util.ArrayList;
import java.util.Random;

import nastavnickidnevnik.popisUcenika.BazaPopisUcenika;
import nastavnickidnevnik.popisUcenika.Ucenik;
import nastavnickidnevnik.predmeti.BazaPredmeti;
import nastavnickidnevnik.predmeti.Predmet;
import nastavnickidnevnik.uspjehUcenici.AdapterExpandableList;
import nastavnickidnevnik.uspjehUcenici.BazaUspjehUcenici;
import nastavnickidnevnik.uspjehUcenici.DialogUnosPredmeta;
import nastavnickidnevnik.uspjehUcenici.Ocjena;
import nastavnickidnevnik.uspjehUcenici.UnosUcenikaDialog;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class UspjehUcenici extends Activity implements OnClickListener{
	ImageView back;
	LinearLayout dodajUcenika,dodajPredmet,edit;
	ExpandableListView lista;
	AdapterExpandableList adapter;
	BazaUspjehUcenici bazaOcjene;
	BazaPredmeti bazaPredmet;
	BazaPopisUcenika bazaUcenici;
	ArrayList<Ucenik> ucenici;
	ArrayList<Predmet> predmeti;
	ArrayList<Ocjena> ocjene;
	boolean editFlag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uspjeh_ucenici);
		inicijalizacija();
		bazaUcenici.otvori();
		ucenici=bazaUcenici.dohvatiUcenike();
		bazaUcenici.zatvori();
		bazaPredmet.otvori();
		predmeti=bazaPredmet.dohvatiPredmete();
		bazaPredmet.zatvori();
		bazaOcjene.otvori();
		ocjene=bazaOcjene.dohvatiOcjene();
		bazaOcjene.zatvori();
		
		adapter=new AdapterExpandableList(this,ucenici,predmeti,ocjene,randomBoje(200),lista,bazaOcjene);
		lista.setAdapter(adapter);
		
	}

	private void inicijalizacija() {
		back=(ImageView) findViewById(R.uspjehUcenici.back);
		back.setOnClickListener(this);
		
		dodajUcenika=(LinearLayout)findViewById(R.uspjehUcenici.dodajUcenika);
		dodajUcenika.setOnClickListener(this);
		
		dodajPredmet=(LinearLayout)findViewById(R.uspjehUcenici.dodajPredmet);
		dodajPredmet.setOnClickListener(this);
		
		edit=(LinearLayout)findViewById(R.uspjehUcenici.edit);
		edit.setOnClickListener(this);
		editFlag=false;
		edit.setBackgroundColor(Color.parseColor("#189E00"));
		
		lista=(ExpandableListView)findViewById(R.uspjehUcenici.expList);
		lista.setGroupIndicator(null);
		
		bazaOcjene=new BazaUspjehUcenici(this);
		bazaPredmet= new BazaPredmeti(this);
		bazaUcenici= new BazaPopisUcenika(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.uspjehUcenici.back:
			finish();
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right );
			break;
		case R.uspjehUcenici.dodajPredmet:
			new DialogUnosPredmeta(this, adapter, predmeti, bazaPredmet).show();
			break;
		case R.uspjehUcenici.dodajUcenika:
			new UnosUcenikaDialog(this, adapter, ucenici, bazaUcenici).show();
			break;
		case R.uspjehUcenici.edit:
			if(editFlag){
				edit.setBackgroundColor(Color.parseColor("#189E00"));
			}else{
				edit.setBackgroundColor(Color.parseColor("#0097B5"));
			}
			editFlag=!editFlag;
			adapter.edit();
		}
	}
	
	private ArrayList<Integer[]> randomBoje(int size){
		String roza="#ED4287",plava="#3988E3",crvena="#ED0928",zuta="#F0873C",zelena="#743BED";
		String roza1="#EDA6C3",plava1="#9FBFE3",crvena1="#ED5F72",zuta1="#F0C6A8",zelena1="#BDA6ED";
		ArrayList<Integer []> boje=new ArrayList<Integer []>();
		
		boje.add(new Integer[]{Color.parseColor(plava),Color.parseColor(plava1)});
		boje.add(new Integer[]{Color.parseColor(roza),Color.parseColor(roza1)});
		boje.add(new Integer[]{Color.parseColor(crvena),Color.parseColor(crvena1)});
		boje.add(new Integer[]{Color.parseColor(zuta),Color.parseColor(zuta1)});
		boje.add(new Integer[]{Color.parseColor(zelena),Color.parseColor(zelena1)});
		ArrayList<Integer[]> vrati=new ArrayList<Integer[]>();
		Random random= new Random();
		int temp=random.nextInt(5);
		for(int i =0;i<size;i++){
			int broj=random.nextInt(5);
			while(temp==broj)broj=random.nextInt(5);
			vrati.add(boje.get(broj));
			temp=broj;
		}
		return vrati;
	}

}
