package com.blog.application.payloads;

import lombok.Data;

@Data
public class RolesDto {
    private Integer id;

    //Name is diff from db column this might cause issue
    private String roleName;
}
