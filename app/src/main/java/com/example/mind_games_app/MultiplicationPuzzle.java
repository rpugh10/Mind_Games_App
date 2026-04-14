package com.example.mind_games_app;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiplicationPuzzle extends AppCompatActivity {
    TextView timerText, difficultyText, pointsText, questionNumText, equationText;
    Button exitButton, startButton;
    EditText answerText;
    int timer;
    int difficulty;

    int num1;
    int num2;
    int num3;

    int answer;
    int questionNum;
    int points;
    int highScore;
    int operator;
    int hardMissingNum;
    double missingNumRand;


    boolean missedQuestion;
    boolean inBetweenQuestions;
    boolean gameLooping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_multiplication_puzzle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // get views
        timerText = findViewById(R.id.timerText);
        difficultyText = findViewById(R.id.difficultyText);
        pointsText = findViewById(R.id.pointsText);
        questionNumText = findViewById(R.id.questionNumText);
        equationText = findViewById(R.id.equationText);
        exitButton = findViewById(R.id.exitButton);
        startButton = findViewById(R.id.startButton);
        answerText = findViewById(R.id.answerText);

        difficulty = 3;

        gameLooping = false;

        if (difficulty == 1) {
            difficultyText.setText("Easy");
        }
        else if (difficulty == 2) {
            difficultyText.setText("Medium");
        }
        else {
            difficultyText.setText("Hard");
        }

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SET RETURN TO MENU INTENT HERE
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameLooping == false) {
                    gameLooping = true;
                    questionNum = 1;
                    points = 0;
                    missedQuestion = true;
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    pointsText.setText(points + "");
                    questionNumText.setText("Question Number: 1/10");
                    // start easy mode game loop
                    if (difficulty == 1) {
                        // set timer thread
                        Runnable timerTask = new Runnable() {
                            @Override
                            public void run() {

                                missedQuestion = true;
                                inBetweenQuestions = false;
                                answer = 0;

                                // generate the question
                                num1 = (int)((Math.random() * 9) + 1);
                                num2 = (int)((Math.random() * 9) + 1);
                                num3 = num1 * num2;
                                equationText.setText(num1 + " * " + num2 + " = " + "?");

                                timer = 20;

                                while (timer > -1) {
                                    timerText.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (timer > 9) {
                                                timerText.setText("0:" + timer);
                                            }
                                            else {
                                                timerText.setText("0:0" + timer);
                                            }
                                        }
                                    });
                                    try {
                                        Thread.sleep(1000);
                                        timer--;
                                    } catch (InterruptedException e) {
                                        Log.i("thread","thread interrupted");
                                    }
                                    if (timer == 0) {
                                        inBetweenQuestions = true;
                                        if (missedQuestion) {
                                            points = points - 5;
                                        }
                                        pointsText.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                pointsText.setText(points + "");
                                            }
                                        });
                                        equationText.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                equationText.setText(num1 + " * " + num2 + " = " + num3);
                                            }
                                        });
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            Log.i("thread","thread interrupted");
                                        }
                                        if (questionNum < 10) {
                                            questionNum++;
                                            questionNumText.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    questionNumText.setText("Question Number: " + questionNum + "/10");
                                                }
                                            });
                                            run();
                                        }
                                        else {
                                            gameLooping = false;
                                            if (highScore < points) {
                                                highScore = points;
                                            }
                                            // SHOW THE GAME RESULT SCREEN HERE
                                        }
                                    }
                                }
                            }
                        };
                        executor.execute(timerTask);
                    }
                    // start medium mode game loop
                    else if (difficulty == 2) {
                        // set timer thread
                        Runnable timerTask = new Runnable() {
                            @Override
                            public void run() {

                                missedQuestion = true;
                                inBetweenQuestions = false;
                                answer = 0;

                                // generate the question
                                num1 = (int)((Math.random() * 19) + 1);
                                num2 = (int)((Math.random() * 19) + 1);
                                if (Math.random() > 0.5) {
                                    operator = 0;
                                    num3 = num1 / num2;
                                    equationText.setText(num1 + " / " + num2 + " = " + "?");
                                }
                                else {
                                    operator = 1;
                                    num3 = num1 * num2;
                                    equationText.setText(num1 + " * " + num2 + " = " + "?");
                                }

                                timer = 12;

                                while (timer > -1) {
                                    timerText.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (timer > 9) {
                                                timerText.setText("0:" + timer);
                                            }
                                            else {
                                                timerText.setText("0:0" + timer);
                                            }
                                        }
                                    });
                                    try {
                                        Thread.sleep(1000);
                                        timer--;
                                    } catch (InterruptedException e) {
                                        Log.i("thread","thread interrupted");
                                    }
                                    if (timer == 0) {
                                        inBetweenQuestions = true;
                                        if (missedQuestion) {
                                            points = points - 5;
                                        }
                                        pointsText.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                pointsText.setText(points + "");
                                            }
                                        });
                                        equationText.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (operator == 1) {
                                                    equationText.setText(num1 + " * " + num2 + " = " + num3);
                                                }
                                                else {
                                                    equationText.setText(num1 + " / " + num2 + " = " + num3);
                                                }
                                            }
                                        });
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            Log.i("thread","thread interrupted");
                                        }
                                        if (questionNum < 10) {
                                            questionNum++;
                                            questionNumText.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    questionNumText.setText("Question Number: " + questionNum + "/10");
                                                }
                                            });
                                            run();
                                        }
                                        else {
                                            gameLooping = false;
                                            if (highScore < points) {
                                                highScore = points;
                                            }
                                            // SHOW THE GAME RESULT SCREEN HERE
                                        }
                                    }
                                }
                            }
                        };
                        executor.execute(timerTask);
                    }
                    // start hard mode game loop
                    else {
                        // set timer thread
                        Runnable timerTask = new Runnable() {
                            @Override
                            public void run() {

                                missedQuestion = true;
                                inBetweenQuestions = false;
                                answer = 0;

                                // generate the question
                                num1 = (int)((Math.random() * 49) + 1);
                                num2 = (int)((Math.random() * 49) + 1);
                                missingNumRand = Math.random();
                                if (missingNumRand > 0.7) {
                                    hardMissingNum = 1;
                                }
                                else if (missingNumRand < 0.7 && missingNumRand > 0.3) {
                                    hardMissingNum = 2;
                                }
                                else {
                                    hardMissingNum = 3;
                                }
                                if (Math.random() > 0.5) {
                                    operator = 0;
                                    num3 = num1 / num2;
                                    if (hardMissingNum == 1) {
                                        equationText.setText("?" + " / " + num2 + " = " + num3);
                                    }
                                    else if (hardMissingNum == 2) {
                                        equationText.setText(num1 + " / " + "?" + " = " + num3);
                                    }
                                    else {
                                        equationText.setText(num1 + " / " + num2 + " = " + "?");
                                    }
                                }
                                else {
                                    operator = 1;
                                    num3 = num1 * num2;
                                    if (hardMissingNum == 1) {
                                        equationText.setText("?" + " * " + num2 + " = " + num3);
                                    }
                                    else if (hardMissingNum == 2) {
                                        equationText.setText(num1 + " * " + "?" + " = " + num3);
                                    }
                                    else {
                                        equationText.setText(num1 + " * " + num2 + " = " + "?");
                                    }
                                }

                                timer = 7;

                                while (timer > -1) {
                                    timerText.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (timer > 9) {
                                                timerText.setText("0:" + timer);
                                            }
                                            else {
                                                timerText.setText("0:0" + timer);
                                            }
                                        }
                                    });
                                    try {
                                        Thread.sleep(1000);
                                        timer--;
                                    } catch (InterruptedException e) {
                                        Log.i("thread","thread interrupted");
                                    }
                                    if (timer == 0) {
                                        inBetweenQuestions = true;
                                        if (missedQuestion) {
                                            points = points - 5;
                                        }
                                        pointsText.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                pointsText.setText(points + "");
                                            }
                                        });
                                        equationText.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (operator == 1) {
                                                    equationText.setText(num1 + " * " + num2 + " = " + num3);
                                                }
                                                else {
                                                    equationText.setText(num1 + " / " + num2 + " = " + num3);
                                                }
                                            }
                                        });
                                        try {
                                            Thread.sleep(3000);
                                        } catch (InterruptedException e) {
                                            Log.i("thread","thread interrupted");
                                        }
                                        if (questionNum < 10) {
                                            questionNum++;
                                            questionNumText.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    questionNumText.setText("Question Number: " + questionNum + "/10");
                                                }
                                            });
                                            run();
                                        }
                                        else {
                                            gameLooping = false;
                                            if (highScore < points) {
                                                highScore = points;
                                            }
                                            // SHOW THE GAME RESULT SCREEN HERE
                                        }
                                    }
                                }
                            }
                        };
                        executor.execute(timerTask);
                    }
                    executor.shutdown();
                }
            }
        });

        answerText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (!inBetweenQuestions) {
                        missedQuestion = false;
                        if (!answerText.getText().toString().isEmpty()) {
                            answer = Integer.parseInt(answerText.getText().toString());
                        }
                        timer = 1;

                        if (difficulty != 3) {
                            if (answer == num3) {
                                points = points + 10;
                            }
                        }
                        else {
                            if (hardMissingNum == 1) {
                                if (answer == num1) {
                                    points = points + 10;
                                }
                            }
                            else if (hardMissingNum == 2) {
                                if (answer == num2) {
                                    points = points + 10;
                                }
                            }
                            else {
                                if (answer == num3) {
                                    points = points + 10;
                                }
                            }
                        }
                    }


                    return true;
                }
                return false;
            }
        });

    }
}