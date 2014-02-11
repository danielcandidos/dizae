package com.dizae.database;

import com.dizae.models.entities.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDAO 
{
	static final String DATABASE_NAME = "dizaedb.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	static final String DATABASE_CREATE = 
			"create table LOGIN "+
			"( ID integer primary key autoincrement," +
			"USERNAME  text," +
			"CPF text," +
			"ENDERECO text," +
			"EMAIL text," +
			"PASSWORD text); ";

	public  SQLiteDatabase db;
	private final Context context;
	private MyDbHelper dbHelper;
	public  UserDAO(Context _context) 
	{
		context = _context;
		dbHelper = new MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public  UserDAO open() throws SQLException 
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

	public void insertEntry(String userName, String cpf, String endereco, String email, String password)
	{
       ContentValues newValues = new ContentValues();
		newValues.put("USERNAME", userName);
		newValues.put("CPF", cpf);
		newValues.put("ENDERECO", endereco);
		newValues.put("EMAIL", email);
		newValues.put("PASSWORD",password);
		db.insert("LOGIN", null, newValues);
	}
	public int deleteEntry(String UserName)
	{
	    String where="EMAIL=?";
	    int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        return numberOFEntriesDeleted;
	}	
	public String getSinlgeEntry(String userName)
	{
		Cursor cursor=db.query("LOGIN", null, " EMAIL=?", new String[]{userName}, null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
        	cursor.close();
        	return "NOT EXIST";
        }
	    cursor.moveToFirst();
		String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
		cursor.close();
		return password;				
	}
	public void  updateEntry(String userName, String cpf, String endereco, String email, String password)
	{
		ContentValues updatedValues = new ContentValues();
		updatedValues.put("USERNAME", userName);
		updatedValues.put("CPF", cpf);
		updatedValues.put("ENDERECO", endereco);
		updatedValues.put("EMAIL", email);
		updatedValues.put("PASSWORD",password);
		
        String where="EMAIL = ?";
	    db.update("LOGIN",updatedValues, where, new String[]{userName});			   
	}
	
	public User getUser(int id){
		
		User usuario = new User();
		
		Cursor cursor=db.query("LOGIN", null, " ID=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
        	cursor.close();
        	return null;
        }
	    cursor.moveToFirst();
	    usuario.setId(cursor.getInt(cursor.getColumnIndex("ID")));
	    usuario.setName(cursor.getString(cursor.getColumnIndex("USERNAME")));
	    usuario.setAddress(cursor.getString(cursor.getColumnIndex("ENDERECO")));
	    usuario.setCpf(cursor.getString(cursor.getColumnIndex("CPF")));
	    usuario.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
		cursor.close();		
		
		return usuario;
		
	}
	
	
}


