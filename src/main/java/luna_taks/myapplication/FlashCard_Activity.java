package luna_taks.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/* This activity will allow the user to review test questions with the
 * help of our learning algorithm
 */
public class FlashCard_Activity extends AppCompatActivity {



    ViewPager pager;
    FlashCardAdapter adapter;

    /* Initial setup */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        /* Get access to all the questions in the DB */
        Questions_DB qb = new Questions_DB(this);

        /* This line will take the database and turn it in to a list of flash card objects. */
        ArrayList<QuizCard> cards = createCardList( qb );

        /* Once the flash cards have been created, display them. */
        adapter = new FlashCardAdapter( getSupportFragmentManager(), cards );
        pager = ( ViewPager ) findViewById( R.id.pager );

        pager.setAdapter(adapter);

    }


    public ArrayList<QuizCard> createCardList( Questions_DB qb ){
      /* List of strings to look up from table */
        String[] categories = new String[]{"GeneralDrive", "TrickySituation",
                "Facts", "TrafficLaws", "Misc"};


        int total = 0;
      /* Create a new array list to hold all the questions */
        ArrayList<QuizCard> masterList = new ArrayList<>();

      /* Create a list of the questions from a category */
        ArrayList<ArrayList<Object>> questionsFromCategory;
      /* Create an array of doubles that will store the stats for each card */

        ArrayList<Integer> stats = new ArrayList<Integer>(5);

        for(int i = 0; i < 5; ++i){
            stats.add(i, qb.getStat(categories[i]));
            total += stats.get(i);
        }//end for loop

        System.out.println( "STATS IS: " + stats );
        for(int i = 0; i < stats.size(); ++i){
            if (total == 0){
                stats.set(i, 20 );
            } else {
                stats.set(i, (int)(( (double)(stats.get(i)) / (double)total) * 100.0));
            }
        }

      /* this nested loop will populate the master list
       * based on the input from the statistics
       */
        //Outer loop goes over the categories
        for(int i = 0; i < stats.size(); ++i){

      /* Pull all questions from the category we need */
            questionsFromCategory = qb.retrieveCategory(categories[i]);

      /* inner loop grabs the questions of type stat[i] */
            for ( int j = 0; j < stats.get(i) && j<questionsFromCategory.size(); ++j ){
                masterList.add( new QuizCard(questionsFromCategory.get(j).get(0),
                        questionsFromCategory.get(j).get(1), questionsFromCategory.get(j).get(2) ));

            }//end inner loop

        }//end outer loop

        //SHUFFLE THAT SHIT
        long seed = System.nanoTime();
        Collections.shuffle(masterList, new Random(seed));

        return masterList;
    }//end createCardList

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent( FlashCard_Activity.this, MainActivity.class );
        FlashCard_Activity.this.startActivity( intent );
        finish();
    }

}
