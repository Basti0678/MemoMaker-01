package com.example.florian.memomaker.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Florian on 19.02.2016.
 */
public class NeuesMemoActivity extends AppCompatActivity{

    //DB
    SQLiteDatabase mydb;
    private static String DBMEMO = "memomaker.db";
    private static String TABLE = "mmdata";

    // Datum
    EditText editDate;
    private String textDate;
    private String text;
    EditText editText;
    Button saveButton;
    public static final String MY_PREFS_NAME = "InstantSave";

    private void updateProperties(){
        // Datum
        editDate =(EditText)findViewById(R.id.memoDate);
        textDate = editDate.getText().toString();

        editText = (EditText)findViewById(R.id.newMemo);
        saveButton = (Button)findViewById(R.id.savebutton);

        text = editText.getText().toString();
    }

    private void saveSettings(){
        updateProperties();

        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = shared.edit();

        edit.putString("KEY_TEXT", text);
        //Datum
        edit.putString("KEY_DATE", textDate);
        edit.commit();
    }

    private void loadSettings(){
        SharedPreferences shared = getSharedPreferences(MY_PREFS_NAME,Context.MODE_PRIVATE);
        text = shared.getString("KEY_TEXT","");

        //Datum
        textDate = shared.getString("KEY_DATE","");
        editDate = (EditText) findViewById(R.id.memoDate);
        editDate.setText(textDate);

        editText = (EditText) findViewById(R.id.newMemo);
        saveButton = (Button)findViewById(R.id.savebutton);

        editText.setText(text);
    }

    @Override
    public void onPause(){
        super.onPause();
        saveSettings();
    }

    @Override
    public void onResume(){
        super.onResume();
        loadSettings();
    }



    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.add_new_memoitem);

        final Button saveButton = (Button) findViewById(R.id.savebutton);
        EditText editText = (EditText) findViewById(R.id.newMemo);

        loadSettings();

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProperties();
                saveSettings();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                saveSettings();
                inserIntoTable();
                finish();
                Toast.makeText(getApplicationContext(), "Eintrag gespeichert", Toast.LENGTH_LONG);
            }
        });

    }
    public void createTable() {
        try {
            mydb = openOrCreateDatabase(DBMEMO, Context.MODE_PRIVATE, null);
            mydb.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (ID INTEGER PRIMARY KEY, TYPE TEXT, " +
                    "DATEMEMO DATE, PRIORITY CHAR, DESCRIPTION TEXT, ARCHIVE INTEGER);");
            mydb.close();
            Toast.makeText(getApplicationContext(), "Erstellen erfolgreich", Toast.LENGTH_LONG);
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Fehler beim Erstellen der Datenbank", Toast.LENGTH_LONG);
        }
    }

    public void inserIntoTable() {
        try {
            mydb = openOrCreateDatabase(DBMEMO, Context.MODE_PRIVATE, null);
            mydb.execSQL("INSERT INTO " + TABLE + " (TYPE, DATEMEMO, PRIORITY, DESCRIPTION, ARCHIVE) " +
                    "VALUES('memo', '" + textDate + "' , '', '" + text + "', 0)");
            /* Testdaten
            mydb.execSQL("INSERT INTO " + TABLE + " (TYPE, DATEMEMO, PRIORITY, DESCRIPTION, ARCHIVE) " +
                            "VALUES('todo', '' , 'A', 'Testtodo1', 0)");
            mydb.execSQL("INSERT INTO " + TABLE + " (TYPE, DATEMEMO, PRIORITY, DESCRIPTION, ARCHIVE) " +
                    "VALUES('todo', '' , 'A', 'Testtodo2', 0)");
            mydb.execSQL("INSERT INTO " + TABLE + " (TYPE, DATEMEMO, PRIORITY, DESCRIPTION, ARCHIVE) " +
                    "VALUES('todo', '' , 'A', 'Testtodo3', 0)");
            mydb.execSQL("INSERT INTO " + TABLE + " (TYPE, DATEMEMO, PRIORITY, DESCRIPTION, ARCHIVE) " +
                    "VALUES('todo', '' , 'A', 'Testtodo4', 0)");
            */
            mydb.close();
            Toast.makeText(getApplicationContext(), "Speichern erfolgreich", Toast.LENGTH_LONG);
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Fehler beim Erstellen der Datenbank", Toast.LENGTH_LONG);
        }
    }

    public void dropTable() {
        try {
            mydb = openOrCreateDatabase(DBMEMO, Context.MODE_PRIVATE, null);
            //String test = ("DROP " + TABLE);
            mydb.execSQL("DROP TABLE " + TABLE);
            mydb.close();
            Toast.makeText(getApplicationContext(), "Loeschen erfolgreich", Toast.LENGTH_LONG);
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Fehler beim Loeschen der Datenbank", Toast.LENGTH_LONG);
        }
    }

}
