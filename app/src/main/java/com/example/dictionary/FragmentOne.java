package com.example.dictionary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SearchView;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.text.TextUtils.split;

public class FragmentOne extends Fragment {
    View v;
    static ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState){

        v = inflater.inflate(R.layout.fragment_one,container,false);

        final DBHandler db = new DBHandler(getActivity().getApplicationContext(),null,null,2);

        //loadWord(db);
        //db.deleteWord("A");
        ListView l = (ListView) v.findViewById(R.id.list);
        final ArrayList<String> list = db.getAllWord();
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.activity_listview,list);
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = adapter.getItem(position).toString();

                Intent i = new Intent(getActivity().getApplicationContext(),ViewMeaning.class);
                i.putExtra("word",value);
                i.putExtra("meaning",db.getMeaning(value));
                startActivity(i);
            }
        });

        SearchView sv = (SearchView) v.findViewById(R.id.sv);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FragmentOne.adapter.getFilter().filter(newText);
                return false;
            }
        });

        return v;
    }

    protected void viewDB(final DBHandler db)
    {
        ListView l = (ListView) v.findViewById(R.id.list);

        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),R.layout.activity_listview,db.getAllWord());
        l.setAdapter(adapter);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = adapter.getItem(position).toString();

                Intent i = new Intent(getActivity().getApplicationContext(),ViewMeaning.class);
                i.putExtra("word",value);
                i.putExtra("meaning",db.getMeaning(value));
                startActivity(i);
            }
        });
    }

    public void loadWord(DBHandler db)
    {
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try{

            String line;
            while ((line = reader.readLine()) != null) {
                String[] strings = split(line, "-");
                if (strings.length < 2) continue;
                Word w = new Word();
                w.set_word(strings[0].trim());
                w.set_meaning(strings[1].trim());
                w.set_mark(0);
                db.addWord(w);
            }
            reader.close();

        } catch (IOException e) {



        }
    }
}
