package com.bili.biliquguanggao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View mytext = findViewById(R.id.sample_text);
        mytext.setVisibility(View.GONE);

    }

}