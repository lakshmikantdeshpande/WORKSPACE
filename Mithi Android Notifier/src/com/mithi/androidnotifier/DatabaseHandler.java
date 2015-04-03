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
		return message;
	}

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