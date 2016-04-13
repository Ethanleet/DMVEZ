package luna_taks.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AnjanBalgovind on 11/2/15.
 * flash card object
 */
public class QuizCard extends Question {


    // call the super constructor to initialize this object
    public QuizCard( ArrayList<Object> q ) {
        super( q.get(0), q.get(1), q.get(5) );
    }

    // overload ctor.
    public QuizCard( Object cat, Object ques, Object answer ) {
        super( cat, ques, answer );
    }

}
