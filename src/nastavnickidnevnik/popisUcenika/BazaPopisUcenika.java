package nastavnickidnevnik.popisUcenika;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BazaPopisUcenika {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_REDNI_BROJ = "redniBroj";
	public static final String KEY_IME = "ime";
	public static final String KEY_PREZIME = "prezime";
	public static final String KEY_BILJESKE = "biljeske";
	public static final String KEY_RODITELJSKI = "roditeljski";
	public static final String KEY_RAZGOVORI = "razgovori";

	private static final String DATABASE_NAME = "Ucenici";
	private static final String DATABASE_TABLE = "popisUcenika";
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
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_REDNI_BROJ
					+ " INTEGER NOT NULL, " + KEY_IME + " VARCHAR NOT NULL, "
					+ KEY_PREZIME + " VARCHAR NOT NULL, " + KEY_BILJESKE
					+ " TEXT NOT NULL, " + KEY_RODITELJSKI + " TEXT NOT NULL, "
					+ KEY_RAZGOVORI + " TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public BazaPopisUcenika(Context c) {
		ourContext = c;
	}

	public BazaPopisUcenika otvori() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}

	public void zatvori() {
		ourHelper.close();
	}

	public long dodaj(int redniBroj, String ime, String prezime)
			throws SQLException {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_REDNI_BROJ, redniBroj);
		cv.put(KEY_IME, ime);
		cv.put(KEY_PREZIME, prezime);
		cv.put(KEY_BILJESKE, "");
		cv.put(KEY_RAZGOVORI, "");
		cv.put(KEY_RODITELJSKI, "");
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public ArrayList<Ucenik> dohvatiUcenike()
			throws SQLException {

		String[] columns = new String[] { KEY_ROWID, KEY_REDNI_BROJ, KEY_IME,
				KEY_PREZIME, KEY_BILJESKE, KEY_RODITELJSKI, KEY_RAZGOVORI };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, KEY_REDNI_BROJ);
		ArrayList<Ucenik> nazad = new ArrayList<Ucenik>();
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Ucenik novi = new Ucenik(c.getLong(0), c.getInt(1), c.getString(2),
					c.getString(3));
			// dohvacanje biljeski iz baze (ako postoje) i pretvaranje u
			// ArrayList
			String biljeska = c.getString(4);
			if (!biljeska.isEmpty()) {
				List<String> list = Arrays.asList(biljeska.substring(1,
						biljeska.length() - 1).split(", "));
				novi.addBiljeska(new ArrayList<String>(list));
			}
			// dohvacanje roditeljskih iz baze (ako postoje) i pretvaranje u
			// ArrayList
			String roditeljski = c.getString(5);
			if (!roditeljski.isEmpty()) {
				List<String> list = Arrays.asList(roditeljski.substring(1,
						roditeljski.length() - 1).split(", "));
				novi.addRoditeljski(new ArrayList<String>(list));
			}
			// dohvacanje razgovora iz baze (ako postoje) i pretvaranje u
			// ArrayList
			String razgovor = c.getString(6);
			if (!razgovor.isEmpty()) {
				List<String> list = Arrays.asList(razgovor.substring(1,
						razgovor.length() - 1).split(", "));
				novi.addRazgovor(new ArrayList<String>(list));
			}
			nazad.add(novi);
		}
		c.close();
		return nazad;
	}

	public void obrisi(long id) throws SQLException {
		// TODO Auto-generated method stub
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + id, null);
	}

	public void updateBiljeska(long idUcenik, ArrayList<String> biljeske) throws SQLException {
		ContentValues cvUpdate = new ContentValues();
		String sUnos=biljeske.toString();
		if(sUnos.contains("[]")){
			cvUpdate.put(KEY_BILJESKE, "");
		}else{
			cvUpdate.put(KEY_BILJESKE,sUnos);
		}
		ourDatabase
				.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + idUcenik, null);
	}
	
	public void updateRoditeljski(long idUcenik, ArrayList<String> roditeljski) throws SQLException {
		ContentValues cvUpdate = new ContentValues();
		String sUnos=roditeljski.toString();
		if(sUnos.contains("[]")){
			cvUpdate.put(KEY_RODITELJSKI, "");
		}else{
			cvUpdate.put(KEY_RODITELJSKI,sUnos);
		}
		ourDatabase
				.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + idUcenik, null);
	}
	
	public void updateRazgovor(long idUcenik, ArrayList<String> razgovor) throws SQLException {
		ContentValues cvUpdate = new ContentValues();
		String sUnos=razgovor.toString();
		if(sUnos.contains("[]")){
			cvUpdate.put(KEY_RAZGOVORI, "");
		}else{
			cvUpdate.put(KEY_RAZGOVORI,sUnos);
		}
		ourDatabase
				.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + idUcenik, null);
	}

}