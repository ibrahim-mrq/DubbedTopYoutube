package com.android.dubbedtop.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.dubbedtop.BuildConfig;
import com.android.dubbedtop.R;

public class AboutActivity extends AppCompatActivity {

    private ImageView imageFacebook, imageWhatsapp, imageTwitter, imageLinkedin,
            imageInstagram, imageGmail, imageYoutube, aboutImageRate, imageShare;
    private TextView aboutTvToolbar, aboutTvYoutube, about_tv_ibrahim;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initView();
        onClick();

        about_tv_ibrahim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWhatsapp("+972592440530");
            }
        });
    }


    private void initView() {
        about_tv_ibrahim = findViewById(R.id.about_tv_ibrahim);
        imageFacebook = findViewById(R.id.image_facebook);
        imageWhatsapp = findViewById(R.id.image_whatsapp);
        imageTwitter = findViewById(R.id.image_twitter);
        imageLinkedin = findViewById(R.id.image_linkedin);
        imageInstagram = findViewById(R.id.image_instagram);
        imageGmail = findViewById(R.id.image_gmail);
        imageYoutube = findViewById(R.id.image_youtube);
        aboutTvToolbar = findViewById(R.id.about_tv_toolbar);
        aboutTvYoutube = findViewById(R.id.about_tv_youtube);
        aboutImageRate = findViewById(R.id.about_image_about);
        imageShare = findViewById(R.id.image_share);
    }

    private void onClick() {
        imageFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFacebook();
            }
        });

        imageWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWhatsapp("+972595577921");
            }
        });

        imageTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTwitter();
            }
        });
        imageLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLinkedin();
            }
        });

        imageInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInstagram();
            }
        });

        imageGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEmail();
            }
        });

        imageYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToYoutube();
            }
        });

        aboutImageRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareMessage = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(shareMessage));
                startActivity(intent);
            }
        });

        aboutTvYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToYoutube();
            }
        });

        imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }

    private void goToYoutube() {
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/channel/UCWNefTdh9RVoRY2xFWprEPA"));
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCWNefTdh9RVoRY2xFWprEPA")));
        }
    }

    private void goToLinkedin() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.linkedin.com/in/mahaghassan/"));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void goToInstagram() {
        Intent intent;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.instagram.android");

            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/mahaghjarour"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Instagram Not Installed!", Toast.LENGTH_LONG).show();
        }
    }

    private void goToTwitter() {
        Intent intent;
        try {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/mahaghjarour"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/mahaghjarour"));
        }
        startActivity(intent);
    }

    private void goToFacebook() {
        Uri uri = Uri.parse("https://www.facebook.com/profile.php?id=100009491841225");
        PackageManager packageManager = getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/profile.php?id=100009491841225");
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void goToEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "mahaa.ghassan@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void goToWhatsapp(String number) {
        String url = "https://api.whatsapp.com/send?phone=" + number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}