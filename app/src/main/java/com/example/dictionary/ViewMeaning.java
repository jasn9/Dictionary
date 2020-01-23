package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.text.TextUtils.split;

public class ViewMeaning extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meaning);
        getSupportActionBar().hide();
        Bundle res = getIntent().getExtras();
        TextView t1 = (TextView) findViewById(R.id.word);
        TextView t2 = (TextView) findViewById(R.id.meaning);

        if(res==null)
        {

        }
        else{

            final String word = res.getString("word");

            final ImageButton btn = (ImageButton) findViewById(R.id.imageButton);

            final DBHandler db = new DBHandler(getApplicationContext(),null,null,2);

            String si = db.getMark(word);

            if(si.equals("1"))
            {
                btn.setImageResource(android.R.drawable.btn_star_big_on);
            }
            else{
                btn.setImageResource(android.R.drawable.btn_star_big_off);
            }

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = db.getMark(word);
                    if(s.equals("1"))
                    {
                        btn.setImageResource(android.R.drawable.btn_star_big_off);
                        db.setMark(word,"0");
                    }
                    else {
                        btn.setImageResource(android.R.drawable.btn_star_big_on);
                        db.setMark(word,"1");
                    }
                }
            });

            String meaning = res.getString("meaning");
            TextView t;
            if(meaning.charAt(0)=='j')
            {
                t = (TextView) findViewById(R.id.textView1);
            }
            else {
                if(meaning.charAt(0)=='n')
                {
                    t = (TextView) findViewById(R.id.textView2);
                }
                else {
                    t = (TextView) findViewById(R.id.textView3);
                }
            }
            t.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
            t.setTextColor(getResources().getColor(android.R.color.white));



            meaning = meaning.substring(3);
            String arr[] = split(meaning,";");
            t2.setText("");
            int x = 1;
            for(String s: arr)
            {
                s = s.substring(0,1).toUpperCase()+s.substring(1);
                t2.setText(t2.getText()+"\n"+x+". "+s.trim());
                x++;
            }

            t1.setText(word);

        }



    }

}
