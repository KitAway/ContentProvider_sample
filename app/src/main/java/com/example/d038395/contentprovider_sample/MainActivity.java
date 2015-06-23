package com.example.d038395.contentprovider_sample;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClickAddName(View view) {
        ContentValues values = new ContentValues();
        String name=((EditText)findViewById(R.id.student_name)).getText().toString();
        values.put(StudentProvider.NAME,name);

        String grade=((EditText)findViewById(R.id.student_grade)).getText().toString();
        values.put(StudentProvider.GRADE, grade);
        ContentResolver cp=getContentResolver();
        Uri uri =cp.insert(StudentProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
    }
    public void onClickDelete(View view) {
        String num=((EditText)findViewById(R.id.student_delete_Num)).getText().toString();
        Uri uri=Uri.parse(StudentProvider.URL+'/'+num);
        int index=getContentResolver().delete(uri,null,null);
        if (index!=0) {
            Toast.makeText(getBaseContext(),index+" record(s) is(are) deleted",
                    Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(getBaseContext(), "No record exists.", Toast.LENGTH_SHORT).show();
    }
    public void onClickRetrieve(View view) {
        Uri students = StudentProvider.CONTENT_URI;
        Cursor c =getContentResolver().query(students,null,null,null,"name");
        // Some providers return null if an error occurs, others throw an exception
        if (null == c) {
    /*
     * Insert code here to handle the error. Be sure not to use the cursor! You may want to
     * call android.util.Log.e() to log this error.
     *
     */
            Toast.makeText(getBaseContext(), "Error happened.", Toast.LENGTH_SHORT).show();
    // If the Cursor is empty, the provider found no matches
        } else if (c.getCount() < 1) {

    /*
     * Insert code here to notify the user that the search was unsuccessful. This isn't necessarily
     * an error. You may want to offer the user the option to insert a new row, or re-type the
     * search term.
     */
            Toast.makeText(getBaseContext(), "Empty provider.", Toast.LENGTH_SHORT).show();
        } else {
            // Insert code here to do something with the results
            if (c.moveToFirst()) {
                do{
                    Toast.makeText(this, c.getString(c.getColumnIndex(StudentProvider._ID)) +
                            ", " + c.getString(c.getColumnIndex(StudentProvider.NAME)) +
                            ", " + c.getString(c.getColumnIndex( StudentProvider.GRADE)),
                            Toast.LENGTH_SHORT).show();
                } while (c.moveToNext());
            }
        }
    }
}
