package com.project.ritesh.upkeep;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner sp1;
   public  String category1,str;
    EditText et1,et2,et3,et4;
    String category[]={"Travel","Meal","Lodging","Misc"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        et1= (EditText) findViewById(R.id.editText1);
        et2= (EditText) findViewById(R.id.editText2);
        et3= (EditText) findViewById(R.id.editText3);
        et4= (EditText) findViewById(R.id.editText4);
        sp1= (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item, category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(this);

        SQLiteDatabase db = openOrCreateDatabase("Upkeep", MODE_APPEND, null);

        db.execSQL("create table if not exists Expense_Details (_id  INTEGER PRIMARY KEY AUTOINCREMENT,Category varchar,Particulars varchar,Amount INTEGER,Date varchar,tid Varchar REFERENCES Trip_Details(_id))");

        db.close();

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        category1=category[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void opencal(View view)
    {
        AddExpenseActivity.DateChoose ref=new AddExpenseActivity.DateChoose();
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
            et3.setText(str);
        }

    }

    public void addexpense(View view)
    {
        String particulars=et1.getText().toString();
        String tripid=et4.getText().toString();
        int amount=Integer.parseInt(et2.getText().toString());
        String date=et3.getText().toString();
        SQLiteDatabase db = openOrCreateDatabase("Upkeep", MODE_APPEND, null);
        String query1="insert into Expense_Details(Category,Particulars,Amount,Date,tid) values ('"+category1+"','"+particulars+"','"+amount+"','"+date+"','"+tripid+"')";
        db.execSQL(query1);
        Snackbar snackbar = Snackbar
                .make(view, "......expense Added", Snackbar.LENGTH_LONG);

        snackbar.show();
    }
}
