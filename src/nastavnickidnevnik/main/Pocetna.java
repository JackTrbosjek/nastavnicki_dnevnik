package nastavnickidnevnik.main;

import nastavnickidnevnik.pocetna.InfoDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Pocetna extends Activity implements OnClickListener {
	LinearLayout kalendar,godina,popisUcenika,uceniciRaspored,predmeti,uspjehUcenici,planRada,mojeObaveze,mojRaspored;
	ImageView infoButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pocetna);
		inicijalizacija();
	
	
	}
 
	private void inicijalizacija() {
		kalendar = (LinearLayout) findViewById(R.id.btnKalendar);
		kalendar.setOnClickListener(this);
		
		godina=(LinearLayout)findViewById(R.id.btnGodina);
		godina.setOnClickListener(this);
		
		popisUcenika=(LinearLayout)findViewById(R.id.popisUcenika);
		popisUcenika.setOnClickListener(this);
		
		uceniciRaspored=(LinearLayout)findViewById(R.id.btnUceniciRaspored);
		uceniciRaspored.setOnClickListener(this);
		
		uspjehUcenici=(LinearLayout)findViewById(R.id.btnUspjehUcenici);
		uspjehUcenici.setOnClickListener(this);
		
		predmeti=(LinearLayout)findViewById(R.id.btnPredmeti);
		predmeti.setOnClickListener(this);
		
		planRada=(LinearLayout)findViewById(R.id.btnPlanRada);
		planRada.setOnClickListener(this);
		
		mojeObaveze=(LinearLayout)findViewById(R.id.btnMojeObaveze);
		mojeObaveze.setOnClickListener(this);
		
		mojRaspored=(LinearLayout)findViewById(R.id.btnMojRaspored);
		mojRaspored.setOnClickListener(this);
		
		infoButton=(ImageView)findViewById(R.id.infoButton);
		infoButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == kalendar) {
			Intent i = new Intent(Pocetna.this, Kalendar.class);
			startActivity(i);
			overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_left );
		}
		if (v == godina) {
			Intent i = new Intent(Pocetna.this, Godina.class);
			startActivity(i);
			overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_left );
		}
		if (v == popisUcenika) {
			Intent i = new Intent(Pocetna.this, PopisUcenika.class);
			startActivity(i);
			overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_left );
		}
		if (v == uceniciRaspored) {
			Intent i = new Intent(Pocetna.this, UceniciRaspored.class);
			startActivity(i);
			overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_left );
		}
		if (v == uspjehUcenici) {
			Intent i = new Intent(Pocetna.this, UspjehUcenici.class);
			startActivity(i);
			overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_left );
		}
		if(v== predmeti){
			Intent i = new Intent(Pocetna.this, Predmeti.class);
			startActivity(i);
			overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_left );
		}
		if(v==planRada){
			Intent i = new Intent(Pocetna.this, PlanRada.class);
			startActivity(i);
			overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_left );
		}
		if(v==mojeObaveze){
			Intent i = new Intent(Pocetna.this, MojeObaveze.class);
			startActivity(i);
			overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_left );
		}
		if(v==mojRaspored){
			Intent i = new Intent(Pocetna.this, MojRaspored.class);
			startActivity(i);
			overridePendingTransition( R.anim.slide_in_left,R.anim.slide_out_left );
		}
		if(v==infoButton){
			new InfoDialog(this).show();
		}
	}
}
