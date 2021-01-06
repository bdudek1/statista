package com.example.statista.entities;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="DATA_SETS")
public class DataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="values")
    @ElementCollection(targetClass=Double.class)
    private List<Double> dataList;

    @NotNull
    @Column(name = "created_at", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private ZonedDateTime creationTime;

    @NotNull
    @Column(name = "last_modified_at", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private ZonedDateTime lastModifiedTime;

    @Value("")
    @Column(name = "description", nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public DataSet(List<Double> dataList){
        this.dataList = dataList;
        creationTime = ZonedDateTime.now();
        lastModifiedTime = ZonedDateTime.now();
    }

    public DataSet(List<Double> dataList, User user){
        this.dataList = dataList;
        this.user = user;
        creationTime = ZonedDateTime.now();
        lastModifiedTime = ZonedDateTime.now();
    }

    public DataSet(List<Double> dataList, String description, User user){
        this.dataList = dataList;
        this.description = description;
        this.user = user;
        creationTime = ZonedDateTime.now();
        lastModifiedTime = ZonedDateTime.now();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public List<Double> getDataList() { return dataList; }

    public void setDataList(List<Double> dataList) { this.dataList = dataList; }

    public ZonedDateTime getCreationTime() { return creationTime; }

    public ZonedDateTime getLastModifiedTime() { return lastModifiedTime; }

    public void setLastModifiedTime(ZonedDateTime lastModifiedTime) { this.lastModifiedTime = lastModifiedTime; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public User getUser() { return user; }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSet dataSet = (DataSet) o;
        return id.equals(dataSet.id) && user.equals(dataSet.user);
    }

    @Override
    public String toString(){
        return user.toString() +"'s dataset." + "\n" + "Values: " + dataList.toString();
    }
}
