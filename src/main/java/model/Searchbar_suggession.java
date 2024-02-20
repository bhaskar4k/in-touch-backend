package model;

public class Searchbar_suggession {
	private String user_name, profile_photo;
	
	public Searchbar_suggession() {
		super();
	}
	
	public Searchbar_suggession(String user_name, String profile_photo) {
		super();
		this.user_name = user_name;
		this.profile_photo = profile_photo;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getProfile_photo() {
		return profile_photo;
	}

	public void setProfile_photo(String profile_photo) {
		this.profile_photo = profile_photo;
	}

	@Override
	public String toString() {
		return "Profile_photo [user_name=" + user_name + ", profile_photo=" + profile_photo + "]";
	}
}
