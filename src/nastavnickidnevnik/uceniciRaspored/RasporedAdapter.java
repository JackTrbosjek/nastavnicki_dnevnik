package nastavnickidnevnik.uceniciRaspored;

import java.util.ArrayList;

import nastavnickidnevnik.main.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RasporedAdapter extends BaseAdapter {
	Context context;
	ArrayList<PredmetRaspored> popis;
	RasporedUceniciBaza baza;
	int prijePoslije;
	public RasporedAdapter(Context c, RasporedUceniciBaza baza, int prijePoslije){
		context=c;
		this.baza=baza;
		this.prijePoslije=prijePoslije;
	}
	public void setPopis(ArrayList<PredmetRaspored> popis){
		this.popis=popis;
		this.notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		return 54;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v =vi.inflate(R.layout.layout_ucenici_raspored_item, null);

		}
		v.setMinimumHeight(45);
		final TextView predmet = (TextView) v.findViewById(R.uceniciRaspored.grid_text);
		if(position==0){
			predmet.setText("");
		}else
		if (position%6==0&&position>0) {
			predmet.setText(position/6-1+".");
			v.setBackgroundColor(Color.BLUE);
		}else
		if(position>0&&position<6){
			v.setBackgroundColor(Color.BLUE);
			switch(position){
			case 1:
				predmet.setText("Ponedjeljak");
				break;
			case 2:
				predmet.setText("Utorak");
				break;
			case 3:
				predmet.setText("Srijeda");
				break;
			case 4:
				predmet.setText("Èetvrtak");
				break;
			case 5:
				predmet.setText("Petak");
				break;
			}
		}else{
			predmet.setText("");
			for(int i=0;i<popis.size();i++){
				if(popis.get(i).getPozicija()==position){
					predmet.setText(popis.get(i).getIme());
				}
			}
			v.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					new DialogUnosRaspored(getAdapter(),position).show();
				}
			});
		}
		
		return v;
	}
	public RasporedAdapter getAdapter(){
		return RasporedAdapter.this;
	}

}
