package luna_taks.myapplication;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;


/**
 * Created by AnjanBalgovind on 11/29/15.
 */
public class ImageCard extends MultipleChoice implements Parcelable {

    private byte[] image;

    public ImageCard( ArrayList<Object> q, Context context ) {
         /* Constructor based on quesiton and context */
        super( q, context, 0 );
        image = (byte[])q.get(6);
    }


    public ImageCard( ArrayList<Object> str, Context context, byte[] im ) {
        super(str, context, 0);
        image = im;
    }


    public byte[] getImage() {
        return image;
    }


    private ImageCard(Parcel in) {

        // This order must match the order in writeToParcel()
        question = in.readString();
        answer = in.readString();
        correct = ( in.readInt() > 0 ) ? true : false;
        userAnswer = in.readString();

    }

    @Override
    public int describeContents() {
        return -1;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags) {

        dest.writeString( question );
        dest.writeString(answer);
        dest.writeInt( ( correct ) ? 1 : 0 );
        dest.writeString(userAnswer);

    }


    public static final Parcelable.Creator<ImageCard> CREATOR = new Parcelable.Creator<ImageCard>() {
        public ImageCard createFromParcel(Parcel in) {
            return new ImageCard(in);
        }
        public ImageCard[] newArray(int size) {
            return new ImageCard[size];
        }
    };


}