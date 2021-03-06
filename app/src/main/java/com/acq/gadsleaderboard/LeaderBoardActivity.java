package com.acq.gadsleaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.acq.gadsleaderboard.ui.main.SectionsPagerAdapter;

public class LeaderBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ViewPager viewPager = findViewById(R.id.view_pager);

        Button submitButton = findViewById(R.id.button_submit_route);

        submitButton.setOnClickListener(view -> startActivity(new Intent(LeaderBoardActivity.this, SubmitActivity.class)));

        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
}