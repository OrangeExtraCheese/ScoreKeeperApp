package tomaszmarzec.udacity.android.scorekeeperapp;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


public class MainActivity extends AppCompatActivity
{
/*    What is a good solution/practise here? In this one I'm declaring global object variables, referencing buttons and TextViews. These globals are then accesed from methods, like aFoulsIncrease().
     They are initialized in onCreate() method.
     The other solution is to use this variables as local ones, inside methods, but then there would be many methods for displaying changes, for example, instead of single
    public void display (int numb, TextView textView)
    {
        textView.setText(""+numb);
    }
    there would be many:
    public void displayTeamAScore (int numb)
    {
        TextView txtTeamAScore = findViewById(R.id.team_a_score);
        txtTeamAScore.setText(""+numb);
    }

    Also, variables referencing buttons and TextViews will be created many times, for each method call, if I'm correct. When they are global they are declared and initialized once.
    On the other hand, I ecountered advices to avoid global variables.

    */

    Button btnTeamASnitch;
    Button btnTeamBSnitch;
    Button btnTeamAGoal;
    Button btnTeamBGoal;
    Button btnTeamAFoul;
    Button btnTeamBFoul;

    TextView txtTeamAScore;
    TextView txtTeamBScore;
    TextView txtTeamAFouls;
    TextView txtTeamBFouls;

    int aScore = 0;
    int bScore = 0;
    int aFouls = 0;
    int bFouls = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTeamAScore =  findViewById(R.id.team_a_score);
        txtTeamBScore =  findViewById(R.id.team_b_score);
        txtTeamAFouls =  findViewById(R.id.team_a_fouls);
        txtTeamBFouls =  findViewById(R.id.team_b_fouls);

        btnTeamAGoal = findViewById(R.id.button_goalA);
        btnTeamBGoal = findViewById(R.id.button_goalB);
        btnTeamAFoul = findViewById(R.id.button_foulA);
        btnTeamBFoul = findViewById(R.id.button_foulB);
        btnTeamASnitch = findViewById(R.id.snitchA);
        btnTeamBSnitch = findViewById(R.id.snitchB);

        renderImages();

        // The whichTeam integer is an argument used by method createAlertListener which of these two methods: aCaughtSnitch and bCaughtSnitch, should be bound to positive button of alert dialog.
        // Alert dialog is the same for both buttons, but the outcome of clicking on positive button should be different, whether the alert dialog was called by button for team A or team B.
            btnTeamASnitch.setOnClickListener(createAlertListener(0));
            btnTeamBSnitch.setOnClickListener(createAlertListener(1));
    }

    private View.OnClickListener createAlertListener(final int whichTeam)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMessage(R.string.dialog_msg);
                if(whichTeam == 0)
                {
                    mBuilder.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            aCaughtSnitch();
                            dialog.dismiss();
                        }
                    });
                }
                if(whichTeam == 1)
                {
                    mBuilder.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            bCaughtSnitch();
                            dialog.dismiss();
                        }
                    });
                }

                mBuilder.setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
                mBuilder.setCancelable(false);

                AlertDialog alertDialog = mBuilder.create();
                alertDialog.show();
            }
        };
    }

    private void renderImages()
    {
        final ConstraintLayout rootView = findViewById(R.id.rootView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //Code based on StackOverflow topic: https://stackoverflow.com/questions/33971626/set-background-image-to-relative-layout-using-glide-in-android/38025862
        // Answer by Chintan Desai
        Glide.with(this).load(R.drawable.field).asBitmap().into(new SimpleTarget<Bitmap>(displayMetrics.widthPixels, displayMetrics.heightPixels)
        {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation)
            {
                Drawable drawable = new BitmapDrawable(resource);
                rootView.setBackground(drawable);
            }
        });
    }

    public void aScoreIncrease(View view)
    {
        aScore+=10;
        display(aScore, txtTeamAScore);
    }
    public void aFoulsIncrease(View view)
    {
        aFouls++;
        display(aFouls, txtTeamAFouls);
    }
    public void bScoreIncrease(View view)
    {
        bScore+=10;
        display(bScore, txtTeamBScore);
    }
    public void bFoulsIncrease(View view)
    {
        bFouls++;
        display(bFouls, txtTeamBFouls);
    }

    public void display (int numb, TextView textView)
    {
        textView.setText(""+numb);
    }

    public void aCaughtSnitch()
    {
        aScore+=150;
        display(aScore, txtTeamAScore);
        buttonsOn(false);
    }

    public void bCaughtSnitch()
    {
        bScore+=150;
        display(bScore, txtTeamBScore);
        buttonsOn(false);
    }

    private void buttonsOn(boolean flag)
    {
        btnTeamASnitch.setEnabled(flag);
        btnTeamBSnitch.setEnabled(flag);
        btnTeamAGoal.setEnabled(flag);
        btnTeamBGoal.setEnabled(flag);
        btnTeamAFoul.setEnabled(flag);
        btnTeamBFoul.setEnabled(flag);
    }

    public void reset(View view)
    {
        aScore=0;
        display(aScore, txtTeamAScore);
        aFouls=0;
        display(aFouls, txtTeamAFouls);
        bScore=0;
        display(bScore, txtTeamBScore);
        bFouls=0;
        display(bFouls, txtTeamBFouls);

        buttonsOn(true);
    }

}



