package com.sample.mytodo;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.mytodo.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodayRecyclerViewAdapter extends RecyclerView.Adapter<TodayRecyclerViewHolder> {
    private List<Todo> todoList = new ArrayList<>();

    public List<Todo> getTodoList() {
        return todoList;
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    @Override
    public TodayRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo, parent, false);
        final TodayRecyclerViewHolder viewHolder = new TodayRecyclerViewHolder(v);
        viewHolder.itemView.findViewById(R.id.date_view).setVisibility(View.INVISIBLE);

        viewHolder.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            AsyncTask.execute(() -> {
                Todo todo = todoList.get(viewHolder.getAdapterPosition());
                todo.setFavorite(isChecked);
                MyApplication.getDb().todoDao().update(todo);
            });
        });

        viewHolder.getTextView().setOnClickListener(view -> {
            AsyncTask.execute(() -> {
                Todo todo = todoList.get(viewHolder.getAdapterPosition());
                todo.setDone(!todo.isDone());
                viewHolder.itemView.post(() -> viewHolder.setData(todo));
                MyApplication.getDb().todoDao().update(todo);
            });
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TodayRecyclerViewHolder holder, int position) {
        holder.setData(todoList.get(position));
    }
}
