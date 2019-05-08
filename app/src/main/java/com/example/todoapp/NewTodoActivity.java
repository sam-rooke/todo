package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class NewTodoActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, NewTodoActivity.class);
    }

    protected Fragment createFragment() {
        return NewTodoFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null){

            Fragment newTodoFragment = createFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_container, newTodoFragment)
                    .commit();
        }
    }

}
