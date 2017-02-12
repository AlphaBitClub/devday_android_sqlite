package android.alphabit;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {


    private static final String DATABASE_NAME = "alphabit.db";
    private static final String TABLE_NAME = "users";
    private static final String NOM = "nom";
    private static final String PRENOM = "prenom";

    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private DBhelper dBhelper;

    public Database (Context c) {
        this.context = c;
    }

    public class DBhelper extends SQLiteOpenHelper {


        public DBhelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase d) {
            /* La fonction execSQL on la donne une requete qu'elle ne retourne pas des données
            comme SELECT on l'utilise pas */
            d.execSQL("create table " + TABLE_NAME +
                    "(" +NOM + " string not null," + PRENOM + " string not null) ");
        }

        // Cette fonction execute en premier lorsque on fait appelle au DBHelper
        @Override
        public void onUpgrade(SQLiteDatabase d, int i, int i1) {
            // Supprime la base de donnée s'il existe déjà
            d.execSQL("DROP IF EXISTS " + TABLE_NAME);
            // Faire appel à la fonction onCreate()
            onCreate(d);
        }
    }

        public void open(){
            // Le dbhelper on l'utilise pour manipuler la base de donnée et non pas pour executer les requetes
            dBhelper = new DBhelper(context);
            // Initialisation du database on mode ecriture
            sqLiteDatabase = dBhelper.getWritableDatabase();
        }

        // Fermeture du database
        public void close(){
            sqLiteDatabase.close();
        }


        // Insertion des données
        public void add (String nom, String prenom) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nom",nom);
            contentValues.put("prenom",prenom);
            sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        }

        public String getData() {
            String[] columns = new String[] {NOM,PRENOM};

            // On utilise un cursor pour parcourir les données récupérer
            Cursor c = sqLiteDatabase.query(TABLE_NAME,columns,null,null,null,null,null);
            String result = "";

            // Le cursor sauvgarde les index des colonnes (des chiffres) et non pas leur nom
            int iNom = c.getColumnIndex(NOM);
            int iPrenom = c.getColumnIndex(PRENOM);


            for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
                result = result + " " +c.getString(iNom)
                        + " " + c.getString(iPrenom) + "\n";
            }
            return result;
        }

        // La suppression d'une donnée
        public void delete(String nom){
            sqLiteDatabase.delete(TABLE_NAME,NOM +"="+ "\""+nom+"\"" ,null);
        }

        // La modification d'une donnée
        public void modify(ContentValues contentValues,String nom){
            sqLiteDatabase.update(TABLE_NAME,contentValues,NOM +"="+ "\""+nom+"\"" ,null);
        }


    }


