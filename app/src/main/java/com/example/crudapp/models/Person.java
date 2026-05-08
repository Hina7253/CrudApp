package com.example.crudapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "persons")
public class Person {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int age;
    private String location;
    private String occupation;
    private String qualification;
    private String contact;
    private String email;
    private String skills;

    // Constructor
    public Person(String name, int age, String location, String occupation,
                  String qualification, String contact, String email, String skills) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.occupation = occupation;
        this.qualification = qualification;
        this.contact = contact;
        this.email = email;
        this.skills = skills;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
}