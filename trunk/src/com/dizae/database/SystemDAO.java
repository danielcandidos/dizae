package com.dizae.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SystemDAO {
	
	static final String DATABASE_NAME = "dizaedb.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	static final String DATABASE_CREATE = "" +
			"create table SYSTEM (" +
			"param text primary key," +
			"valor  text); ";

	public  SQLiteDatabase db;
	private final Context context;
	private MyDbHelper dbHelper;
	
	public SystemDAO(Context _context) 
	{
		context = _context;
		dbHelper = new MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public  SystemDAO open() throws SQLException 
	{
		db = dbHelper.getWritableDatabase();
		return this;
	}
	public void close() 
	{
		db.close();
	}

	public  SQLiteDatabase getDatabaseInstance()
	{
		return db;
	}
	
	public String getParam(String param){
		
		Cursor cursor=db.query("SYSTEM", null, " param=?", new String[]{param}, null, null, null, null);
        if(cursor.getCount()<1) 
        {
        	cursor.close();
        	return "";
        }
	    cursor.moveToFirst();
		String valor= cursor.getString(cursor.getColumnIndex("valor"));
		cursor.close();
		return valor;	
	}
	
	public void setParam(String param, String valor){
		
	}
	
	

}
