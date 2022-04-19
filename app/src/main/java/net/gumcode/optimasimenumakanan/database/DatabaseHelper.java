package net.gumcode.optimasimenumakanan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.gumcode.optimasimenumakanan.model.Akg;
import net.gumcode.optimasimenumakanan.model.Bahan;
import net.gumcode.optimasimenumakanan.model.Ga;
import net.gumcode.optimasimenumakanan.model.Hasil;
import net.gumcode.optimasimenumakanan.model.Keluarga;
import net.gumcode.optimasimenumakanan.model.Sa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by A. Fauzi Harismawan on 8/2/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "optimasibmgasa";
    private static final int DB_VERSION = 1;

    public static String TABLE_ANJURAN_PORSI = "anjuran_porsi";
    public static String TABLE_BUAH = "buah";
    public static String TABLE_SUSU = "susu";
    public static String TABLE_AKG = "akg";
    public static String TABLE_HASIL = "hasil";
    public static String TABLE_ANGGOTA_KELUARGA = "anggota_keluarga";
    public static String TABLE_PARAMETER_AG = "parameter_ag";
    public static String TABLE_PARAMETER_SA = "parameter_sa";
    public static String TABLE_FAKTOR_AKTIVITAS = "faktor_aktifitas";
    public static String TABLE_GULA = "gula";
    public static String TABLE_KARBOHIDRT = "karbohidrt";
    public static String TABLE_LEMAK = "lemak";
    public static String TABLE_PROTEIN_HEWANI = "protein_hewani";
    public static String TABLE_PROTEIN_NABATI = "protein_nabati";
    public static String TABLE_SAYURANA = "sayurana";
    public static String TABLE_SAYURANB = "sayuranb";

    public static String COLUMN_TYPE = "type";
    public static String COLUMN_UMUR_ATAS = "umur_atas";
    public static String COLUMN_UMUR_BAWAH = "umur_bawah";
    public static String COLUMN_KH_L = "kh_l";
    public static String COLUMN_KH_P = "kh_p";
    public static String COLUMN_PH_L = "ph_l";
    public static String COLUMN_PH_P = "ph_p";
    public static String COLUMN_PN_L = "pn_l";
    public static String COLUMN_PN_P = "pn_p";
    public static String COLUMN_SAYURB_L = "sayurB_l";
    public static String COLUMN_SAYURB_P = "sayurB_p";
    public static String COLUMN_BUAH_L = "buah_l";
    public static String COLUMN_BUAH_P = "buah_p";
    public static String COLUMN_LEMAK_L = "lemak_l";
    public static String COLUMN_LEMAK_P = "lemak_p";
    public static String COLUMN_SUSU_L = "susu_l";
    public static String COLUMN_SUSU_P = "susu_p";
    public static String COLUMN_GULA_L = "gula_l";
    public static String COLUMN_GULA_P = "gula_p";
    public static String COLUMN_INDEX_BM = "index_bm";
    public static String COLUMN_NAMA_BM = "nama_bm";
    public static String COLUMN_HARGA = "harga";
    public static String COLUMN_BERAT = "berat";
    public static String COLUMN_KALORI = "kalori";
    public static String COLUMN_KARBOHIDRAT = "karbohidrat";
    public static String COLUMN_PROTEIN = "protein";
    public static String COLUMN_LEMAK = "lemak";
    public static String COLUMN_NO = "no";
    public static String COLUMN_NAMA = "nama";
    public static String COLUMN_USIA = "usia";
    public static String COLUMN_JK = "jk";
    public static String COLUMN_TINGGI = "tinggi";
    public static String COLUMN_AKTIVITAS = "aktivitas";
    public static String COLUMN_ID = "id";
    public static String COLUMN_POPSIZE = "popsize";
    public static String COLUMN_CR = "cr";
    public static String COLUMN_MR = "mr";
    public static String COLUMN_GEN = "gen";
    public static String COLUMN_TA = "ta";
    public static String COLUMN_TN = "tn";
    public static String COLUMN_ALPHA = "alpha";
    public static String COLUMN_AKTIFITAS = "aktifitas";
    public static String COLUMN_PRIA = "pria";
    public static String COLUMN_WANITA = "wanita";
    public static String COLUMN_KKAL = "kkal";
    public static String COLUMN_TIPE = "tipe";
    public static String COLUMN_KROMOSOM = "kromosom";
    public static String COLUMN_FITNESS = "fitness";
    public static String COLUMN_HARI = "hari";
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

