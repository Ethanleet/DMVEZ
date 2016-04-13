package luna_taks.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by AnjanBalgovind on 11/5/15.
 */

// Super adapter class used for making custom adapters
public class CustomAdapter extends ArrayAdapter<Question> {

    protected int layout;
    protected int id;
    protected int id1 = -1;
    protected int id2 = -1;
    protected ViewHolder holder;
    protected Question instanceOfQuestion;

    // contructor
    public CustomAdapter( Context context, ArrayList<Question> cards,
                          int l, int i, int i1, int i2 ) {
        super( context, 0, cards );
        layout = l;
        id = i;  id1 = i1;  id2 = i2;
    }

    // class that contains fields used in subclass adapters
    protected class ViewHolder {

        TextView a_question;
        TextView txt_CorrectAnswer;
        TextView txt_UserAnswer;

    }

    // determines whats displayed to the user
    public View getView( int position, View convertView, ViewGroup parent ) {

        instanceOfQuestion = getItem(position);

        if( convertView == null ) {

            // create a holder and inflate the view
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate( layout , parent, false);
            holder.a_question = (TextView) convertView.findViewById(id);

            // create the text views
            if( id1 > -1 && id2 > -1 ) {
                holder.txt_CorrectAnswer = (TextView) convertView.findViewById(id1);
                holder.txt_UserAnswer = (TextView) convertView.findViewById(id2);
            }

            convertView.setTag( holder );

        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;

    }

    // return the state of the holder
    protected ViewHolder getViewHolder() {   return holder;  }



}
