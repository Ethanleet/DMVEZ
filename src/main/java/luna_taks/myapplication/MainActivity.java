package luna_taks.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;






/* Main menu activity. Allows user to navigate to anywhere. */
public class MainActivity extends AppCompatActivity {

    /* Handles initial set up */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.app_bar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // no option here
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    /* These next few methods handle what to do when each
     * topics button was pressed.
     */
    public void beginQuestionnaire( View v ) {
        Intent intent = new Intent( this, DMVActivity.class );
        String value = "\n\t\tBEGIN THE PERMIT TEST!\n";
        intent.putExtra("key", value);
        startActivity(intent);
        finish();
    }


    public void beginFlashCards( View v ) {
        Intent intent = new Intent( this, FlashCard_Activity.class );
        startActivity(intent);
        finish();
    }


    public void beginTips( View v ){
        Intent intent = new Intent( this, activity_tips.class );
        startActivity(intent);
        finish();
    }

    public void beginResources( View v ) {
        Intent intent = new Intent(this, Resources.class);
        startActivity(intent);
        finish();
    }

    public void beginNotes( View v ) {
        Intent intent = new Intent(this, Note.class);
        startActivity(intent);
        finish();
    }


}
