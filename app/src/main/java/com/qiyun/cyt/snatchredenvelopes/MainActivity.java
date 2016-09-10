package com.qiyun.cyt.snatchredenvelopes;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initEvent();
    }

    private void initUI() {
        Button action_settings = (Button) findViewById(R.id.action_settings);

        action_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"请把【微信抢红包神器】切换到【开启】状态",Toast.LENGTH_LONG).show();
                    }
                },1000);
            }
        });
    }

    private void initEvent() {
        startService(new Intent(MainActivity.this, SnatchRedEnvelopesService.class));
    }
}
