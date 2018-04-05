package com.johansson.daniel.dailyworkout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "userinput.txt";

    EditText txtActivity;
    EditText txtMiles;
    EditText txtDate;

    TextView txtHeader;
    TextView txtOutput;

    Button btnAdd;

    ArrayList<DailyActivity> dailyActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtActivity = findViewById(R.id.txtActivity);
        txtMiles = findViewById(R.id.txtMiles);
        txtDate = findViewById(R.id.txtDate);

        txtOutput = findViewById(R.id.txtOutput);
        txtHeader = findViewById(R.id.txtHeader);

        txtHeader.setText("Activity" + " " + "Miles" + " " + "Dates");

        dailyActivities = new ArrayList<DailyActivity>();

        loadData();
        outputData();
    }

    public void add(View v){
        String activity = txtActivity.getText().toString();
        String miles = txtMiles.getText().toString();
        String date = txtDate.getText().toString();

        DailyActivity dailyActivity = new DailyActivity(activity, miles, date);
        dailyActivities.add(dailyActivity);
        saveData();
        loadData();
        outputData();

        txtActivity.getText().clear();
        txtMiles.getText().clear();
        txtDate.getText().clear();
    }

    public void saveData(){
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            OutputStreamWriter outputFile = new OutputStreamWriter(fos);

            for (int i = 0; i < dailyActivities.size(); i++){
                outputFile.write(dailyActivities.get(i).getActivity() + "," + dailyActivities.get(i).getMiles() + "," + dailyActivities.get(i).getDate() + "\n");
            }

            outputFile.flush();
            outputFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData(){

        dailyActivities.clear();

        File file = getApplicationContext().getFileStreamPath(FILE_NAME);
        String lineFromFile;

        if (file.exists()){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(FILE_NAME)));

                while ((lineFromFile = reader.readLine()) != null) {
                    StringTokenizer tokens = new StringTokenizer(lineFromFile, ",");

                    DailyActivity dailyActivity = new DailyActivity(tokens.nextToken(), tokens.nextToken(), tokens.nextToken());
                    dailyActivities.add(dailyActivity);
                }

                reader.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void outputData(){
        String userInput = "";

        for (int i = 0; i < dailyActivities.size(); i++){
            userInput = userInput + dailyActivities.get(i).getActivity() + " " + dailyActivities.get(i).getMiles() + " " + dailyActivities.get(i).getDate() + "\n";
        }

        txtOutput.setText(userInput);
    }

}
