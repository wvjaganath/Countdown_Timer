package edu.umflint.countdown_timer;


import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //Variables

    private Button startbtn;
    EditText hours;
    EditText mins;
    EditText secs;
    TextView txtvw4;
    TextView txtvw5;
    TextView txtvw1;
    private int totalTimeCountInMilliseconds;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding Edittext and Buttons
        startbtn = (Button) findViewById(R.id.button);
        hours = (EditText) findViewById(R.id.editText3);
        mins = (EditText) findViewById(R.id.editText2);
        secs = (EditText) findViewById(R.id.editText);
        txtvw4 = (TextView) findViewById(R.id.textView4);
        txtvw5 = (TextView) findViewById(R.id.textView5);
        txtvw1 = (TextView) findViewById(R.id.textView);

        // Setting the completion text box to invisible
        txtvw1.setVisibility(View.INVISIBLE);

        // Listener for Start Button
        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //To minimize the number pad after entering the time
            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(startbtn.getWindowToken(), 0);

                //On reset
                String btn = startbtn.getText().toString();
                if (btn == "Reset") {
                    hours.setVisibility(View.VISIBLE);
                    mins.setVisibility(View.VISIBLE);
                    secs.setVisibility(View.VISIBLE);
                    txtvw4.setVisibility(View.VISIBLE);
                    txtvw5.setVisibility(View.VISIBLE);
                    txtvw1.setVisibility(View.INVISIBLE);
                    startbtn.setText("Start");
                } else if (secs.getText().toString().equals("00") && mins .getText().toString().equals("00") && hours.getText().toString().equals("00")) {
                    Toast.makeText(MainActivity.this, "Please Enter Some Value...",Toast.LENGTH_LONG).show();
                } else
                    {
                    secs.setCursorVisible(false);
                    mins.setCursorVisible(false);
                    hours.setCursorVisible(false);
                    new timer().execute();
                }

            }
        });
    }

    private  class timer extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected void onPreExecute() {

         /* String time = secs.getText().toString();
            String time1 = mins.getText().toString();
            String time2 = hours.getText().toString();
            Toast.makeText(getApplicationContext(), time, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), time, Toast.LENGTH_LONG).show();
           Toast.makeText(getApplicationContext(), time1, Toast.LENGTH_LONG).show();
           Toast.makeText(getApplicationContext(), time2, Toast.LENGTH_LONG).show(); */

            // To Calculate the total time entered
            int time = 0;
            int time1 = 0;
            int time2 = 0;
            time = Integer.parseInt(secs.getText().toString());
            time1 = Integer.parseInt(mins.getText().toString());
            time2 = Integer.parseInt(hours.getText().toString());
            totalTimeCountInMilliseconds = (time + (time1 * 60) + (time2 * 3600));

        }

        @Override
        protected Integer doInBackground(Integer... params) {
            //Starting the countdown
            for (int i = totalTimeCountInMilliseconds; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                    // To update the progress
                    publishProgress(i);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... i) {
            // Updating the Edittext with the countdown
            int post = i[0].intValue();
            secs.setText(String.format("%02d", post % 60));
            mins.setText(String.format("%02d", post / 60));
            hours.setText(String.format("%02d", post /3600 ));
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);

            //On finish, setting time text to invisible
            hours.setVisibility(View.INVISIBLE);
            mins.setVisibility(View.INVISIBLE);
            secs.setVisibility(View.INVISIBLE);
            txtvw4.setVisibility(View.INVISIBLE);
            txtvw5.setVisibility(View.INVISIBLE);
            txtvw1.setVisibility(View.VISIBLE);
            txtvw1.setText("Time's up!");

            Toast.makeText(getApplicationContext(), "CountDown is done", Toast.LENGTH_LONG).show();

            startbtn.setText("Reset");
        }
    }
}



