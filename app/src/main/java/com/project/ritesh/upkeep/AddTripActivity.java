package com.project.ritesh.upkeep;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Date;

public class AddTripActivity extends AppCompatActivity {
ImageButton ib1;
    EditText et1,et2,et3,et4,et5,et6;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        ib1= (ImageButton) findViewById(R.id.imageButton1);
        et1= (EditText) findViewById(R.id.editText1);
        et2= (EditText) findViewById(R.id.editText2);
        et3= (EditText) findViewById(R.id.editText3);
        et4= (EditText) findViewById(R.id.editText4);
        et5= (EditText) findViewById(R.id.editText5);
        et6= (EditText) findViewById(R.id.editText6);
        SQLiteDatabase db = openOrCreateDatabase("Upkeep", MODE_APPEND, null);
        db.execSQL("create table if not exists Trip_Details (_id VARCHAR PRIMARY KEY,Destination VARCHAR, Source VARCHAR,Date_Start VARCHAR,Date_End VARCHAR,Approved_budget INTEGER,Balance_budget INTEGER)");
        db.close();
    }
    public void opencalendarstart(View view)
    {
        DateChoose ref=new DateChoose();
        Date d=new Date();
        DatePickerDialog dialog=new DatePickerDialog(this,ref,d.getYear(),d.getMonth(),d.getDay());
        dialog.show();

    }
    class DateChoose implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
        {
            str= i+"/"+(i1+1) +"/"+i2;
            et4.setText(str);
        }

    }
    public void opencalendarend(View view)
    {
        DateChoose1 ref=new DateChoose1();
        Date d=new Date();
        DatePickerDialog dialog=new DatePickerDialog(this,ref,d.getYear(),d.getMonth(),d.getDay());
        dialog.show();
    }
    class DateChoose1 implements DatePickerDialog.OnDateSetListener
    {

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
        {
            str= i+"/"+(i1+1) +"/"+i2;
            et5.setText(str);
        }

    }
    public void submitTrip(View view)
    {
        String trip_id=et1.getText().toString();
        String destination=et2.getText().toString();
        String from=et3.getText().toString();
        String date_start=et4.getText().toString();
        String date_end=et5.getText().toString();
        int aproved_budget=Integer.parseInt(et6.getText().toString());
        int balance_budget=aproved_budget;
        SQLiteDatabase db = openOrCreateDatabase("Upkeep", MODE_APPEND, null);
        String query="insert into Trip_Details(_id,Destination,Source,Date_Start,Date_End,Approved_budget,Balance_budget) values ('"+trip_id+"','"+destination+"','"+from+"','"+date_start+"','"+date_end+"',"+aproved_budget+","+balance_budget+")";
        db.execSQL(query);
        Snackbar snackbar = Snackbar
                .make(view, "......Trip Started", Snackbar.LENGTH_LONG);

        snackbar.show();
    }
}
