package com.johansson.daniel.dailyworkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    //Create variables used across the application
    private static final String FILE_NAME = "userinput.txt";

    EditText txtActivity;
    EditText txtMiles;
    EditText txtDate;
    TableLayout outputTable;
    Button btnAdd;
    ArrayList<DailyActivity> dailyActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //User input fields
        txtActivity = findViewById(R.id.txtActivity);
        txtMiles = findViewById(R.id.txtMiles);
        txtDate = findViewById(R.id.txtDate);

        //Table layout for data output
        outputTable = findViewById(R.id.outputTable);

        //ArrayList to hold user input
        dailyActivities = new ArrayList<>();

        //Call load and display functions
        loadData();
        outputData();
    }

    //Function for actions when add button is clicked
    public void add(View v){
        //Make sure no input fields are empty before submitting to file to avoid crash
        if ((!txtActivity.getText().toString().equals("")) && (!txtMiles.getText().toString().equals("")) && (!txtDate.getText().toString().equals("")))
        {
            //Get text from user input
            String activity = txtActivity.getText().toString();
            String miles = txtMiles.getText().toString();
            String date = txtDate.getText().toString();

            //Add user input to ArrayList
            DailyActivity dailyActivity = new DailyActivity(activity, miles, date);
            dailyActivities.add(dailyActivity);


            //Call functions to save, load, and display the user input
            saveData();
            loadData();
            outputData();

            //Clear user input TextViews
            txtActivity.getText().clear();
            txtMiles.getText().clear();
            txtDate.getText().clear();
        } else {
            // If any fields are empty, display Toast message
            Toast.makeText(MainActivity.this, "Please enter all information", Toast.LENGTH_LONG).show();
        }
    }

    //Function to save user input data
    public void saveData(){

        FileOutputStream fos = null;

        try {
            //Create output stream functions to read the text file
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            OutputStreamWriter outputFile = new OutputStreamWriter(fos);

            //Loop through the arraylist and read each input and separate with comma
            for (int i = 0; i < dailyActivities.size(); i++){
                outputFile.write(dailyActivities.get(i).getActivity() + "," + dailyActivities.get(i).getMiles() + "," + dailyActivities.get(i).getDate() + "\n");
            }

            //Flush and close the output file
            outputFile.flush();
            outputFile.close();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Load the stored data
    public void loadData(){

        //Clear the arraylist
        dailyActivities.clear();
        //Clear old data from the table, except the first row holding the header
        while (outputTable.getChildCount() > 1)
            outputTable.removeView(outputTable.getChildAt(outputTable.getChildCount() - 1));

        //Create a file variable along with a variable for data from the file
        File file = getApplicationContext().getFileStreamPath(FILE_NAME);
        String lineFromFile;

        //Make sure the file exists
        if (file.exists()){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(FILE_NAME)));

                //Read lines from the file, as long as the line is not empty
                while ((lineFromFile = reader.readLine()) != null) {
                    //Recognize comma delimiter
                    StringTokenizer tokens = new StringTokenizer(lineFromFile, ",");

                    //Populate the arraylist with data from the text file
                    DailyActivity dailyActivity = new DailyActivity(tokens.nextToken(), tokens.nextToken(), tokens.nextToken());
                    dailyActivities.add(dailyActivity);
                }

                //Close the reader
                reader.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Display the stored data
    public void outputData(){

        //Create a new arraylist and reverse the order so the latest entry is displayed first
        ArrayList<DailyActivity> outputList = new ArrayList<>();
        int size = dailyActivities.size()-1;

        for(int i=size;i>=0;i--){
            outputList.add(dailyActivities.get(i));
        }

        //Loop through the arraylist, create a new row on each loop and new TextViews for each value and finally insert into the outputTable
        for (int i = 0; i < outputList.size(); i++){
            TableRow row = new TableRow(this);
            TextView activities = new TextView(this);
            TextView miles = new TextView(this);
            TextView date = new TextView(this);
            activities.setText(outputList.get(i).getActivity());
            miles.setText(outputList.get(i).getMiles());
            date.setText(outputList.get(i).getDate());
            row.addView(activities);
            row.addView(miles);
            row.addView(date);
            outputTable.addView(row);
        }
    }

}