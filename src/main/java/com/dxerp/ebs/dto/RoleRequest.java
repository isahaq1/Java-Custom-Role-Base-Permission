package com.dxerp.ebs.dto;
import lombok.Data;
import java.util.*;
@Data

public class RoleRequest {
    private String name;
    private List<Long> menuIds;
}