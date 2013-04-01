package nastavnickidnevnik.mojRaspored;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MojRasporedBaza {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_POZICIJA = "redniBroj";
	public static final String KEY_IME = "ime";
	public static final String KEY_PRIJEPODNE = "prijepodne";

	private static final String DATABASE_NAME = "MojRaspored";
	private static final String DATABASE_TABLE = "mojRaspored";
	private static final int DATABASE_VERSION = 2;

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
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_POZICIJA
					+ " INTEGER NOT NULL, " + KEY_PRIJEPODNE
					+ " INTEGER NOT NULL, " + KEY_IME + " VARCHAR NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public MojRasporedBaza(Context c) {
		ourContext = c;
	}

	public MojRasporedBaza otvori() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void zatvori() {
		ourHelper.close();
	}

	public long dodaj(int pozicija, String ime) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_POZICIJA, pozicija);
		cv.put(KEY_IME, ime);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public ArrayList<PredmetMojRaspored> dohvatiPrijepodne() throws SQLException {

		String[] columns = new String[] { KEY_ROWID, KEY_POZICIJA, KEY_IME,
				KEY_PRIJEPODNE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_PRIJEPODNE+"=1", null, null,
				null, null);
		ArrayList<PredmetMojRaspored> nazad = new ArrayList<PredmetMojRaspored>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			PredmetMojRaspored novi = new PredmetMojRaspored(c.getLong(0), c.getInt(1),
					c.getString(2), c.getInt(3));
			nazad.add(novi);
		}
		c.close();
		return nazad;
	}
	public ArrayList<PredmetMojRaspored> dohvatiPoslijepodne() throws SQLException {

		String[] columns = new String[] { KEY_ROWID, KEY_POZICIJA, KEY_IME,
				KEY_PRIJEPODNE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_PRIJEPODNE+"=0", null, null,
				null, null);
		ArrayList<PredmetMojRaspored> nazad = new ArrayList<PredmetMojRaspored>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			PredmetMojRaspored novi = new PredmetMojRaspored(c.getLong(0), c.getInt(1),
					c.getString(2), c.getInt(3));
			nazad.add(novi);
		}
		c.close();
		return nazad;
	}

	public void obrisi(long id) throws SQLException {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + id, null);
	}

	public void updatePredmet(int pozicija, String ime, int prijepodne)
			throws SQLException {
		String[] columns = new String[] { KEY_ROWID, KEY_POZICIJA, KEY_IME,
				KEY_PRIJEPODNE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_PRIJEPODNE+"="+prijepodne+" AND "+KEY_POZICIJA+"="+pozicija, null, null,
				null, null);
		ContentValues cv = new ContentValues();
		cv.put(KEY_IME, ime);
		if(c.moveToFirst()){
			ourDatabase
			.update(DATABASE_TABLE, cv, KEY_PRIJEPODNE + "=" + prijepodne+" AND "+KEY_POZICIJA+"="+pozicija, null);
		}else{
			cv.put(KEY_POZICIJA, pozicija);
			cv.put(KEY_PRIJEPODNE, prijepodne);
			ourDatabase.insert(DATABASE_TABLE, null, cv);
		}
	}
}
