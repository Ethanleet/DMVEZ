package luna_taks.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by AnjanBalgovind on 11/29/15.
 * another fragment for the use of an image quiz
 */
public class ImageCardFragment extends Fragment {

    public static final String QUESTION_KEY = "Question"; //refers to the question passed in
    public static final String ANSWER_KEY = "Answer";// refers to the answer passed in
    public static final String IMAGE_KEY = "Image";// refers to the image passed in

    private boolean showAnswer; // determines if the card was flipped or not.

    // handles what is shown when the view is created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // retrieve what is sent through the bundle from the adapter
        Bundle bundle = getArguments();
        View view = inflater.inflate(R.layout.image_card, container, false);

        if (bundle != null) {
            setValues(view, bundle.getString(QUESTION_KEY), bundle.getString(ANSWER_KEY),
                    bundle.getByteArray(IMAGE_KEY));
        }

        return view;

    }

    // sets the values of what is being displayed
    private void setValues( View view, final String question, final String answer,
                            final byte[] im ) {

        // create a view for the image and for the entire view
        final View vGroup = view;
        final ImageView img = (ImageView) view.findViewById(R.id.image_card);
        // decompress the byte array that is the image
        if( im != null ) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(im);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            img.setImageBitmap(theImage);
            // throw an expcetin if it is null
            try {imageStream.close();}
            catch (Exception e) {
                Log.e("akld", e.toString());
            }
        }


        // call the flip animation function to flip the card
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer = !showAnswer;
                if (showAnswer) {
                    flipCard(vGroup, img, answer, null);
                } else {
                    flipCard(vGroup, img, question, im);
                }
            }
        });



    }

    // flip the card
    private void flipCard(View vGroup, final ImageView view, String quesAns, byte[] im ) {

        View rootLayout = vGroup.findViewById(R.id.imageView);
        View cardFace = vGroup.findViewById(R.id.image_face);
        View cardBack = vGroup.findViewById(R.id.image_back);

        // create a new instance of flip animation and pass in the layout values
        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE) {
            flipAnimation.flip();
        }
        // start the flip animation
        rootLayout.startAnimation(flipAnimation);

    }


}


