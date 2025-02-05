package com.dxerp.ebs.dto;

import lombok.Data;

@Data
public class VendorDTO {
    private Integer id;
    private String name;
    private Boolean status;


    public Boolean getStats() {
        return status;
    }
}
