package nastavnickidnevnik.predmeti;

public class Predmet {
	final long id;
	final String ime;
	private int satiPlanirano,satiOstvareno,satiRazlika,satiDopunska,satiStrucno,satiNeStrucno;
	private int ocjeneOdlican,ocjeneVrloDobar,ocjeneDobar,ocjeneDovoljan,ocjeneNedovoljan;
	private float ocjeneSrednja;
	public Predmet(long id,String ime){
		this.ime=ime;
		this.id=id;
		this.satiPlanirano=-1;
		this.satiOstvareno=-1;
		this.satiDopunska=-1;
		this.satiStrucno=-1;
		this.satiNeStrucno=-1;
		this.ocjeneOdlican=-1;
		this.ocjeneVrloDobar=-1;
		this.ocjeneDobar=-1;
		this.ocjeneDovoljan=-1;
		this.ocjeneNedovoljan=-1;
	}
	public Predmet(long id,String ime,int satiPlanirano,int satiOstvareno,int satiDopunska,int satiStrucno,int satiNeStrucno,int ocjeneOdlican,int ocjeneVrloDobar,int ocjeneDobar,int ocjeneDovoljan,int ocjeneNedovoljan){
		this.id=id;
		this.ime=ime;
		this.satiPlanirano=satiPlanirano;
		this.satiOstvareno=satiOstvareno;
		this.satiRazlika=satiPlanirano-satiOstvareno;
		this.satiDopunska=satiDopunska;
		this.satiStrucno=satiStrucno;
		this.satiNeStrucno=satiNeStrucno;
		this.ocjeneOdlican=ocjeneOdlican;
		this.ocjeneVrloDobar=ocjeneVrloDobar;
		this.ocjeneDobar=ocjeneDobar;
		this.ocjeneDovoljan=ocjeneDovoljan;
		this.ocjeneNedovoljan=ocjeneNedovoljan;
		this.ocjeneSrednja=(float)(ocjeneOdlican+ocjeneVrloDobar+ocjeneDobar+ocjeneDovoljan+ocjeneNedovoljan)/5;
	}
	public long getId(){
		return id;
	}
	public String getIme(){
		return ime;
	}
	public int getSatiPlanirano(){
		return satiPlanirano;
	}
	public int getSatiOstvareno(){
		return satiOstvareno;
	}
	public int getSatiRazlika(){
		satiRazlika=satiPlanirano-satiOstvareno;
		return satiRazlika;
	}
	public int getSatiDopunska(){
		return satiDopunska;
	}
	public int getSatiStrucno(){
		return satiStrucno;
	}
	public int getSatiNeStrucno(){
		return satiNeStrucno;
	}
	public void setSatiPlanirano(String sati){
		satiPlanirano=Integer.parseInt(sati);
	}
	public void setSatiOstvareno(String sati){
		satiOstvareno=Integer.parseInt(sati);
	}
	public void setSatiDopunska(String sati){
		satiDopunska=Integer.parseInt(sati);
	}
	public void setSatiStrucno(String sati){
		satiStrucno=Integer.parseInt(sati);
	}
	public void setSatiNeStrucno(String sati){
		satiNeStrucno=Integer.parseInt(sati);
	}
	public int getOdlican(){
		return ocjeneOdlican;
	}
	public int getVrloDobar(){
		return ocjeneVrloDobar;
	}
	public int getDobar(){
		return ocjeneDobar;
	}
	public int getDovoljan(){
		return ocjeneDovoljan;
	}
	public int getNedovoljan(){
		return ocjeneNedovoljan;
	}
	public float getSrednjaOcjena(){
		int zbroj=0;
		int brojac=0;
		if(ocjeneOdlican>0){
			zbroj+=ocjeneOdlican*5;
			brojac+=ocjeneOdlican;
		}
		if(ocjeneVrloDobar>0){
			zbroj+=ocjeneVrloDobar*4;
			brojac+=ocjeneVrloDobar;
		}
		if(ocjeneDobar>0){
			zbroj+=ocjeneDobar*3;
			brojac+=ocjeneDobar;
		}
		if(ocjeneDovoljan>0){
			zbroj+=ocjeneDovoljan*2;
			brojac+=ocjeneDovoljan;
		}
		if(ocjeneNedovoljan>0){
			zbroj+=ocjeneNedovoljan;
			brojac+=ocjeneNedovoljan;
		}
		if(brojac>0)
			ocjeneSrednja=(float)zbroj/brojac;
		else
			ocjeneSrednja=0;
		return ocjeneSrednja;
	}
	public void setOdlican(String ocjena){
		ocjeneOdlican=Integer.parseInt(ocjena);
	}
	public void setVrloDobar(String ocjena){
		ocjeneVrloDobar=Integer.parseInt(ocjena);
	}
	public void setDobar(String ocjena){
		ocjeneDobar=Integer.parseInt(ocjena);
	}
	public void setDovoljan(String ocjena){
		ocjeneDovoljan=Integer.parseInt(ocjena);
	}
	public void setNedovoljan(String ocjena){
		ocjeneNedovoljan=Integer.parseInt(ocjena);
	}
}
