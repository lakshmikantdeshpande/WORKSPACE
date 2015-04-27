package com.mithi.androidnotifier;

import com.orm.SugarRecord;

//We are using this class to handle SQLite database

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
		return message;
	}

	//GETTERS AND SETTERS
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	


}