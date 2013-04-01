package nastavnickidnevnik.popisUcenika;

import java.util.ArrayList;

import nastavnickidnevnik.main.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class DialogDodatnoAdapter extends BaseExpandableListAdapter{

	public LayoutInflater minflater;
	private Context mContext;
	private ExpandableListView pregled;
	private ArrayList<Integer[]> boje;
	private Ucenik ucenik;
	private BazaPopisUcenika baza;

	public DialogDodatnoAdapter(Ucenik ucenik, Context c, ExpandableListView pregled, ArrayList<Integer[]> boje) {
		this.ucenik = ucenik;
		this.pregled=pregled;
		this.boje=boje;
		mContext = c;
		minflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		baza= new BazaPopisUcenika(c);
		
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
		switch(groupPosition){
		case 0:
			naslov.setText(ucenik.getBiljeske().get(childPosition));
			break;
		case 1:
			naslov.setText(ucenik.getRoditeljski().get(childPosition));
			break;
		case 2:
			naslov.setText(ucenik.getRazgovori().get(childPosition));
			break;
		}
		Button delete=(Button)convertView.findViewById(R.id.expandableListDelete);
		delete.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				AlertDialog.Builder confirmDialog= new AlertDialog.Builder(mContext);
				confirmDialog.setTitle("Brisanje");
				confirmDialog.setMessage("Jeste li sigurni da želite obrisati?");
				confirmDialog.setPositiveButton("Da", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						obrisi(groupPosition,childPosition);
					}
				});
				confirmDialog.setNegativeButton("Ne", null);
				confirmDialog.create().show();
			}
		});
		convertView.setBackgroundColor(boje.get(groupPosition)[1]);
		return convertView;
	}
	private void obrisi(int groupPosition,int childPosition){
		baza.otvori();
		switch(groupPosition){
		case 0:
			ucenik.getBiljeske().remove(childPosition);
			baza.updateBiljeska(ucenik.getId(), ucenik.getBiljeske());
			break;
		case 1:
			ucenik.getRoditeljski().remove(childPosition);
			baza.updateRoditeljski(ucenik.getId(), ucenik.getRoditeljski());
			break;
		case 2:
			ucenik.getRazgovori().remove(childPosition);
			baza.updateRazgovor(ucenik.getId(), ucenik.getRazgovori());
			break;
		}
		baza.zatvori();
		this.notifyDataSetChanged();
	}
	


	@Override
	public int getChildrenCount(int groupPosition) {
		if(groupPosition==0){
			return ucenik.getBiljeske().size();
		}
		if(groupPosition==1){
			return ucenik.getRoditeljski().size();
		}
		if(groupPosition==2){
			return ucenik.getRazgovori().size();
		}
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return 3;
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
		switch(groupPosition){
		case 0: 
			naslov.setText("Bilješke");
			break;
		case 1:
			naslov.setText("Roditeljski sastanci");
			break;
		case 2:
			naslov.setText("Razgovori");
			break;
		}
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