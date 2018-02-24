package com.aiora.trendy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ImageView trendyIV = findViewById(R.id.trendyImageView);
        ImageView aioraIM = findViewById(R.id.aioraImageView);

        Picasso.with(this)
                .load(R.drawable.aiora_logo)
                .resize(500, 500)
                .centerInside()
                .into(aioraIM);

        Picasso.with(this)
                .load(R.drawable.app_logo)
                .resize(500, 500)
                .centerInside()
                .into(trendyIV);

        TextView getInTouch = findViewById(R.id.get_in_touch_team_text_view);
        getInTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(about.this,ContactTeam.class));
            }
        });
    }
}
