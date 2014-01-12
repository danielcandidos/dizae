package com.dizae.models.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dizae.database.ExternalDataBase;
import com.dizae.models.entities.User;

public abstract class UserController {

	public static boolean register(User user) throws Exception {
		
		ExternalDataBase.connect();

		if(!checkExistenceEmail(user.getEmail())){
			return false;
		}

		if(!checkExistenceCpf(user.getCpf())){
			return false;
		}
		
		Boolean result = insert(user);
		ExternalDataBase.diconnect();

		return result;

	}

	private static boolean checkExistenceEmail(String email){
		try{
			Connection connection = ExternalDataBase.getConnection();
			PreparedStatement query = connection.prepareStatement(
					"SELECT email FROM user WHERE email=?");
			query.setString(1, email);
			ResultSet result = query.executeQuery();

			if(result.next()){
				return true;
			}else{
				return false;
			}

		}catch(Exception e){
			return false;
		}



	}

	private static boolean checkExistenceCpf(String cpf){
		try{
			Connection connection = ExternalDataBase.getConnection();
			PreparedStatement query = connection.prepareStatement(
					"SELECT cpf FROM user WHERE cpf=?");
			query.setString(1, cpf);
			ResultSet result = query.executeQuery();

			if(result.next()){
				return true;
			}else{
				return false;
			}

		}catch(Exception e){
			return false;
		}

	}

	private static boolean insert(User user) {
		try{
			Connection connection = ExternalDataBase.getConnection();

			PreparedStatement insertionQuery = connection.prepareStatement(
					"INSERT INTO user (name,email,password,cpf,address) VALUES (?,?,?,?,?)");
			insertionQuery.setString(1, user.getName());
			insertionQuery.setString(2, user.getEmail());
			insertionQuery.setString(3, user.getPassword());
			insertionQuery.setString(4, user.getCpf());
			insertionQuery.setString(5, user.getAddress());

			insertionQuery.executeUpdate();
			return true;
		}catch(Exception e){
			return false;
		}


	}

}
