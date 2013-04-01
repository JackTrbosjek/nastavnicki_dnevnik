package nastavnickidnevnik.main;

import java.util.ArrayList;

import nastavnickidnevnik.mojeObaveze.BazaObaveze;
import nastavnickidnevnik.mojeObaveze.DialogNeposredni;
import nastavnickidnevnik.mojeObaveze.DialogOstali;
import nastavnickidnevnik.mojeObaveze.DialogPosebniPrekovremeni;
import nastavnickidnevnik.mojeObaveze.Obaveza;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MojeObaveze extends Activity implements OnClickListener {
	ImageView back;
	TableLayout neposredni, ostali, posebni, prekovremeni;
	BazaObaveze baza;
	ArrayList<Obaveza> obaveze;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moje_obaveze);
		inicijalizacija();

		// dohvaƒáanje postojeƒáih obaveza iz baze
		baza.otvori();
		obaveze = baza.dohvatiObaveze();
		baza.zatvori();
		
		//poËetno namje≈°tanje TableLayout-a neposredni odgojno-obrazovni rad s uƒçenicima
		for (int i = 1; i < neposredni.getChildCount() - 1; i++) {
			TableRow tr = (TableRow) neposredni.getChildAt(i);
			final int redniBroj = i;
			// button dio
			LinearLayout ll = (LinearLayout) tr.getChildAt(1);
			final Button btn = (Button) ll.getChildAt(0);
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					DialogNeposredni dialog = new DialogNeposredni(context,
							baza, obaveze, redniBroj, btn.getText().toString());
					dialog.show();
					dialog.setOnDismissListener(new OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {
							osvjeziNeposredni();
						}
					});
				}
			});
		}
		osvjeziNeposredni();
		
		//poËetno namjeötanje TableLayout-a ostali poslovi
		for (int i = 1; i < ostali.getChildCount() - 1; i++) {
			TableRow tr = (TableRow) ostali.getChildAt(i);
			final int redniBroj = i;
			// button dio
			LinearLayout ll = (LinearLayout) tr.getChildAt(1);
			final Button btn = (Button) ll.getChildAt(0);
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					DialogOstali dialog = new DialogOstali(context,
							baza, obaveze, redniBroj, btn.getText().toString());
					dialog.show();
					dialog.setOnDismissListener(new OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {
							osvjeziOstali();
						}
					});
				}
			});
		}
		osvjeziOstali();
		
		//poËetno namjeötanje TableLayout-a posebni poslovi
		for (int i = 1; i < posebni.getChildCount() - 1; i++) {
			TableRow tr = (TableRow) posebni.getChildAt(i);
			final int redniBroj = i;
			// button dio
			LinearLayout ll = (LinearLayout) tr.getChildAt(1);
			final Button btn = (Button) ll.getChildAt(0);
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					DialogPosebniPrekovremeni dialog = new DialogPosebniPrekovremeni(context,
							baza, obaveze, redniBroj, btn.getText().toString(),"posebni");
					dialog.show();
					dialog.setOnDismissListener(new OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {
							osvjeziPosebni();
						}
					});
				}
			});
		}
		osvjeziPosebni();
		//poËetno namjeötanje TableLayout-a posebni poslovi
				for (int i = 1; i < prekovremeni.getChildCount() - 1; i++) {
					TableRow tr = (TableRow) prekovremeni.getChildAt(i);
					final int redniBroj = i;
					// button dio
					LinearLayout ll = (LinearLayout) tr.getChildAt(1);
					final Button btn = (Button) ll.getChildAt(0);
					btn.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							DialogPosebniPrekovremeni dialog = new DialogPosebniPrekovremeni(context,
									baza, obaveze, redniBroj, btn.getText().toString(),"prekovremeni");
							dialog.show();
							dialog.setOnDismissListener(new OnDismissListener() {
								@Override
								public void onDismiss(DialogInterface dialog) {
									osvjeziPrekovremeni();
								}
							});
						}
					});
				}
				osvjeziPrekovremeni();
	}

	private void osvjeziPrekovremeni() {
		int satiUkupno=0;
		for (int i = 1; i < prekovremeni.getChildCount() - 1; i++) {
			TableRow tr = (TableRow) prekovremeni.getChildAt(i);
			// tra≈æenje ako veÊ postoji zapis obaveze
			Obaveza trenutna = dohvatiObavezu(i, "prekovremeni");
			if (trenutna != null) {
				LinearLayout li=(LinearLayout) tr.getChildAt(1);
				Button btn=(Button) li.getChildAt(0);
				btn.setText(trenutna.getVrsta());
				// sati
				TextView sati = (TextView) tr.getChildAt(2);
				sati.setText(trenutna.getSati() + "");
				satiUkupno+=trenutna.getSati();
			}
		}
		//ukupno
		TableRow zadnji=(TableRow)prekovremeni.getChildAt(prekovremeni.getChildCount()-1);
		TextView ukupno=(TextView)zadnji.getChildAt(2);
		ukupno.setText(satiUkupno+"");
	}

	private void osvjeziPosebni() {
		int satiUkupno=0;
		for (int i = 1; i < posebni.getChildCount() - 1; i++) {
			TableRow tr = (TableRow) posebni.getChildAt(i);
			// tra≈æenje ako veÊ postoji zapis obaveze
			Obaveza trenutna = dohvatiObavezu(i, "posebni");
			if (trenutna != null) {
				LinearLayout li=(LinearLayout) tr.getChildAt(1);
				Button btn=(Button) li.getChildAt(0);
				btn.setText(trenutna.getVrsta());
				// sati
				TextView sati = (TextView) tr.getChildAt(2);
				sati.setText(trenutna.getSati() + "");
				satiUkupno+=trenutna.getSati();
			}
		}
		//ukupno
		TableRow zadnji=(TableRow)posebni.getChildAt(posebni.getChildCount()-1);
		TextView ukupno=(TextView)zadnji.getChildAt(2);
		ukupno.setText(satiUkupno+"");
	}

	private void osvjeziOstali() {
		int satiUkupno=0;
		for (int i = 1; i < ostali.getChildCount() - 1; i++) {
			TableRow tr = (TableRow) ostali.getChildAt(i);
			// tra≈æenje ako veÊ postoji zapis obaveze
			Obaveza trenutna = dohvatiObavezu(i, "ostali");
			if (trenutna != null) {
				// sati
				TextView sati = (TextView) tr.getChildAt(2);
				sati.setText(trenutna.getSati() + "");
				satiUkupno+=trenutna.getSati();
			}
		}
		//ukupno
		TableRow zadnji=(TableRow)ostali.getChildAt(ostali.getChildCount()-1);
		TextView ukupno=(TextView)zadnji.getChildAt(2);
		ukupno.setText(satiUkupno+"");
	}

	private void osvjeziNeposredni() {
		int satiUkupno=0;
		for (int i = 1; i < neposredni.getChildCount() - 1; i++) {
			TableRow tr = (TableRow) neposredni.getChildAt(i);
			// traûenje ako veÊ postoji zapis obaveze
			Obaveza trenutna = dohvatiObavezu(i, "neposredni");
			if (trenutna != null) {
				// razredni odjeli
				TextView razOdjel = (TextView) tr.getChildAt(2);
				razOdjel.setText(trenutna.getRazredniOdjel());
				// sati
				TextView sati = (TextView) tr.getChildAt(3);
				sati.setText(trenutna.getSati() + "");
				satiUkupno+=trenutna.getSati();
			}
		}
		//ukupno
		TableRow zadnji=(TableRow)neposredni.getChildAt(neposredni.getChildCount()-1);
		TextView ukupno=(TextView)zadnji.getChildAt(3);
		ukupno.setText(satiUkupno+"");
	}

	//funcija koja pretra≈æuje ArrayList obaveza i vraÊa obavezu za zadane paramtere
	private Obaveza dohvatiObavezu(int redniBr, String naziv) {
		for (int i = 0; i < obaveze.size(); i++) {
			if (obaveze.get(i).getRedniBroj() == redniBr
					&& obaveze.get(i).getNaziv().equalsIgnoreCase(naziv)) {
				return obaveze.get(i);
			}
		}
		return null;
	}

	private void inicijalizacija() {
		context = this;

		back = (ImageView) findViewById(R.mojeObaveze.back);
		back.setOnClickListener(this);

		neposredni = (TableLayout) findViewById(R.mojeObaveze.neposredni);
		ostali = (TableLayout) findViewById(R.mojeObaveze.ostaliPoslovi);
		posebni = (TableLayout) findViewById(R.mojeObaveze.posebniPoslovi);
		prekovremeni = (TableLayout) findViewById(R.mojeObaveze.prekovremeno);

		baza = new BazaObaveze(this);
	}

	@Override
	public void onClick(View v) {
		if (v == back) {
			finish();
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right );
		}
	}

}
