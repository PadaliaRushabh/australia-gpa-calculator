package com.rushabh.australiagpacalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    AppCompatSpinner universitySpinner;
    LinearLayout main;
    HashMap<String, Float> grade = new HashMap<String, Float>();
    ArrayAdapter<CharSequence> universityAdapter;
    FloatingActionButton fab;
    int position = 0;
    int count = 1;
    ArrayAdapter<String> gradeAdapter;
    ArrayList<String> gradList;
    ArrayList<Integer> ids;
    HashMap<Integer, Integer> gradeUnitPair;

    TextView creditPoint, gpaUnitScore, gpa;

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ids = new ArrayList<Integer>();
        main = (LinearLayout) findViewById(R.id.linear_layout_gpa);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        creditPoint = (TextView)findViewById(R.id.credit_point);
        gpaUnitScore = (TextView)findViewById(R.id.gpa_unit_score);
        gpa = (TextView) findViewById(R.id.gpa);

        universitySpinner = (AppCompatSpinner) findViewById(R.id.university_spinner);
        universityAdapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.university,  android.R.layout.simple_spinner_item);
        universityAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        universitySpinner.setAdapter(universityAdapter);
        universitySpinner.setSelection(0);


        gradList = new ArrayList<String>();
        gradeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, gradList);

        gradeUnitPair = new HashMap<Integer, Integer>();
        putGrade(0);
        addCourse();


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
                addCourse();
            }
        });

    }

    private void addCourse() {
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f);


        AppCompatSpinner spinnerGrade = new AppCompatSpinner(this);

        spinnerGrade.setId(count*1);
        spinnerGrade.setLayoutParams(params);
        spinnerGrade.setAdapter(gradeAdapter);

        ll.addView(spinnerGrade);

        spinnerGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG , calcGPA()+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText unit = new EditText(this);
        unit.setId(count*100);
        gradeUnitPair.put(spinnerGrade.getId(), unit.getId());
        unit.setHint("Course Unit");
        unit.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        unit.setLayoutParams(params);
        ll.addView(unit);

        unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG , calcGPA()+"");
            }
        });
        count++;

        main.addView(ll);

    }

    private float calcGPA() {
        float gpa = 0;
        String grade;
        float gradeValue = 0f;
        int unit = 0;
        int singleUnit = 0;
        float weightedGPAUnit = 0;


        for (Map.Entry<Integer, Integer> entry : gradeUnitPair.entrySet()) {
            Spinner spinnerGrade = getspinner(entry.getKey());
            EditText textUnit = getUnit(entry.getValue());
            try {
                singleUnit = (!textUnit.getText().toString().trim().isEmpty() ? Integer.parseInt(textUnit.getText().toString()) : 0);
                unit = unit + singleUnit;
                if (!spinnerGrade.getSelectedItem().toString().equals("")) {
                    grade = spinnerGrade.getSelectedItem().toString() != "" ? spinnerGrade.getSelectedItem().toString() : "";
                    if(this.grade.containsKey(grade)) {
                        gradeValue = this.grade.get(grade);
                    }
                    weightedGPAUnit = weightedGPAUnit + (gradeValue * singleUnit);
                    gpa = weightedGPAUnit/unit;
                    if(Float.isNaN(gpa)) gpa = 0.0f;
                }
            } catch(NumberFormatException ex){
                ex.printStackTrace();
            }
        }
        setAnswer(unit, weightedGPAUnit, gpa);
        return gpa;
    }

    private void setAnswer(int creditPoint, float gpaUnitScore, float gpa){
        this.creditPoint.setText("Credit Pont:" + creditPoint);
        this.gpaUnitScore.setText("GPA Unit Score:" + gpaUnitScore);
        this.gpa.setText("GPA:" + gpa);
    }

    private Spinner getspinner(int id){
        return (Spinner) findViewById(id);
    }
    private EditText getUnit(int id){
        return (EditText) findViewById(id);
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
        if (id == R.id.action_help) {
            return true;
        } else if(id == R.id.action_info){
            
        }

        return super.onOptionsItemSelected(item);
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
