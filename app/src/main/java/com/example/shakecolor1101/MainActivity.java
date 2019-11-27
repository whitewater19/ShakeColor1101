package com.example.shakecolor1101;
//1介面畫一個垂直linearlayoutt;長寬都300(正方形)
//2增一個類別
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    public static LinearLayout square;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//13指定完回類別
        square = findViewById(R.id.squarE);

//16啟動服務(2)
        Intent intent = new Intent(this, ShakeService.class);
        //class
        startService(intent);
        //Service,非Activity
//17到manifests做註冊(才能啟動)
    }
}

