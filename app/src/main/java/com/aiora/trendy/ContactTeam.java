package com.aiora.trendy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ContactTeam extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_team);

        LinearLayout kevinFacebook = findViewById(R.id.kevin_facebook);
        LinearLayout kevinInsta = findViewById(R.id.kevin_insta);
        LinearLayout sidFacebook = findViewById(R.id.sid_facebook);
        LinearLayout sidInsta = findViewById(R.id.sid_insta);
        LinearLayout harshFacebook = findViewById(R.id.harsh_facebook);
        LinearLayout harshInsta = findViewById(R.id.harsh_insta);

        kevinFacebook.setOnClickListener(this);
        kevinInsta.setOnClickListener(this);
        sidFacebook.setOnClickListener(this);
        sidInsta.setOnClickListener(this);
        harshFacebook.setOnClickListener(this);
        harshInsta.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.kevin_facebook:
                openFacebook("https://www.facebook.com/profile.php?id=100002521572279");
                break;
            case R.id.kevin_insta:
                openInsta("https://www.instagram.com/valanikevin/");
                break;
            case R.id.sid_facebook:
                openFacebook("https://www.facebook.com/profile.php?id=100003359891717");
                break;
            case R.id.sid_insta:
                openInsta("https://www.instagram.com/siddique_as/");
                break;
            case R.id.harsh_facebook:
                openFacebook("https://www.facebook.com/profile.php?id=100005832402121");
                break;
            case R.id.harsh_insta:
                openInsta("https://www.instagram.com/harsh_patel_99/");
                break;
        }
    }

    public void openFacebook(String url){
        Intent facebookIntent = getOpenFacebookIntent(this,url);
        startActivity(facebookIntent);
    }

    public static Intent getOpenFacebookIntent(Context context, String url) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url)); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url)); //catches and opens a url to the desired page
        }
    }

    public void openInsta(String url){
        Uri uri = Uri.parse(url);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url)));
        }
    }

}
