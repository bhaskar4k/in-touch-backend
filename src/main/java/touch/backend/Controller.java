package touch.backend;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import model.Search_user_request_parameter;
import model.Searchbar_suggession;
import model.Update_user;
import model.User;
import searchbar_service.Searchbar_service;
import user_information_get_service.User_information_get_service;
import user_information_service.User_information_service;
import user_information_update_service.User_information_update_service;

@RestController
@CrossOrigin
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

	
	/*--------- Change profile photo ------------------------------------------------------------------------------*/
	@PostMapping("/change_profile_photo")
	public String change_profile_photo(@RequestParam("image") MultipartFile file, @RequestParam("user_name") String user_name) throws SQLException, IOException {
        InputStream fileInputStream = file.getInputStream();
        String result=user_information_update_service.change_profile_photo(fileInputStream,user_name);
        fileInputStream.close();
		return result;
	}
	/*-------------------------------------------------------------------------------------------------------------*/

	
	/*--------- Change profile photo ------------------------------------------------------------------------------*/
	@PostMapping("/get_profile_photo")
	public Searchbar_suggession get_profile_photo(@RequestBody String user_name) throws SQLException, IOException {
        Searchbar_suggession suggession=new Searchbar_suggession(user_name,user_information_get_service.get_profile_photo(user_name));
        return suggession;
	}
	/*-------------------------------------------------------------------------------------------------------------*/
	
	
	/*--------- Get search suggession ------------------------------------------------------------------------------*/
	@PostMapping("/find_result_of_searched_input")
	public ArrayList<Searchbar_suggession> find_result_of_searched_input(@RequestBody Search_user_request_parameter search_user_request_parameter) throws SQLException, IOException {
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
        return user_information_update_service.delete_user(user_name);
	}
	/*-------------------------------------------------------------------------------------------------------------*/
}