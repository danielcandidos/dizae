package com.dizae.database;

import java.util.ArrayList;
import com.dizae.models.entities.Problema;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProblemaDAO {
	
	static final String DATABASE_NAME = "dizaedb.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	static final String DATABASE_CREATE = 
			"create table PROBLEMA (" +
			"ID integer primary key autoincrement," +
			"USUARIO  integer," +
			"DESCRICAO text," +
			"CATEGORIA text," +
			"FOTO text," +
			"LATITUDE double," +
			"LONGITUDE double); ";

	public  SQLiteDatabase db;
	private final Context context;
	private MyDbHelper dbHelper;
	
	public ProblemaDAO(Context _context) 
	{
		context = _context;
		dbHelper = new MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public  ProblemaDAO open() throws SQLException 
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
	
	public void updateEntry(Problema problema){
		ContentValues updatedValues = new ContentValues();
		updatedValues.put("USUARIO", problema.getUsuario().getId());
		updatedValues.put("DESCRICAO",problema.getDescricao());
		updatedValues.put("CATEGORIA",problema.getCategoria());
		updatedValues.put("LATITUDE", problema.getLatitude());
		updatedValues.put("LONGITUDE",problema.getLongitude());
		updatedValues.put("FOTO", problema.getFoto());	
		
		String where="ID = ?";
	    db.update("PROBLEMA",updatedValues, where, new String[]{String.valueOf(problema.getId())});
		
	}
	
	public void inserEntry(Problema problema){
		ContentValues newValues = new ContentValues();
		newValues.put("USUARIO", problema.getUsuario().getId());
		newValues.put("DESCRICAO", problema.getDescricao());
		newValues.put("CATEGORIA", problema.getCategoria());
		newValues.put("LATITUDE", problema.getLatitude());
		newValues.put("LONGITUDE",problema.getLongitude());
		newValues.put("FOTO",problema.getFoto());
		
		db.insert("PROBLEMA", null, newValues);		
	}
	
	public void deleteEntry(Problema problema){
		
	}
	
	public Problema getProblema(int id){
		
		Cursor cursor=db.query("PROBLEMA", null, " ID=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
        	cursor.close();
        	return null;
        }
	    cursor.moveToFirst();
	    Problema problema = cursorToProblema(cursor);
	    cursor.close();
		return problema;
	}
	
	public ArrayList<Problema> getProblemasPorCategoria(String categoria){
		Cursor cursor=db.query("PROBLEMA", null, " CATEGORIA=?", new String[]{categoria}, null, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
        	cursor.close();
        	return null;
        }
        ArrayList<Problema> problemas = new ArrayList<Problema>();
        cursor.moveToNext();
        while (!cursor.isLast()) {
        	problemas.add(cursorToProblema(cursor))	;
        	cursor.moveToNext();
		}	    
	    cursor.close();
		return problemas;
	}
	
	private Problema cursorToProblema(Cursor cursor){
		
		UserDAO userDAO = new UserDAO(context);
		Problema problema = new Problema(userDAO.getUser(cursor.getInt(cursor.getColumnIndex("USUARIO"))),
				cursor.getString(cursor.getColumnIndex("DESCRICAO")),
				cursor.getString(cursor.getColumnIndex("CATEGORIA")),
				cursor.getString(cursor.getColumnIndex("FOTO")),
				cursor.getDouble(cursor.getColumnIndex("LATITUDE")),
				cursor.getDouble(cursor.getColumnIndex("LONGITUDE")));
		
		/* problema.setId(cursor.getInt(cursor.getColumnIndex("ID")));
	    problema.setUsuario(userDAO.getUser(cursor.getInt(cursor.getColumnIndex("USUARIO"))));
	    problema.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
	    problema.setLatitude(cursor.getDouble(cursor.getColumnIndex("LATITUDE")));
	    problema.setLongitude(cursor.getDouble(cursor.getColumnIndex("LONGITUDE")));
	    problema.setCategoria(cursor.getString(cursor.getColumnIndex("CATEGORIA")));
	    problema.setFoto(cursor.getString(cursor.getColumnIndex("FOTO"))); */
	    
	    return problema;		
		
	}
	
	
	
		

}
