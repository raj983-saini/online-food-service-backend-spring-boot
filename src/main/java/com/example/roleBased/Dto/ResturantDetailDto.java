package com.example.roleBased.Dto;

import com.example.roleBased.Entity.Adressing;
import com.example.roleBased.Entity.ContactInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ResturantDetailDto {
private  long id;
private  String name;
private  String description;
private  String cusiontype;
private  String openinghour;
private Adressing adressing;
private ContactInfo contactInfo;
private List<String> image;
private String profilepitcher;



}
