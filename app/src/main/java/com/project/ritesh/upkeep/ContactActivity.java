package com.project.ritesh.upkeep;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

    }
    public void displaycontact(View view)
    {
        Snackbar snackbar = Snackbar
                .make(view, "Developer-Ritesh Sinha\nemail-ritesh.sinha18@ymail.com", Snackbar.LENGTH_LONG);

        snackbar.show();
    }
}
