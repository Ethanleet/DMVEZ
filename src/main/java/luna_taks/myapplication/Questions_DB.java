package luna_taks.myapplication;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by AnjanBalgovind on 10/23/15.
 */
public class Questions_DB {


    private static final String DB_NAME = "MCQuestions";
    private static String TB_NAME = "DMVQuestions";
    private static String TB_STATISTICS = "UserStatistics";
    private static String TB_IMAGE = "ImageQuiz";

    // the category the question belongs to
    private static final String COL_1 = "Category";

    private static final String STATS_COL1 = "GeneralDrive";
    private static final String STATS_COL2 = "TrickySituation";
    private static final String STATS_COL3 = "Facts";
    private static final String STATS_COL4 = "TrafficLaws";
    private static final String STATS_COL5 = "Misc";

    // the actual question itself
    private static final String COL_2 = "Question";

    /* the multiple choice for the question */
    private static final String COL_3 = "Choice1";
    private static final String COL_4 = "Choice2";
    private static final String COL_5 = "Choice3";
    private static final String COL_6 = "Choice4";

    // determines if the question needs a radio or check box
    private static final String COL_7 = "Answer";
    private static final String COL_8 = "Image";

    // unique number to identify the question
    private static final String QUES_ID = "_id";
    private static final String STATS_ID = "stats_id";

    private static final String IMAGETABLE = "ImageQuiz";
    private static final String IMAGE_ID = "id";


    private SQLiteDatabase data_base;
    Context context;


    public Questions_DB(Context context) {

        this.context = context;
        Questions_DB_Helper helper = new Questions_DB_Helper(context);
        data_base = helper.getWritableDatabase();

    }

    /* Create a private inner class to help simplify DB queries */
    private class Questions_DB_Helper extends SQLiteOpenHelper {

        public Questions_DB_Helper(Context context) {
            super(context, DB_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String tableQueryString = "create table " + TB_NAME +
                    " (" + QUES_ID + " integer primary key autoincrement not null, " +
                    COL_1 + " text not null, " +
                    COL_2 + " text not null, " +
                    COL_3 + " text not null, " +
                    COL_4 + " text not null, " +
                    COL_5 + " text not null, " +
                    COL_6 + " text, " +
                    COL_7 + " text not null, " +
                    COL_8 + " blob" + " );";

            db.execSQL(tableQueryString);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        }
    }


    // Add a row to the database
    public void addEntry(String[] allStrings, Drawable image) {

        // create object that is used to query data into the table.

        ContentValues rowEntries = new ContentValues();

        // Insert all entries into the string columns
        for (int i = 0; i < allStrings.length; i++) {

            switch (i) {

                case 0:
                    rowEntries.put(COL_1, allStrings[i]);
                    break;
                case 1:
                    rowEntries.put(COL_2, allStrings[i]);
                    break;
                case 2:
                    rowEntries.put(COL_3, allStrings[i]);
                    break;
                case 3:
                    rowEntries.put(COL_4, allStrings[i]);
                    break;
                case 4:
                    rowEntries.put(COL_5, allStrings[i]);
                    break;
                case 5:
                    rowEntries.put(COL_6, allStrings[i]);
                    break;
                case 6:
                    rowEntries.put(COL_7, allStrings[i]);
                    break;

            }


        }

        /* take the image parameter and compress it into a byte array of PNG format
         * and insert it into the database  */
        if (image != null) {
            Bitmap map = ((BitmapDrawable) image).getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            map.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
            rowEntries.put(COL_8, byteArray.toByteArray());
        } else {
            rowEntries.put(COL_8, "");
        }

        // throw an exception if there is something wrong during insertion
        try {
            data_base.insert(TB_NAME, null, rowEntries);
        } catch (Exception thrownException) {
            Log.e("ERROR WHEN INSERTING", thrownException.toString());
            thrownException.printStackTrace();
        }

    }

    // delete a row from the database
    public void deleteEntry(long row_ID) {

        try {
            data_base.delete(TB_NAME, QUES_ID + "=" + row_ID, null);
        } catch (Exception thrownException) {
            Log.e("DELETE ENTRY ERROR " + row_ID, thrownException.toString());
            thrownException.printStackTrace();
        }

    }

    // overload deleteRow
    public void deleteEntry(String ques) {

        try {
            data_base.delete(TB_NAME, COL_2 + "=" + ques, null);
        } catch (Exception thrownException) {
            Log.e("DELETE ENTRY ERROR " + ques, thrownException.toString());
            thrownException.printStackTrace();
        }

    }


    // clear the table
    public void clearTable() {

        try {
            data_base.execSQL("delete from " + TB_NAME);
        } catch (Exception exp) {
            Log.e("ERROR IN CLEARTABLE", exp.toString());
            exp.printStackTrace();
        }

    }

