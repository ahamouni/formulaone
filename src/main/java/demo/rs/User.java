package demo.rs;


/**
 * Builder pattern
 * @author sammy_ilian
 *
 */
public class User {
	
	private String name;
	private int age;
	private String address;
	
	private User(UserBuilder userBuilder) {
		this.name=userBuilder.name;
		this.age=userBuilder.age;
		this.address = userBuilder.address;
	}
	
	
	
	
	public static class UserBuilder {
		private String name;
		private int age;
		private String address;
		
		public UserBuilder(String name) {
			this.name=name;
		}
		
		public UserBuilder age(int age) {
			this.age=age;
			return this;
		}
		
		public UserBuilder address(String address) {
			this.address = address;
			return this;
		}
		
		public User buildUser() {
			return new User(this);
			
		}
	}

}
