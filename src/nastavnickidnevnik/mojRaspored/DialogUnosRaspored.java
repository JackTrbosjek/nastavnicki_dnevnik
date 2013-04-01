package nastavnickidnevnik.mojRaspored;

import nastavnickidnevnik.main.R;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DialogUnosRaspored extends Dialog implements android.view.View.OnClickListener {
	TextView naslov;
	EditText naziv;
	Button spremi,odustani;
	MojRasporedAdapter adapter;
	int redni;
	public DialogUnosRaspored(MojRasporedAdapter rasporedAdapter, int position) {
		super(rasporedAdapter.context);
		adapter=rasporedAdapter;
		redni=position;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_ucenici_raspored_dialog);
		
		inicijalizacija();
		
		naslov.setText(getSat()+" sat "+getDan());
	}

	private void inicijalizacija() {
		naslov=(TextView)findViewById(R.uceniciRaspored.dialog_naslov);
		
		naziv=(EditText)findViewById(R.uceniciRaspored.dialog_naziv);
		
		spremi=(Button)findViewById(R.uceniciRaspored.dialog_spremi);
		spremi.setOnClickListener(this);
		
		odustani=(Button)findViewById(R.uceniciRaspored.dialog_odustani);
		odustani.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==spremi){
			String naziv=this.naziv.getText().toString();
			adapter.popis.add(new PredmetMojRaspored(555, redni, naziv,adapter.prijePoslije));
			adapter.baza.otvori();
			adapter.baza.updatePredmet(redni,naziv,adapter.prijePoslije);
			adapter.baza.zatvori();
			adapter.notifyDataSetChanged();
			dismiss();
		}
		if(v==odustani){
			cancel();
		}
	}
	
	private String getDan() {
		if(redni%6==1){
			return "Ponedjeljak";
		}
		if(redni%6==2){
			return "Utorak";
		}
		if(redni%6==3){
			return "Srijeda";
		}
		if(redni%6==4){
			return "Èetvrtak";
		}
		if(redni%6==5){
			return "Petak";
		}
		return "";
	}

	private String getSat() {
		String sat=(redni/6-1)+".";
		return sat;
	}

}
