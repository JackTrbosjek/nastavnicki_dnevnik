package nastavnickidnevnik.uspjehUcenici;

import java.text.DecimalFormat;
import java.util.ArrayList;

import nastavnickidnevnik.main.R;
import nastavnickidnevnik.popisUcenika.Ucenik;
import nastavnickidnevnik.predmeti.Predmet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AdapterExpandableList extends BaseExpandableListAdapter {
	Context mContext;
	LayoutInflater minflater;
	ArrayList<Ucenik> ucenici;
	ArrayList<Predmet> predmeti;
	ArrayList<Ocjena> ocjene;
	ArrayList<Integer[]> boje;
	ExpandableListView lista;
	BazaUspjehUcenici bazaOcjene;

	public AdapterExpandableList(Context c, ArrayList<Ucenik> ucenici,
			ArrayList<Predmet> predmeti, ArrayList<Ocjena> ocjene,
			ArrayList<Integer[]> boje, ExpandableListView lista,
			BazaUspjehUcenici bazaOcjene) {
		mContext = c;
		this.ucenici = ucenici;
		this.predmeti = predmeti;
		this.ocjene = ocjene;
		this.boje = boje;
		this.lista = lista;
		this.bazaOcjene = bazaOcjene;
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

	private boolean edit = false;

	public void edit() {
		edit = !edit;
		this.notifyDataSetChanged();
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = minflater.inflate(
					R.layout.layout_uspjeh_ucenici_list_child, null);
		}

		TextView predmet = (TextView) convertView
				.findViewById(R.uspjehUcenici.expList_predmet);
		predmet.setText(predmeti.get(childPosition).getIme());

		final RadioGroup odabir = (RadioGroup) convertView
				.findViewById(R.uspjehUcenici.expList_radio_group);
		RadioButton rb5, rb4, rb3, rb2, rb1, rbN;
		rb5 = (RadioButton) convertView
				.findViewById(R.uspjehUcenici.expList_radio_5);
		rb4 = (RadioButton) convertView
				.findViewById(R.uspjehUcenici.expList_radio_4);
		rb3 = (RadioButton) convertView
				.findViewById(R.uspjehUcenici.expList_radio_3);
		rb2 = (RadioButton) convertView
				.findViewById(R.uspjehUcenici.expList_radio_2);
		rb1 = (RadioButton) convertView
				.findViewById(R.uspjehUcenici.expList_radio_1);
		rbN = (RadioButton) convertView
				.findViewById(R.uspjehUcenici.expList_radio_n);
		odabir.clearCheck();
		rb5.setOnClickListener(new RadioListener(groupPosition, childPosition));
		rb4.setOnClickListener(new RadioListener(groupPosition, childPosition));
		rb3.setOnClickListener(new RadioListener(groupPosition, childPosition));
		rb2.setOnClickListener(new RadioListener(groupPosition, childPosition));
		rb1.setOnClickListener(new RadioListener(groupPosition, childPosition));
		rbN.setOnClickListener(new RadioListener(groupPosition, childPosition));

		final TextView ocjenaText = (TextView) convertView
				.findViewById(R.uspjehUcenici.expList_ocjena_text);
		ocjenaText.setText("");
		for (int i = 0; i < ocjene.size(); i++) {
			if (ucenici.get(groupPosition).getId() == ocjene.get(i)
					.getUcenikId()
					&& predmeti.get(childPosition).getId() == ocjene.get(i)
							.getPredmetId()) {
				int ocjena = ocjene.get(i).getOcjena();
				switch (ocjena) {
				case 5:
					rb5.toggle();
					break;
				case 4:
					rb4.toggle();
					break;
				case 3:
					rb3.toggle();
					break;
				case 2:
					rb2.toggle();
					break;
				case 1:
					rb1.toggle();
				}
				ocjenaText.setText(ocjena + "");
			}
		}

		if (edit) {
			odabir.setVisibility(View.VISIBLE);
			ocjenaText.setVisibility(View.INVISIBLE);
		} else {
			odabir.setVisibility(View.GONE);
			ocjenaText.setVisibility(View.VISIBLE);
		}

		convertView.setBackgroundColor(boje.get(groupPosition)[1]);
		return convertView;
	}

	private class RadioListener implements OnClickListener {
		int groupPosition, childPosition;

		public RadioListener(int groupPosition, int childPosition) {
			this.groupPosition = groupPosition;
			this.childPosition = childPosition;
		}

		@Override
		public void onClick(View v) {
			long ucenikId = ucenici.get(groupPosition).getId();
			long predmetId = predmeti.get(childPosition).getId();
			bazaOcjene.otvori();
			switch (v.getId()) {
			case R.uspjehUcenici.expList_radio_5:
				bazaOcjene.dodaj(ucenikId, predmetId, 5);
				obrisiIzListe(ucenikId,predmetId);
				ocjene.add(new Ocjena(22, ucenikId, predmetId, 5));
				break;
			case R.uspjehUcenici.expList_radio_4:
				bazaOcjene.dodaj(ucenikId, predmetId, 4);
				obrisiIzListe(ucenikId,predmetId);
				ocjene.add(new Ocjena(22, ucenikId, predmetId, 4));
				break;
			case R.uspjehUcenici.expList_radio_3:
				bazaOcjene.dodaj(ucenikId, predmetId, 3);
				obrisiIzListe(ucenikId,predmetId);
				ocjene.add(new Ocjena(22, ucenikId, predmetId, 3));
				break;
			case R.uspjehUcenici.expList_radio_2:
				bazaOcjene.dodaj(ucenikId, predmetId, 2);
				obrisiIzListe(ucenikId,predmetId);
				ocjene.add(new Ocjena(22, ucenikId, predmetId, 2));
				break;
			case R.uspjehUcenici.expList_radio_1:
				bazaOcjene.dodaj(ucenikId, predmetId, 1);
				obrisiIzListe(ucenikId,predmetId);
				ocjene.add(new Ocjena(22, ucenikId, predmetId, 1));
				break;
			case R.uspjehUcenici.expList_radio_n:
				bazaOcjene.obrisi(ucenikId, predmetId);
				obrisiIzListe(ucenikId, predmetId);
				break;
			}
			bazaOcjene.zatvori();
		}

	}

	private void obrisiIzListe(long ucenikId, long predmetId) {
		for (int i = 0; i < ocjene.size(); i++) {
			if (ocjene.get(i).getUcenikId() == ucenikId
					&& ocjene.get(i).getPredmetId() == predmetId) {
				ocjene.remove(i);
			}
		}
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return predmeti.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return ucenici.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = minflater.inflate(
					R.layout.layout_uspjeh_ucenici_list_group, null);
		}

		TextView ime = (TextView) convertView
				.findViewById(R.uspjehUcenici.expList_ime);
		ime.setText(ucenici.get(groupPosition).getRedniBroj() + ". "
				+ ucenici.get(groupPosition).getIme() + " "
				+ ucenici.get(groupPosition).getPrezime());

		TextView srednja = (TextView) convertView
				.findViewById(R.uspjehUcenici.expList_sred_ocjena);
		int brojac = 0, zbroj = 0;
		for (int i = 0; i < ocjene.size(); i++) {
			if (ocjene.get(i).getUcenikId() == ucenici.get(groupPosition)
					.getId()) {
				zbroj += ocjene.get(i).getOcjena();
				brojac++;
			}
		}
		if (zbroj > 0) {
			float srednjaOcjena = (float) zbroj / brojac;
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			srednja.setText(df.format(srednjaOcjena) + "");
		} else {
			srednja.setText("");
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

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	private int lastExpandedGroupPosition = -1;

	@Override
	public void onGroupExpanded(int groupPosition) {
		if (groupPosition != lastExpandedGroupPosition) {
			lista.collapseGroup(lastExpandedGroupPosition);
		}
		super.onGroupExpanded(groupPosition);
		lastExpandedGroupPosition = groupPosition;
	}
}
