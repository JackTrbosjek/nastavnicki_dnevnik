package nastavnickidnevnik.godina;

public class Dani {
	private final String opis;
	private final int dan,mjesec;
	public Dani(int dan,int mjesec,String opis){
		this.dan=dan;
		this.mjesec=mjesec;
		this.opis=opis;
	}
	public int getDan(){
		return dan;
	}
	public int getMjesec(){
		return mjesec;
	}
	public String getOpis(){
		return opis;
	}
}
