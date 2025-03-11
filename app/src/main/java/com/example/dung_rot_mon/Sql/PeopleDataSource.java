package com.example.dung_rot_mon.Sql;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PeopleDataSource {
    // Các trường database.
    private SQLiteDatabase database;
    private Sqlheplper dbHelper;
    private String[] allColumns = {Sqlheplper.COLUMN_ID,
            Sqlheplper.COLUMN_PERSON};

    public PeopleDataSource(Context context) {
        dbHelper = new Sqlheplper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Person createPerson(String pName) {
        ContentValues values = new ContentValues();
        values.put(Sqlheplper.COLUMN_PERSON, pName);
        long insertId = database.insert(Sqlheplper.TABLE_PEOPLE, null,
                values);
        Cursor cursor = database.query(Sqlheplper.TABLE_PEOPLE,
                allColumns, Sqlheplper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Person newPerson = cursorToPerson(cursor);
        cursor.close();
        return newPerson;
    }

    public void deletePerson(Person p) {
        long id = p.getId();
        Log.e("SQLite", "Person entry deleted with id: " + id);
        database.delete(Sqlheplper.TABLE_PEOPLE, Sqlheplper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Person> getAllPeople() {
        List<Person> people = new ArrayList<Person>();

        Cursor cursor = database.query(Sqlheplper.TABLE_PEOPLE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Person person = cursorToPerson(cursor);
            people.add(person);
            cursor.moveToNext();
        }
        // Nhớ đóng con trỏ lại nhé.
        cursor.close();
        return people;
    }

    private Person cursorToPerson(Cursor cursor) {
        Person person = new Person();
        person.setId(cursor.getLong(0));
        person.setName(cursor.getString(1));
        return person;
    }
}
