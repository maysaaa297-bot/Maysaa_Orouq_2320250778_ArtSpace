package com.example.artspace;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_INDEX = "current_index";

    private TextView tvCounter;
    private ImageView ivArtwork;
    private TextView tvTitle;
    private TextView tvArtistYear;
    private Button btnPrevious;
    private Button btnNext;

    private List<Artwork> artworkList;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left + dpToPx(20),
                    systemBars.top + dpToPx(20),
                    systemBars.right + dpToPx(20),
                    systemBars.bottom + dpToPx(20)
            );
            return insets;
        });

        initViews();
        initArtworks();

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX, 0);
        }

        setupListeners();
        updateUI();
    }

    private void initViews() {
        tvCounter = findViewById(R.id.tvCounter);
        ivArtwork = findViewById(R.id.ivArtwork);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtistYear = findViewById(R.id.tvArtistYear);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
    }

    private void initArtworks() {
        artworkList = new ArrayList<>();
        artworkList.add(new Artwork(
                R.drawable.art_1,
                "Cobalt Archway",
                "Camille Laurent",
                "2020",
                "https://www.louvre.fr"
        ));
        artworkList.add(new Artwork(
                R.drawable.art_2,
                "Golden Blossom Miniature",
                "Parviz Shirazi",
                "2018",
                "https://www.metmuseum.org"
        ));
        artworkList.add(new Artwork(
                R.drawable.art_3,
                "Drifter Under Pale Skies",
                "Rohan Deshmukh",
                "2019",
                "https://www.artic.edu"
        ));
        artworkList.add(new Artwork(
                R.drawable.art_4,
                "Jade Tile & Palm",
                "Ananya Kapoor",
                "2022",
                "https://www.rijksmuseum.nl"
        ));
        artworkList.add(new Artwork(
                R.drawable.art_5,
                "Velvet Dusk Texture",
                "Henrik Solberg",
                "2023",
                "https://www.moma.org"
        ));
        artworkList.add(new Artwork(
                R.drawable.art_6,
                "Medina Color Alley",
                "Youssef Benali",
                "2021",
                "https://www.nationalgallery.org.uk"
        ));
    }

    private void setupListeners() {
        btnNext.setOnClickListener(v -> {
            if (currentIndex < artworkList.size() - 1) {
                currentIndex++;
                updateUI();
            }
        });

        btnPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateUI();
            }
        });

        ivArtwork.setOnClickListener(v -> {
            Artwork currentArtwork = artworkList.get(currentIndex);
            Intent intent = new Intent(MainActivity.this, ArtworkDetailsActivity.class);
            intent.putExtra("title", currentArtwork.getTitle());
            intent.putExtra("artist", currentArtwork.getArtist());
            intent.putExtra("year", currentArtwork.getYear());
            intent.putExtra("website", currentArtwork.getWebsiteUrl());
            startActivity(intent);
        });
    }

    private void updateUI() {
        Artwork currentArtwork = artworkList.get(currentIndex);

        tvCounter.setText((currentIndex + 1) + " / " + artworkList.size());
        ivArtwork.setImageResource(currentArtwork.getImageResId());
        tvTitle.setText(currentArtwork.getTitle());
        tvArtistYear.setText(currentArtwork.getArtist() + " (" + currentArtwork.getYear() + ")");

        btnPrevious.setEnabled(currentIndex > 0);
        btnNext.setEnabled(currentIndex < artworkList.size() - 1);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    private static class Artwork {
        private final int imageResId;
        private final String title;
        private final String artist;
        private final String year;
        private final String websiteUrl;

        public Artwork(int imageResId, String title, String artist, String year, String websiteUrl) {
            this.imageResId = imageResId;
            this.title = title;
            this.artist = artist;
            this.year = year;
            this.websiteUrl = websiteUrl;
        }

        public int getImageResId() {
            return imageResId;
        }

        public String getTitle() {
            return title;
        }

        public String getArtist() {
            return artist;
        }

        public String getYear() {
            return year;
        }

        public String getWebsiteUrl() {
            return websiteUrl;
        }
    }
}