package luna_taks.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;




/**
 * Created by AnjanBalgovind on 10/26/15.
 */


/* This class represents a multiple choice question for the quiz, complete with
 * its own buttons and everything
 */
public class MultipleChoice extends Question implements Parcelable {

    public RadioGroup group;
    protected RadioButton choiceA;   // The first choice
    protected RadioButton choiceB;   // The second choice
    protected RadioButton choiceC;   // The third choice

    private int qIndex;
    protected boolean correct;


    protected String userAnswer = "YOU DID NOT SELECT AN ANSWER!";
    public static int ttlCorrect = 0;




    /*  Constructor
     *  Parameters:  1.)  q:        The array list that contains the data
     *                              queried from the data base
     *                    context:  The context that the textview is added to
     *
     */
    public MultipleChoice( ArrayList<Object> q, Context context, int questionIndex ) {

        super( q.get(0), q.get(1), q.get(5) );
        /* Initialize the buttons with the answers */
        choiceA = new RadioButton(context);
        choiceC = new RadioButton(context);
        choiceB = new RadioButton(context);
        choiceA.setText((String) q.get(2));
        choiceB.setText((String) q.get(3));
        choiceC.setText((String) q.get(4));

        qIndex = questionIndex;

    }
    /* no arg ctor */
    public MultipleChoice() {}

    /* Ctor tat takes in Parcel */
    private MultipleChoice(Parcel in) {

        // This order must match the order in writeToParcel()
        category = in.readString();
        question = in.readString();
        answer = in.readString();
        qIndex = in.readInt();
        correct = ( in.readInt() > 0 ) ? true : false;
        userAnswer = in.readString();


    }

    /* Check to see if the user has selected the corect answer */
    public void checkCorrect( RadioButton selected ){

        if( selected == null ) {

            return;
        }

        userAnswer = selected.getText().toString();

        if( userAnswer.equalsIgnoreCase(answer) ) {
            correct = true;
            ttlCorrect++;
        }

    }

    /* Overloaded checkCorrect used for testing */
    public void checkCorrect(){
        checkCorrect(
                (RadioButton) group.findViewById(group.getCheckedRadioButtonId()));

    }

    /* This method actually displays the question */
    public final void addToQuizView( LinearLayout lin, Context context ) {

        TextView view = new TextView(context);
        view.setPadding(20, 0, 0, 20);
        /* Set text here */
        String questionText = "\n" + qIndex + ".  " + question;
        view.setText( questionText );
        view.setTextColor(Color.BLACK);
        view.setTextSize(20);
        view.setShadowLayer(1,1, 1, Color.BLACK);
        lin.addView(view);



        group = new RadioGroup(context);
        ArrayList<RadioButton> but = new ArrayList<RadioButton>();
        group.setPadding(50,0,50,0);
        /* Add the buttons to the viewgroup */
        but.add(choiceA);
        but.add(choiceB);
        but.add(choiceC);

        choiceA.setPadding(30, 0, 0, 30);
        choiceB.setPadding(30, 0, 0, 30);
        choiceC.setPadding(30, 0, 0, 30);


        choiceA.setTextSize(18);
        choiceB.setTextSize(18);
        choiceC.setTextSize(18);

        /* Shuffles the questions to present them in a random order */
        Collections.shuffle(but);


        for( int j = 0; j < 3; j++ )  {
            group.addView(but.get(j));
        }

        lin.addView(group);
        view.append("\n");
        view.setPadding(50,0,50,0);
    }


    public int describeContents() {
        return -1;
    }

    /* Prepares parcel */
    public void writeToParcel( Parcel dest, int flags) {

        dest.writeString( category );
        dest.writeString( question );
        dest.writeString( answer );
        dest.writeInt( qIndex );
        dest.writeInt( ( correct ) ? 1 : 0 );
        dest.writeString( userAnswer );

    }

    public static final Creator<MultipleChoice> CREATOR = new Creator<MultipleChoice>() {
        public MultipleChoice createFromParcel(Parcel in) {
            return new MultipleChoice(in);
        }

        public MultipleChoice[] newArray(int size) {
            return new MultipleChoice[size];
        }
    };

    protected boolean getCorrect() {  return correct;  }

    /* toString method. Pretty self explanitory */
    @Override
    public String toString() {
        if( choiceA != null && choiceB != null && choiceC!= null ) {
            return question + ", " + answer + ", " + choiceA.getText() + ", " +
                    choiceB.getText() + ", " + choiceC.getText();
        }

        else {
            return "{ Question: " + question + " CorrectAnswer: " + answer +
                    " User got correct?: " + correct + " Question#: " + qIndex +
                    " UserAnswer: " + userAnswer + " }";
        }
    }

    /* This method will check the radio button with the right answer- useful for testing */
    public void setRightAnswer(){

        if(choiceA.getText().toString().equals(answer))
            choiceA.setChecked(true);

        if(choiceB.getText().toString().equals(answer))
            choiceB.setChecked(true);

        if(choiceC.getText().toString().equals(answer))
            choiceC.setChecked(true);
    }

    public final int getIndex() {
        return qIndex;
    }

    public String getUserAnswer() {  return userAnswer;  }


}
