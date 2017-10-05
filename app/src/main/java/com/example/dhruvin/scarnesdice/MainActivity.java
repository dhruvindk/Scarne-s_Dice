package com.example.dhruvin.scarnesdice;

import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private int yourOverAllScore = 0;
    private int yourTurnScore;
    private int compOverAllScore = 0;
    private int comTurnScore;
    private Random random = new Random();

    ImageView diceView;

    TextView ComputerScoreValue;
    TextView yourScoreValue;
    TextView yourTurnScoreValue;
    TextView yourTurnScoreLable;
    TextView compTurnScoreValue;
    TextView compTurnScoreLable;

    Button rollButton;
    Button holdButton;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollButton = (Button)findViewById(R.id.rollButton);
        holdButton = (Button) findViewById(R.id.holdButton);
        resetButton = (Button) findViewById(R.id.resetButton);

        ComputerScoreValue = (TextView) findViewById(R.id.computerScoreValue);
        yourScoreValue = (TextView) findViewById(R.id.yourScoreValue);
        yourTurnScoreValue = (TextView) findViewById(R.id.yourTurnScoreValue);
        yourTurnScoreLable = (TextView) findViewById(R.id.yourTurnScoreLable);
        compTurnScoreLable = (TextView) findViewById(R.id.compTurnScoreLable);
        compTurnScoreValue = (TextView) findViewById(R.id.compTurnScoreValue);


        diceView = (ImageView) findViewById(R.id.diceView);


        rollButton.setOnClickListener(this);
        holdButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

        rollingDice(6,1);
        yourTurn();

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rollButton:
                int score = rollingDice(6,1);
                if(score == 1){
                    yourTurnScore = 0;
                    yourTurnScoreValue.setText(String.valueOf(yourTurnScore));
                    Toast.makeText(getApplicationContext(),"You Encounter 1",Toast.LENGTH_SHORT).show();
                    computerTurn();
                }
                else{
                    yourTurnScore+=score;
                    yourTurnScoreValue.setText(String.valueOf(yourTurnScore));
                }
                break;
            case R.id.holdButton:
                yourOverAllScore += yourTurnScore;
                yourScoreValue.setText((String.valueOf(yourOverAllScore)));
                if(yourOverAllScore >= 100){
                    Toast.makeText(getApplicationContext(),"You Wins! Keep Playing....!!!",Toast.LENGTH_SHORT).show();
                    startNewGame();
                }else {
                    computerTurn();
                }
                break;
            case R.id.resetButton:
                resetGame();
                break;
        }
    }

    private void computerTurn() {
        Toast.makeText(getApplicationContext(),"Computer Turn",Toast.LENGTH_SHORT).show();
        comTurnScore = 0;
        compTurnScoreValue.setText(String.valueOf(comTurnScore));
        rollButton.setEnabled(false);
        holdButton.setEnabled(false);
        resetButton.setEnabled(false);
        yourTurnScoreValue.setVisibility(View.INVISIBLE);
        yourTurnScoreLable.setVisibility(View.INVISIBLE);
        compTurnScoreLable.setVisibility(View.VISIBLE);
        compTurnScoreValue.setVisibility(View.VISIBLE);


        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                computerPlays();
            }
        },1000);
    }

    private void computerPlays() {
        int score;
        boolean mode=false;
        if(!mode) {
            score = rollingDice(6, 1);
        }
        else{
            score = rollingDice(5,2);
        }
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {}
        },1000);
        if(score == 1){
            comTurnScore = 0;
            compTurnScoreValue.setText(String.valueOf(comTurnScore));
            Toast.makeText(getApplicationContext(),"Computer Encounter 1",Toast.LENGTH_SHORT).show();
            yourTurn();
        }
        else {
            comTurnScore += score;
            compTurnScoreValue.setText(String.valueOf(comTurnScore));
            if (compOverAllScore + comTurnScore >= 100) {
                computerHold();
            } else {
                if (comTurnScore >= 15) {
                    computerHold();
                } else {
                    handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            computerPlays();
                        }
                    }, 1000);
                }
            }
        }
    }


    private void computerHold() {
        Toast.makeText(getApplicationContext(),"Computer Holds",Toast.LENGTH_SHORT).show();
        compOverAllScore += comTurnScore;
        ComputerScoreValue.setText(String.valueOf(compOverAllScore));
        if(compOverAllScore >= 100){
            Toast.makeText(getApplicationContext(),"Computer Wins! Keep Trying",Toast.LENGTH_SHORT).show();
            final android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startNewGame();
                }
            },1000);
        }else{
            final android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    yourTurn();;
                }
            },1000);
        }
    }

    private void startNewGame() {
        Toast.makeText(getApplicationContext(),"Starting New Game.....!!!",Toast.LENGTH_SHORT).show();
        rollingDice(6,1);
        resetGame();
    }

    private void resetGame() {
        compOverAllScore = 0;
        yourOverAllScore = 0;
        ComputerScoreValue.setText(String.valueOf(compOverAllScore));
        yourScoreValue.setText(String.valueOf(yourOverAllScore));
        yourTurn();
    }


    private void yourTurn() {
        Toast.makeText(getApplicationContext(),"Your Turn",Toast.LENGTH_SHORT).show();
        yourTurnScore = 0;
        yourTurnScoreValue.setText(String.valueOf(yourTurnScore));
        yourTurnScoreValue.setVisibility(View.VISIBLE);
        yourTurnScoreLable.setVisibility(View.VISIBLE);
        compTurnScoreLable.setVisibility(View.INVISIBLE);
        compTurnScoreValue.setVisibility(View.INVISIBLE);

        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
        resetButton.setEnabled(true);
    }
    private int rollingDice(int x,int y) {
        int diceFront = random.nextInt(x)+y;
        switch (diceFront){
            case 1:
                diceView.setImageResource(R.drawable.dice1);
                break;
            case 2:
                diceView.setImageResource(R.drawable.dice2);
                break;
            case 3:
                diceView.setImageResource(R.drawable.dice3);
                break;
            case 4:
                diceView.setImageResource(R.drawable.dice4);
                break;
            case 5:
                diceView.setImageResource(R.drawable.dice5);
                break;
            case 6:
                diceView.setImageResource(R.drawable.dice6);
                break;
        }
        return diceFront;
    }







}











































