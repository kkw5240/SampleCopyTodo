package com.sample.mytodo.page;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sample.mytodo.MyApplication;
import com.sample.mytodo.R;
import com.sample.mytodo.ScheduleRecyclerViewAdapter;
import com.sample.mytodo.TodayRecyclerViewAdapter;
import com.sample.mytodo.TodoDao;
import com.sample.mytodo.model.Memo;
import com.sample.mytodo.model.Todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleFragment extends Fragment {

    private TodoDao todoDao;

    private RecyclerView recyclerView;
    private ScheduleRecyclerViewAdapter scheduleRecyclerViewAdapter;
    private CheckBox checkBox;
    private EditText editText;
    private ImageButton moreImageButton;
    private ImageButton submitImageButton;

    private Button allDoneButton;
    private Button deleteDoneTodoButton;
    private ImageButton closeImageButton;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public ScheduleRecyclerViewAdapter getScheduleRecyclerViewAdapter() {
        return scheduleRecyclerViewAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        scheduleRecyclerViewAdapter = new ScheduleRecyclerViewAdapter();

        recyclerView.setAdapter(scheduleRecyclerViewAdapter);


        todoDao = MyApplication.getDb().todoDao();

        AsyncTask.execute(() ->
            scheduleRecyclerViewAdapter.setDate(new Date()));

        checkBox = v.findViewById(R.id.check_box);
        editText = v.findViewById(R.id.edit_text);
        editText.addTextChangedListener(textWatcher);
        moreImageButton = v.findViewById(R.id.more_image_button);
        submitImageButton = v.findViewById(R.id.submit_image_button);

        moreImageButton.setOnClickListener(button -> {
            checkBox.setVisibility(View.INVISIBLE);
            editText.setEnabled(false);
            editText.setVisibility(View.INVISIBLE);
            allDoneButton.setVisibility(View.VISIBLE);
            deleteDoneTodoButton.setVisibility(View.VISIBLE);
            closeImageButton.setVisibility(View.VISIBLE);
        });

        submitImageButton.setOnClickListener(button -> {
            final String text = editText.getText().toString();
            final boolean isChecked = checkBox.isChecked();

            if(text.isEmpty()) { return; }
            checkBox.setChecked(false);
            editText.setText("");

            AsyncTask.execute(() -> {
                Todo todo = new Todo();
                todo.setText(text);
                todo.setDate(scheduleRecyclerViewAdapter.getDate());
                todo.setFavorite(isChecked);
                todoDao.insert(todo);

                scheduleRecyclerViewAdapter.setDate(todo.getDate());
            });

        });

        allDoneButton = v.findViewById(R.id.all_done_button);
        deleteDoneTodoButton = v.findViewById(R.id.delete_done_todo_button);
        closeImageButton = v.findViewById(R.id.close_image_button);

        allDoneButton.setOnClickListener(button -> {
            List<Todo> todoList = scheduleRecyclerViewAdapter.getTodoList();
            for(Todo t : todoList)
                t.setDone(true);
            AsyncTask.execute(() -> todoDao.update(todoList));
            scheduleRecyclerViewAdapter.notifyDataSetChanged();
        });
        deleteDoneTodoButton.setOnClickListener(button -> {
            List<Todo> originTodoList = scheduleRecyclerViewAdapter.getTodoList();
            List<Todo> todoList = new ArrayList<>();
            for(Todo t : originTodoList)
                if(t.isDone())
                    todoList.add(t);
            originTodoList.removeAll(todoList);
            AsyncTask.execute(() -> todoDao.delete(todoList));
            scheduleRecyclerViewAdapter.notifyDataSetChanged();
        });

        closeImageButton.setOnClickListener(button -> {
            checkBox.setVisibility(View.VISIBLE);
            editText.setEnabled(true);
            editText.setVisibility(View.VISIBLE);
            allDoneButton.setVisibility(View.INVISIBLE);
            deleteDoneTodoButton.setVisibility(View.INVISIBLE);
            closeImageButton.setVisibility(View.INVISIBLE);
        });

        return v;
    }

    public void refresh() {
        if(scheduleRecyclerViewAdapter == null) return;
        AsyncTask.execute(() ->
            scheduleRecyclerViewAdapter.setDate(scheduleRecyclerViewAdapter.getDate()));
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            boolean isEmpty = s.length() == 0;

            moreImageButton.setVisibility(
                    isEmpty ? View.VISIBLE : View.INVISIBLE
            );
            submitImageButton.setVisibility(
                    isEmpty ? View.INVISIBLE : View.VISIBLE
            );
        }
    };
}
