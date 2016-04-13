package luna_taks.myapplication;

/**
 * Created by AnjanBalgovind on 11/5/15.
 * super class to every question type that is used in this app
 */
public class Question {

    protected String question; // contains the question
    protected String answer; // contains the answer
    protected String category; // contains the category


    public Question() {}

    // initialize all member variables
    public Question( Object cat, Object ques, Object ans ) {
        category = new String( (String) cat );
        question = new String( (String)ques );
        answer = new String( (String) ans );

    }

    // retrieve the answer of this object
    public String getAnswer() {  return answer;  }

    // retrive the cateogry of this object. returns an int because that is how
    // it is used for a method in the data base.
    public int getCategory() {

        // a bunch of ifs on what the category might be
        if( category.equals("GeneralDrive") ) {
            return 1;
        }

        else if( category.equals("TrickySituation") ) {
            return 2;
        }

        else if( category.equals("Facts") ) {
            return 3;
        }

        else if( category.equals("TrafficLaws") ) {
            return 4;
        }

        else if( category.equalsIgnoreCase( "Misc" ) ) {
            return 5;
        }

        return 0;

    }

    // standard getters and setters
    public String getQuestion() {  return question;  }


    public void setAnswer( String ans ) {  answer = ans;  }


    public void setQuestion( String ques ) {  question = ques;  }


    // to string method
    public String toString() {
        return "Category: " + category + " Question: " + question + " Answer: " + answer;
    }







}
