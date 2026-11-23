package com.example.artspace;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ArtworkDetailsActivity extends AppCompatActivity {

    private TextView tvDetailsInfo;
    private Button btnVisitWebsite;
    private Button btnBackToMain;
    private String websiteUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_artwork_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_details), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left + dpToPx(24),
                    systemBars.top + dpToPx(24),
                    systemBars.right + dpToPx(24),
                    systemBars.bottom + dpToPx(24)
            );
            return insets;
        });

        tvDetailsInfo = findViewById(R.id.tvDetailsInfo);
        btnVisitWebsite = findViewById(R.id.btnVisitWebsite);
        btnBackToMain = findViewById(R.id.btnBackToMain);

        Intent incomingIntent = getIntent();
        String title = incomingIntent.getStringExtra("title");
        String artist = incomingIntent.getStringExtra("artist");
        String year = incomingIntent.getStringExtra("year");
        websiteUrl = incomingIntent.getStringExtra("website");

        if (title == null) title = "Unknown Title";
        if (artist == null) artist = "Unknown Artist";
        if (year == null) year = "N/A";
        if (websiteUrl == null || websiteUrl.isEmpty()) {
            websiteUrl = "https://www.artic.edu";
        }

        String detailsText = "Title: " + title + "\n\n"
                + "Artist: " + artist + "\n\n"
                + "Creation Year: " + year;
        tvDetailsInfo.setText(detailsText);

        btnVisitWebsite.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
            startActivity(browserIntent);
        });

        btnBackToMain.setOnClickListener(v -> finish());
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}