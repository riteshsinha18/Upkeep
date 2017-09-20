package com.project.ritesh.upkeep;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    TextView tv1;
    String tripid,destination,from;
    int budget,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        tv1 = (TextView) findViewById(R.id.textView2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        SQLiteDatabase db = openOrCreateDatabase("Upkeep", MODE_APPEND, null);
        db.execSQL("create table if not exists Trip_Details (_id VARCHAR PRIMARY KEY,Destination VARCHAR, Source VARCHAR,Date_Start VARCHAR,Date_End VARCHAR,Approved_budget INTEGER,Balance_budget INTEGER)");
        String query = "Select * from Trip_Details";
        StringBuilder sb = new StringBuilder();

        Cursor cursor = db.rawQuery(query, null);
        try{

            if (cursor.moveToNext()) {
                tripid = cursor.getString(0);
                destination = cursor.getString(1);
                from = cursor.getString(2);
                budget = cursor.getInt(5);


            }
            String query1 = "Select sum(Amount) from Expense_Details Where tid='" + tripid + "'";
            Cursor cursor1 = db.rawQuery(query1, null);
            if ((cursor1.moveToNext())) {
                amount = cursor1.getInt(0);
            }
            int balance = budget - amount;
            sb.append("Trip:" + from);
            sb.append("->");
            sb.append(" " + destination);
            sb.append("\n");
            sb.append("Approved Budget:" + budget);
            sb.append("\n");
            sb.append("Balance:" + balance);
            db.close();

            tv1.setText(sb);
            if (balance<=(0.1*budget))
            {
                Toast.makeText(this, "You're about to go OVERLIMIT", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Intent intent=new Intent(Main2Activity.this,AddTripActivity.class);
            startActivity(intent);
        }

        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.exprep:Intent intent=new Intent(Main2Activity.this,GenerateReportActivity.class);
                             startActivity(intent);
                             break;
            case R.id.contact:Intent intent1 = new Intent(Main2Activity.this, ContactActivity.class);
                              startActivity(intent1);
                              break;
            case R.id.exit:Toast.makeText(this, "Closing App", Toast.LENGTH_SHORT).show();
                           finish();
                            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void triphistory(View view) {
        Intent intent = new Intent(Main2Activity.this, historyActivity.class);
        startActivity(intent);

    }

    public void dialog(View view) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Main2Activity.this);
        builderSingle.setIcon(R.drawable.aeroplaneicon);
        builderSingle.setTitle("Spend what is left after Saving");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Add Expenses");
        arrayAdapter.add("Edit Changes");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();

            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if(strName=="Add Expenses")
                {
                    Intent intent=new Intent(Main2Activity.this,AddExpenseActivity.class);
                    startActivity(intent);
                }
                else if(strName=="Edit Changes")
                {
                    Toast.makeText(Main2Activity.this, "edt change", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builderSingle.show();

    }
}
