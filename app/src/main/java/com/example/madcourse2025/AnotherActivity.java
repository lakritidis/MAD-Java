package com.example.madcourse2025;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        Bundle b = getIntent().getExtras();

        String draw_monument = b.getString("monument");
        int pid = b.getInt("product_id");
        float price = b.getFloat("price");

        ImageView img_view = findViewById(R.id.img_view);

        switch (draw_monument) {
            case "white tower":
                img_view.setImageResource(R.drawable.whitetower);
                break;
            case "citadel":
                img_view.setImageResource(R.drawable.acropolis);
                break;
            case "cat03":
                img_view.setImageResource(R.drawable.prod_cat03);
                break;
        }

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(
                v -> {
                    AnotherActivity.this.finish();

//                    Intent MyIntent = new Intent(AnotherActivity.this, MainActivity.class);
//                    startActivity(MyIntent);
                }
        );
    }
}