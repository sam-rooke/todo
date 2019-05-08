package com.example.todoapp;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class TodoListFragment extends Fragment {
    private RecyclerView mTodoRecyclerView;
    TodoAdapter mTodoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);

        mTodoRecyclerView = (RecyclerView) view.findViewById(R.id.todo_recycler_view);
        mTodoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public class TodoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Todo mTodo;
        private TextView mTextViewHeading;
        private TextView mTextViewInfo;
        private TextView mTextViewDate;

        public TodoHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.todo_list_item, parent, false));

            itemView.setOnClickListener(this);

            itemView.setBackgroundColor(Color.parseColor(randomColour()));

            mTextViewHeading = (TextView) itemView.findViewById(R.id.todo_heading);
            mTextViewInfo = (TextView) itemView.findViewById(R.id.todo_info);
            mTextViewDate = (TextView) itemView.findViewById(R.id.todo_date);
        }

        public void onClick(View view) {
            Intent intent = TodoActivity.newIntent(getActivity(), mTodo.getID());
            startActivity(intent);
        }

        public void bind(Todo todo) {
            mTodo = todo;
            mTextViewHeading.setText(mTodo.getHeading());
            mTextViewInfo.setText(mTodo.getInfo());

            String formattedDate = DateFormat.format("dd-MM-yyyy", mTodo.getDate()).toString();

            mTextViewDate.setText(formattedDate);
        }

        private String randomColour() {
            Random random = new Random();

            // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
            int nextInt = random.nextInt(0xffffff + 1);

            // format it as hexadecimal string (with hashtag and leading zeros)
            String colorCode = String.format("#%06x", nextInt);

            // print it
            System.out.println(colorCode);
            return colorCode;
        }

    }

    public class TodoAdapter extends RecyclerView.Adapter<TodoListFragment.TodoHolder> {

        private List<Todo> mTodoList;

        public TodoAdapter(List<Todo> todos) { mTodoList = todos; }

        @Override
        public TodoListFragment.TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new TodoHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TodoHolder holder, int position) {
            Todo todo = mTodoList.get(position);
            holder.bind(todo);
        }

        @Override
        public int getItemCount() { return mTodoList.size(); }

        public void setTodoList(List<Todo> todoList) { mTodoList = todoList; }

    }

    private void updateUI() {
        TodoModel todoModel = TodoModel.get(getActivity());
        List<Todo> todoList = todoModel.getTodoList();

        if (mTodoAdapter == null) {
            mTodoAdapter = new TodoAdapter(todoList);
            mTodoRecyclerView.setAdapter(mTodoAdapter);
        } else {
            mTodoAdapter.setTodoList(todoList);
            mTodoAdapter.notifyDataSetChanged();
        }
    }
}
