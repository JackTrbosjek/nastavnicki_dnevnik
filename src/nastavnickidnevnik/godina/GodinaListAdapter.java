package nastavnickidnevnik.godina;

import java.util.ArrayList;
import nastavnickidnevnik.main.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GodinaListAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<Dani> popis;

	public GodinaListAdapter(Context c, ArrayList<Dani> popis) {
		mContext = c;
		this.popis=popis;

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

	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.layout_godina_list, null);

		}
		TextView dan;
		dan = (TextView) v.findViewById(R.id.godinaListUnos);
		dan.setText(popis.get(position).getDan()+"."+(popis.get(position).getMjesec()+1)+". "+popis.get(position).getOpis());
		return v;
	}

}
