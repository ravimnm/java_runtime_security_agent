//package org.jrsa.backend.entity;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//@Document(collection="users")
//public class User {
//	
//	@Id
//	private String id;
//	
//	@Indexed(unique=true)
//	private String email;
//	private String Name;
//	private String password;
//	
//	private String role;
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getName() {
//		return Name;
//	}
//
//	public void setName(String name) {
//		Name = name;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}
//
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", email=" + email + ", Name=" + Name + ", password=" + password + ", role=" + role
//				+ "]";
//	}
//
//	public User(String id, String email, String name, String password, String role) {
//		super();
//		this.id = id;
//		this.email = email;
//		Name = name;
//		this.password = password;
//		this.role = role;
//	}
//
//	public User() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	
//
//
//
//}