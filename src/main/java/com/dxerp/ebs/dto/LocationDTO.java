package com.dxerp.ebs.dto;

import lombok.Data;

@Data
public class LocationDTO {
    private Integer id;
    private String name;
    private String address;
    private String contact;
    private Integer locationType;
    private Boolean status;


    public Boolean getStats() {
        return status;
    }
    public String getAddress() {
        return address;
    }
    public String getContact() {
        return contact;
    }
    public Integer getLocationType() {
        return locationType;
    }
}
