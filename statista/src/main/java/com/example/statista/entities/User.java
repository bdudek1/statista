package com.example.statista.entities;

import com.sun.istack.NotNull;
import io.micrometer.core.lang.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NonNull
    @Transient
    private String password;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private ZonedDateTime creationTime;

    @NotNull
    @Column(name = "last_modified_at", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private ZonedDateTime lastModifiedTime;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<DataSet> dataSets;

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.creationTime = ZonedDateTime.now();
        this.lastModifiedTime = ZonedDateTime.now();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @NonNull public String getPassword() { return password; }

    public void setPassword(@NonNull String password) { this.password = password; }

    public Set<DataSet> getDataSets() { return dataSets; }

    public void setDataSets(Set<DataSet> dataSets) { this.dataSets = dataSets; }

    public ZonedDateTime getCreationTime() { return creationTime; }

    public ZonedDateTime getLastModifiedTime() { return lastModifiedTime; }

    public void setLastModifiedTime(ZonedDateTime lastModifiedTime) { this.lastModifiedTime = lastModifiedTime; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

}
