package nastavnickidnevnik.popisUcenika;

import java.util.ArrayList;
import java.util.Random;

import nastavnickidnevnik.main.R;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class DialogDodatno extends Dialog implements OnClickListener {

	private EditText biljeska, razgovor, roditeljski;
	private Button spremi, odustani, zatvori;
	private Context mContext;
	private ExpandableListView pregled;
	private Ucenik ucenik;
	private TabHost tabs;
	private BazaPopisUcenika baza;
	private DialogDodatnoAdapter pregledAdapter;

	public DialogDodatno(Context context, Ucenik ucenik) {
		super(context);
		mContext = context;
		this.ucenik = ucenik;
		baza = new BazaPopisUcenika(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.layout_popis_ucenika_dialog_dodatno);

		tabs = (TabHost) findViewById(R.id.tabhost);
		tabs.setup();

		TabSpec tabUnosEdit = tabs.newTabSpec("Tab1");
		tabUnosEdit.setIndicator("PREGLED");
		tabUnosEdit.setContent(R.id.tab11);
		tabs.addTab(tabUnosEdit);

		TabSpec tabPregled = tabs.newTabSpec("Tab2");
		tabPregled.setIndicator("UNOS");

		tabPregled.setContent(R.id.tab22);
		tabs.addTab(tabPregled);
		for (int i = 0; i < tabs.getTabWidget().getChildCount(); i++) {
			tabs.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.tab_selector);
		}

		if ((ucenik.getBiljeske().size() > 0)
				|| (ucenik.getRoditeljski().size() > 0)
				|| (ucenik.getRazgovori().size() > 0)) {
			tabs.setCurrentTab(0);
		} else {
			tabs.setCurrentTab(1);
			tabs.getTabWidget().getChildTabViewAt(0).setEnabled(false);
		}

		inicijalizacijaUnos();
		inicijalizacijaPregled();
	}

	private void inicijalizacijaPregled() {
		pregled = (ExpandableListView) findViewById(R.popisUcenika.dodatno_expandable_list);
		pregled.setGroupIndicator(null);
		pregled.setClickable(true);
		pregledAdapter = new DialogDodatnoAdapter(ucenik, mContext, pregled,
				randomBoje(3));
		pregled.setAdapter(pregledAdapter);

		zatvori = (Button) findViewById(R.popisUcenika.dodatno_zatvori);
		zatvori.setOnClickListener(this);

	}

	private void inicijalizacijaUnos() {

		biljeska = (EditText) findViewById(R.popisUcenika.dodatno_biljeska);
		razgovor = (EditText) findViewById(R.popisUcenika.dodatno_razgovor);
		roditeljski = (EditText) findViewById(R.popisUcenika.dodatno_roditeljski);

		spremi = (Button) findViewById(R.popisUcenika.dodatno_spremi);
		odustani = (Button) findViewById(R.popisUcenika.dodatno_odustani);

		spremi.setOnClickListener(this);
		odustani.setOnClickListener(this);

	}

	private boolean imaBiljesku() {
		if (biljeska.getText().toString().matches("")) {
			return false;
		}
		return true;
	}

	private boolean imaRoditeljski() {
		if (roditeljski.getText().toString().matches("")) {
			return false;
		}
		return true;
	}

	private boolean imaRazgovor() {
		if (razgovor.getText().toString().matches("")) {
			return false;
		}
		return true;
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.popisUcenika.dodatno_spremi:
			if (!imaRazgovor() && !imaBiljesku() && !imaRoditeljski()) {
				Toast.makeText(mContext, "Unesite barem jedno polje",
						Toast.LENGTH_SHORT).show();
				break;
			} else {
				baza.otvori();
				if (imaRazgovor()) {
					ucenik.addRazgovor(razgovor.getText().toString());
					baza.updateRazgovor(ucenik.getId(), ucenik.getRazgovori());
				}
				if (imaBiljesku()) {
					ucenik.addBiljeska(biljeska.getText().toString());
					baza.updateBiljeska(ucenik.getId(), ucenik.getBiljeske());
				}
				if (imaRoditeljski()) {
					ucenik.addRoditeljski(roditeljski.getText().toString());
					baza.updateRoditeljski(ucenik.getId(),
							ucenik.getRoditeljski());
				}
				baza.zatvori();
				dismiss();
				break;
			}
			
		case R.popisUcenika.dodatno_zatvori:
			cancel();
			break;

		case R.popisUcenika.dodatno_odustani:
			cancel();
			break;
		}
	}

	// generiranje različitih boja za pregled bilješki
	private ArrayList<Integer[]> randomBoje(int size) {
		String roza = "#ED4287", plava = "#3988E3", crvena = "#ED0928", zuta = "#F0873C", zelena = "#743BED";
		String roza1 = "#EDA6C3", plava1 = "#9FBFE3", crvena1 = "#ED5F72", zuta1 = "#F0C6A8", zelena1 = "#BDA6ED";
		ArrayList<Integer[]> boje = new ArrayList<Integer[]>();

		boje.add(new Integer[] { Color.parseColor(plava),
				Color.parseColor(plava1) });
		boje.add(new Integer[] { Color.parseColor(roza),
				Color.parseColor(roza1) });
		boje.add(new Integer[] { Color.parseColor(crvena),
				Color.parseColor(crvena1) });
		boje.add(new Integer[] { Color.parseColor(zuta),
				Color.parseColor(zuta1) });
		boje.add(new Integer[] { Color.parseColor(zelena),
				Color.parseColor(zelena1) });
		ArrayList<Integer[]> vrati = new ArrayList<Integer[]>();
		Random random = new Random();
		int temp = random.nextInt(5);
		for (int i = 0; i < size; i++) {
			int broj = random.nextInt(5);
			while (temp == broj)
				broj = random.nextInt(5);
			vrati.add(boje.get(broj));
			temp = broj;
		}
		return vrati;
	}
}
