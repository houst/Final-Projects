package com.cinema.entity;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 8454495510704992051L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotBlank(message = "Email may not be blank")
    @Column(unique = true)
	private String email;
	
    @NotBlank(message = "Password may not be blank")
    @Column(name = "password")
    private String password;
    
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Role> authorities;
    
    @NotBlank(message = "Name may not be blank")
    @Column(name = "username")
    private String username;
    
    @NotBlank(message = "Phone number may not be blank")
    @Column(name = "tel")
    private String tel;
    
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Ticket> tickets;
	
    @Column(name = "is_account_non_expired")
	private boolean accountNonExpired;
	
    @Column(name = "is_account_non_locked")
	private boolean accountNonLocked;
	
    @Column(name = "is_credentials_non_expired")
	private boolean credentialsNonExpired;
	
    @Column(name = "is_enabled")
	private boolean enabled;

}