package nastavnickidnevnik.mojeObaveze;

public class Obaveza {
	private final long id;
	private final String naziv,vrsta,razredniOdjel;
	private final int redniBroj,sati;
	public Obaveza(long id,String vrsta,int redniBroj, String naziv,String razredniOdjel,int sati){
		this.id=id;
		this.vrsta=vrsta;
		this.redniBroj=redniBroj;
		this.naziv=naziv;
		this.razredniOdjel=razredniOdjel;
		this.sati=sati;
	}
	public long getId(){
		return id;
	}
	public String getVrsta(){
		return vrsta;
	}
	public int getRedniBroj(){
		return redniBroj;
	}
	public String getNaziv(){
		return naziv;
	}
	public String getRazredniOdjel(){
		return razredniOdjel;
	}
	public int getSati(){
		return sati;
	}
}
