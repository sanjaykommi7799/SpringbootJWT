package com.sanjay.SecurityJWT.Entity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="Users_Table")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer Id;
	private String name;
	private String username;
	private String password;
	private String email;
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="Roles_Table",joinColumns=@JoinColumn(name="Id"))
	@Column(name="roles")
	private Set<String> roles;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public User(Integer id, String name, String username, String password, String email, Set<String> roles) {
		super();
		Id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}
	public User() {}
	@Override
	public String toString() {
		return "User [Id=" + Id + ", name=" + name + ", username=" + username + ", password=" + password + ", email="
				+ email + ", roles=" + roles + "]";
	}
	
	
}
