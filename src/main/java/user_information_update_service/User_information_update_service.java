package user_information_update_service;

import java.io.InputStream;
import java.sql.SQLException;

import model.Update_user;

public class User_information_update_service {
	private DB_user_information_update_service db_user_information_update_service;
	
	public User_information_update_service() throws SQLException{
		this.db_user_information_update_service=new DB_user_information_update_service();
	}
	
	
	/*--------- Change profile photo ------------------ -----------------------------------------------*/
	public String change_profile_photo(InputStream fileInputStream, String user_name) {
		if(db_user_information_update_service.change_profile_photo(fileInputStream,user_name)==true) {
			return "0|Profile photo updated successfully.";
		}
		
		return "2|Internal Server Error.";
	}
	/*-------------------------------------------------------------------------------------------------*/
	
	/*--------- Update user information ------------------ -----------------------------------------------*/
	public String update_user_information(Update_user update_user) {
		if(db_user_information_update_service.update_user_information(update_user)==true) {
			return "0";
		}
		
		System.out.println("TERI MA KI CHUT ");
		return "2|Internal Server Error.";
	}
	/*-------------------------------------------------------------------------------------------------*/
}
