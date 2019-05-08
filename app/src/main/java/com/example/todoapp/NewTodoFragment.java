package com.example.todoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NewTodoFragment extends Fragment {

    public static NewTodoFragment newInstance() { return new NewTodoFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceBundle) {

        super.onCreate(savedInstanceBundle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_new_todo, container, false);

        Button saveButton = (Button) view.findViewById(R.id.save_todo);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView headingView = (TextView) view.findViewById(R.id.new_heading);
                TextView infoView = (TextView) view.findViewById(R.id.new_info);
                String heading = headingView.getText().toString();
                String info = infoView.getText().toString();

                if (heading.isEmpty() || heading.length() == 0 || info.isEmpty() || heading.length() == 0) {
                    Toast.makeText(getActivity(), "Both fields are required.", Toast.LENGTH_LONG).show();
                    return;
                }

                Todo todo = new Todo();
                todo.setHeading(heading);
                todo.setInfo(info);
                todo.setComplete(0);

                TodoModel todoModel = TodoModel.get(getActivity());
                todoModel.addTodo(todo);

                Toast.makeText(getActivity(), "Successfully added todo", Toast.LENGTH_LONG).show();

                getActivity().onBackPressed();
            }
        });

        return view;

    }

}
