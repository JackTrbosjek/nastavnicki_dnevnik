package nastavnickidnevnik.uceniciRaspored;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RasporedUceniciBaza {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_POZICIJA = "redniBroj";
	public static final String KEY_IME = "ime";
	public static final String KEY_PRIJEPODNE = "prijepodne";

	private static final String DATABASE_NAME = "RasporedUcenici";
	private static final String DATABASE_TABLE = "rasporedUcenici";
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

	public RasporedUceniciBaza(Context c) {
		ourContext = c;
	}

	public RasporedUceniciBaza otvori() throws SQLException {
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

	public ArrayList<PredmetRaspored> dohvatiPrijepodne() throws SQLException {

		String[] columns = new String[] { KEY_ROWID, KEY_POZICIJA, KEY_IME,
				KEY_PRIJEPODNE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_PRIJEPODNE+"=1", null, null,
				null, null);
		ArrayList<PredmetRaspored> nazad = new ArrayList<PredmetRaspored>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			PredmetRaspored novi = new PredmetRaspored(c.getLong(0), c.getInt(1),
					c.getString(2), c.getInt(3));
			nazad.add(novi);
		}
		c.close();
		return nazad;
	}
	public ArrayList<PredmetRaspored> dohvatiPoslijepodne() throws SQLException {

		String[] columns = new String[] { KEY_ROWID, KEY_POZICIJA, KEY_IME,
				KEY_PRIJEPODNE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_PRIJEPODNE+"=0", null, null,
				null, null);
		ArrayList<PredmetRaspored> nazad = new ArrayList<PredmetRaspored>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			PredmetRaspored novi = new PredmetRaspored(c.getLong(0), c.getInt(1),
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