    // update an entry in the table
    public void updateEntry(long id, String[] entries, Drawable image) {

        ContentValues updatedEntries = new ContentValues();

        for (int i = 0; i < entries.length; i++) {
            String entry = "COL_" + (i + 1);
            updatedEntries.put(entry, entries[i]);
        }

        /* take the image parameter and compress it into a byte array of PNG format
         * and insert it into the database  */
        if (image != null) {
            Bitmap map = ((BitmapDrawable) image).getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            map.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
            updatedEntries.put(COL_8, byteArray.toByteArray());
        }

        try {
            data_base.update(TB_NAME, updatedEntries, QUES_ID + "=" + id, null);
        } catch (Exception thrownException) {
            Log.e("UPDATE ENTRY ERROR " + id, thrownException.toString());
            thrownException.printStackTrace();
        }

    }


    // retrieve a specific value from the database
    public ArrayList<Object> retrieveEntry(int id) {

        ArrayList<Object> entry = new ArrayList<Object>();
        Cursor curse;

        try {

            curse = data_base.query(
                    TB_NAME, new String[]{
                            QUES_ID, COL_1, COL_2,
                            COL_3, COL_4,
                            COL_5, COL_6,
                            COL_7, COL_8
                    }, null,
                    null, null, null, null, null
            );

            if (!curse.moveToFirst()) {
                return null;
            }

            curse.move(id + curse.getPosition());
            // loop through every column in the current row
            for (int i = 1; i < 8; i++) {
                if (curse.getString(i) != null) {
                    entry.add(curse.getString(i));
                }
            }

            // add the image to the arraylist
            entry.add(curse.getBlob(8));
            curse.close();
        } catch (Exception thrownSQLException) {
            Log.e("RETRIEVE VALUE ERROR " + id, thrownSQLException.toString());
            thrownSQLException.printStackTrace();
        }


        return entry;
    }

