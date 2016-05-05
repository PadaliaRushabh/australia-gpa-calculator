package com.rushabh.australiagpacalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    AppCompatSpinner universitySpinner, spinnerGrade;
    EditText unit;
    LinearLayout main;
    HashMap<String, Float> grade = new HashMap<String, Float>();
    ArrayAdapter<CharSequence> universityAdapter;
    FloatingActionButton fab;
    int position = 0;
    ArrayAdapter<String> gradeAdapter;
    ArrayList<String> gradList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        main = (LinearLayout) findViewById(R.id.linear_layout_gpa);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        universitySpinner = (AppCompatSpinner) findViewById(R.id.university_spinner);
        universityAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.university,  android.R.layout.simple_spinner_item);
        universityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        universitySpinner.setAdapter(universityAdapter);
        universitySpinner.setSelection(0);


        gradList = new ArrayList<String>();
        gradeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, gradList);

        putGrade(0);
        addCourse(0);

        universitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                putGrade(position);
                setPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse(position);
            }
        });

    }

    private void addCourse(int id) {
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);

        spinnerGrade = new AppCompatSpinner(this);
        spinnerGrade.setId(id);
        spinnerGrade.setLayoutParams(params);

        spinnerGrade.setAdapter(gradeAdapter);

        ll.addView(spinnerGrade);

        unit = new EditText(this);
        unit.setId(id);
        unit.setHint("Course Unit");
        unit.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        unit.setLayoutParams(params);
        ll.addView(unit);

        main.addView(ll);

    }

    public void putGrade(int position){
        grade.clear();
        if(universityAdapter.getItem(position).equals("Australian National University")){
            grade.put("HD" , 7f);
            grade.put("D" , 6f);
            grade.put("CR" , 5f);
            grade.put("P" , 4f);
            grade.put("PS" , 4f);
            grade.put("N" , 0f);
            grade.put("NCN" , 0f);
            grade.put("WN" , 0f);
            grade.put("H1" , 7f);
            grade.put("H2A" , 6f);
            grade.put("H2B" , 5f);
            grade.put("H3" , 4f);
        } else if(universityAdapter.getItem(position).equals("Monash University")){
            grade.put("HD" , 4f);
            grade.put("D" , 3f);
            grade.put("C" , 2f);
            grade.put("P" , 1f);
            grade.put("NP" , 0.7f);
            grade.put("N" , 0.3f);
            grade.put("WF" , 0f);
        }
        gradList= new ArrayList<String>(grade.keySet());
        gradeAdapter.clear();
        gradeAdapter.addAll(gradList);
        gradeAdapter.notifyDataSetChanged();
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

    public void setPosition(int position) {
        this.position = position;
    }
}
