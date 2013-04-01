package nastavnickidnevnik.uspjehUcenici;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BazaUspjehUcenici {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_PREDMET_ID = "predmetid";
	public static final String KEY_UCENIK_ID = "ucenikid";
	public static final String KEY_OCJENA = "ocjena";

	private static final String DATABASE_NAME = "UceniciUspjeh";
	private static final String DATABASE_TABLE = "uspjehucenika";
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
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_UCENIK_ID
					+ " INTEGER NOT NULL, " + KEY_PREDMET_ID
					+ " INTEGER NOT NULL, " + KEY_OCJENA
					+ " INTEGER NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public BazaUspjehUcenici(Context c) {
		ourContext = c;
	}

	public BazaUspjehUcenici otvori() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void zatvori() {
		ourHelper.close();
	}

	public long dodaj(long ucenikid, long predmetid, int ocjena)
			throws SQLException {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_UCENIK_ID,
				KEY_PREDMET_ID, KEY_OCJENA };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_UCENIK_ID
				+ "=" + ucenikid + " AND " + KEY_PREDMET_ID + "=" + predmetid,
				null, null, null, null);
		ContentValues cv = new ContentValues();
		cv.put(KEY_UCENIK_ID, ucenikid);
		cv.put(KEY_PREDMET_ID, predmetid);
		cv.put(KEY_OCJENA, ocjena);
		if (c.moveToFirst()) {
			long id = c.getInt(0);
			ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=" + id, null);
			return id;
		} else {
			return ourDatabase.insert(DATABASE_TABLE, null, cv);
		}

	}

	public ArrayList<Ocjena> dohvatiOcjene() throws SQLException {

		String[] columns = new String[] { KEY_ROWID, KEY_UCENIK_ID,
				KEY_PREDMET_ID, KEY_OCJENA };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		ArrayList<Ocjena> nazad = new ArrayList<Ocjena>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Ocjena novi = new Ocjena(c.getLong(0), c.getLong(1), c.getLong(2),
					c.getInt(3));
			nazad.add(novi);
		}
		c.close();
		return nazad;
	}

	public void obrisi(long predmetId,long ucenikId) throws SQLException {
		String[] columns = new String[] { KEY_ROWID, KEY_UCENIK_ID,
				KEY_PREDMET_ID, KEY_OCJENA };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_UCENIK_ID
				+ "=" + ucenikId + " AND " + KEY_PREDMET_ID + "=" + predmetId,
				null, null, null, null);
		if (c.moveToFirst()) {
			ourDatabase.delete(DATABASE_TABLE, KEY_UCENIK_ID + "=" +ucenikId+" AND "+KEY_PREDMET_ID+"="+predmetId , null);
		}
	}
}