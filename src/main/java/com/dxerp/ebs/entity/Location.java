package com.dxerp.ebs.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "locations")

public class Location {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@Column(nullable = false)
private String name;

@Column(nullable = true)
private String address;

@Column(nullable = true)
private String contact;

@Column(name = "location_type")
private Integer locationType;

@Column(name = "status", nullable = false)
private boolean status = true;

// Getters and Setters
public String getName() {
return name;
}

public void setName(String name) {
    this.name = name;
}
public String getAddress() {
    return address;
    }
    
public void setAddress(String address) {
    this.address = address;
}

public String getContact() {
    return contact;
    }
    
public void setContact(String contact) {
    this.contact = contact;
}

public Integer getLocationType() {
    return locationType;
    }
    
public void setLocationType(Integer locationType) {
    this.locationType = locationType;
}


public Boolean getStatus() {
    return status;
    }
    
public void setStatus(Boolean status) {
    this.status = status;
}
}
