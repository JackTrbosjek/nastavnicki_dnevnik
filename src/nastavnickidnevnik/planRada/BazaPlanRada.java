package nastavnickidnevnik.planRada;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BazaPlanRada {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_SADRZAJ = "sadrzaj";
	public static final String KEY_NOSITELJ = "nositelj";
	public static final String KEY_PLANIRANO_VRIJEME = "satiplanirano";
	public static final String KEY_OSTVARENO_VRIJEME = "satiostvareno";
	

	private static final String DATABASE_NAME = "PlanRada";
	private static final String DATABASE_TABLE = "planrada";
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
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SADRZAJ
					+ " VARCHAR NOT NULL, " + KEY_NOSITELJ
					+ " VARCHAR NOT NULL, " + KEY_PLANIRANO_VRIJEME
					+ " VARCHAR NOT NULL, " + KEY_OSTVARENO_VRIJEME
					+ " VARCHAR NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public BazaPlanRada(Context c) {
		ourContext = c;
	}

	public BazaPlanRada otvori() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void zatvori() {
		ourHelper.close();
	}

	public long dodajSadrzaj(String sadrzaj,String nositelj,String planirano,String ostvareno) throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_SADRZAJ, sadrzaj);
		cv.put(KEY_NOSITELJ, nositelj);
		cv.put(KEY_PLANIRANO_VRIJEME, planirano);
		cv.put(KEY_OSTVARENO_VRIJEME, ostvareno);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public ArrayList<Sadrzaj> dohvatiSadrzaje() throws SQLException {

		String[] columns = new String[] { KEY_ROWID, KEY_SADRZAJ,
				KEY_NOSITELJ, KEY_PLANIRANO_VRIJEME, KEY_OSTVARENO_VRIJEME};
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		ArrayList<Sadrzaj> nazad = new ArrayList<Sadrzaj>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			nazad.add(new Sadrzaj(c.getLong(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4)));
		}
		c.close();
		return nazad;
	}

	public void obrisi(long id) throws SQLException {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + id, null);
	}

	public void update(long id, String nositelj,String planirano,String ostvareno)
			throws SQLException {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_NOSITELJ, nositelj);
		cvUpdate.put(KEY_PLANIRANO_VRIJEME, planirano);
		cvUpdate.put(KEY_OSTVARENO_VRIJEME, ostvareno);
		ourDatabase.update(DATABASE_TABLE, cvUpdate,
				KEY_ROWID + "=" + id, null);
	}
}
