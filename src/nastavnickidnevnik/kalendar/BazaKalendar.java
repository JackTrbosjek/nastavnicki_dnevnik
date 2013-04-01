package nastavnickidnevnik.kalendar;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BazaKalendar {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_DAN = "dan";
	public static final String KEY_MJESEC = "mjesec";
	public static final String KEY_GODINA = "godina";
	public static final String KEY_NASLOV = "naslov";
	public static final String KEY_OPIS = "opis";

	private static final String DATABASE_NAME = "Nastavnicki";
	private static final String DATABASE_TABLE = "kalendar";
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
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DAN
					+ " INTEGER NOT NULL, " + KEY_MJESEC + " INTEGER NOT NULL,"
					+ KEY_GODINA + " INTEGER NOT NULL, " + KEY_NASLOV
					+ " VARCHAR NOT NULL, " + KEY_OPIS + " VARCHAR NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public BazaKalendar(Context c) {
		ourContext = c;
	}

	public BazaKalendar otvori() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void zatvori() {
		ourHelper.close();
	}

	public long dodaj(int dan, int mjesec, int godina, String naslov, String opis) throws SQLException{
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_DAN, dan);
		cv.put(KEY_MJESEC, mjesec);
		cv.put(KEY_GODINA, godina);
		cv.put(KEY_NASLOV, naslov);
		cv.put(KEY_OPIS, opis);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	
	
	public ArrayList<Unosi> dohvati(int mjesec, int godina) throws SQLException{
		
		String[] columns = new String[] { KEY_ROWID, KEY_DAN, KEY_MJESEC,
				KEY_GODINA, KEY_NASLOV, KEY_OPIS };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns,KEY_MJESEC + "=" + mjesec + " AND "
				+ KEY_GODINA + "=" + godina, null, null, null, null);
		ArrayList<Unosi> nazad= new ArrayList<Unosi>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			nazad.add(new Unosi(c.getInt(0),c.getInt(1),c.getString(4),c.getString(5)));		
		}
		c.close();
		return nazad;
	}



	public void obrisi(int id) throws SQLException {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + id, null);
	}
	
	public void update(int id,String naslov,String opis) throws SQLException{
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_NASLOV, naslov);
		cvUpdate.put(KEY_OPIS, opis);
		ourDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID+"="+id, null);
	}

}