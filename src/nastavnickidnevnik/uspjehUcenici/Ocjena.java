package nastavnickidnevnik.uspjehUcenici;

public class Ocjena {
	private final long id, ucenikid, predmetid;
	private final int ocjena;

	public Ocjena(long id, long ucenikid, long predmetid, int ocjena) {
		this.id=id;
		this.ucenikid=ucenikid;
		this.predmetid=predmetid;
		this.ocjena=ocjena;
	}
	public long getID(){
		return id;
	}
	public long getUcenikId(){
		return ucenikid;
	}
	public long getPredmetId(){
		return predmetid;
	}
	public int getOcjena(){
		return ocjena;
	}
}
