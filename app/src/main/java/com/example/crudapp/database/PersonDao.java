package com.example.crudapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.crudapp.models.Person;
import java.util.List;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM persons ORDER BY id DESC")
    List<Person> getAllPersons();

    @Insert
    void insertPerson(Person person);

    @Update
    void updatePerson(Person person);

    @Delete
    void deletePerson(Person person);

    @Query("SELECT * FROM persons WHERE name LIKE '%' || :search || '%' OR occupation LIKE '%' || :search || '%'")
    List<Person> searchPersons(String search);
}