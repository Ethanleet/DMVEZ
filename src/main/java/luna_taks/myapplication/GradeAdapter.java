package luna_taks.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.camera2.params.BlackLevelPattern;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AnjanBalgovind on 10/31/15.
 * custome grade adapter for displaying grade after taking the quiz
 */

public class GradeAdapter extends CustomAdapter {


    // constructor recieves the questions that were displayed to user
    public GradeAdapter( Context context, ArrayList<Question> mul ) {
        super( context, mul, R.layout.content_lv_items, R.id.Question,
                R.id.CorrectAnswer, R.id.UserAnswer );

    }

    // customize what is shown to the user by setting padding and margins
    public View getView( int position, View convertView, ViewGroup parent ) {

        View view = super.getView(position, convertView, parent);

        MultipleChoice choice = ( MultipleChoice ) super.instanceOfQuestion;
        // use the holder to set its question and answer fields
        holder.a_question.setText(choice.getIndex() + ".\t" + choice.getQuestion());
        holder.a_question.setTypeface(null, Typeface.BOLD);
        holder.txt_CorrectAnswer.setText("Correct Answer:\t\t" + choice.getAnswer());
        holder.txt_CorrectAnswer.setTextColor(Color.rgb(50,158,62));
        holder.txt_CorrectAnswer.setPadding(120, 0, 0, 25);
        // set the padding and text of every field.
        holder.txt_CorrectAnswer.setTextSize(18);
        holder.txt_CorrectAnswer.setTypeface(null, Typeface.BOLD_ITALIC);
        holder.txt_UserAnswer.setText("Your Answer:\t\t" + choice.getUserAnswer());
        holder.txt_UserAnswer.setTextColor(Color.rgb(160, 0, 0));
        holder.txt_UserAnswer.setPadding(120, 0, 0, 0);
        holder.txt_UserAnswer.setTextSize(18);


        return view;

    }


}

