package com.example.ecommerce_backend.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(length = 200)
    private String address;
    @Column(length = 200,nullable = false)
    private String password;
    @Column(name="is_active")
    private boolean isActive;
    @Column(name="date_of_birth")
    private Date dateOfBirth;
    @Column(name="facebook_account_id")
    private int facebookAccountId;
    @Column(name="google_account_id")
    private int googleAccountId;
    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

}
