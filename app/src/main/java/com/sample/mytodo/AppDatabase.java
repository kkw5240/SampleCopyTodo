package com.sample.mytodo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sample.mytodo.model.Memo;
import com.sample.mytodo.model.Todo;

@Database(entities = {Todo.class, Memo.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TodoDao todoDao();
    public abstract MemoDao memoDao();
}