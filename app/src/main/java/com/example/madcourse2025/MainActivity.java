package com.example.madcourse2025;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText et = findViewById(R.id.edit_surname);
        et.setText(R.string.cap_hello);

        Button btn_citadel = findViewById(R.id.btn_citadel);
        btn_citadel.setOnClickListener(
                v-> {
                    Intent MyIntent = new Intent(MainActivity.this, AnotherActivity.class);

                    Bundle b = new Bundle();

                    b.putString("monument", et.getText().toString());
                    b.putString("footballclub", "Liverpool");
                    b.putInt("temperature", 20);
                    b.putIntArray("product_ids", new int[]{20,308,407} );
                    b.putFloat("price", 10.5F);

                    MyIntent.putExtras(b);

                    startActivity(MyIntent);
                }
        );

        Button btn_connect = findViewById(R.id.btn_connect);
        btn_connect.setOnClickListener(
                v-> {
                    Intent MyIntent = new Intent(MainActivity.this, ConnectActivity.class);
                    startActivity(MyIntent);
                }
        );
    }
}