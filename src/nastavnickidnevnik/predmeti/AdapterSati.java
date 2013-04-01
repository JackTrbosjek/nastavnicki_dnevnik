package nastavnickidnevnik.predmeti;

import java.util.ArrayList;

import nastavnickidnevnik.main.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterSati extends BaseAdapter {
	Context mContext;
	ArrayList<Predmet> sati;
	BazaPredmeti baza;
	private boolean brisi;

	public AdapterSati(Context c, ArrayList<Predmet> sati) {
		mContext = c;
		this.sati = sati;
		baza = new BazaPredmeti(mContext);
		brisi = false;
	}

	@Override
	public int getCount() {
		return sati.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {
		View v = convertView;

		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.layout_predmeti_list_item, null);

		}
		ImageView obrisi = (ImageView) v.findViewById(R.predmeti.list_obrisi);
		if (brisi) {
			obrisi.setVisibility(View.VISIBLE);
			obrisi.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					baza.otvori();
					baza.obrisi(sati.get(position).getId());
					baza.zatvori();
					sati.remove(position);
					getAdapter().notifyDataSetChanged();
				}
			});
		} else {
			obrisi.setVisibility(View.INVISIBLE);
		}
		TextView predmet = (TextView) v.findViewById(R.predmeti.listIme);
		final TextView planirano = (TextView) v
				.findViewById(R.predmeti.list_prvi);
		final TextView ostvareno = (TextView) v
				.findViewById(R.predmeti.list_drugi);
		final TextView razlika = (TextView) v
				.findViewById(R.predmeti.list_treci);
		final TextView dopunska = (TextView) v
				.findViewById(R.predmeti.list_cetvrti);
		final TextView strucno = (TextView) v
				.findViewById(R.predmeti.list_peti);
		final TextView nestrucno = (TextView) v
				.findViewById(R.predmeti.list_sesti);

		predmet.setText(sati.get(position).getIme());
		int iplan = sati.get(position).getSatiPlanirano();
		if (iplan > -1) {
			planirano.setText(iplan + "");
		} else {
			planirano.setText("");
		}
		int iostv = sati.get(position).getSatiOstvareno();
		if (iostv > -1) {
			ostvareno.setText(iostv + "");
		} else {
			ostvareno.setText("");
		}
		int iraz = sati.get(position).getSatiRazlika();
		if (iostv > -1 && iplan > -1) {
			razlika.setText(iraz + "");
		} else {
			razlika.setText("");
		}
		int idop = sati.get(position).getSatiDopunska();
		if (idop > -1) {
			dopunska.setText(idop + "");
		} else {
			dopunska.setText("");
		}
		int istruc = sati.get(position).getSatiStrucno();
		if (istruc > -1) {
			strucno.setText(istruc + "");
		} else {
			strucno.setText("");
		}
		int inestruc = sati.get(position).getSatiNeStrucno();
		if (inestruc > 0) {
			nestrucno.setText(inestruc + "");
		} else {
			nestrucno.setText("");
		}

		v.setOnClickListener(new Listener(position));
		return v;
	}

	private class Listener implements OnClickListener {
		int position;

		public Listener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			new DialogUnosSati(mContext, getAdapter(), sati.get(position), baza)
					.show();
		}

	}

	public void brisanje() {
		brisi = !brisi;
		this.notifyDataSetChanged();
	}

	public AdapterSati getAdapter() {
		return AdapterSati.this;
	}
}
