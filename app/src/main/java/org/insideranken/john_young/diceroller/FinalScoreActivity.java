package org.insideranken.john_young.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalScoreActivity extends AppCompatActivity {


    //Program widget variables
    TextView tvFinalScore;
    TextView tvHighScore;
    TextView tvCategoryOutput;
    TextView tvScoreOutput;
    Button btnNewGame;

    //Program non-widget variables
    int highScore = 0;
    String categoryStr = "";
    String scoreStr = "";
    String finalScoreStr = "";
    String highScoreStr = "";
    String [] categoryArray = new String[] {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes",
                                            "Bonus", "Three of a Kind", "Four of a Kind", "Full House", "Small Straight", "Large Straight",
                                            "Five of a Kind", "Chance", "Five of a Kind Bonus", "Upper Section Total", "Lower Section Total", "Grand Total" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        tvFinalScore        = findViewById(R.id.tvFinalScore);
        tvHighScore        = findViewById(R.id.tvHighScore);
        tvCategoryOutput      = findViewById(R.id.tvCategoryOutput);
        tvScoreOutput      = findViewById(R.id.tvScoreOutput);
        btnNewGame    = findViewById(R.id.btnNewGame);


        Intent finalScoreIntent = getIntent();
        int[] scoreArray = finalScoreIntent.getIntArrayExtra("scoreArray");

        finalScoreStr = "Final Score: " + scoreArray[17];

        // Get from the SharedPreferences
        SharedPreferences prefs = getSharedPreferences("PreferencesName", MODE_PRIVATE);
        highScore = prefs.getInt("highScore", 0); // 0 is default

        if (scoreArray[17] > highScore)
        {
            highScore = scoreArray[17];
            highScoreStr = "NEW HIGH SCORE:  " + highScore + "!!!";
        }
        else
        {
            highScoreStr = "High Score:  " + highScore;
        }

        for (int i = 0; i < categoryArray.length; i++) {
            categoryStr += "\n" + categoryArray[i];
        }

        for (int i = 0; i < scoreArray.length; i++) {
            scoreStr += "\n" + scoreArray[i];
        }

        tvFinalScore.setText(finalScoreStr);
        tvHighScore.setText(highScoreStr);
        tvCategoryOutput.setText(categoryStr);
        tvScoreOutput.setText(scoreStr);

        SharedPreferences.Editor editor = getSharedPreferences("PreferencesName", MODE_PRIVATE).edit();
        editor.putInt("highScore", highScore);
        editor.apply();

        btnNewGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
