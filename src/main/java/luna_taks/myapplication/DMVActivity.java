package luna_taks.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;




/**
 * This class will be used to generate the database that hold all our questions
 */
public class DMVActivity extends AppCompatActivity {

    /*
     * References to everything we need to actually draw the quiz
     */
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu menu;
    private String mActivityTitle;
    private ArrayList<MultipleChoice> mul;
    private ArrayList<ArrayList<Object>> entries;
    private LinearLayout lin;
    private Context con = this;
    private int questionCounter = 46;

    @Override
    protected void onCreate( Bundle savedInstances ) {

        /*
         Handles all the setup when the activity is first launched.
         */
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_dmv);

        setSupportActionBar((Toolbar) findViewById(R.id.app_bar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("DMV Quiz");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.show();

        lin = (LinearLayout) findViewById(R.id.scrollable);

        // Initialize database
        Questions_DB qb = new Questions_DB(this);
        //get all questions in DB
        //entries = qb.retrieveDB();
        entries = readFromFiles(qb);
        mul = new ArrayList<>();

        /* This loop takes the raw DB entries and converts them to our
         * multiple choice class
         */
        for( int i = 0; i < 46; i++ ) {
            mul.add(new MultipleChoice(entries.get(i), this, i + 1) );
            mul.get(i).addToQuizView(lin, this);
        }

        mul.get(0).ttlCorrect = 0;

        /* This block will set up some things for the drawer menu */
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        addDrawerItems();
        setupDrawer();
    }

    /*
     * Reads from a file to create  database of questions.
     * @param qb A reference to the database to store the file in to
     * @return An ArrayList of ArrayLists representing questions
     */
    private ArrayList<ArrayList<Object>> readFromFiles( Questions_DB qb ) {

        // clear the table so no duplicate insertions occur
        qb.clearTable();

        //initialize inputstreams
        InputStream is = null;
        InputStream ip = null;

        try {
            //get the questions and the choices from the text files stored in raw
            is = getResources().openRawResource( R.raw.questions );
        }
        catch( Exception exp ) {
            Log.e("\n-FILE DOESN'T EXIST-\n", exp.toString());
            exp.printStackTrace();
        }


        String temp;

        try {

            // place those strings in a buffer
            BufferedReader readerQues = new BufferedReader( new InputStreamReader(is) );
            ArrayList<String> newEntries = new ArrayList<>();
            while( (temp = readerQues.readLine()) != null ) {
                if (temp.isEmpty()) {
                    newEntries.add(5, null);
                    newEntries.add(null);
                    qb.addEntry(newEntries.toArray(new String[newEntries.size()]), null);
                    newEntries = new ArrayList<>();
                    continue;
                }
                newEntries.add(temp);
            }

        }

        catch( Exception exp ) {
            Log.e("ERROR WHEN READING: ", exp.toString() );
            exp.printStackTrace();
        }

        return qb.retrieveDB();
    }

    /* Creates option menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        this.menu = menu;
        return true;
    }

    /* Overrides the back button- now it will generate a new quiz */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /* Action bar navigation handler */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            /* move to the grade quiz activity */
            case R.id.action_gradeQuiz:
                gradeQuiz();
                return true;

            /* Generate a new quiz */
            case R.id.action_newQuiz:
                Questions_DB qb = new Questions_DB(this);
                entries = qb.retrieveDB();
                mul.clear();
                lin.removeAllViewsInLayout();
                for( int i = 0; i < 46; i++ ) {
                    mul.add(new MultipleChoice(entries.get(i), this, i + 1) );
                    mul.get(i).addToQuizView(lin, this);
                }

                MenuItem it = menu.getItem(0);
                it.setVisible(true);
                questionCounter = 46;
                mul.get(0).ttlCorrect = 0;
                break;
            /* END QUIZ GENERATION */

            /* Add 20 questions to the quiz */
            case R.id.action_moreQuestions:
                displayMoreQuestions();
                if ((questionCounter + 20) > entries.size()) {
                    item.setVisible(false);
                }
                return true;
        }
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* Grades the quiz */
    public void gradeQuiz() {

        int forgottenQuestions = 0;

        /* Determine the selected answer, or is the user left an answer unchecked */
        for (int i = 0; i < mul.size(); i++) {
            RadioButton selected = (RadioButton) findViewById(
                    mul.get(i).group.getCheckedRadioButtonId());

            if (selected == null) {
                forgottenQuestions++;
            }

            mul.get(i).checkCorrect(selected);
        }

        /* If the user forgot to select an answer, prompt them */
        if (forgottenQuestions > 0) {

            SubmitDialog alert = new SubmitDialog();
            alert.setMemberVariables( DMVActivity.this, mul );
            alert.show(getFragmentManager(), "Alert Dialog");

        } else {
            /* Else grade the quiz */
            Intent quizGradeIntent = new Intent(DMVActivity.this, QuizGrade_Activity.class);
            quizGradeIntent.putParcelableArrayListExtra("AnswersToGrade", mul);
            DMVActivity.this.startActivity(quizGradeIntent);
            finish();
        }
    }

    /* Adds moree questions to the quiz display */
    public void displayMoreQuestions() {
        /* While there are still questions that CAN be added, allow the user to add more */
        if( questionCounter < entries.size() ) {
            int limit = questionCounter + 20;
            for (int i = questionCounter; i < limit; i++) {
                mul.add(new MultipleChoice(entries.get(i), this, i + 1));
                mul.get(i).addToQuizView(lin, this);
            }
            questionCounter += 20;
        }
    }

    /* This method builds the drawable navigaton menu */
    private void addDrawerItems() {
        String[] menuItems = {"Home", "DMV Quiz", "Flash Card", "Tips", "Resource"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(DMVActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                /* Determine where the user wants to navigate to */
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent( con, MainActivity.class );
                        String value = "\n\t\tBEGIN THE PERMIT TEST!\n";
                        intent0.putExtra("key", value);
                        startActivity(intent0);
                        finish();
                        break;

                    case 1:
                        mDrawerLayout.closeDrawers();
                        break;

                    case 2:
                        Intent intent2 = new Intent( con, FlashCard_Activity.class );
                        startActivity(intent2);
                        finish();
                        break;

                    case 3:
                        Intent intent3 = new Intent( con, activity_tips.class );
                        startActivity(intent3);
                        finish();
                        break;
                }
            }
        });
    }

    /* Some initialization for the drawer menu */
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