    // retrieve all entries in the database
    public ArrayList<ArrayList<Object>> retrieveDB() {

        // each index in entries contains column data, which may vary in length.
        ArrayList<ArrayList<Object>> entries = new ArrayList<ArrayList<Object>>();
        Cursor cursor; // the object returned when retrieving from a data table.

        try {
            cursor = data_base.query(
                    TB_NAME, new String[]{
                            QUES_ID, COL_1, COL_2, COL_3,
                            COL_4, COL_5, COL_6,
                            COL_7, COL_8
                    }, null, null, null,
                    null, null, null
            );

            if (!cursor.moveToFirst()) {
                return null;
            }

            // loop until the cursor is past the end of the last entry in the table
            do {

                //create an array list that will hold all the entries from the current row
                ArrayList<Object> entry = new ArrayList<Object>();

                for (int i = 1; i < 8; i++) {
                    if (cursor.getString(i) != null) {
                        entry.add(cursor.getString(i));
                    }
                }
                // get the image from the database using a special method
                entry.add(cursor.getBlob(8));
                entries.add(entry);

            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception thrownException) {
            Log.e("ERROR IN retrieveDB", thrownException.toString());
            thrownException.printStackTrace();
        }

        /* generate a seed to shuffle the array list of entries, this will produce a random
         * sequence of questions that will be shown to the user.  */


        long seed = System.nanoTime();
        Collections.shuffle(entries, new Random(seed));


        entries.trimToSize();
        return entries;

    }


    public void removeAndReplaceTable(String whichTable, String tableName) {

        if (whichTable.equals(TB_STATISTICS)) {
            try {

                data_base.execSQL("CREATE TABLE " + tableName +
                        " (" + STATS_ID + " integer primary key autoincrement not null, " +
                        STATS_COL1 + " integer, " +
                        STATS_COL2 + " integer, " +
                        STATS_COL3 + " integer, " +
                        STATS_COL4 + " integer, " +
                        STATS_COL5 + " integer " + " );");

                data_base.execSQL("INSERT INTO " + tableName + " SELECT * FROM " + TB_STATISTICS);

                data_base.execSQL("DROP TABLE " + TB_STATISTICS);
                TB_STATISTICS = tableName;


            } catch (Exception exp) {
                Log.e("REMOVE TABLE EXCEPTION ", exp.toString());
                exp.printStackTrace();
            }

        }

    }


    public void addTableStatistics() {

        String tableQueryString = "create table " + TB_STATISTICS +
                " (" + STATS_ID + " integer primary key autoincrement not null, " +
                STATS_COL1 + " integer, " +
                STATS_COL2 + " integer, " +
                STATS_COL3 + " integer, " +
                STATS_COL4 + " integer, " +
                STATS_COL5 + " integer" + " );";

        data_base.execSQL(tableQueryString);

    }


    public ArrayList<QuizCard> getQuestions() {

        ArrayList<QuizCard> cards = new ArrayList<>();

        for (int i = 0; i < 84; i++) {
            QuizCard ques_ans = new QuizCard(retrieveEntry(i));
            cards.add(ques_ans);
        }

        long seed = System.nanoTime();
        Collections.shuffle(cards, new Random(seed));
        return cards;

    }


    public void incrementStatsTB(int column) {

        try {
            Cursor curse = data_base.query(
                    TB_STATISTICS, new String[]{STATS_ID, STATS_COL1,
                            STATS_COL2, STATS_COL3, STATS_COL4, STATS_COL5
                    }, null, null, null, null, null, null
            );

            ContentValues rowEntries = new ContentValues();

            if (!curse.moveToFirst()) {

                for (int i = 0; i < 5; i++) {

                    switch (i) {
                        case 0:
                            rowEntries.put(STATS_COL1, 0);
                            break;
                        case 1:
                            rowEntries.put(STATS_COL2, 0);
                            break;
                        case 2:
                            rowEntries.put(STATS_COL3, 0);
                            break;
                        case 3:
                            rowEntries.put(STATS_COL4, 0);
                            break;
                        case 4:
                            rowEntries.put(STATS_COL5, 0);
                    }

                }

                data_base.insert(TB_STATISTICS, null, rowEntries);

                return;
            }

            int increment = curse.getInt(column);
            increment++;

            String[] columns = {"blach", STATS_COL1, STATS_COL2, STATS_COL3, STATS_COL4, STATS_COL5};
            for (int i = 1; i < 6; i++) {
                rowEntries.put(columns[i], (column == i) ? increment : curse.getInt(i));
            }


            data_base.update(TB_STATISTICS, rowEntries, STATS_ID + "=" + 50, null);

            /*for (int i = 0; i < 6; i++) {
                System.out.println(curse.getInt(i));
            }*/


        } catch (Exception e) {
            Log.e("INCREMENT ERROR", e.toString());
            e.printStackTrace();
        }


        /*data_base.update( TB_STATISTICS,  , STATS_ID + "=" + 0, null );
        data_base.update(TB_NAME, updatedEntries, QUES_ID + "=" + id, null);*/
    }

    /* This method will get the number of incorrect answers for each category */
    public int getStat(String category) {
        int val = -1;
        Cursor curse;

        try {

            curse = data_base.query(TB_STATISTICS, new String[]{}, null, null, null, null, null, null);

            if (!curse.moveToFirst()) {
                return -1;
            }

            if (category.equals(STATS_COL1)) {
                val = curse.getInt(1);
            } else if (category.equals(STATS_COL2)) {
                val = curse.getInt(2);
            } else if (category.equals(STATS_COL3)) {
                val = curse.getInt(3);
            } else if (category.equals(STATS_COL4)) {
                val = curse.getInt(4);
            } else if (category.equals(STATS_COL5)) {
                val = curse.getInt(5);
            }

            curse.close();
        } catch (Exception thrownSQLException) {
            Log.e("RETRIEVE STAT ERROR", thrownSQLException.toString());
            thrownSQLException.printStackTrace();
        }


        return val;

    }//end getStat


    public ArrayList<ArrayList<Object>> retrieveCategory(String category) {
        ArrayList<ArrayList<Object>> entries = new ArrayList<ArrayList<Object>>();

        Cursor curse;

        try {


            curse = data_base.query(
                    TB_NAME, new String[]{COL_1, COL_2, COL_7},
                    null, null, null, null, null);

            if (!curse.moveToFirst()) {
                return null;
            }

            /*System.out.println( curse.getString(0) + " : " + category + " : " +
                    curse.getString(0).equals(category));*/

            do {

                ArrayList<Object> entry = new ArrayList();

                if (curse.getString(0).equals(category)) {
                    for (int i = 0; i < 3; ++i) {
                        entry.add(i, curse.getString(i));
                    }//end for
                    entries.add(entry);
                }


            } while (curse.moveToNext());

            curse.close();

        } catch (Exception thrownSQLException) {
            Log.e("RETRIEVE CATEGORY ERROR", thrownSQLException.toString());
            thrownSQLException.printStackTrace();
        }
        return entries;
    }

    public void createImageQuizTable() {
        String create = "CREATE TABLE ImageQuiz (" + IMAGE_ID + " INTEGER PRIMARY KEY NOT NULL," +
                COL_1 + " text not null, " +
                COL_2 + " text not null, " +
                COL_3 + " text not null, " +
                COL_4 + " text not null, " +
                COL_5 + " text not null, " +
                COL_7 + " text not null, " +
                COL_8 + " blob not null" + " );";
        data_base.execSQL(create);
    }


    public void insertImage(String[] arr, Context context, byte[] im) {

        ContentValues content = new ContentValues();
        content.put(COL_1, arr[0]);
        content.put(COL_2, arr[1]);
        content.put(COL_3, arr[2]);
        content.put(COL_4, arr[3]);
        content.put(COL_5, arr[4]);
        content.put(COL_7, arr[5]);
        content.put(COL_8, im);
        data_base.insert(TB_IMAGE, null, content);

    }


    public ArrayList<ImageCard> retrieveImages( Context context ) {

        ArrayList<ImageCard> image = new ArrayList<>();
        String selectQuery = "SELECT * FROM ImageQuiz";

        Cursor cursor = data_base.rawQuery(selectQuery,null);

        if( cursor.moveToFirst() ) {
            do {

                ArrayList<Object> str = new ArrayList<Object>();
                for( int i = 0; i < 6; i++ ) {
                    str.add( cursor.getString(i) );
                }

                byte[] im = cursor.getBlob(6);
                image.add( new ImageCard(str, context, im) );

            } while( cursor.moveToNext() );
        }

        System.out.println(image.get(0).getImage());
        return image;
    }

}
