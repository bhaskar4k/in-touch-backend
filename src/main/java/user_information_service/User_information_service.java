package user_information_service;

import java.sql.SQLException;

import model.User;

public class User_information_service {
	private DB_user_information_service db_user_information_service;
	
	public User_information_service() throws SQLException {
		db_user_information_service=new DB_user_information_service();
	}
	
	
	/*Checking if the user_name is available to use or not
	  by calling function "login_registration.DB_login_registration.valid_username()" */
	public boolean is_this_username_available(String user_name) {
		return db_user_information_service.valid_username(user_name);
	}
	/*-------------------------------------------------------------------------------------------------*/
	
	
	/*Checking if the email is available to use or not
	  by calling function "login_registration.DB_login_registration.valid_email()" */
	public boolean is_this_email_available(String email) {
		return db_user_information_service.valid_email(email);
	}
	/*-------------------------------------------------------------------------------------------------*/
	
	
	/*Registering the user if the user_name and email is available to use
	  by calling function "login_registration.DB_login_registration.register_user()" */
	public String register_new_user(User user) {
		if(is_this_username_available(user.getUser_name())==true) {
			return "2|Username: [ "+user.getUser_name()+" ] is not available/used by other user. Try something new.";
		}
		
		if(is_this_email_available(user.getEmail())==true) {
			return "2|Email: [ "+user.getEmail()+" ] is not available/used by other user. Try something new.";
		}
		
		if(db_user_information_service.register_user(user)==true) {
			return "0|Username: [ "+user.getUser_name()+" ] is registered successfully. Login now.";
		}
		
		return "2|Internal Server Error.";
	}
	/*-------------------------------------------------------------------------------------------------*/
	
	
	/*--------- Login user ------------------------------------------------------------------------------*/
	public String login_user(User user) {	
		try {
			String user_name=db_user_information_service.login_user(user);
			if(user_name!="null") {
				return "0|"+user_name;
			}else {
				return "2|Incorrect credentials or account doesn't exist.";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "2|Internal Server Error.";
	}
	/*---------------------------------------------------------------------------------------------------*/
}
