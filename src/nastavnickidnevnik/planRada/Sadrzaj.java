package nastavnickidnevnik.planRada;

public class Sadrzaj {
	private final long id;
	private final String sadrzaj;
	private String nositelj,planiranoVrijeme,ostvarenoVrijeme;
	public Sadrzaj(long id,String sadrzaj){
		this.id=id;
		this.sadrzaj=sadrzaj;
	}
	public Sadrzaj(long id,String sadrzaj,String nositelj,String planiranoVrijeme,String ostvarenoVrijeme){
		this.id=id;
		this.sadrzaj=sadrzaj;
		this.nositelj=nositelj;
		this.planiranoVrijeme=planiranoVrijeme;
		this.ostvarenoVrijeme=ostvarenoVrijeme;
	}
	public long getId(){
		return id;
	}
	public String getSadrzaj(){
		return sadrzaj;
	}
	public String getNositelj(){
		return nositelj;
	}
	public void setNositelj(String nositelj){
		this.nositelj=nositelj;
	}
	public String getPlaniranoVrijeme(){
		return planiranoVrijeme;
	}
	public void setPlaniranoVrijeme(String planiranoVrijeme){
		this.planiranoVrijeme=planiranoVrijeme;
	}
	public String getOstvarenoVrijeme(){
		return ostvarenoVrijeme;
	}
	public void setOstvarenoVrijeme(String ostvarenoVrijeme){
		this.ostvarenoVrijeme=ostvarenoVrijeme;
	}
}
