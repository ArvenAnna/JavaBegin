package com.main.acad.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Builder;

@Getter
@Setter
@Builder(builderMethodName = "_myuserbuilder")
@ToString
public class User {
    private String login;
    private Integer password;
    private String role;
}
