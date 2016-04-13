package luna_taks.myapplication;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AnjanBalgovind on 11/12/15.
 * fragemnt for the flashcards
 */
public class FlashCardFragment extends Fragment {

    public static final String QUESTION_KEY = "Question"; // refers to the passed in question
    public static final String ANSWER_KEY = "Answer"; // refers to the passed in answer
    public static final String CATEGORY_KEY = "Category"; // refers to the passed in category

    private boolean showAnswer; // flag that determines if the card has been flipped or not

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // retrieve all passed in arguments
        Bundle bundle = getArguments();
        View view = inflater.inflate(R.layout.quiz_card, container, false);

        // call helper method to set up the environment
        if( bundle != null ) {
            setValues(view, bundle.getString(QUESTION_KEY), bundle.getString(ANSWER_KEY),
                    bundle.getInt(CATEGORY_KEY));
        }

        return view;

    }

    // calls flip method and sets the approriate values for each flash card
    private void setValues( View view, final String question, final String answer,
                            final int category ) {

        final View vGroup = view;
        final TextView quizView = (TextView) view.findViewById(R.id.quizCard);

        // set the category of the current flash card
        String heading = "General Drive";
        switch( category ) {
            case 2:
                heading = "Tricky Situation";
                break;
            case 3:
                heading = "Facts";
                break;
            case 4:
                heading = "Traffic Laws";
                break;
            case 5:
                heading = "Misc";
        }

        quizView.setText(heading + "\n\n\n" + question);
        final String cat = heading;
        // flip the card
        quizView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer = !showAnswer;
                if (showAnswer) {
                    flipCard(vGroup, quizView, answer, null);
                } else {
                    flipCard(vGroup, quizView, question, cat);
                }
            }
        });

    }

    // flip the card on click
    private void flipCard(View vGroup, final TextView view, String quesAns, String cat ) {
        // call in the respective layouts
        View rootLayout = vGroup.findViewById(R.id.cardView);
        View cardFace = vGroup.findViewById(R.id.card_face);
        View cardBack = vGroup.findViewById(R.id.card_back);

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);
        // if the card is visible on click then make it invisible
        if (cardFace.getVisibility() == View.GONE) {
            flipAnimation.flip();
        }

        rootLayout.startAnimation(flipAnimation);
        // est the category padding
        if( cat != null ) {
            view.setText(cat + "\n\n\n" + quesAns);
            view.setPadding(30,50,30,40);
        }

        // set the test depending on whether it card is on the back or front
        else {
            view.setText(quesAns);
            view.setPadding(30, 150, 30, 40);
        }


        view.setTextColor(Color.BLACK);

    }

}
