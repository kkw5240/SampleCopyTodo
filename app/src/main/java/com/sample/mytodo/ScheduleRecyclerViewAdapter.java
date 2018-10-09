package com.sample.mytodo;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.FrameLayout;

import com.sample.mytodo.model.Todo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter {
    private Date date = new Date();
    private List<Todo> todoList = new ArrayList<>();

    public List<Todo> getTodoList() {
        return todoList;
    }

    @Override
    public int getItemCount() {
        return 1 + todoList.size();
    }

    private final static int TYPE_CALENDER = 1;
    private final static int TYPE_TODO = 2;

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_CALENDER : TYPE_TODO;
    }

    public Date getDate() {
        return date;
    }

    @WorkerThread
    public void setDate(Date date) {
        this.date = date;
        Todo todo = new Todo();
        todo.setDate(date);
        todoList.clear();
        todoList.addAll(MyApplication.getDb().todoDao().getDateList(todo.getDateString(), 0, 20));
        new Handler(Looper.getMainLooper()).post(this::notifyDataSetChanged);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CALENDER: {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar, parent, false);
                ScheduleCalendarRecycleViewHolder holder =  new ScheduleCalendarRecycleViewHolder(v);
                CalendarView calendarView = holder.itemView.findViewById(R.id.calendar_view);
                calendarView.setDate(date.getTime());
                calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth, 0, 0, 0);
                    AsyncTask.execute(() -> setDate(calendar.getTime()));
                });
                return holder;
            }
            case TYPE_TODO: {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo, parent, false);
                final TodayRecyclerViewHolder viewHolder = new TodayRecyclerViewHolder(v);
                viewHolder.itemView.findViewById(R.id.date_view).setVisibility(View.INVISIBLE);

                viewHolder.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
                    AsyncTask.execute(() -> {
                        Todo todo = todoList.get(viewHolder.getAdapterPosition() - 1);
                        todo.setFavorite(isChecked);
                        MyApplication.getDb().todoDao().update(todo);
                    });
                });

                viewHolder.getTextView().setOnClickListener(view -> {
                    AsyncTask.execute(() -> {
                        Todo todo = todoList.get(viewHolder.getAdapterPosition() - 1);
                        todo.setDone(!todo.isDone());
                        viewHolder.itemView.post(() -> viewHolder.setData(todo));
                        MyApplication.getDb().todoDao().update(todo);
                    });
                });

                return viewHolder;
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TodayRecyclerViewHolder) {
            TodayRecyclerViewHolder viewHolder = (TodayRecyclerViewHolder)holder;
            viewHolder.setData(todoList.get(position-1));
        }
    }
}
