package com.example.cst2335.lyrics_ovh;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335.R;

public class LyricsMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyrics_main_activity);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new SearchLyricsFragment())
        .commit();
    }
}
