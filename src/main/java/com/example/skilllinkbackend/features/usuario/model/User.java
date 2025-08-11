package com.example.skilllinkbackend.features.usuario.model;

import com.example.skilllinkbackend.features.role.model.Role;
import com.example.skilllinkbackend.features.usuario.dto.UserRegisterRequestDTO;
import com.example.skilllinkbackend.features.usuario.dto.UserUpdateDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Table(name = "users")
@Entity(name = "User")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String goals;
    private String email;
    private String password;
    private String biography;
    private String photo;
    private String dni;
    private boolean enabled = true;

    // Guarda la fecha de creación en UTC
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE", updatable = false)
    private OffsetDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(Long userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public User(UserRegisterRequestDTO userDto) {
        this.email = userDto.email();
        this.firstName = userDto.firstName();
        this.lastName = userDto.lastName();
        this.goals = userDto.goals();
        setPassword(userDto.password().toCharArray());
        this.biography = userDto.biography();
        this.photo = userDto.photo();
        this.dni = userDto.dni();
    }

    public void setPassword(char[] plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(new String(plainPassword));
        java.util.Arrays.fill(plainPassword, ' ');
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().name());
                    //System.out.println("Authority created:" + authority.getAuthority());
                    return authority;
                })
                .collect(Collectors.toSet());
        //System.out.println("All authorities: " + authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void deactive() {
        this.enabled = false;
    }

    public void update(UserUpdateDTO userDto, Set<Role> roles) {
        this.userId = userDto.userId();
        this.firstName = userDto.firstName();
        this.lastName = userDto.lastName();
        this.goals = userDto.goals();
        this.email = userDto.email();
        setPassword(userDto.password().toCharArray());
        this.biography = userDto.biography();
        this.photo = userDto.photo();
        this.roles = roles;
        this.dni = userDto.dni();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
