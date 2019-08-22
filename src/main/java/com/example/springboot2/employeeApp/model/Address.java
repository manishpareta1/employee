package com.example.springboot2.employeeApp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="address")
public class Address implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private String addressLine1;
	private String addressLine2;
	private CountryDetail country;
	
	public Address(){
		super();
	}
	
	public Address(String addressLine1, String addressLine2, CountryDetail country) {
		super();
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.country = country;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	@Column(name="address_line_1")
	public String getAddressLine1() {
		return addressLine1;
	}


	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}


	@Column(name="address_line_2")
	public String getAddressLine2() {
		return addressLine2;
	}


	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}


	/*@JsonIgnore
	@ManyToOne(cascade=CascadeType.ALL)*/
	@OneToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="country_id",referencedColumnName="id")
	public CountryDetail getCountry() {
		return country;
	}

	public void setCountry(CountryDetail country) {
		this.country = country;
	}



	@Override
	public String toString() {
		return "AddressDetails [addressLine1=" + addressLine1 
				+ ", addressLine2=" + addressLine2 
				+ ", country=" + country + "]";
	}
	
	
}