/*
    public int user_overall_score=0;
    public int user_turn_score=0;
    public int computer_overall_score=0;
    public int computer_turn_score=0;
    public int temp_current_score_user=0;
    public int temp_current_score_computer=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button roll_button=(Button)findViewById(R.id.roll);
        roll_button.setOnClickListener(roll_event);

        Button hold_button=(Button)findViewById(R.id.hold);
        hold_button.setOnClickListener(hold_event);

        Button reset_button=(Button)findViewById(R.id.reset);
        reset_button.setOnClickListener(reset_event);
    }

    private View.OnClickListener reset_event=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            computer_overall_score=0;
            user_overall_score=0;
            temp_current_score_user=0;
            temp_current_score_computer=0;
            TextView fake1=(TextView)findViewById(R.id.dis_current_score_computer);
            fake1.setText(String.valueOf(temp_current_score_computer));
            TextView temp_cu_score=(TextView)findViewById(R.id.dis_current_score_user);
            temp_cu_score.setText(String.valueOf(temp_current_score_user));
            TextView user_score=(TextView)findViewById(R.id.dis_yourscore);
            user_score.setText(String.valueOf(computer_overall_score));
            TextView com_score=(TextView)findViewById(R.id.dis_computerscore);
            com_score.setText(String.valueOf(computer_overall_score));
        }
    };
    private View.OnClickListener hold_event=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            user_overall_score=user_overall_score+temp_current_score_user;
            temp_current_score_user=0;
            TextView fake=(TextView)findViewById(R.id.dis_current_score_user);
            fake.setText(String.valueOf(temp_current_score_user));
            fake=(TextView)findViewById(R.id.dis_yourscore);
            fake.setText(String.valueOf(user_overall_score));
            computer_event();
            fake=(TextView)findViewById(R.id.dis_current_score_computer);
            fake.setText(String.valueOf(0));
        }
    };

    private View.OnClickListener roll_event = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this,"User Turn",Toast.LENGTH_SHORT).show();
            userroll();
        }
    };

    public void userroll(){
        user_turn_score=0;
        android.os.Handler handler=new android.os.Handler();
        final Toast toast=Toast.makeText(MainActivity.this,"User Turn",Toast.LENGTH_SHORT);
        toast.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        },3000);
        int dice_number=new Random().nextInt(6)+1;
        TextView comp_score=(TextView)findViewById(R.id.dis_yourscore);
        ImageView image=(ImageView)findViewById(R.id.imageView);
        user_turn_score=dice_number;
        //long mili=1500;
        if(user_overall_score<100){

            switch (dice_number){
                case 1:
                    image.setImageResource(R.drawable.dice1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            computer_event();

                        }
                    },2000);
                    break;
                case 2:
                    image.setImageResource(R.drawable.dice2);
                    break;
                case 3:
                    image.setImageResource(R.drawable.dice3);
                    break;
                case 4:
                    image.setImageResource(R.drawable.dice4);
                    break;
                case 5:
                    image.setImageResource(R.drawable.dice5);
                    break;
                case 6:
                    image.setImageResource(R.drawable.dice6);
                    break;
            }
            comp_score.setText(String.valueOf(user_overall_score));
            temp_current_score_user=temp_current_score_user+dice_number;
            TextView turn_score=(TextView)findViewById(R.id.dis_current_score_user);
            turn_score.setText(String.valueOf(temp_current_score_user));
        }
        else {
            Toast.makeText(MainActivity.this,"You have won the game",Toast.LENGTH_SHORT).show();
        }
    }

    public void computer_event(){
        computer_turn_score=0;
        Toast.makeText(MainActivity.this,"Computer Turn",Toast.LENGTH_SHORT).show();
        int dice_number=new Random().nextInt(6)+1;
        TextView your_score=(TextView)findViewById(R.id.dis_computerscore);
        ImageView image=(ImageView)findViewById(R.id.imageView);
        image.setImageResource(R.drawable.dice1);
        computer_turn_score=dice_number;


        if(user_overall_score<100){

            switch (dice_number){
                case 1:
                    image.setImageResource(R.drawable.dice1);
                    userroll();
                    break;
                case 2:
                    image.setImageResource(R.drawable.dice2);
                    break;
                case 3:
                    image.setImageResource(R.drawable.dice3);
                    break;
                case 4:
                    image.setImageResource(R.drawable.dice4);
                    break;
                case 5:
                    image.setImageResource(R.drawable.dice5);
                    break;
                case 6:
                    image.setImageResource(R.drawable.dice6);
                    break;
            }
            temp_current_score_computer=temp_current_score_computer+dice_number;
            computer_overall_score=computer_turn_score+computer_overall_score;
            TextView fake1=(TextView)findViewById(R.id.dis_current_score_computer);
            fake1.setText(String.valueOf(temp_current_score_computer));
            your_score.setText(String.valueOf(computer_overall_score));
        }
        else {
            Toast.makeText(MainActivity.this,"You have won the game",Toast.LENGTH_SHORT).show();
        }


    }
}*/


