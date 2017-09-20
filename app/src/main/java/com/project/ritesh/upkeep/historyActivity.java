package com.project.ritesh.upkeep;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class historyActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv1;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lv1 = (ListView) findViewById(R.id.listView1);

        SQLiteDatabase db = openOrCreateDatabase("Upkeep", MODE_APPEND, null);
        c = db.rawQuery("select * from Trip_Details",null);

        MyAdapter myAdapter = new MyAdapter(this,c,0);
        lv1.setAdapter(myAdapter);
        lv1.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, long l)
    {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(historyActivity.this);
        builderSingle.setIcon(R.drawable.aeroplaneicon);
        builderSingle.setTitle("Spend what is left after Saving");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(historyActivity.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("View");
        arrayAdapter.add("Delete");
        arrayAdapter.add("Cancel");
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
                if(strName=="View")
                {
                    Cursor cursor=(Cursor)adapterView.getItemAtPosition(i);
                    String trip=cursor.getString(cursor.getColumnIndex("_id"));
                    Intent intent=new Intent(historyActivity.this,ViewTripActivity.class);
                    intent.putExtra("Trip",trip);
                    startActivity(intent);
                }
                else if(strName=="Delete")
                {
                    Toast.makeText(historyActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                }
                else if(strName=="Cancel")
                {
                    finish();
                }
            }
        });
        builderSingle.show();


    }


    class MyAdapter extends CursorAdapter
    {

        public MyAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v=inflater.inflate(R.layout.row,viewGroup,false);
            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

            TextView tvdestination=(TextView) view.findViewById(R.id.tvdestination);
            TextView tvsource=(TextView) view.findViewById(R.id.tvsource);
            TextView tvtrip=(TextView) view.findViewById(R.id.tvid);
            String destination=cursor.getString(cursor.getColumnIndex("Destination"));
            String source=cursor.getString(cursor.getColumnIndex("Source"));
            String tripid=cursor.getString(cursor.getColumnIndex("_id"));
            tvdestination.setText(destination);
            tvsource.setText(source);
            tvtrip.setText(tripid);

        }
    }
}
