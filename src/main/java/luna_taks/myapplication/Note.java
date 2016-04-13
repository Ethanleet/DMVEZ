package luna_taks.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Note extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        readFromFile();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void saveNotes( View v ) {

        EditText noteTake = (EditText) findViewById(R.id.takeNotes);
        String notesToSave = noteTake.getText().toString();
        ArrayList<String> write = new ArrayList<>();
        write.add(notesToSave);
        try {
            FileOutputStream file = openFileOutput("note.txt", MODE_WORLD_WRITEABLE);
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(file));
            for( int i = 0; i < write.size(); i++ ) {
                writer.write(write.get(i));
            }
            writer.close();
        }

        catch (Exception e) {
            Log.e("Unable to save", e.toString());
        }
    }


    public void clearNotes( View v ) {
        EditText noteTake = (EditText) findViewById(R.id.takeNotes);
        noteTake.setText("");
        try {
            FileOutputStream file = openFileOutput("note.txt", MODE_WORLD_WRITEABLE);
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(file));
            writer.write("");
            writer.close();
        }

        catch (Exception e) {
            Log.e("Unable to save", e.toString());
        }
    }


    public void readFromFile() {
        String file = "note.txt";
        String line = null;
        EditText editor = (EditText) findViewById(R.id.takeNotes);

        try {

            FileInputStream reader = openFileInput("note.txt");
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(reader));
            ArrayList<String> lines = new ArrayList<>();
            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            for( int i = 0; i < lines.size(); i++ ) {
                editor.setText(lines.get(i), TextView.BufferType.EDITABLE);
            }

            // close file
            bufferedReader.close();

        }

        catch(Exception e) {
            Log.e("Error when reading", e.toString());
        }




    }

}
