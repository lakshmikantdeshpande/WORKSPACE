package com.mithi.androidnotifier;

import com.orm.SugarRecord;

public class DatabaseHandler extends SugarRecord<DatabaseHandler> {

	String message;
	String datetime;

	public DatabaseHandler()
	{

	}

	public DatabaseHandler(String message,String datetime)
	{
		this.message = message;
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return super.toString();
	}




}