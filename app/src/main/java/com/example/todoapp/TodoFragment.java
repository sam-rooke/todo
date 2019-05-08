package com.example.todoapp;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class TodoFragment extends Fragment {

    private static final String ARG_TODO_ID = "todo_id";

    private Todo mTodo;
    private EditText mEditTextHeading;
    private EditText mEditTextInfo;
    private CheckBox mCheckBoxIsComplete;
    private Button mButtonDate;

    public static TodoFragment newInstance(UUID todoID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TODO_ID, todoID);

        TodoFragment fragment = new TodoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setHasOptionsMenu(true);

        UUID todoID = (UUID) getArguments().getSerializable(ARG_TODO_ID);
        mTodo = TodoModel.get(getActivity()).getTodo(todoID);

        ((TodoActivity) getActivity()).setActionBarTitle(mTodo.getHeading());
    }

    @Override
    public void onPause() {
        super.onPause();

        TodoModel.get(getActivity()).updateTodo(mTodo);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo, container, false);

        mEditTextHeading = (EditText) view.findViewById(R.id.todo_heading);
        mEditTextHeading.setText(mTodo.getHeading());
        mEditTextHeading.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No need to add anything here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTodo.setHeading(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No need to add anything here
            }
        });

        mEditTextInfo = (EditText) view.findViewById(R.id.todo_info);
        mEditTextInfo.setText(mTodo.getInfo());
        mEditTextInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No need to add anything here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTodo.setInfo(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No need to add anything here
            }
        });

        mCheckBoxIsComplete = (CheckBox) view.findViewById(R.id.todo_complete);
        mCheckBoxIsComplete.setChecked(mTodo.isComplete() == 1 ? true : false);
        mCheckBoxIsComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTodo.setComplete(isChecked == true ? 1 : 0);
            }
        });

        mButtonDate = (Button) view.findViewById(R.id.button_date);
        String formattedDate = DateFormat.format("dd-MM-yyyy", mTodo.getDate()).toString();
        mButtonDate.setText(formattedDate);

        mButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment(mTodo, mButtonDate);
                datePicker.show(getFragmentManager(), "date picker");
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_todo:

                TodoModel todoModel = TodoModel.get(getActivity());
                todoModel.deleteTodo(mTodo.getID());

                Toast.makeText(getActivity(), "Successfully deleted todo", Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
