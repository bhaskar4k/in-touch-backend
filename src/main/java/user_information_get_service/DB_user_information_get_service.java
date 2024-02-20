package user_information_get_service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import database_configuration.Logical_sharding;
import database_configuration.MySQLDatabaseConfig;

public class DB_user_information_get_service {
	private Connection connection;
	
	public DB_user_information_get_service() throws SQLException{
		connection = MySQLDatabaseConfig.getConnection();
	}
	
	
	/*--------- Get profile photo ---------------------------------------------------------------------------------*/
	public String get_profile_photo(String user_name) throws IOException {
		PreparedStatement preparedStatement=null;
		String base64Image="NULL";
		
        try {
        	String database_shard=Logical_sharding.get_shard_name(user_name);
            String sql = "SELECT profile_photo FROM touch.profile_photo_"+database_shard+" WHERE user_name=?";
            
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user_name);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {               
                InputStream profile_photo_stream=null;
                if(resultSet.getBinaryStream("profile_photo")!=null) {
                	profile_photo_stream=resultSet.getBinaryStream("profile_photo");
                }else {
                	return "NULL";
                }
                              
                if (profile_photo_stream != null) {
                	byte[] profilePhotoBytes = profile_photo_stream.readAllBytes();
                    profile_photo_stream.close();
                    base64Image = Base64.getEncoder().encodeToString(profilePhotoBytes);
                }
            }
            
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            try {
            	preparedStatement.close();				
			} catch (SQLException e) {
				e.printStackTrace();
			}
            
        }
        
        return base64Image;
	}
	/*-------------------------------------------------------------------------------------------------------------*/
}
