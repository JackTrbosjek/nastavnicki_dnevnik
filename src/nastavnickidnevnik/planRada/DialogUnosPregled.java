package nastavnickidnevnik.planRada;

import java.util.ArrayList;

import nastavnickidnevnik.main.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class DialogUnosPregled extends Dialog implements
		android.view.View.OnClickListener {
	TabHost tabHost;
	EditText nositelj, planirano, ostvareno;
	Button spremi, odustani,zatvori;
	ListView list;
	String sadrzaj;
	ArrayList<Sadrzaj> sadrzaji;
	BazaPlanRada baza;
	Context context;
	DialogListAdapter adapter;
	Sadrzaj edit;
	boolean editFlag;

	public DialogUnosPregled(Context context, String sadrzaj,
			ArrayList<Sadrzaj> sadrzaji, BazaPlanRada baza) {
		super(context);
		this.context = context;
		this.sadrzaj = sadrzaj;
		this.sadrzaji = sadrzaji;
		this.baza = baza;
		editFlag=false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_plan_rada_dialog_unos_pregled);
		inicijalizacija();
		inicijalizacijaUnos();
		inicijalizacijaPregled();
	}
	public void edit(Sadrzaj edit){
		this.edit=edit;
		this.nositelj.setText(edit.getNositelj());
		this.planirano.setText(edit.getPlaniranoVrijeme());
		this.ostvareno.setText(edit.getOstvarenoVrijeme());
		editFlag=true;
		tabHost.setCurrentTab(0);
	}

	private void inicijalizacijaPregled() {
		adapter=new DialogListAdapter(context, sadrzaji,this);
		
		list=(ListView)findViewById(R.planRada.dialog_list);
		list.setAdapter(adapter);
		
	}

	private void inicijalizacijaUnos() {
		nositelj = (EditText) findViewById(R.planRada.dialog_unos_nositelj);
		planirano = (EditText) findViewById(R.planRada.dialog_unos_planirano);
		ostvareno = (EditText) findViewById(R.planRada.dialog_unos_ostvareno);
		planirano.addTextChangedListener(new DateChecker(planirano));
		ostvareno.addTextChangedListener(new DateChecker(ostvareno));

		spremi = (Button) findViewById(R.planRada.dialog_unos_spremi);
		spremi.setOnClickListener(this);
		odustani = (Button) findViewById(R.planRada.dialog_unos_odustani);
		odustani.setOnClickListener(this);
	}

	private void inicijalizacija() {
		tabHost = (TabHost) findViewById(R.planRada.dialog_tab_host);
		tabHost.setup();

		TabSpec tabUnosEdit = tabHost.newTabSpec("Tab1");
		tabUnosEdit.setIndicator("UNOS");
		tabUnosEdit.setContent(R.planRada.dialog_tab_unos);
		tabHost.addTab(tabUnosEdit);

		TabSpec tabPregled = tabHost.newTabSpec("Tab2");
		tabPregled.setIndicator("PREGLED");
		tabPregled.setContent(R.planRada.dialog_tab_pregled);
		tabHost.addTab(tabPregled);
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.tab_selector);
		}
	}
	
	@Override
	public void onClick(View v) {
		if (v == spremi) {
			if (provjeriDuzinu(ostvareno) && provjeriDuzinu(planirano)) {
				baza.otvori();
				
				if(editFlag){
					baza.update(edit.getId(),nositelj.getText()
							.toString(), planirano.getText().toString(), ostvareno
							.getText().toString());
					updateEdit();
					editFlag=false;
				}else{
					long id = baza.dodajSadrzaj(sadrzaj, nositelj.getText()
							.toString(), planirano.getText().toString(), ostvareno
							.getText().toString());
					sadrzaji.add(new Sadrzaj(id, sadrzaj, nositelj.getText()
							.toString(), planirano.getText().toString(), ostvareno
							.getText().toString()));
				}
				baza.zatvori();
				
				dismiss();
			} else {
				Toast.makeText(context,
						"Provjerite jesu li podaci dobro unešeni",
						Toast.LENGTH_SHORT).show();
			}
		}
		if (v == odustani) {
			cancel();
		}
	}

	private void updateEdit() {
		edit.setNositelj(nositelj.getText().toString());
		edit.setPlaniranoVrijeme(planirano.getText().toString());
		edit.setOstvarenoVrijeme(ostvareno.getText().toString());
	}

	private boolean provjeriDuzinu(EditText et) {
		if ((et.getText().toString().length() == 11 || et.getText().toString()
				.length() == 0)
				&& nositelj.getText().toString().length() != 0) {
			return true;
		}
		return false;
	}

	private class DateChecker implements TextWatcher {
		final EditText editText;

		public DateChecker(EditText editText) {
			this.editText = editText;
		}

		@Override
		public void afterTextChanged(Editable s) {
			String brojevi = sviBrojevi(s);
			String uredjeno = uredivanje(brojevi);
			if (uredjeno.length() > 11) {
				uredjeno = uredjeno.substring(0, 11);
			}
			editText.removeTextChangedListener(this);
			editText.setText(uredjeno);
			int kraj;
			if(uredjeno.length()==11){
				kraj=10;
			}else{
				kraj=uredjeno.length();
			}
			editText.setSelection(kraj);
			editText.addTextChangedListener(this);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

		private String uredivanje(String s) {
			String nazad = "";
			for (int i = 0; i < s.length(); i++) {
				if (i == 2 || i == 4) {
					nazad += ".";
				}
				nazad += s.charAt(i);
				if(i==7){
					nazad+=".";
				}
			}
			return nazad;
		}

		private String sviBrojevi(Editable s) {
			String nazad = "";
			for (int i = 0; i < s.length(); i++) {
				if (Character.isDigit(s.charAt(i))) {
					nazad += s.charAt(i);
				}
			}
			return nazad;
		}
	}
}
