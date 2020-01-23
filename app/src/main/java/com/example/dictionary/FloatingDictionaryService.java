package com.example.dictionary;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;

import static android.text.TextUtils.split;

public class FloatingDictionaryService extends Service {

    private View floatView;
    private WindowManager windowManager;

    public FloatingDictionaryService(){

    }

    @Override
    public IBinder onBind(Intent i)
    {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        floatView = LayoutInflater.from(this).inflate(R.layout.layout_floating_window, null);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() == null)
        {
            stopSelf();
        }
        //Add the view to the window.
        final WindowManager.LayoutParams params,params1;

        // params when float window is closed
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }else{
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }

        // params when float window is open
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params1 = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT);
        }else{
            params1 = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT);
        }


        //Specify the view position

        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 600;
        params.y = 150;

        //Add the view to the window
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        //Toast.makeText(getApplicationContext(), "io2", Toast.LENGTH_SHORT).show();

        windowManager.addView(floatView, params);

        ImageButton btn = (ImageButton) floatView.findViewById(R.id.collapse_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if(auth.getCurrentUser() == null)
                {
                    stopSelf();return;
                }
                windowManager.updateViewLayout(floatView,params1);
                RelativeLayout r = (RelativeLayout) floatView.findViewById(R.id.collapse_view);
                RelativeLayout s = (RelativeLayout) floatView.findViewById(R.id.expand_view);

                r.setVisibility(View.GONE);
                s.setVisibility(View.VISIBLE);

            }
        });

        ImageButton crossButton = (ImageButton) floatView.findViewById(R.id.close_btn);

        crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSelf();
            }
        });

        Button closeButton = (Button) floatView.findViewById(R.id.close);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.updateViewLayout(floatView,params);
                RelativeLayout r = (RelativeLayout) floatView.findViewById(R.id.collapse_view);
                RelativeLayout s = (RelativeLayout) floatView.findViewById(R.id.expand_view);

                s.setVisibility(View.GONE);
                r.setVisibility(View.VISIBLE);
            }
        });

        ImageButton searchButton = (ImageButton) floatView.findViewById(R.id.search);
        final TextView tv = (TextView) floatView.findViewById(R.id.meaning);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText et = (EditText) floatView.findViewById(R.id.search_word);

                String si = et.getText().toString();

                DBHandler db = new DBHandler(getApplicationContext(),null,null,2);

                String meaning = db.getMeaning(si);
                if(meaning=="Not Found"){
                    tv.setText("Error: WORD NOT FOUND");
                }else {
                    meaning = meaning.substring(3);
                    String arr[] = split(meaning, ";");
                    tv.setText("");
                    int x = 1;
                    for (String s : arr) {
                        s = s.substring(0, 1).toUpperCase() + s.substring(1);
                        if(x==1)
                            tv.setText(x + ". " + s.trim());
                        else
                            tv.setText(tv.getText() + "\n" + x + ". " + s.trim());
                        x++;
                    }
                    meaning = null;
                }



            }
        });

        searchButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getBaseContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });



    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (floatView != null) {
            windowManager.removeView(floatView);
        }
    }

}
