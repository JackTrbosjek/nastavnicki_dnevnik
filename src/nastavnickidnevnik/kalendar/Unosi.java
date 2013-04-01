package nastavnickidnevnik.kalendar;

public class Unosi {
	private final int id,dan;
	private final String naslov,opis;
	
	public Unosi(int id,int dan, String naslov,String opis){
		this.id=id;
		this.dan=dan;
		this.naslov=naslov;
		this.opis=opis;
	}
	public int getID(){return id;}
	public int getDan(){return dan;}
	public String getNaslov(){return naslov;}
	public String getOpis(){return opis;}
}
