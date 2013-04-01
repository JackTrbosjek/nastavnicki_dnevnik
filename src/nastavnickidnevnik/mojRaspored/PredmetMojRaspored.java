package nastavnickidnevnik.mojRaspored;

public class PredmetMojRaspored {
	final long id;
	final int pozicija;
	final int prijepodne;
	final String ime;
	public PredmetMojRaspored(long id,int pozicija,String ime,int prijepodne){
		this.id=id;
		this.pozicija=pozicija;
		this.ime=ime;
		this.prijepodne=prijepodne;
	}
	public int getPozicija(){
		return pozicija;
	}
	public String getIme(){
		return ime;
	}
	public int prijepodne(){
		return prijepodne;
	}
}
