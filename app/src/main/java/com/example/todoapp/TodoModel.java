package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.TodoBaseHelper;
import database.TodoCursorWrapper;
import database.TodoDbSchema;

public class TodoModel {

    private static TodoModel sTodoModel;
    private static Context mContext;
    private SQLiteDatabase mDatabase;

    public static TodoModel get(Context context) {

        mContext = context.getApplicationContext();
        if (sTodoModel == null) {
            sTodoModel = new TodoModel(context);
        }

        return sTodoModel;
    }

    private TodoModel(Context context){

        mContext = context.getApplicationContext();
        mDatabase = new TodoBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void updateTodo(Todo todo){
        String uuidString = todo.getID().toString();
        ContentValues values = getContentValues(todo);

        mDatabase.update(TodoDbSchema.TodoTable.NAME, values,
                TodoDbSchema.TodoTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private TodoCursorWrapper queryTodoList(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TodoDbSchema.TodoTable.NAME,
                null, // null for all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new TodoCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Todo todo) {
        ContentValues values = new ContentValues();
        values.put(TodoDbSchema.TodoTable.Cols.UUID, todo.getID().toString());
        values.put(TodoDbSchema.TodoTable.Cols.TITLE, todo.getHeading());
        values.put(TodoDbSchema.TodoTable.Cols.DETAIL, todo.getInfo());
        values.put(TodoDbSchema.TodoTable.Cols.DATE, todo.getDate().getTime());
        values.put(TodoDbSchema.TodoTable.Cols.IS_COMPLETE, todo.isComplete()==1 ? 1 : 0);

        return values;
    }

    public List<Todo> getTodoList() {
        ArrayList<Todo> todoList = new ArrayList<>();

        TodoCursorWrapper cursor = queryTodoList(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                todoList.add(cursor.getTodo());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return todoList;

    }

    public Todo getTodo(UUID id){

        TodoCursorWrapper cursor = queryTodoList(
                TodoDbSchema.TodoTable.Cols.UUID + " = ?",
                new String[] {id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTodo();
        } finally {
            cursor.close();
        }
    }

    public void addTodo(Todo todo){
        ContentValues values = getContentValues(todo);
        mDatabase.insert(TodoDbSchema.TodoTable.NAME, null, values);
    }

    public void deleteTodo(UUID id) {
        mDatabase.delete(TodoDbSchema.TodoTable.NAME, TodoDbSchema.TodoTable.Cols.UUID + " = ?",
                new String[] {id.toString() });
    }

}