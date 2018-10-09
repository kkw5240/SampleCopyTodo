package com.sample.mytodo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sample.mytodo.model.Todo;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Update
    void update(List<Todo> todoList);

    @Delete
    void delete(Todo todo);

    @Delete
    void delete(List<Todo> todoList);

    @Query("SELECT * FROM todo WHERE dateString = :dateString ORDER BY id desc LIMIT :index, :count")
    List<Todo> getDateList(String dateString, int index, int count);
}
/*
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}*/
