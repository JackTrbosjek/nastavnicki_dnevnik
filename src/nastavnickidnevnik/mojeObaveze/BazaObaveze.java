package nastavnickidnevnik.mojeObaveze;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BazaObaveze {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_VRSTA = "vrsta";
	public static final String KEY_REDNI_BROJ = "rb";
	public static final String KEY_NAZIV = "naziv";
	public static final String KEY_RAZREDNI_ODJEL = "razred";
	public static final String KEY_SATI = "sati";

	private static final String DATABASE_NAME = "MojeObaveze";
	private static final String DATABASE_TABLE = "mojeobaveze";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" 
			+ KEY_ROWID	+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ KEY_VRSTA + " VARCHAR NOT NULL, " 
			+ KEY_REDNI_BROJ + " INTEGER NOT NULL, "
			+ KEY_NAZIV + " VARCHAR NOT NULL, "
			+ KEY_RAZREDNI_ODJEL + " VARCHAR NOT NULL, "
			+ KEY_SATI + " INTEGER NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public BazaObaveze(Context c) {
		ourContext = c;
	}

	public BazaObaveze otvori() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void zatvori() {
		ourHelper.close();
	} 

	public ArrayList<Obaveza> dohvatiObaveze() throws SQLException {

		String[] columns = new String[] { KEY_ROWID, KEY_VRSTA, KEY_REDNI_BROJ,KEY_NAZIV,KEY_RAZREDNI_ODJEL,KEY_SATI };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		ArrayList<Obaveza> nazad = new ArrayList<Obaveza>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Obaveza novi = new Obaveza(c.getLong(0),c.getString(1),c.getInt(2),c.getString(3),c.getString(4),c.getInt(5));
			nazad.add(novi);
		}
		c.close();
		return nazad;
	}

	public void obrisi(long id) throws SQLException {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + id, null);
	}

	public void updateObaveza(int redniBroj,String naziv,String vrsta,String razredniOdjel,int sati)
			throws SQLException {
		String[] columns = new String[] { KEY_ROWID };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_REDNI_BROJ+"="+redniBroj+" AND "+KEY_NAZIV+"='"+naziv+"'", null, null,
				null, null);
		ContentValues cv = new ContentValues();
		cv.put(KEY_VRSTA, vrsta);
		cv.put(KEY_RAZREDNI_ODJEL, razredniOdjel);
		cv.put(KEY_SATI, sati);
		if(c.moveToFirst()){
			ourDatabase
			.update(DATABASE_TABLE, cv, KEY_REDNI_BROJ + "=" + redniBroj+" AND "+KEY_NAZIV+"='"+naziv+"'", null);
		}else{
			cv.put(KEY_REDNI_BROJ, redniBroj);
			cv.put(KEY_NAZIV, naziv);
			ourDatabase.insert(DATABASE_TABLE, null, cv);
		}
	}
}