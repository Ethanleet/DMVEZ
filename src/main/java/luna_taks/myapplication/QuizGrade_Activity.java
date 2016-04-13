package luna_taks.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/*
 * The activity for grding the quiz
 */
public class QuizGrade_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_grade_);
        // activate the nvaigation bar
        setSupportActionBar((Toolbar) findViewById(R.id.quiz_grade_bar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.show();

        // initialize the array list that contains the questions from the previous activity
        ArrayList<MultipleChoice> mul = getIntent().getParcelableArrayListExtra( "AnswersToGrade" );
        ArrayList<Question> questions = new ArrayList<>();
        Questions_DB qb = new Questions_DB( this );

        // determine which question they got correct
        for( int i = 0; i < mul.size(); i++ ) {
            if( !mul.get(i).getCorrect() ) {
                qb.incrementStatsTB( mul.get(i).getCategory() );
                questions.add(mul.get(i));
            }
        }

        // customize the title depending on how they performed
        actionBar.setTitle( "SCORE: " + mul.get(0).ttlCorrect + "/" + mul.size() );

        if( mul.get(0).ttlCorrect == mul.size() ) {
            actionBar.setSubtitle( "CONGRATULATIONS YOU ACED THE TEST!" );
            return;
        }


        if( mul.get(0).ttlCorrect < mul.size() - 8 ) {
            actionBar.setSubtitle("Better luck next time!\n");

        }

        else {
            actionBar.setSubtitle("You are now a certified driver!\n");
        }

        // delegate the view to the adapter
        ListView list = (ListView) findViewById(R.id.gradeView);
        GradeAdapter ga = new GradeAdapter(this, questions);
        list.setAdapter(ga);

    }

    // fire the intent when the back button is pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent( QuizGrade_Activity.this, DMVActivity.class );
        QuizGrade_Activity.this.startActivity( intent );
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_grade, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DMVActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
