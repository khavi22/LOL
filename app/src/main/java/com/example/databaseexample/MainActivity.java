package com.example.databaseexample;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

	DatabaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB=new DatabaseHelper(this, "app");
        setContentView(R.layout.activity_main);
    }
        
    public void doList(View v){
		TextView n = findViewById(R.id.names);
		TextView a = findViewById(R.id.ages);
		Cursor c = myDB.doQuery("SELECT name, age from students");
		int nameIndex = c.getColumnIndex("name");
		int ageIndex = c.getColumnIndex("age");

		if (nameIndex == -1 || ageIndex == -1) {
			System.err.println("Error: One or more columns not found.");
			c.close();
			return;
		}
		while (c.moveToNext()) {
			String name = c.getString(nameIndex);
			long age = c.getLong(ageIndex);
			String ages = Long.toString(age);
			n.setText(name);
			a.setText(ages);
		}

    }
    
    public void doInsert(View v){
		EditText n = findViewById(R.id.InputName);
		 EditText a = findViewById(R.id.InputAge);
		String name = n.getText().toString();
		String age = a.getText().toString();
    	String[] vals = {name,age};
    	myDB.doUpdate("Insert into students(name, age) values (?,?);", vals);
    }

	public void doQuery(View v){
		Cursor c = myDB.doQuery("SELECT name, age from students");
		int nameIndex = c.getColumnIndex("name");
		int ageIndex = c.getColumnIndex("age");

		if (nameIndex == -1 || ageIndex == -1) {
			System.err.println("Error: One or more columns not found.");
			c.close();
			return;
		}

		while (c.moveToNext()) {
			String name = c.getString(nameIndex);
			long age = c.getLong(ageIndex);
			System.out.println(name + ", " + age);
		}
		c.close();
	}
}
