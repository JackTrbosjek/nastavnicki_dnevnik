package nastavnickidnevnik.kalendar;

import java.util.ArrayList;
import nastavnickidnevnik.main.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class PregledBiljeskiAdapter extends BaseExpandableListAdapter{

	public ArrayList<Unosi> biljeske;
	public LayoutInflater minflater;
	private Context mContext;
	private ExpandableListView pregled;
	private KalendarAdapter adapterKalendar;
	private ArrayList<Integer[]> boje;

	public PregledBiljeskiAdapter(ArrayList<Unosi> biljeske, Context c, KalendarAdapter adapter, ExpandableListView pregled, ArrayList<Integer[]> boje) {
		this.biljeske = biljeske;
		this.pregled=pregled;
		this.adapterKalendar=adapter;
		this.boje=boje;
		mContext = c;
		minflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.layout_kalendar_expandable_list_child, null);
		}
		TextView naslov= (TextView)convertView.findViewById(R.id.expandableListChild);
		naslov.setText(biljeske.get(groupPosition).getOpis());
		Button delete=(Button)convertView.findViewById(R.id.expandableListDelete);
		delete.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AlertDialog.Builder confirmDialog= new AlertDialog.Builder(mContext);
				confirmDialog.setTitle("Brisanje bilješke");
				confirmDialog.setMessage("Jeste li sigurni da želite obrisati bilješku?");
				confirmDialog.setPositiveButton("Da", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						obrisiBiljesku(groupPosition);
					}
				});
				confirmDialog.setNegativeButton("Ne", null);
				confirmDialog.create().show();
			}
		});
		convertView.setBackgroundColor(boje.get(groupPosition)[1]);
		return convertView;
	}
	public void obrisiBiljesku(int groupPosition){
		adapterKalendar.obrisiBiljesku(biljeske.get(groupPosition).getID());
		biljeske.remove(groupPosition);
		this.notifyDataSetChanged();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return biljeske.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}
	
	private int lastExpandedGroupPosition;
	@Override
	public void onGroupExpanded(int groupPosition) {
		if(groupPosition != lastExpandedGroupPosition){
			pregled.collapseGroup(lastExpandedGroupPosition);
        }

        super.onGroupExpanded(groupPosition);           
        lastExpandedGroupPosition = groupPosition;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.layout_kalendar_expandable_list_group, null);
		}
		
		TextView naslov= (TextView)convertView.findViewById(R.id.expandableListParent);
		naslov.setText(biljeske.get(groupPosition).getNaslov());
		convertView.setBackgroundColor(boje.get(groupPosition)[0]);
		return convertView;

	}
	

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}