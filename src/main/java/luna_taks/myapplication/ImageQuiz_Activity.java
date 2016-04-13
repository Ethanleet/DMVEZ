package luna_taks.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ImageQuiz_Activity extends AppCompatActivity {

    ViewPager pager;   // view paer object for the adapter
    ImageQuizAdapter adapter; // custom adapter
    // on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_quiz_);

        Questions_DB qb = new Questions_DB(this);
        ArrayList<ImageCard> cards = qb.retrieveImages(this);
        adapter = new ImageQuizAdapter( getSupportFragmentManager(), cards );
        pager = (ViewPager) findViewById( R.id.pager );

        pager.setAdapter(adapter);

    }

    // insert questions into the local database.
    public void insertQuestions( Questions_DB db ) {

        String[] q1 = {"Signs", "This sign represents",
                "A pedestrian crosswalk", "A library", "A school zone", "A school zone" };

        Bitmap img = BitmapFactory.decodeResource(this.getResources(), R.drawable.pedestrian);
        ByteArrayOutputStream imgStream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 100, imgStream);
        byte im[] = imgStream.toByteArray();
        db.insertImage( q1,this, im );
        try { imgStream.close(); }
        catch (Exception e ){
            Log.e("BLAH", e.toString());
        }


    }


}
