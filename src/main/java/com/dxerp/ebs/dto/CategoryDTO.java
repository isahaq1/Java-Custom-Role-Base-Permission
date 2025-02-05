package com.dxerp.ebs.dto;
import lombok.Data;

@Data
public class CategoryDTO {
    private Integer id;
    private String name;
    private Boolean status;

    public String getName() {
        return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }

        public Boolean getStatus() {
            return status;
            }
            
        public void setStatus(Boolean status) {
            this.status = status;
        }

}
