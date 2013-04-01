package nastavnickidnevnik.predmeti;

import java.text.DecimalFormat;
import java.util.ArrayList;

import nastavnickidnevnik.main.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterOcjene extends BaseAdapter{
	Context mContext;
	ArrayList<Predmet> ocjene;
	BazaPredmeti baza;
	private boolean brisi;
	public AdapterOcjene(Context c,ArrayList<Predmet> ocjene){
		mContext = c;
		this.ocjene=ocjene;
		baza=new BazaPredmeti(mContext);
		brisi=false;
	}
	@Override
	public int getCount() {
		return ocjene.size();
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
	public View getView(final int position, View convertView, final ViewGroup parent) {
		View v = convertView;

		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.layout_predmeti_list_item, null);

		}
		ImageView obrisi=(ImageView)v.findViewById(R.predmeti.list_obrisi);
		if(brisi){
			obrisi.setVisibility(View.VISIBLE);
			obrisi.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					baza.otvori();
					baza.obrisi(ocjene.get(position).getId());
					baza.zatvori();
					ocjene.remove(position);
					getAdapter().notifyDataSetChanged();
				}
			});
		}else{
			obrisi.setVisibility(View.INVISIBLE);
		}
		TextView predmet=(TextView)v.findViewById(R.predmeti.listIme);
		final TextView odlican = (TextView) v.findViewById(R.predmeti.list_prvi);
		final TextView vrloDobar=(TextView)v.findViewById(R.predmeti.list_drugi);
		final TextView dobar=(TextView)v.findViewById(R.predmeti.list_treci);
		final TextView dovoljan=(TextView)v.findViewById(R.predmeti.list_cetvrti);
		final TextView nedovoljan=(TextView)v.findViewById(R.predmeti.list_peti);
		TextView srednja=(TextView)v.findViewById(R.predmeti.list_sesti);

		predmet.setText(ocjene.get(position).getIme());
		int i5=ocjene.get(position).getOdlican();
		if(i5>-1){
			odlican.setText(i5+"");
		}else{
			odlican.setText("");
		}
		int i4=ocjene.get(position).getVrloDobar();
		if(i4>-1){
			vrloDobar.setText(i4+"");	
		}else{
			vrloDobar.setText("");
		}
		int i3=ocjene.get(position).getDobar();
		if(i3>-1){
			dobar.setText(i3+"");
		}else{
			dobar.setText("");
		}
		int i2=ocjene.get(position).getDovoljan();
		if(i2>-1){
			dovoljan.setText(i2+"");
		}else{
			dovoljan.setText("");
		}
		int i1=ocjene.get(position).getNedovoljan();
		if(i1>-1){
			nedovoljan.setText(i1+"");
		}else{
			nedovoljan.setText("");
		}
		Float isr=ocjene.get(position).getSrednjaOcjena();
		if(isr>0){
			DecimalFormat df=new DecimalFormat();
			df.setMaximumFractionDigits(2);
			srednja.setText(df.format(isr)+"");
		}else{
			srednja.setText("");
		}
		
		v.setOnClickListener(new Listener(position));
		return v;
	}
	private class Listener implements OnClickListener{
		int position;
		public Listener(int position){
			this.position=position;
		}
		@Override
		public void onClick(View v) {
			//new DialogUnosOcjena(mContext, getAdapter(), ocjene.get(position),baza).show();
		}
		
	}
	public void brisanje(){
		brisi=!brisi;
		this.notifyDataSetChanged();
	}
	public AdapterOcjene getAdapter(){
		return AdapterOcjene.this;
	}
}
