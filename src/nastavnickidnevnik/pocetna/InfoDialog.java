package nastavnickidnevnik.pocetna;

import nastavnickidnevnik.main.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class InfoDialog extends Dialog implements android.view.View.OnClickListener {
	Button zatvori;
	public InfoDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_pocetna_info_dialog);
		
		zatvori=(Button)findViewById(R.pocetna.info_dialog_zatvori);
		zatvori.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}

}
