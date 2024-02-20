package user_information_update_service;

import java.io.InputStream;
import java.sql.SQLException;

import model.Update_user;
import user_information_service.User_information_service;

public class User_information_update_service {
	private DB_user_information_update_service db_user_information_update_service;
	private User_information_service user_information_service;
	
	public User_information_update_service() throws SQLException{
		this.db_user_information_update_service=new DB_user_information_update_service();
		this.user_information_service=new User_information_service();
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
		String field=update_user.getField_name();
		
		System.out.println(update_user.getUpdated_value()+field+" asche baire");
		if(field=="user_name") {
			System.out.println(update_user.getUpdated_value()+" asche vetor");
			if(user_information_service.is_this_username_available(update_user.getUpdated_value())==true) {			
				return "3";
			}
		}
		if(db_user_information_update_service.update_user_information(update_user)==true) {
			return "0";
		}
		
		return "2|Internal Server Error.";
	}
	/*-------------------------------------------------------------------------------------------------*/
}
