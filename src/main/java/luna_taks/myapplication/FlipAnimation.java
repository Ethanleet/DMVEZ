package luna_taks.myapplication;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by AnjanBalgovind on 11/11/15.
 */
public class FlipAnimation extends Animation {
    private Camera cam;

    private float cent_Y;
    private View f_View;


    private float cent_X;
    private View t_View;

    private boolean front = true;

    /**
     * 3d flip animation
     */
    public FlipAnimation(View from, View to) {
        this.f_View = from;
        this.t_View = to;

        setDuration(500);
        setFillAfter(false);
        setInterpolator(new AccelerateDecelerateInterpolator());
    }


    @Override
    public void initialize(int w, int h, int p_w, int p_h) {
        super.initialize(w, h, p_w, p_h);
        cent_X = w / 2;
        cent_Y = h / 2;
        cam = new Camera();
    }

    @Override
    protected void applyTransformation(float time, Transformation transform) {
        // rotate around the y-axis
        double rad = Math.PI * time;
        float deg = (float) (180.0 * rad / Math.PI);

        // search for the midpoint
        if (time >= 0.5f) {
            deg -= 180.f;
            f_View.setVisibility(View.GONE);
            t_View.setVisibility(View.VISIBLE);
        }

        // invert the flip direction
        if (front)
            deg = -1 * deg;

        Matrix mat = transform.getMatrix();
        cam.save();
        cam.rotateY(deg);
        cam.getMatrix(mat);
        cam.restore();
        mat.preTranslate(-cent_X, -cent_Y);
        mat.postTranslate(cent_X, cent_Y);
    }


    public void flip() {
        front = false;
        View toggleView = t_View;
        t_View = f_View;
        f_View = toggleView;

    }

}
