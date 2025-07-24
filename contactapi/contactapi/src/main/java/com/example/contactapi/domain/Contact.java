package com.example.contactapi.domain;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class Contact {
	@Id 
	@UuidGenerator
	@GeneratedValue
	@Column(name="id", unique = true , updatable = false )
	
	private String id;
	private String name;
	private String email;
	private String title;
	private String phone;
	private String address;
	private String status;
	private String photoUrl;
	
	
	
	 public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    public String getAddress() { return address; }
	    public void setAddress(String address) { this.address = address; }

	    public String getTitle() { return title; }
	    public void setTitle(String title) { this.title = title; }

	    public String getStatus() { return status; }
	    public void setStatus(String status) { this.status = status; }
	


	public void setPhotoUrl(String photoUrl2) {
		// TODO Auto-generated method stub
		
	}

}
