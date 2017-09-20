package com.project.ritesh.upkeep;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ViewTripActivity extends AppCompatActivity {
    TextView tv2;
    String trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trip);
        tv2 = (TextView) findViewById(R.id.textView2);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        trip = bundle.getString("Trip");
        SQLiteDatabase db = openOrCreateDatabase("Upkeep", MODE_APPEND, null);
        String query="Select Category,sum(Amount) from Expense_Details Where tid='"+trip+"' group by Category";
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToNext())
        {
            String category = cursor.getString(0);
           // String amount = cursor.getString(1);
            int amount = cursor.getInt(1);
            tv2.setText("Category:"+category+"\nAmount:"+amount);

        }
        db.close();

    }
}
