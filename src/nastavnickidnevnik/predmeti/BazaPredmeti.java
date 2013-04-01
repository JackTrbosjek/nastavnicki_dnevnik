package nastavnickidnevnik.predmeti;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BazaPredmeti {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_IME = "ime";
	public static final String KEY_SATI_PLANIRANO = "satiplanirano";
	public static final String KEY_SATI_OSTVARENO = "satiostvareno";
	public static final String KEY_SATI_DOPUNSKA = "satidopunska";
	public static final String KEY_SATI_STRUCNO = "satistrucno";
	public static final String KEY_SATI_NESTRUCNO = "satinestrucno";
	public static final String KEY_OCJENA_ODLICAN = "odlican";
	public static final String KEY_OCJENA_VRLO_DOBAR = "vrlodobar";
	public static final String KEY_OCJENA_DOBAR = "dobar";
	public static final String KEY_OCJENA_DOVOLJAN = "dovoljan";
	public static final String KEY_OCJENA_NEDOVOLJAN = "nedovoljan";

	private static final String DATABASE_NAME = "predmeti";
	private static final String DATABASE_TABLE = "predmet";
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
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_IME
					+ " VARCHAR NOT NULL, " + KEY_SATI_PLANIRANO
					+ " INTEGER NOT NULL, " + KEY_SATI_OSTVARENO
					+ " INTEGER NOT NULL, " + KEY_SATI_DOPUNSKA
					+ " INTEGER NOT NULL, " + KEY_SATI_STRUCNO
					+ " INTEGER NOT NULL, " + KEY_SATI_NESTRUCNO
					+ " INTEGER NOT NULL, " + KEY_OCJENA_ODLICAN
					+ " INTEGER NOT NULL, " + KEY_OCJENA_VRLO_DOBAR
					+ " INTEGER NOT NULL, " + KEY_OCJENA_DOBAR
					+ " INTEGER NOT NULL, " + KEY_OCJENA_DOVOLJAN
					+ " INTEGER NOT NULL, " + KEY_OCJENA_NEDOVOLJAN
					+ " INTEGER NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public BazaPredmeti(Context c) {
		ourContext = c;
	}

	public BazaPredmeti otvori() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void zatvori() {
		ourHelper.close();
	}

	public long dodajPredmet(String ime) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_IME, ime);
		cv.put(KEY_SATI_PLANIRANO, -1);
		cv.put(KEY_SATI_OSTVARENO, -1);
		cv.put(KEY_SATI_DOPUNSKA, -1);
		cv.put(KEY_SATI_STRUCNO, -1);
		cv.put(KEY_SATI_NESTRUCNO, -1);
		cv.put(KEY_OCJENA_ODLICAN, -1);
		cv.put(KEY_OCJENA_VRLO_DOBAR, -1);
		cv.put(KEY_OCJENA_DOBAR, -1);
		cv.put(KEY_OCJENA_DOVOLJAN, -1);
		cv.put(KEY_OCJENA_NEDOVOLJAN, -1);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public ArrayList<Predmet> dohvatiPredmete() throws SQLException {

		String[] columns = new String[] { KEY_ROWID, KEY_IME,
				KEY_SATI_PLANIRANO, KEY_SATI_OSTVARENO, KEY_SATI_DOPUNSKA,
				KEY_SATI_STRUCNO, KEY_SATI_NESTRUCNO, KEY_OCJENA_ODLICAN,
				KEY_OCJENA_VRLO_DOBAR, KEY_OCJENA_DOBAR, KEY_OCJENA_DOVOLJAN,
				KEY_OCJENA_NEDOVOLJAN };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		ArrayList<Predmet> nazad = new ArrayList<Predmet>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			nazad.add(new Predmet(c.getLong(0),c.getString(1),c.getInt(2),c.getInt(3),c.getInt(4),c.getInt(5),c.getInt(6),c.getInt(7),c.getInt(8),c.getInt(9),c.getInt(10),c.getInt(11)));
		}
		c.close();
		return nazad;
	}

	public void obrisi(long id) throws SQLException {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + id, null);
	}

	public void update(long id, String value,String KEY)
			throws SQLException {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY, value);
		ourDatabase.update(DATABASE_TABLE, cvUpdate,
				KEY_ROWID + "=" + id, null);
	}
}
