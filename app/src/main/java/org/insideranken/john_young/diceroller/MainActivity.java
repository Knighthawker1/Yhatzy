package org.insideranken.john_young.diceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.Toast;


import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final String DEFAULTSCORE = "00";
    //Should be 14 be default. Only change to make the game faster for testing.
    final int ROUNDSPERGAME = 14;

    //program widget variables
    TextView tvRoll;
    TextView tvRound;
    TextView tvDice0;
    TextView tvDice1;
    TextView tvDice2;
    TextView tvDice3;
    TextView tvDice4;
    TextView tvOnesScore;
    TextView tvTwosScore;
    TextView tvThreesScore;
    TextView tvFoursScore;
    TextView tvFivesScore;
    TextView tvSixesScore;
    TextView tvThreeKindScore;
    TextView tvFourKindScore;
    TextView tvFullHouseScore;
    TextView tvSmallStraightScore;
    TextView tvLargeStraightScore;
    TextView tvFiveKindScore;
    TextView tvChanceScore;

    ImageView ivDice0;
    ImageView ivDice1;
    ImageView ivDice2;
    ImageView ivDice3;
    ImageView ivDice4;

    CheckBox cbDice0;
    CheckBox cbDice1;
    CheckBox cbDice2;
    CheckBox cbDice3;
    CheckBox cbDice4;

    Button btnRollAll;
    Button btnRollSelected;
    Button btnScore;
    Button btnRules;

    RadioGroup rgCategory;
    RadioButton rbOnes;
    RadioButton rbTwos;
    RadioButton rbThrees;
    RadioButton rbFours;
    RadioButton rbFives;
    RadioButton rbSixes;
    RadioButton rbThreeKind;
    RadioButton rbFourKind;
    RadioButton rbFullHouse;
    RadioButton rbSmallStraight;
    RadioButton rbLargeStraight;
    RadioButton rbFiveKind;
    RadioButton rbChance;

    Toast t;

    //Program non-widget variables
    //scoreArray should hold a value for each category in order, and two value at the end for bonuses.
    int[] scoreArray = new int[18];
    int[] rollArray = new int[5];
    String [] categoryArray = new String[] {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes",
            "Bonus", "Three of a Kind", "Four of a Kind", "Full House", "Small Straight", "Large Straight",
            "Five of a Kind", "Chance", "Five of a Kind Bonus", "Upper Section Total", "Lower Section Total", "Grand Total" };

    String contentDesc = "";
    String labelStr = "";
    Integer rollCount  = 0;
    Integer roundCount = 1;
    boolean validChoice = false;
    boolean newGame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRoll                = findViewById(R.id.tvRoll);
        tvRound               = findViewById(R.id.tvRound);
        tvDice0               = findViewById(R.id.tvDice0);
        tvDice1               = findViewById(R.id.tvDice1);
        tvDice2               = findViewById(R.id.tvDice2);
        tvDice3               = findViewById(R.id.tvDice3);
        tvDice4               = findViewById(R.id.tvDice4);
        tvOnesScore           = findViewById(R.id.tvOnesScore);
        tvTwosScore           = findViewById(R.id.tvTwosScore);
        tvThreesScore         = findViewById(R.id.tvThreesScore);
        tvFoursScore          = findViewById(R.id.tvFoursScore);
        tvFivesScore          = findViewById(R.id.tvFivesScore);
        tvSixesScore          = findViewById(R.id.tvSixesScore);
        tvThreeKindScore      = findViewById(R.id.tvThreeKindScore);
        tvFourKindScore       = findViewById(R.id.tvFourKindScore);
        tvFullHouseScore      = findViewById(R.id.tvFullHouseScore);
        tvSmallStraightScore  = findViewById(R.id.tvSmallStraightScore);
        tvLargeStraightScore  = findViewById(R.id.tvLargeStraightScore);
        tvFiveKindScore       = findViewById(R.id.tvFiveKindScore);
        tvChanceScore         = findViewById(R.id.tvChanceScore);

        ivDice0 = findViewById(R.id.ivDice0);
        ivDice1 = findViewById(R.id.ivDice1);
        ivDice2 = findViewById(R.id.ivDice2);
        ivDice3 = findViewById(R.id.ivDice3);
        ivDice4 = findViewById(R.id.ivDice4);

        cbDice0  = findViewById(R.id.cbDice0);
        cbDice1  = findViewById(R.id.cbDice1);
        cbDice2  = findViewById(R.id.cbDice2);
        cbDice3  = findViewById(R.id.cbDice3);
        cbDice4  = findViewById(R.id.cbDice4);

        btnRollAll       = findViewById(R.id.btnRollAll);
        btnRollSelected  = findViewById(R.id.btnRollSelected);
        btnScore         = findViewById(R.id.btnScore);
        btnRules         = findViewById(R.id.btnRules);


        rgCategory       = findViewById(R.id.rgCategory);
        rbOnes           = findViewById(R.id.rbOnes);
        rbTwos           = findViewById(R.id.rbTwos);
        rbThrees         = findViewById(R.id.rbThrees);
        rbFours          = findViewById(R.id.rbFours);
        rbFives          = findViewById(R.id.rbFives);
        rbSixes          = findViewById(R.id.rbSixes);
        rbThreeKind      = findViewById(R.id.rbThreeKind);
        rbFourKind       = findViewById(R.id.rbFourKind);
        rbFullHouse      = findViewById(R.id.rbFullHouse);
        rbSmallStraight  = findViewById(R.id.rbSmallStraight);
        rbLargeStraight  = findViewById(R.id.rbLargeStraight);
        rbFiveKind       = findViewById(R.id.rbFiveKind);
        rbChance         = findViewById(R.id.rbChance);


        btnRollAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rollAllDice();
            }
        });

        btnRollSelected.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                rollSelectedDice();
            }
        });

        btnScore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                calculateScore();
            }
        });

        btnRules.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                displayRules();
            }
        });
    }

    private void displayRoll()
    {
        cbDice0.setChecked(false);
        cbDice1.setChecked(false);
        cbDice2.setChecked(false);
        cbDice3.setChecked(false);
        cbDice4.setChecked(false);

        Arrays.sort(rollArray);

        String imageName = "";
        for (int i = 0; i < rollArray.length; i++) {
            if (rollCount == 0)
            {
                imageName = "dice_default";
                contentDesc = "Dice not yet rolled.";
                labelStr = "?";
            }
            else
            {
                imageName = "dice_" + Integer.toString(rollArray[i]);
                contentDesc = "Die " + Integer.toString(i) + " is "  + Integer.toString(rollArray[i]) + ".";
                labelStr = Integer.toString(rollArray[i]);
            }
            Resources resources = getResources();
            final int resourceId = resources.getIdentifier(imageName,
                    "drawable", getPackageName());

            switch (i)
            {
                case 0:
                    ivDice0.setImageResource(resourceId);
                    ivDice0.setContentDescription(contentDesc);
                    tvDice0.setText(labelStr);
                    break;

                case 1:
                    ivDice1.setImageResource(resourceId);
                    ivDice0.setContentDescription(contentDesc);
                    tvDice1.setText(labelStr);
                    break;

                case 2:
                    ivDice2.setImageResource(resourceId);
                    ivDice0.setContentDescription(contentDesc);
                    tvDice2.setText(labelStr);
                    break;

                case 3:
                    ivDice3.setImageResource(resourceId);
                    ivDice0.setContentDescription(contentDesc);
                    tvDice3.setText(labelStr);
                    break;

                case 4:
                    ivDice4.setImageResource(resourceId);
                    ivDice0.setContentDescription(contentDesc);
                    tvDice4.setText(labelStr);
                    break;
            }
        }
        switch (rollCount)
        {
            case 0:
                tvRoll.setText("3 Rolls Remaining");
                btnRollSelected.setEnabled(false);
                btnScore.setEnabled(false);
                btnRollAll.setEnabled(true);
                break;

            case 1:
                tvRoll.setText("2 Rolls Remaining");
                btnRollSelected.setEnabled(true);
                btnScore.setEnabled(true);
                break;

            case 2:
                tvRoll.setText("1 Roll Remaining");
                break;

            case 3:
                tvRoll.setText("Out of Rolls");
                btnRollAll.setEnabled(false);
                btnRollSelected.setEnabled(false);
                break;
        }

        tvRound.setText("Round " + roundCount + " of " + (ROUNDSPERGAME - 1) );
        displayScore();
    }

    private void displayScore()
    {
        tvOnesScore.setText(Integer.toString(scoreArray[0]));
        tvTwosScore.setText(Integer.toString(scoreArray[1]));
        tvThreesScore.setText(Integer.toString(scoreArray[2]));
        tvFoursScore.setText(Integer.toString(scoreArray[3]));
        tvFivesScore.setText(Integer.toString(scoreArray[4]));
        tvSixesScore.setText(Integer.toString(scoreArray[5]));
        tvThreeKindScore.setText(Integer.toString(scoreArray[7]));
        tvFourKindScore.setText(Integer.toString(scoreArray[8]));
        tvFullHouseScore.setText(Integer.toString(scoreArray[9]));
        tvSmallStraightScore.setText(Integer.toString(scoreArray[10]));
        tvLargeStraightScore.setText(Integer.toString(scoreArray[11]));
        tvFiveKindScore.setText(Integer.toString(scoreArray[12]));
        tvChanceScore.setText(Integer.toString(scoreArray[13]));
    }

    private int generateRandomNumber()
    {
        Random r = new Random();
        return r.nextInt(7 - 1) + 1;
    }

    private void rollAllDice()
    {
        for (int i = 0; i < rollArray.length; i++) {
            rollArray[i] = generateRandomNumber();
        }
        rollCount++;
        displayRoll();
    }

    private void rollSelectedDice()
    {
        boolean newRoll = false;

        if(cbDice0.isChecked())
        {
            rollArray[0] = generateRandomNumber();
            newRoll = true;
        }
        if(cbDice1.isChecked())
        {
            rollArray[1] = generateRandomNumber();
            newRoll = true;
        }
        if(cbDice2.isChecked())
        {
            rollArray[2] = generateRandomNumber();
            newRoll = true;
        }
        if(cbDice3.isChecked())
        {
            rollArray[3] = generateRandomNumber();
            newRoll = true;
        }
        if(cbDice4.isChecked())
        {
            rollArray[4] = generateRandomNumber();
            newRoll = true;
        }
        if(newRoll)
        {
            rollCount++;
            displayRoll();
        }
        else
        {
            displayToast("No dice selected.");
        }
    }

    private void calculateScore()
    {
        validChoice = false;


        if(rbOnes.isChecked())
        {
            scoreArray[0] = calculateUpperSection(1);
            scoreArray[14] += calculateFiveKind();
            rbOnes.setEnabled(false);
            rbOnes.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[0] + " - " + scoreArray[0] + " points");
        }
        else if(rbTwos.isChecked())
        {
            scoreArray[1] = calculateUpperSection(2);
            scoreArray[14] += calculateFiveKind();
            rbTwos.setEnabled(false);
            rbTwos.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[1] + " - " + scoreArray[1] + " points");
        }
        else if(rbThrees.isChecked())
        {
            scoreArray[2] = calculateUpperSection(3);
            scoreArray[14] += calculateFiveKind();
            rbThrees.setEnabled(false);
            rbThrees.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[2] + " - " + scoreArray[2] + " points");
        }
        else if(rbFours.isChecked())
        {
            scoreArray[3] = calculateUpperSection(4);
            scoreArray[14] += calculateFiveKind();
            rbFours.setEnabled(false);
            rbFours.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[3] + " - " + scoreArray[3] + " points");
        }
        else if(rbFives.isChecked())
        {
            scoreArray[4] = calculateUpperSection(5);
            scoreArray[14] += calculateFiveKind();
            rbFives.setEnabled(false);
            rbFives.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[4] + " - " + scoreArray[4] + " points");
        }
        else if(rbSixes.isChecked())
        {
            scoreArray[5] = calculateUpperSection(6);
            scoreArray[14] += calculateFiveKind();
            rbSixes.setEnabled(false);
            rbSixes.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[5] + " - " + scoreArray[5] + " points");
        }
        else if(rbThreeKind.isChecked())
        {
            scoreArray[7] = calculateMultipleKind(3);
            scoreArray[14] += calculateFiveKind();
            rbThreeKind.setEnabled(false);
            rbThreeKind.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[7] + " - " + scoreArray[7] + " points");
        }
        else if(rbFourKind.isChecked())
        {
            scoreArray[8] = calculateMultipleKind(4);
            scoreArray[14] += calculateFiveKind();
            rbFourKind.setEnabled(false);
            rbFourKind.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[8] + " - " + scoreArray[8] + " points");
        }
        else if(rbFullHouse.isChecked())
        {
            scoreArray[9] = calculateFullHouse();
            scoreArray[14] += calculateFiveKind();
            rbFullHouse.setEnabled(false);
            rbFullHouse.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[9] + " - " + scoreArray[9] + " points");
        }
        else if(rbSmallStraight.isChecked())
        {
            scoreArray[10] = calculateStraight(true);
            scoreArray[14] += calculateFiveKind();
            rbSmallStraight.setEnabled(false);
            rbSmallStraight.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[10] + " - " + scoreArray[10] + " points");
        }
        else if(rbLargeStraight.isChecked())
        {
            scoreArray[11] = calculateStraight(false);
            scoreArray[14] += calculateFiveKind();
            rbLargeStraight.setEnabled(false);
            rbLargeStraight.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[11] + " - " + scoreArray[11] + " points");
        }
        else if(rbFiveKind.isChecked())
        {
            scoreArray[12] = calculateFiveKind();
            rbFiveKind.setEnabled(false);
            rbFiveKind.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[12] + " - " + scoreArray[12] + " points");
        }
        else if(rbChance.isChecked())
        {
            scoreArray[13] = diceTotal();
            scoreArray[14] += calculateFiveKind();
            rbChance.setEnabled(false);
            rbChance.setChecked(false);
            validChoice = true;
            displayToast(categoryArray[13] + " - " + scoreArray[13] + " points");
        }
        if(validChoice)
        {
            newRound();
        }
        else
        {
            displayToast("No category selected.");
            displayRoll();
        }

    }

    private void newRound()
    {
        roundCount++;
        rollCount = 0;
        btnRollAll.setEnabled(true);
        btnScore.setEnabled(false);
        cbDice0.setChecked(false);
        cbDice1.setChecked(false);
        cbDice2.setChecked(false);
        cbDice3.setChecked(false);
        cbDice4.setChecked(false);
        displayRoll();
        if (roundCount >= ROUNDSPERGAME)
        {
            displayFinalScore();
        }
    }

    private int calculateUpperSection(int x)
    {
        int score = 0;
        for (int i = 0; i < rollArray.length; i++) {
            if(rollArray[i] == x)
            {
                score ++;
            }
        }

        return score * x;
    }

    private int calculateMultipleKind(int min)
    {
        int score = 0;
        int counter = 0;

        for (int x = 1; x < 7; x++)
        {
            counter = 0;
            for (int i = 0; i < rollArray.length; i++) {
                if(rollArray[i] == x)
                {
                    counter++;
                }
            }

            if (counter >= min)
            {
                score = diceTotal();
            }
        }

        return score;
    }

    private int calculateFullHouse()
    {
        int score = 0;
        int counter = 0;
        boolean firstValid = false;
        boolean secondValid = false;

        for (int x = 1; x < 7; x++)
        {
            counter = 0;
            for (int i = 0; i < rollArray.length; i++) {
                if(rollArray[i] == x)
                {
                    counter++;
                }
            }

            if (counter == 3)
            {
                firstValid = true;
            }

            if (counter == 2)
            {
                secondValid = true;
            }
        }

        if ((firstValid) && (secondValid))
        {
            score = 25;
        }

        return score;
    }

    private int calculateStraight(boolean small)
    {
        Arrays.sort(rollArray);
        boolean oneCheck   = false;
        boolean twoCheck   = false;
        boolean threeCheck = false;
        boolean fourCheck  = false;
        boolean fiveCheck  = false;
        boolean sixCheck   = false;
        int score = 0;

        for (int i = 0; i < rollArray.length; i++) {
            if(rollArray[i] == 1)
            {
                oneCheck = true;
            }
            if(rollArray[i] == 2)
            {
                twoCheck = true;
            }
            if(rollArray[i] == 3)
            {
                threeCheck = true;
            }
            if(rollArray[i] == 4)
            {
                fourCheck = true;
            }
            if(rollArray[i] == 5)
            {
                fiveCheck = true;
            }
            if(rollArray[i] == 6)
            {
                sixCheck = true;
            }
        }

        if(small)
        {
            if((oneCheck) && (twoCheck) && (threeCheck) && (fourCheck))
            {
                score = 30;
            }
            if((twoCheck) && (threeCheck) && (fourCheck) && (fiveCheck))
            {
                score = 30;
            }
            if((threeCheck) && (fourCheck) && (fiveCheck) && (sixCheck))
            {
                score = 30;
            }
        }
        if(!small)
        {
            if((oneCheck) && (twoCheck) && (threeCheck) && (fourCheck) && (fiveCheck))
            {
                score = 40;
            }
            if((twoCheck) && (threeCheck) && (fourCheck) && (fiveCheck) && (sixCheck))
            {
                score = 40;
            }
        }

        return score;
    }

    private int calculateFiveKind()
    {

        int score = 0;
        int counter = 0;

        for (int x = 1; x < 7; x++)
        {
            counter = 0;
            for (int i = 0; i < rollArray.length; i++) {
                if(rollArray[i] == x)
                {
                    counter++;
                }
            }

            if (counter == 5)
            {
                if (scoreArray[12] == 0)
                {
                    score = 50;
                }
                else
                {
                    score = 100;
                }
            }
        }

        return score;
    }
    
    private int diceTotal()
    {
        int score = 0;
        for (int i = 0; i < rollArray.length; i++) {
            score += rollArray[i];
        }
        
        return score;
    }

    private void displayRules()
    {
        Uri uriUrl = Uri.parse("https://www.dicegamedepot.com/yahtzee-rules/");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void displayFinalScore()
    {
        for (int i = 0; i < 6; i++) {
            scoreArray[15] += scoreArray[i];
        }
        for (int i = 7; i < 14; i++) {
            scoreArray[16] += scoreArray[i];
        }

        scoreArray[17] = scoreArray[15] + scoreArray[16];

        if(scoreArray[15] >= 63)
        {
            scoreArray[6] = 35;
        }

        newGame = true;

        //Create and initialize an intent
        Intent finalScoreIntent = new Intent(this, FinalScoreActivity.class);

        finalScoreIntent.putExtra("scoreArray", scoreArray);

        //Start the activity
        startActivity(finalScoreIntent);
    }

    private void startNewGame()
    {
        newGame = false;
        roundCount = 0;
        newRound();
        displayRoll();


        for (int i = 0; i < scoreArray.length; i++) {
            scoreArray[i] = 0;
        }

        displayScore();

        rbOnes.setEnabled(true);
        rbTwos.setEnabled(true);
        rbThrees.setEnabled(true);
        rbFours.setEnabled(true);
        rbFives.setEnabled(true);
        rbSixes.setEnabled(true);
        rbThreeKind.setEnabled(true);
        rbFourKind.setEnabled(true);
        rbFullHouse.setEnabled(true);
        rbSmallStraight.setEnabled(true);
        rbLargeStraight.setEnabled(true);
        rbFiveKind.setEnabled(true);
        rbChance.setEnabled(true);
    }

    public void onResume()
    {
        super.onResume();
        if (newGame)
        {
            startNewGame();
        }
    }

    private void displayToast (String outputStr)
    {
        t = Toast.makeText(getApplicationContext(), outputStr, Toast.LENGTH_LONG);
        t.show();
    }
}
