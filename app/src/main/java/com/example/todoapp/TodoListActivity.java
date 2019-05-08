package com.example.todoapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TodoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            TodoListFragment todoListFragment = new TodoListFragment();
            fm.beginTransaction()
                .add(R.id.fragment_container, todoListFragment)
                .commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewTodoActivity.newIntent(TodoListActivity.this);
                startActivity(intent);
            }
        });
    }

    /////////////////////////
    // WHY DO THESE NOT WORK?
    /////////////////////////

//    @Override
//    public void onStop() {
//        super.onStop();
//
//        View fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setVisibility(View.GONE);
//
//        Log.d("Stop: ", "Fab should now be hidden");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        View fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setVisibility(View.VISIBLE);
//
//        Log.d("Resume: ", "Fab should now be visible");
//    }
}