//    private boolean checkDataBase() {
//        SQLiteDatabase checkDB = null;
//        try {
//            String myPath = context.getDatabasePath(DB_NAME).getPath();
//            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//        } catch (SQLiteException e) {
//            e.printStackTrace();
//        }
//
//        if(checkDB != null) {
//            checkDB.close();
//        }
//
//        return checkDB != null;
//    }

    public void createDataBase() {
        getReadableDatabase();
        copyDataBase();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() {
        try {
            // Open your local db as the input stream
            InputStream myInput = context.getAssets().open(DB_NAME);
            // Path to the just created empty db
            File outFileName = context.getDatabasePath(DB_NAME);
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int CountRow(String tableName) {
        Cursor mCount = getReadableDatabase().rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);

        mCount.close();
        return count;
    }

    public int HasilQuery2(String NamaKolom, String NamaTabel, String Where, int Value) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + NamaKolom + " FROM " + NamaTabel + " WHERE " + Where + "=" + Value, null);
        c.moveToFirst();

        int result = c.getInt(c.getColumnIndex(NamaKolom));

        db.close();
        c.close();
        return result;
    }

    public String HasilQuery1(String NamaKolom, String NamaTabel, String Where, int Value) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT " + NamaKolom + " FROM " + NamaTabel + " WHERE " + Where + "=" + Value, null);
        c.moveToFirst();

        String result = c.getString(c.getColumnIndex(NamaKolom));

        db.close();
        c.close();
        return result;
    }

    public void setGa(Ga instance) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID, 1);
        values.put(DatabaseHelper.COLUMN_POPSIZE, instance.popsize);
        values.put(DatabaseHelper.COLUMN_CR, instance.cr);
        values.put(DatabaseHelper.COLUMN_MR, instance.mr);
        values.put(DatabaseHelper.COLUMN_GEN, instance.generasi);
        getWritableDatabase().replace(DatabaseHelper.TABLE_PARAMETER_AG, null, values);
    }

    public Ga getGa() {
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_PARAMETER_AG, null);
        c.moveToFirst();

        Ga ga = new Ga();
        if (c.getCount() != 0) {
            ga.popsize = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_POPSIZE));
            ga.cr = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_CR));
            ga.mr = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_MR));
            ga.generasi = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_GEN));
        }

        c.close();
        return ga;
    }

    public void setHari(int instance) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_HARI, instance);
        getWritableDatabase().replace(DatabaseHelper.COLUMN_HARI, null, values);
    }

    public int getHari() {
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + COLUMN_HARI, null);
        c.moveToFirst();

        int hari = 0;
        if (c.getCount() != 0) {
            hari = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_HARI));
        }

        c.close();
        return hari;
    }

    public void setSa(Sa instance) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ID, 1);
        values.put(DatabaseHelper.COLUMN_TA, instance.to);
        values.put(DatabaseHelper.COLUMN_TN, instance.tn);
        values.put(DatabaseHelper.COLUMN_ALPHA, instance.alpha);
        getWritableDatabase().replace(DatabaseHelper.TABLE_PARAMETER_SA, null, values);
    }

    public Sa getSa() {
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_PARAMETER_SA, null);
        c.moveToFirst();

        Sa sa = new Sa();
        if (c.getCount() != 0) {
            sa.to = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_TA));
            sa.tn = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_TN));
            sa.alpha = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_ALPHA));
        }

        c.close();
        return sa;
    }

    public void deleteHasil() {
        getWritableDatabase().execSQL("DELETE FROM " + TABLE_HASIL);
    }

    public void setHasil(Hasil instance) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_GEN, instance.gen);
        values.put(DatabaseHelper.COLUMN_KROMOSOM, instance.kromosom);
        values.put(DatabaseHelper.COLUMN_FITNESS, instance.fitness);
        getWritableDatabase().replace(DatabaseHelper.TABLE_HASIL, null, values);
    }

    public ArrayList<Hasil> getHasil() {
        ArrayList<Hasil> get = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_HASIL, null);
        c.moveToFirst();

        if (!c.isAfterLast()) {
            do {
                Hasil m = new Hasil();
                m.gen = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_GEN));
                m.kromosom = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_KROMOSOM));
                m.fitness = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_FITNESS));
                get.add(m);
            } while (c.moveToNext());
        }

        c.close();
        return get;
    }

    public void deleteAkg() {
        getWritableDatabase().execSQL("DELETE FROM " + TABLE_AKG);
    }

    public void setAkg(Akg instance) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NO, instance.no);
        values.put(DatabaseHelper.COLUMN_NAMA, instance.nama);
        values.put(DatabaseHelper.COLUMN_KKAL, instance.energi);
        values.put(DatabaseHelper.COLUMN_KARBOHIDRAT, instance.karbohidrat);
        values.put(DatabaseHelper.COLUMN_PROTEIN, instance.protein);
        values.put(DatabaseHelper.COLUMN_LEMAK, instance.lemak);
        values.put(DatabaseHelper.COLUMN_TIPE, instance.tipe);
        getWritableDatabase().replace(DatabaseHelper.TABLE_AKG, null, values);
    }

    public ArrayList<Akg> getAkg() {
        ArrayList<Akg> get = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_AKG, null);
        c.moveToFirst();

        if (!c.isAfterLast()) {
            do {
                Akg m = new Akg();
                m.no = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_NO));
                m.nama = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAMA));
                m.energi = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_KKAL));
                m.protein = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_PROTEIN));
                m.lemak = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_LEMAK));
                m.karbohidrat = c.getFloat(c.getColumnIndex(DatabaseHelper.COLUMN_KARBOHIDRAT));
                m.tipe = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_TIPE));
                get.add(m);
            } while (c.moveToNext());
        }

        c.close();
        return get;
    }

    public ArrayList<Bahan> getBahan(String table) {
        ArrayList<Bahan> get = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + table, null);
        c.moveToFirst();

        if (!c.isAfterLast()) {
            do {
                Bahan m = new Bahan();
                m.index_bm = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_INDEX_BM));
                m.nama_bm = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAMA_BM));
                m.harga = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_HARGA));
                m.berat = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_BERAT));
                m.kalori = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_KALORI));
                m.karbohidrat = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_KARBOHIDRAT));
                m.protein = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_PROTEIN));
                m.lemak = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_LEMAK));
                get.add(m);
            } while (c.moveToNext());
        }

        c.close();
        return get;
    }

    public void insertKeluarga(Keluarga instance) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAMA, instance.nama);
        values.put(DatabaseHelper.COLUMN_USIA, instance.usia);
        values.put(DatabaseHelper.COLUMN_JK, instance.jk);
        values.put(DatabaseHelper.COLUMN_BERAT, instance.berat);
        values.put(DatabaseHelper.COLUMN_TINGGI, instance.tinggi);
        values.put(DatabaseHelper.COLUMN_JK, instance.jk);
        values.put(DatabaseHelper.COLUMN_AKTIVITAS, instance.aktivitas);
        getWritableDatabase().insert(DatabaseHelper.TABLE_ANGGOTA_KELUARGA, null, values);
    }

    public void deleteAllKeluarga() {
        getWritableDatabase().execSQL("DELETE FROM " + TABLE_ANGGOTA_KELUARGA);
    }

    public void deleteKeluarga(int id) {
        getWritableDatabase().execSQL("DELETE FROM " + TABLE_ANGGOTA_KELUARGA + " WHERE NO = " + id);
    }

    public ArrayList<Keluarga> getKeluarga() {
        ArrayList<Keluarga> get = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_ANGGOTA_KELUARGA, null);
        c.moveToFirst();

        if (!c.isAfterLast()) {
            do {
                Keluarga m = new Keluarga();
                m.id = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_NO));
                m.nama = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_NAMA));
                m.usia = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_USIA));
                m.jk = c.getString(c.getColumnIndex(DatabaseHelper.COLUMN_JK));
                m.berat = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_BERAT));
                m.tinggi = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_TINGGI));
                m.aktivitas = c.getInt(c.getColumnIndex(DatabaseHelper.COLUMN_AKTIVITAS));
                get.add(m);
            } while (c.moveToNext());
        }

        c.close();
        return get;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

