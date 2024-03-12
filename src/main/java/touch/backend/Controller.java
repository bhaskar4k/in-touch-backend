package touch.backend;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import model.Search_user_request_parameter;
import model.Profile_photo;
import model.Update_user;
import model.User;
import searchbar_service.Searchbar_service;
import user_information_get_service.User_information_get_service;
import user_information_service.User_information_service;
import user_information_update_service.User_information_update_service;

@RestController
@CrossOrigin
@EnableCaching
public class Controller {
	private User_information_service user_information_service;
	private User_information_update_service user_information_update_service;
	private User_information_get_service user_information_get_service;
	private Searchbar_service searchbar_service;

	
	@Autowired
	public Controller() throws SQLException {
		System.out.println("Rest controller object created");
		this.user_information_service=new User_information_service();
		this.user_information_update_service=new User_information_update_service();
		this.user_information_get_service=new User_information_get_service();
		this.searchbar_service=new Searchbar_service();
	}
	
	
	/*--------- Registering new user ------------------------------------------------------------------------------*/
	@PostMapping("/register_user")
	public String register_user(@RequestBody User user) {
		user.print();
		// Registering user by calling the function "login_registration.Registration.register_new_user()"
		return user_information_service.register_new_user(user);
	}
	/*-------------------------------------------------------------------------------------------------------------*/
	
	
	/*--------- Login user ----------------------------------------------------------------------------------------*/
	@PostMapping("/login_user")
	public User login_user(@RequestBody User user) {
		// Login user by calling the function "login_registration.Login.login_user()"
		return user_information_service.login_user(user);
	}
	/*-------------------------------------------------------------------------------------------------------------*/
	
	
	/*--------- Is it a valid profile ------------------------------------------------------------------------------*/
	@PostMapping("/is_it_a_valid_profile")
	public String is_it_a_valid_profile(@RequestBody String user_name) throws SQLException, IOException {		
		return user_information_service.is_it_a_valid_profile(user_name);
	}
	/*-------------------------------------------------------------------------------------------------------------*/
	
	
	/*--------- Change profile photo ------------------------------------------------------------------------------*/
	@PostMapping("/change_profile_photo")
	@CacheEvict(value = "Profile_photo", key = "#user_name")
	public String change_profile_photo(@RequestParam("image") MultipartFile file, @RequestParam("user_name") String user_name) throws SQLException, IOException {
        InputStream fileInputStream = file.getInputStream();
        String result=user_information_update_service.change_profile_photo(fileInputStream,user_name);
        fileInputStream.close();
		return result;
	}
	/*-------------------------------------------------------------------------------------------------------------*/

	
	/*--------- Get profile photo ------------------------------------------------------------------------------*/
	@PostMapping("/get_profile_photo")
	@Cacheable(key="#user_name", value="Profile_photo")
	public Profile_photo get_profile_photo(@RequestBody String user_name) throws SQLException, IOException {
        Profile_photo profile_photo=new Profile_photo(user_name,user_information_get_service.get_profile_photo(user_name));
        return profile_photo;
	}
	/*-------------------------------------------------------------------------------------------------------------*/
	
	
	/*--------- Get user bio ------------------------------------------------------------------------------*/
	@PostMapping("/get_user_bio")
	public String get_user_bio(@RequestBody String user_name) throws SQLException, IOException {
        return user_information_get_service.get_user_bio(user_name);
	}
	/*-------------------------------------------------------------------------------------------------------------*/
	
	
	/*--------- Get search suggession ------------------------------------------------------------------------------*/
	@PostMapping("/find_result_of_searched_input")
	public ArrayList<Profile_photo> find_result_of_searched_input(@RequestBody Search_user_request_parameter search_user_request_parameter) throws SQLException, IOException {
		return searchbar_service.get_search_suggession(search_user_request_parameter);
	}
	/*-------------------------------------------------------------------------------------------------------------*/
	
	
	/*--------- Update user information ------------------------------------------------------------------------------*/
	@PostMapping("/update_user_information")
    public String update_user_information(@RequestBody Update_user update_user) {
		return user_information_update_service.update_user_information(update_user);
    }
	/*-------------------------------------------------------------------------------------------------------------*/
	
	
	/*--------- Change profile photo ------------------------------------------------------------------------------*/
	@PostMapping("/delete_user")
	public String delete_user(@RequestBody String user_name) throws SQLException, IOException {
        return user_information_service.delete_user(user_name);
	}
	/*-------------------------------------------------------------------------------------------------------------*/

	
	/*--------- Get post count ------------------------------------------------------------------------------*/
	@PostMapping("/get_post_count")
	public int get_post_count(@RequestBody String user_name) {
		return user_information_get_service.get_post_count(user_name);
	}
	/*-------------------------------------------------------------------------------------------------------------*/

	
	/*--------- Get post count ------------------------------------------------------------------------------*/
	@PostMapping("/update_post_count")
	public String update_post_count(@RequestBody String user_name) {
		return user_information_update_service.update_post_count(user_name);
	}
	/*-------------------------------------------------------------------------------------------------------------*/
}