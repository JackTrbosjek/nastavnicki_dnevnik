package nastavnickidnevnik.main;

import java.util.ArrayList;

import nastavnickidnevnik.planRada.BazaPlanRada;
import nastavnickidnevnik.planRada.DialogUnosPregled;
import nastavnickidnevnik.planRada.Sadrzaj;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PlanRada extends Activity implements OnClickListener {
	ImageView back;
	TableLayout table;
	Context context;
	BazaPlanRada baza;
	ArrayList<Sadrzaj> sadrzaji;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan_rada);
		inicijalizacija();
		pocetnoPostavljanje();
		postavljanjeVrijednosti();
	}
	private void postavljanjeVrijednosti() {
		baza.otvori();
		sadrzaji=baza.dohvatiSadrzaje();
		baza.zatvori();
		for(int i =1;i<table.getChildCount();i++){
			ArrayList<Sadrzaj> trenutni=dohvatiTrenutniSadrzaj(getSadrzaj(i));
			TableRow row=(TableRow) table.getChildAt(i);
			TextView nositelji=(TextView)row.getChildAt(1);
			TextView planirano=(TextView)row.getChildAt(2);
			TextView ostvareno=(TextView)row.getChildAt(3);
			String nositelj="";
			String planiran="";
			String ostvaren="";
			for(int j=0;j<trenutni.size();j++){
				nositelj+=trenutni.get(j).getNositelj()+"\n";
				planiran+=trenutni.get(j).getPlaniranoVrijeme()+"\n";
				ostvaren+=trenutni.get(j).getOstvarenoVrijeme()+"\n";
			}
			nositelji.setText(nositelj);
			planirano.setText(planiran);
			ostvareno.setText(ostvaren);
		}
	}
	
	private ArrayList<Sadrzaj> dohvatiTrenutniSadrzaj(String s) {
		ArrayList<Sadrzaj> trenutni=new ArrayList<Sadrzaj>();
		for(int i =0;i<sadrzaji.size();i++){
			if(sadrzaji.get(i).getSadrzaj().equals(s)){
				trenutni.add(sadrzaji.get(i));
			}
		}
		return trenutni;
	}
	private void pocetnoPostavljanje() {
		for(int i = 1; i<table.getChildCount();i++){
			final int broj=i;
			TableRow row=(TableRow) table.getChildAt(i);
			TextView sadrzaj=(TextView)row.getChildAt(0);
			sadrzaj.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					DialogUnosPregled dialog=new DialogUnosPregled(context,getSadrzaj(broj),dohvatiTrenutniSadrzaj(getSadrzaj(broj)),baza);
					dialog.show();
					dialog.setOnDismissListener(new OnDismissListener(){
						@Override
						public void onDismiss(DialogInterface dialog) {
							postavljanjeVrijednosti();
						}});
					dialog.setOnCancelListener(new OnCancelListener(){
						@Override
						public void onCancel(DialogInterface arg0) {
							postavljanjeVrijednosti();
						}});
				}
			});
		}
	}
	private void inicijalizacija() {
		context=this;
		back=(ImageView)findViewById(R.planRada.back);
		back.setOnClickListener(this);
		
		table=(TableLayout)findViewById(R.planRada.table);
		
		baza=new BazaPlanRada(context);
		baza.otvori();
		sadrzaji=baza.dohvatiSadrzaje();
		baza.zatvori();
	}
	
	@Override
	public void onClick(View v) {
		if(v==back){
			finish();
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right );
		}
	}
	public static String getSadrzaj(int i){
		switch(i){
		case 1: return "rad s ucenicima";
		case 2: return "suradnja s roditeljima";
		case 3: return "suradnja s clanovima rv";
		case 4: return "kulturna i drustvena djelatnost";
		case 5: return "zdravstvena i socijalna zaštita";
		case 6: return "administrativni poslovi";
		}
		return null;
	}

}
