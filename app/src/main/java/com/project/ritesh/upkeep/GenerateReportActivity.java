package com.project.ritesh.upkeep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GenerateReportActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String trip,dateend;
    int budget;
    ListView lv1;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        lv1 = (ListView) findViewById(R.id.listView67);
        SQLiteDatabase db = openOrCreateDatabase("Upkeep", MODE_APPEND, null);
        c = db.rawQuery("select * from Trip_Details",null);
        MyAdapter myAdapter = new MyAdapter(this,c,0);
        c.moveToNext();
        lv1.setAdapter(myAdapter);
        lv1.setOnItemClickListener(this);

        }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {

    }

    class MyAdapter extends CursorAdapter
    {

        public MyAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=inflater.inflate(R.layout.row2,viewGroup,false);
            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView tripid=(TextView) view.findViewById(R.id.tripid);
            TextView budget1=(TextView) view.findViewById(R.id.budget);
            TextView date=(TextView) view.findViewById(R.id.balance);
            trip=cursor.getString(cursor.getColumnIndex("_id"));
            budget= cursor.getInt(cursor.getColumnIndex("Approved_budget"));
            dateend=cursor.getString(cursor.getColumnIndex("Date_End"));


           // SQLiteDatabase db = openOrCreateDatabase("Upkeep", MODE_APPEND, null);
           // String query1 = "Select sum(Amount) from Expense_Details Where tid='" + trip + "'";
           // Cursor cursor1 = db.rawQuery(query1, null);
           // if ((cursor1.moveToNext())) {
           //     amount = cursor1.getInt(0);

           // int balance1=budget-amount;
            tripid.setText(trip);
            budget1.setText(budget);
            date.setText(dateend);
          //  balance.setText(balance1);
           // db.close();

        }
    }


    }
