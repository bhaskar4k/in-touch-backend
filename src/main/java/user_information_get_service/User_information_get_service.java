package user_information_get_service;

import java.io.IOException;
import java.sql.SQLException;

public class User_information_get_service {
	private DB_user_information_get_service db_user_information_get_service;
	
	public User_information_get_service() throws SQLException {
		this.db_user_information_get_service=new DB_user_information_get_service();
	}
	
	
	/*--------- Get profile photo ---------------------------------------------------------------------------------*/
	public String get_profile_photo(String user_name) {
		try {
			return db_user_information_get_service.get_profile_photo(user_name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "NULL";
	}
	/*-------------------------------------------------------------------------------------------------------------*/
}