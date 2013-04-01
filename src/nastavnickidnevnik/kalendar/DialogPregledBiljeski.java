package nastavnickidnevnik.kalendar;

import java.util.ArrayList;
import java.util.Random;

import nastavnickidnevnik.main.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class DialogPregledBiljeski extends Dialog implements OnClickListener {

	private EditText naslov, opis;
	private Button spremi, odustani,zatvori;
	private Context mContext;
	private TextView title,titlePregled;
	private String textNaslov,textNaslovPregled;
	private BazaKalendar baza;
	private int dan, mjesec, godina;
	private ExpandableListView pregled;
	private ArrayList<Unosi> biljeske;
	private KalendarAdapter adapter;
	private PregledBiljeskiAdapter pregledAdapter;
	private TabHost tabs;

	public DialogPregledBiljeski(Context context, int dan, int mjesec,
			int godina, ArrayList<Unosi> biljeske, KalendarAdapter adapter) {
		super(context);
		mContext = context;
		this.dan = dan;
		this.mjesec = mjesec;
		this.godina = godina;
		this.adapter = adapter;
		textNaslov = "Unos nove biljeöke: " + dan + "." + (mjesec + 1) + "."
				+ godina + ".";
		textNaslovPregled = "Pregled biljeöki: " + dan + "." + (mjesec + 1) + "."
				+ godina + ".";
		baza = new BazaKalendar(context);
		this.biljeske = biljeske;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.layout_kalendar_pregled_biljeski);

		tabs = (TabHost) findViewById(R.id.tabhost);
		tabs.setup();

		TabSpec tabUnosEdit = tabs.newTabSpec("Tab1");
		tabUnosEdit.setIndicator("PREGLED BILJEäKI");
		tabUnosEdit.setContent(R.id.tab1);
		tabs.addTab(tabUnosEdit);

		TabSpec tabPregled = tabs.newTabSpec("Tab2");
		tabPregled.setIndicator("UNOS BILJEäKE");

		tabPregled.setContent(R.id.tab2);
		tabs.addTab(tabPregled);
		for (int i = 0; i < tabs.getTabWidget().getChildCount(); i++) {
			tabs.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.tab_selector);
		}
		
		if(biljeske.size()>0){
			tabs.setCurrentTab(0);
		}else{
			tabs.setCurrentTab(1);
			tabs.getTabWidget().getChildTabViewAt(0).setEnabled(false);
		}

		inicijalizacijaUnos();
		inicijalizacijaPregled();
	}

	private void inicijalizacijaPregled() {
		titlePregled=(TextView)findViewById(R.id.dialog_naslovPregled);
		titlePregled.setText(textNaslovPregled);
		
		pregled = (ExpandableListView) findViewById(R.id.kalendar_expandableListView);
		pregled.setGroupIndicator(null);
		pregled.setClickable(true);
		pregledAdapter = new PregledBiljeskiAdapter(biljeske,
				mContext, this.adapter, pregled,randomBoje(biljeske.size()));
		pregled.setAdapter(pregledAdapter);
		
		zatvori=(Button)findViewById(R.id.dialogZatvori);
		zatvori.setOnClickListener(this);

	}

	private void inicijalizacijaUnos() {
		title = (TextView) findViewById(R.id.dialogNaslov);
		title.setText(textNaslov);

		naslov = (EditText) findViewById(R.id.dialogUnosNaslov);
		opis = (EditText) findViewById(R.id.dialogUnosOpis);
		spremi = (Button) findViewById(R.id.dialogSpremi);
		odustani = (Button) findViewById(R.id.dialogOdustani);

		spremi.setOnClickListener(this);
		odustani.setOnClickListener(this);

	}

	private boolean imaNaslov() {
		if (naslov.getText().toString().matches("")) {
			return false;
		}
		return true;
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.dialogSpremi:
			if (!imaNaslov()) {
				Toast.makeText(mContext, "Niste unjeli naslov",
						Toast.LENGTH_SHORT).show();
			} else {
				baza.otvori();
				long id = baza.dodaj(dan, mjesec, godina, naslov.getText()
						.toString(), opis.getText().toString());
				baza.zatvori();
				adapter.dodajBiljesku((int) id, dan, naslov.getText()
						.toString(), opis.getText().toString());
				adapter.notifyDataSetChanged();
				dismiss();
			}
			break;
		case R.id.dialogOdustani:
			cancel();
			break;

		case R.id.dialogZatvori:
			cancel();
			break;
		}
	}
	
	//generiranje razliƒçitih boja za pregled biljeski
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
