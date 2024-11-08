package com.example.roleBased.Dto;

import com.example.roleBased.Entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Registerdto {
    public String fullname;
    public String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public  String  password;
    public Role role;


}
