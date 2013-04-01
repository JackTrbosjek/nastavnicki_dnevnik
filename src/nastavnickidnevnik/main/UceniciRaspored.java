package nastavnickidnevnik.main;

import java.util.ArrayList;

import nastavnickidnevnik.uceniciRaspored.PredmetRaspored;
import nastavnickidnevnik.uceniciRaspored.RasporedAdapter;
import nastavnickidnevnik.uceniciRaspored.RasporedUceniciBaza;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;

public class UceniciRaspored extends Activity implements OnClickListener,OnCheckedChangeListener {
	GridView raspored;
	RasporedAdapter adapter;
	ImageView back;
	TableLayout table;
	RadioGroup radioGroup;
	int prijePoslije;
	ArrayList<PredmetRaspored> prijepodne,poslijepodne;
	RasporedUceniciBaza baza;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ucenici_raspored);
		inicijalizacija();
		
	}

	private void inicijalizacija() {
		baza=new RasporedUceniciBaza(this);
		baza.otvori();
		prijepodne=baza.dohvatiPrijepodne();
		poslijepodne=baza.dohvatiPoslijepodne();
		baza.zatvori();
		prijePoslije=1;
		
		back=(ImageView)findViewById(R.uceniciRaspored.back);
		back.setOnClickListener(this);
		
		radioGroup=(RadioGroup)findViewById(R.uceniciRaspored.radio_group);
		radioGroup.setOnCheckedChangeListener(this);
		adapter=new RasporedAdapter(this,baza,prijePoslije);
		raspored=(GridView)findViewById(R.uceniciRaspored.grid);
		raspored.setEnabled(false);
		raspored.setAdapter(adapter);
		adapter.setPopis(prijepodne);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.uceniciRaspored.back:
			finish();
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right );
			break; 
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.uceniciRaspored.prijepodne:
			prijePoslije=1;
			adapter.setPopis(prijepodne);
			break;
		case R.uceniciRaspored.poslijepodne:
			prijePoslije=0;
			adapter.setPopis(poslijepodne);
			break;
		}
	}
}
