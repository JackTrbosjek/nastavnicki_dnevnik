package nastavnickidnevnik.popisUcenika;

import java.util.ArrayList;
import java.util.Collections;

import nastavnickidnevnik.main.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PopisUcenikaAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<Ucenik> popis;
	BazaPopisUcenika baza;
	public boolean BRISANJE;
	public int RANDOM;

	public PopisUcenikaAdapter(Context c, ArrayList<Ucenik> popis) {
		mContext = c;
		this.popis=popis;
		baza= new BazaPopisUcenika(c);
		BRISANJE=false;
		RANDOM=-1;
	}
	public boolean areAllItemsEnabled()
	{
	    return false;
	}

	public boolean isEnabled(int position)
	{
	    return false;
	}


	public int getCount() {
		return popis.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.layout_popis_ucenika_list_item, null);

		}
		
		TextView redniBroj = (TextView) v.findViewById(R.popisUcenika.list_redni_broj);
		redniBroj.setText(popis.get(position).getRedniBroj()+"");
		
		TextView ime=(TextView)v.findViewById(R.popisUcenika.list_ime);
		ime.setText(popis.get(position).getIme());
		
		TextView prezime=(TextView)v.findViewById(R.popisUcenika.list_prezime);
		prezime.setText(popis.get(position).getPrezime());
		
		ImageView dodatno=(ImageView)v.findViewById(R.popisUcenika.list_dodatno);
		dodatno.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				otvoriDialog(popis.get(position));
			}
		});
		
		ImageView obrisi=(ImageView)v.findViewById(R.popisUcenika.list_obrisi);
		obrisi.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				obrisiItem(position);
			}
		});
		//prikaz gumbova za brisanje
		if(BRISANJE){
			obrisi.setVisibility(View.VISIBLE);
		}else{
			obrisi.setVisibility(View.INVISIBLE);
		}
		//promjena boje nasumicnog itema
		if(RANDOM==position){
			v.setBackgroundColor(Color.parseColor("#1B7EE0"));
		}else{
			v.setBackgroundColor(Color.BLUE);
		}
		return v;
	}
	private void obrisiItem(int position) {
		baza.otvori();
		baza.obrisi(popis.get(position).getId());
		baza.zatvori();
		popis.remove(position);
		this.notifyDataSetChanged();
	}
	private void otvoriDialog(Ucenik ucenik) {
		new DialogDodatno(mContext,ucenik).show();
	}
	
	public void dodajUcenika(int redniBroj,String ime,String prezime){
		baza.otvori();
		long bazaID=baza.dodaj(redniBroj, ime, prezime);
		baza.zatvori();
		popis.add(new Ucenik(bazaID,redniBroj,ime,prezime));
		sortirajUcenike();
		this.notifyDataSetChanged();
	}
	private void sortirajUcenike() {
		Collections.sort(popis);
	}

}
