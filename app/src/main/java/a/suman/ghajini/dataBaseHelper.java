package a.suman.ghajini;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class dataBaseHelper extends SQLiteOpenHelper {
    public static final String Dbname = "Save.db";
    public static final String Tbname = "List";

    public dataBaseHelper(Context con) {
        super(con, Dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase d) {
        String Q = "CREATE TABLE " + Tbname + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," + "ITEM TEXT)";
        d.execSQL(Q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase d, int oldVersion, int newVersion) {
        d.execSQL("DROP TABLE IF EXISTS " + Tbname);
    }

    public boolean Add(String item) {
        SQLiteDatabase d = this.getWritableDatabase();
        ContentValues ConV = new ContentValues();
        ConV.put("ITEM", item);
        long r = d.insert(Tbname, null, ConV);
        if (r == -1)
            return false;
        else
            return true;
    }

    Cursor getLCon() {
        SQLiteDatabase d = this.getWritableDatabase();
        Cursor data = d.rawQuery("SELECT * FROM " + Tbname, null);
        return data;
    }



     void Edt(String item, String a) {
        SQLiteDatabase d=this.getWritableDatabase();
        ContentValues conV= new ContentValues();
        conV.put("ITEM", item);
        d.update(Tbname, conV, "ITEM=?",new String[]{a});
    }
    void Del(String a){
        SQLiteDatabase d=this.getWritableDatabase();
        d.delete(Tbname, "ITEM=?",new String[]{a});
    }

}

