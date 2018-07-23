package com.healthcare.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
    
    public final static int TYPE_DOCTOR = 1;
    public final static int TYPE_PATIENT = 2;
    public final static int TYPE_ADMIN = 3;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String email;
    
    @JsonIgnore
    private String password;
    
    private Date createdDate;
    
    private Date lastLoginDate;

}
