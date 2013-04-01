package nastavnickidnevnik.planRada;

import java.util.ArrayList;

import nastavnickidnevnik.main.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DialogListAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<Sadrzaj> popis;
	DialogUnosPregled dialog;

	public DialogListAdapter(Context c, ArrayList<Sadrzaj> popis, DialogUnosPregled dialog) {
		mContext = c;
		this.popis=popis;
		this.dialog=dialog;

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
			v = vi.inflate(R.layout.layout_plan_rada_dialog_list_item, null);

		}
		
		TextView nositelj=(TextView)v.findViewById(R.planRada.dialog_list_nositelj);
		TextView planirano=(TextView)v.findViewById(R.planRada.dialog_list_planirano);
		TextView ostvareno=(TextView)v.findViewById(R.planRada.dialog_list_ostvareno);
		
		nositelj.setText("Nositelj: "+popis.get(position).getNositelj());
		planirano.setText("Planirano vrijeme: "+popis.get(position).getPlaniranoVrijeme());
		ostvareno.setText("Vrijeme ostvarivanja: "+popis.get(position).getOstvarenoVrijeme());
		
		Button obrisi=(Button)v.findViewById(R.planRada.dialog_list_obrisi);
		Button uredi=(Button)v.findViewById(R.planRada.dialog_list_uredi);
		
		obrisi.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog.baza.otvori();
				dialog.baza.obrisi(popis.get(position).getId());
				dialog.baza.zatvori();
				popis.remove(position);
				getAdapter().notifyDataSetChanged();
			}
		});
		
		uredi.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				dialog.edit(popis.get(position));
			}
		});
		return v;
	}
	private DialogListAdapter getAdapter(){
		return this;
	}

}