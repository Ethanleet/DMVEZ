package luna_taks.myapplication;

/**
 * Created by AnjanBalgovind on 11/7/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/*
 * the dialog button that pop up in the multiple choice activity
 * when the submit button is pressed. Only pops up when blank questions are present
 */
public class SubmitDialog extends DialogFragment {

    private Activity act; // the activity the dialog will pop in
    private ArrayList<MultipleChoice> mul;  // the array list of questions to parse


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // User will not be able to dismiss dialog by clicking the background
        this.setCancelable(false);
        builder.setTitle("Submit your quiz with question left blank?");
        builder.setMessage("You left some questions blank, " +
                "are you sure you want to submit your quiz? The grader will not give " +
                "credit for those questions.");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // if the submit button is clicked fire the grade activity intent.
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent quizGradeIntent = new Intent(act, QuizGrade_Activity.class);
                quizGradeIntent.putParcelableArrayListExtra("AnswersToGrade", mul );
                act.startActivity(quizGradeIntent);
                act.finish();
            }
        });

        Dialog dialog = builder.create();

        return dialog;
    }

    // helped ctor method
    public void setMemberVariables( Activity a, ArrayList<MultipleChoice> m ) {
        act = a;
        mul = m;
    }


}
