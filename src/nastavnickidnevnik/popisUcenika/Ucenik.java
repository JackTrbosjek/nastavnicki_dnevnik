package nastavnickidnevnik.popisUcenika;

import java.util.ArrayList;

public class Ucenik implements Comparable<Ucenik> {
	private final String ime,prezime;
	private final long id;
	private final int redniBroj;
	private ArrayList<String> biljeske,roditeljski,razgovori;
	public Ucenik(long id,int redniBroj,String ime,String prezime){
		this.ime=ime;
		this.prezime=prezime;
		this.id=id;
		this.redniBroj=redniBroj;
		biljeske=new ArrayList<String>();
		roditeljski=new ArrayList<String>();
		razgovori=new ArrayList<String>();
	}
	public int getRedniBroj(){
		return redniBroj;
	}
	public long getId(){
		return id;
	}
	public String getIme(){
		return ime;
	}
	public String getPrezime(){
		return prezime;
	}
	public ArrayList<String> getBiljeske(){
		return biljeske;
	}
	public ArrayList<String> getRoditeljski(){
		return roditeljski;
	}
	public ArrayList<String> getRazgovori(){
		return razgovori;
	}
	public void addRazgovor(String unos){
		razgovori.add(unos);
	}
	public void addRazgovor(ArrayList<String> unos){
		razgovori=unos;
	}
	public void addBiljeska(String unos){
		biljeske.add(unos);
	}
	public void addBiljeska(ArrayList<String> unos){
		biljeske=unos;
	}
	public void addRoditeljski(String unos){
		roditeljski.add(unos);
	}
	public void addRoditeljski(ArrayList<String> unos){
		roditeljski=unos;
	}
	@Override
	public int compareTo(Ucenik another) {
		int compareQuantity = ((Ucenik) another).getRedniBroj(); 
		 
		//ascending order
		return this.redniBroj - compareQuantity;
 
		//descending order
		//return compareQuantity - this.quantity;
	}
}
