 package nastavnickidnevnik.main;

import java.util.ArrayList;
import nastavnickidnevnik.kalendar.DialogPregledBiljeski;
import nastavnickidnevnik.kalendar.KalendarAdapter;
import nastavnickidnevnik.kalendar.Unosi;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

public class Kalendar extends Activity implements OnClickListener,OnItemClickListener {
	ImageView iduciMjesec,prethodniMjesec,back;
	KalendarAdapter adapter;
	GridView gridview;
	TextView naslovMjesec;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kalendar);
		
		inicijalizacija();
		
		gridview.setAdapter(adapter);
		
		naslovMjesec.setText(imeMjeseca(adapter.dohvatiMjesec())+", "+adapter.dohvatiGodinu());
	}
	private void inicijalizacija() {
		
		iduciMjesec=(ImageView)findViewById(R.id.kalendarIduciMjesec);
		iduciMjesec.setOnClickListener(this);
		prethodniMjesec=(ImageView)findViewById(R.id.kalendarPrethodniMjesec);
		prethodniMjesec.setOnClickListener(this);
		back=(ImageView)findViewById(R.id.kalendarBack);
		back.setOnClickListener(this);
		
		naslovMjesec=(TextView)findViewById(R.id.kalendarMjesec);
		
		gridview=(GridView)findViewById(R.id.gridKalendar);
		adapter= new KalendarAdapter(this);
		gridview.setOnItemClickListener(this);
		
		
	}
	@Override 
	public void onClick(View v) {
		if(v==iduciMjesec){
			adapter.iduciMjesec();
			adapter.notifyDataSetChanged();
			naslovMjesec.setText(imeMjeseca(adapter.dohvatiMjesec())+", "+adapter.dohvatiGodinu());
		}
		if(v==prethodniMjesec){
			adapter.prethodniMjesec();
			adapter.notifyDataSetChanged();
			naslovMjesec.setText(imeMjeseca(adapter.dohvatiMjesec())+", "+adapter.dohvatiGodinu());
		}
		if(v==back){
			finish();
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right );
		}
	}
	
	private String imeMjeseca(int mjesec){
		switch(mjesec){
		case 0: return "Sijeèanj"; 
		case 1: return "Veljaèa";
		case 2: return "Ožujak"; 
		case 3: return "Travanj";
		case 4: return "Svibanj";
		case 5: return "Lipanj";
		case 6: return "Srpanj";
		case 7: return "Kolovoz";
		case 8: return "Rujan";
		case 9: return "Listopad";
		case 10: return "Studeni";
		case 11: return "Prosinac";
		}
		return null;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id){
		ArrayList<Unosi> posalji= adapter.dohvatiBiljeskeZaDan(position);
		if(posalji.size()>0){
			DialogPregledBiljeski dialog=new DialogPregledBiljeski(this,adapter.dohvatiDan(position),adapter.dohvatiMjesec(),adapter.dohvatiGodinu(),posalji,adapter);
			dialog.show();
		}else{
			DialogPregledBiljeski dialog=new DialogPregledBiljeski(this,adapter.dohvatiDan(position),adapter.dohvatiMjesec(),adapter.dohvatiGodinu(),posalji,adapter);
			dialog.show();
		}
	}

	
}
