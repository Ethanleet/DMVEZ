package luna_taks.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by AnjanBalgovind on 11/29/15.
 */
public class ImageQuizAdapter extends FragmentPagerAdapter {

    String[] questions; // contains the questions that were passed in
    String[] answers; // contains the answers that were passed in
    byte[][] images; // contains the images that were passed in

    // constructor for the adapter
    public ImageQuizAdapter( FragmentManager fm, ArrayList<ImageCard> options) {

        super( fm );
        questions = new String[options.size()]; // initialize the members
        answers = new String[options.size()];
        images = new byte[1][options.size()];

        // populate the members with the passed in data values.
        for( int i = 0; i < options.size(); i++ ) {
            questions[i] = options.get(i).getQuestion();
            answers[i] = options.get(i).getAnswer();
            images[i] = options.get(i).getImage();
        }

    }

    // retrieve the item and bundle it to the fragment.
    @Override
    public Fragment getItem( int position ) {

        Bundle bundle = new Bundle();
        // pass in each member to the bundle
        bundle.putString( ImageCardFragment.QUESTION_KEY, questions[position] );
        bundle.putString( ImageCardFragment.ANSWER_KEY, answers[position] );
        bundle.putByteArray( ImageCardFragment.IMAGE_KEY, images[position] );
        // create the fragment and pass to it the bundle to display
        ImageCardFragment fragment = new ImageCardFragment();
        fragment.setArguments(bundle);

        return fragment;

    }

    // the total number of objects in the fragment.
    @Override
    public int getCount() {
        return questions.length;
    }
}

