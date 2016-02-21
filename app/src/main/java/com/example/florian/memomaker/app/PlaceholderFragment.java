package com.example.florian.memomaker.app;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */


    ListView listViewtodo;
    ListView listViewmemo;
    ListView listViewArchivTodo;
    ListView listViewArchivMemo;
    String[] values;
    String[] valuesMemo;
    String[] archiveTodoArray;
    String[] archiveMemoArray;

    SQLiteDatabase mydb;
    private static String DBMEMO = "memomaker.db";
    private static String TABLE = "mmdata";

    private static final String ARG_SECTION_NUMBER = "section_number";


    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){

            listViewtodo = (ListView) rootView.findViewById(R.id.listViewTodo);
            listViewmemo = (ListView) rootView.findViewById(R.id.listViewMemo);


            values = loadTodoData();
            valuesMemo = loadMemoData();
/*
            //TEST-Array mit Daten für die Listview
            values = new String[]{
                    "Neue Activity starten",
                    "Link zur FH-Website",
                    "Kontakte editieren",
                    "Foto machen",
                    "Musik abspielen",
                    "Eigene Funktion",
                    "Mehr",
                    "funktionen",
                    "A",
                    "B",
                    "test",
                    "Poster",
                    "Peter",
                    "HORST",
                    "dirk"
            };

            //TEST-Array für MemoView
            valuesMemo = new String[]{
                    "Erstes Testelement",
                    "Zweites Element",
                    "MEMO",
                    "Mehr",
                    "elemente",
                    "befülle die",
                    "SCHEISS",
                    "LISTÖÖÖEEE"
            };
            */

            //Adapter für To-Do definieren
            //Kontext, Layout je Reihe, ID der TextView mit den Daten, Arraydaten
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,
                    android.R.id.text1, values);

            //Adapter für MemoView
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, valuesMemo);

            //Array mit Adapter verknüpfen
            listViewtodo.setAdapter(adapter1);
            listViewmemo.setAdapter(adapter2);


        }//Ende If AKTIVE List-View


        //Arrays für ARCHIVE
        if(getArguments().getInt(ARG_SECTION_NUMBER)== 2){

            //ListView initialisieren
            listViewArchivTodo = (ListView) rootView.findViewById(R.id.listViewTodo);
            listViewArchivMemo = (ListView) rootView.findViewById(R.id.listViewMemo);

            // Ladefunktionen, noch nicht aktiv
            //values = loadTodoData();
            //valuesMemo = loadMemoData();

            archiveTodoArray = new String[]{
                    "Neeeeeeee",
                    "Link zur FH-Website",
                    "Kontakte editieren",
                    "Foto machen",
                    "Musik abspielen",
                    "Eigene Funktion",
                    "Mehr",
                    "funktionen",
                    "A",
                    "B",
            };

            archiveMemoArray = new String[]{
                    "Kontakte editieren",
                    "Foto machen",
                    "Musik abspielen",
                    "Eigene Funktion",
                    "Mehr",
                    "funktionen",
                    "A",
                    "B",
                    "ftntdh",
                    "gerhgs",
                    "Peter",
                    "HORST",
            };


            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, archiveTodoArray);

            ArrayAdapter<String> adapter4 = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, archiveMemoArray);

            //Array mit Adapter verknüpfen
            listViewArchivTodo.setAdapter(adapter3);
            listViewArchivMemo.setAdapter(adapter4);
        }//Ende If ARCHIVE List-View


        return rootView;
    }

    @Override
    public void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);
        //Save Data
        //TODO: aktuelle Daten abspeichern
    }

    //DB
    public String[] loadTodoData() {
        String[] tdData;
        try {
            mydb = getActivity().openOrCreateDatabase(DBMEMO, Context.MODE_PRIVATE, null);
            Cursor allrows = mydb.rawQuery("select * from " + TABLE + " where TYPE = 'todo' and ARCHIVE = 0 " +
                    "order by PRIORITY, DESCRIPTION", null);
            Integer cindex = allrows.getColumnIndex("PRIORITY");
            Integer cindex1 = allrows.getColumnIndex("DESCRIPTION");
            tdData = new String[allrows.getCount()];

            if(allrows.moveToFirst()) {
                int i = 0;
                do {
                    tdData[i] = allrows.getString(cindex1);
                    i++;
                } while (allrows.moveToNext());
                //Test
                Toast.makeText(getActivity().getApplicationContext(), "geladen", Toast.LENGTH_LONG);
            }

        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Fehler beim Lesen der Datenbank", Toast.LENGTH_LONG);
            tdData = new String[0];
        }
        return tdData;
    }

    public String[] loadMemoData() {
        String[] mmData;
        try {
            mydb = getActivity().openOrCreateDatabase(DBMEMO, Context.MODE_PRIVATE, null);
            Cursor allrows = mydb.rawQuery("select * from " + TABLE + " where TYPE = 'memo' and ARCHIVE = 0 " +
                    "order by PRIORITY, DESCRIPTION", null);
            Integer cindex = allrows.getColumnIndex("DATEMEMO");
            Integer cindex1 = allrows.getColumnIndex("DESCRIPTION");
            mmData = new String[allrows.getCount()];

            if(allrows.moveToFirst()) {
                int i = 0;
                do {
                    mmData[i] = allrows.getString(cindex1);
                    i++;
                } while (allrows.moveToNext());
                //Test
                Toast.makeText(getActivity().getApplicationContext(), "geladen", Toast.LENGTH_LONG);
            }

        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Fehler beim Lesen der Datenbank", Toast.LENGTH_LONG);
            mmData = new String[0];
        }
        return mmData;
    }
    // Archiv --> noch testen
    public String[] loadTodoDataArchive() {
        String[] tdData;
        try {
            mydb = getActivity().openOrCreateDatabase(DBMEMO, Context.MODE_PRIVATE, null);
            Cursor allrows = mydb.rawQuery("select * from " + TABLE + " where TYPE = 'todo' and ARCHIVE = 1 " +
                    "order by PRIORITY, DESCRIPTION", null);
            Integer cindex = allrows.getColumnIndex("PRIORITY");
            Integer cindex1 = allrows.getColumnIndex("DESCRIPTION");
            tdData = new String[allrows.getCount()];

            if(allrows.moveToFirst()) {
                int i = 0;
                do {
                    tdData[i] = allrows.getString(cindex1);
                    i++;
                } while (allrows.moveToNext());
                //Test
                Toast.makeText(getActivity().getApplicationContext(), "geladen", Toast.LENGTH_LONG);
            }

        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Fehler beim Lesen der Datenbank", Toast.LENGTH_LONG);
            tdData = new String[0];
        }
        return tdData;
    }

    public String[] loadMemoDataArchive() {
        String[] mmData;
        try {
            mydb = getActivity().openOrCreateDatabase(DBMEMO, Context.MODE_PRIVATE, null);
            Cursor allrows = mydb.rawQuery("select * from " + TABLE + " where TYPE = 'memo' and ARCHIVE = 1 " +
                    "order by PRIORITY, DESCRIPTION", null);
            Integer cindex = allrows.getColumnIndex("DATEMEMO");
            Integer cindex1 = allrows.getColumnIndex("DESCRIPTION");
            mmData = new String[allrows.getCount()];

            if(allrows.moveToFirst()) {
                int i = 0;
                do {
                    mmData[i] = allrows.getString(cindex1);
                    i++;
                } while (allrows.moveToNext());
                //Test
                Toast.makeText(getActivity().getApplicationContext(), "geladen", Toast.LENGTH_LONG);
            }

        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Fehler beim Lesen der Datenbank", Toast.LENGTH_LONG);
            mmData = new String[0];
        }
        return mmData;
    }
}
