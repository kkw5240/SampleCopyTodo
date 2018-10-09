package com.sample.mytodo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sample.mytodo.model.Memo;
import com.sample.mytodo.model.Todo;

import java.util.List;

@Dao
public interface MemoDao {
    @Insert
    void insert(Memo memo);

    @Update
    void update(Memo memo);

    @Update
    void update(List<Memo> memoList);

    @Delete
    void delete(Memo memo);

    @Delete
    void delete(List<Memo> memoList);

    @Query("SELECT * FROM memo ORDER BY id desc LIMIT :index, :count")
    List<Memo> getList(int index, int count);
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
