package luna_taks.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by AnjanBalgovind on 11/12/15.
 */
public class FlashCardAdapter extends FragmentPagerAdapter {

    String[] questions; // the questions of the object coming in
    String[] answers; // the answers to those questions
    int[] categories; // categories of those questions

    // constructor initializes all the member fields and calls the super constructor
    public FlashCardAdapter( FragmentManager fm, ArrayList<QuizCard> mul) {

        super( fm );
        questions = new String[mul.size()];
        answers = new String[mul.size()];
        categories = new int[mul.size()];

        // after initialization populate each array
        for( int i = 0; i < mul.size(); i++ ) {
            questions[i] = mul.get(i).getQuestion();
            answers[i] = mul.get(i).getAnswer();
            categories[i] = mul.get(i).getCategory();
        }

    }

    // override method from the super class to pass items to a fragment
    @Override
    public Fragment getItem( int position ) {

        // initialize bundle which copies member values to the fragment
        Bundle bundle = new Bundle();
        bundle.putString( FlashCardFragment.QUESTION_KEY, questions[position] );
        bundle.putString( FlashCardFragment.ANSWER_KEY, answers[position] );
        bundle.putInt( FlashCardFragment.CATEGORY_KEY, categories[position]);

        // create the fragment and pass the bundle to it
        FlashCardFragment fragment = new FlashCardFragment();
        fragment.setArguments(bundle);

        return fragment;

    }

    // the count of the number of items in the fragment.
    @Override
    public int getCount() {
        return questions.length;
    }
}
