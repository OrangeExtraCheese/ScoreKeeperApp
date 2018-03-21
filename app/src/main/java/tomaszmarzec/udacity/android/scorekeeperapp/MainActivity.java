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
/*    What is a good solution/practise here? In this one I'm declaring global object variables, referencing Buttons and TextViews. They are initialized in onCreate() method.
      These memeber variables are then accesed from methods, like aFoulsIncrease().
     The other solution is to use this variables as local ones, inside methods, but then there would be many methods for displaying changes, for example, instead of single
    public void display (int numb, TextView textView)
    {
        textView.setText(""+numb);
    }
    there would be many like:
    public void displayTeamAScore (int numb)
    {
        TextView txtTeamAScore = findViewById(R.id.team_a_score);
        txtTeamAScore.setText(""+numb);
    }
    On the other hand using member variables is discouraged because of risk of memory leaks. What is a good strategy to handle this matter?
    */

    Button btnTeamASnitch;
    Button btnTeamBSnitch;
    Button btnTeamAGoal;
    Button btnTeamBGoal;
    Button btnTeamAFoul;
    Button btnTeamBFoul;
    Button btnReset;

    TextView txtTeamAScore;
    TextView txtTeamBScore;
    TextView txtTeamAFouls;
    TextView txtTeamBFouls;
    TextView txtTeamAName;
    TextView txtTeamBName;
    TextView txtWinner;

    private int aScore = 0;
    private int bScore = 0;
    private int aFouls = 0;
    private int bFouls = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTeamAScore =  findViewById(R.id.team_a_score);
        txtTeamBScore =  findViewById(R.id.team_b_score);
        txtTeamAFouls =  findViewById(R.id.team_a_fouls);
        txtTeamBFouls =  findViewById(R.id.team_b_fouls);
        txtTeamAName=    findViewById(R.id.team_a_name);
        txtTeamBName=    findViewById(R.id.team_b_name);
        txtWinner =      findViewById(R.id.winner);

        btnTeamAGoal = findViewById(R.id.button_goalA);
        btnTeamBGoal = findViewById(R.id.button_goalB);
        btnTeamAFoul = findViewById(R.id.button_foulA);
        btnTeamBFoul = findViewById(R.id.button_foulB);
        btnTeamASnitch = findViewById(R.id.snitchA);
        btnTeamBSnitch = findViewById(R.id.snitchB);
        btnReset = findViewById(R.id.button_reset);

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
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
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
        displayWinner();
        buttonsOn(false);
    }

    public void bCaughtSnitch()
    {
        bScore+=150;
        display(bScore, txtTeamBScore);
        displayWinner();
        buttonsOn(false);
    }

    //This method turns button active or inactive. It is intended for all buttons except reset to be inactive when match is finished.
    private void buttonsOn(boolean flag)
    {
        btnTeamASnitch.setEnabled(flag);
        btnTeamBSnitch.setEnabled(flag);
        btnTeamAGoal.setEnabled(flag);
        btnTeamBGoal.setEnabled(flag);
        btnTeamAFoul.setEnabled(flag);
        btnTeamBFoul.setEnabled(flag);
    }

    private void displayWinner()
    {
        if (aScore > bScore)
            txtWinner.setText(txtTeamAName.getText()+ getResources().getString( R.string.victory_text));
        else
            txtWinner.setText(txtTeamBName.getText()+ getResources().getString( R.string.victory_text));

        btnReset.setText(R.string.new_match);
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

        txtTeamAName.setText("");
        txtTeamBName.setText("");

        buttonsOn(true);
        btnReset.setText(R.string.btn_reset);
    }

}