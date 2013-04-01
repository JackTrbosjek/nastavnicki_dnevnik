package nastavnickidnevnik.main;

import java.util.ArrayList;

import nastavnickidnevnik.mojRaspored.MojRasporedAdapter;
import nastavnickidnevnik.mojRaspored.MojRasporedBaza;
import nastavnickidnevnik.mojRaspored.PredmetMojRaspored;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.app.Activity;

public class MojRaspored extends Activity implements OnClickListener,OnCheckedChangeListener {
	GridView raspored;
	MojRasporedAdapter adapter;
	ImageView back;
	TableLayout table;
	RadioGroup radioGroup;
	int prijePoslije;
	ArrayList<PredmetMojRaspored> prijepodne;
	ArrayList<PredmetMojRaspored> poslijepodne;
	MojRasporedBaza baza;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moj_raspored);
		inicijalizacija();
		
	}

	private void inicijalizacija() {
		baza=new MojRasporedBaza(this);
		baza.otvori();
		prijepodne=baza.dohvatiPrijepodne();
		poslijepodne=baza.dohvatiPoslijepodne();
		baza.zatvori();
		prijePoslije=1;
		
		back=(ImageView)findViewById(R.mojRaspored.back);
		back.setOnClickListener(this);
		
		radioGroup=(RadioGroup)findViewById(R.mojRaspored.radio_group);
		radioGroup.setOnCheckedChangeListener(this);
		adapter=new MojRasporedAdapter(this,baza,prijePoslije);
		raspored=(GridView)findViewById(R.mojRaspored.grid);
		raspored.setEnabled(false);
		raspored.setAdapter(adapter);
		adapter.setPopis(prijepodne);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.mojRaspored.back:
			finish();
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right );
			break; 
		}
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.mojRaspored.prijepodne:
			prijePoslije=1;
			adapter.setPopis(prijepodne);
			break;
		case R.mojRaspored.poslijepodne:
			prijePoslije=0;
			adapter.setPopis(poslijepodne);
			break;
		}
	}
}