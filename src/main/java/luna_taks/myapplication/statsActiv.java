package luna_taks.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class statsActiv extends AppCompatActivity {

    private ArrayList<ArrayList<Object>> entries;
    private ArrayList<MultipleChoice> mul;
    private int oldNumWrong;
    private int newNumWrong;
    private static String[] categories = new String[]{"GeneralDrive", "TrickySituation",
            "Facts", "TrafficLaws", "Misc"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmv);



        testMain();
    }



    public int testMain() {

        // Initialize database
        Questions_DB qb = new Questions_DB(this);
        //get all questions in DB
        entries = qb.retrieveDB();
        LinearLayout lin = (LinearLayout) findViewById(R.id.scrollable);
        mul = new ArrayList<>();
        MultipleChoice myQuest;
        /* This loop takes the raw DB entries and converts them to our
         * multiple choice class
         */
        for (int i = 0; i < 46; i++) {
            mul.add(new MultipleChoice(entries.get(i), this, i + 1));
            mul.get(i).addToQuizView(lin,this);
        }



        /* Given I have taken the test
        /* We will use this opprotunity to test quiz generation (retrieval from DB) */

        /*This is the abbreviated test of "When I select the quiz activity a test will be presented
         * so that I may practice for my driving test
         */

        /* Check to see if we have a quiz of the required minimum length */
        if(mul.size() != 46){
            System.err.println("ERROR: QUIZ GENERATION FAILED");
            System.exit(1);
        }

        System.out.println("Quiz Generation test passed\nContinuing...");

        myQuest = mul.get(0);

        /* When I submit my quiz THEN I want it to be graded... */
        /* In this test we will make sure the question class can report that it is correct */
        myQuest.setRightAnswer();

        myQuest.checkCorrect();

        /*Test to see if the correct field is true now */
        if(!myQuest.getCorrect()) {
            System.err.println("ERROR: MULTCHOICE CORRECT TEST FAILED");
            System.exit(1);
        }

        System.out.println("Multiple choice test passed\nContinuing...");

        /*
         * When I get an answer wrong I want my stats to be updated
         */
        /* Get the old value of the user stats so we can check that it has been incremented. */
        oldNumWrong = qb.getStat(categories[mul.get(0).getCategory()-1]);
        qb.incrementStatsTB(mul.get(0).getCategory());

        newNumWrong = qb.getStat(categories[mul.get(0).getCategory()-1]);

        /* Check to see that the values differ by exactly one */
        if (newNumWrong == oldNumWrong + 1) {

            System.out.println("STATTRACK TEST PASSED");
        } else {
            System.out.println("STATTRACK TEST FAILED");
        }

        /* So that my studying can be more guided */
        /* This comes up in the flashcard activity */

        return 0;
    }

}
