package com.example.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentTwo extends Fragment {

    static ArrayAdapter<String> adapter;
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        v = inflater.inflate(R.layout.fragment_two,container,false);

        mark();

        return v;
    }

    public void mark()
    {
        final DBHandler db = new DBHandler(getActivity().getApplicationContext(),null,null,1);

        ListView l = (ListView) v.findViewById(R.id.list);

        adapter = new ArrayAdapter<String>(
                getActivity().getApplicationContext(),
                R.layout.activity_listview,
                db.getAllWordWithMark()
        );

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

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        mark();
    }
}
