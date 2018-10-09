package com.sample.mytodo.page;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sample.mytodo.AppDatabase;
import com.sample.mytodo.MemoDao;
import com.sample.mytodo.MemoRecyclerViewAdapter;
import com.sample.mytodo.MyApplication;
import com.sample.mytodo.R;
import com.sample.mytodo.TodoDao;
import com.sample.mytodo.model.Memo;
import com.sample.mytodo.model.Todo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoFragment extends Fragment {

    private MemoDao memoDao;

    private RecyclerView recyclerView;
    private MemoRecyclerViewAdapter memoRecyclerViewAdapter;
    private CheckBox checkBox;
    private EditText editText;
    private ImageButton moreImageButton;
    private ImageButton submitImageButton;

    private Button allDoneButton;
    private Button deleteDoneTodoButton;
    private ImageButton closeImageButton;

    public MemoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_memo, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        memoRecyclerViewAdapter = new MemoRecyclerViewAdapter();

        recyclerView.setAdapter(memoRecyclerViewAdapter);


        memoDao = MyApplication.getDb().memoDao();

        AsyncTask.execute(() -> {
//            Todo todo = new Todo();
//            todo.setText("aaaa");
//            todo.setDate(new Date());


//            TodoDao todoDao = MyApplication.getDb().todoDao();
//            todoDao.insert(todo);

            memoRecyclerViewAdapter.getMemoList().addAll(
                    memoDao.getList(0, 20)
            );

            recyclerView.post(() -> {
                memoRecyclerViewAdapter.notifyDataSetChanged();
            });
        });

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
                Memo memo = new Memo();
                memo.setText(text);
                memo.setDate(new Date());
                memo.setFavorite(isChecked);
                memoDao.insert(memo);

                memoRecyclerViewAdapter.getMemoList().clear();
                memoRecyclerViewAdapter.getMemoList().addAll(
                        memoDao.getList(0, 20)
                );

                recyclerView.post(() -> memoRecyclerViewAdapter.notifyDataSetChanged());
            });

        });

        allDoneButton = v.findViewById(R.id.all_done_button);
        deleteDoneTodoButton = v.findViewById(R.id.delete_done_todo_button);
        closeImageButton = v.findViewById(R.id.close_image_button);

        allDoneButton.setOnClickListener(button -> {
            List<Memo> memoList = memoRecyclerViewAdapter.getMemoList();
            for(Memo m : memoList)
                m.setDone(true);
            AsyncTask.execute(() -> memoDao.update(memoList));
            memoRecyclerViewAdapter.notifyDataSetChanged();
        });
        deleteDoneTodoButton.setOnClickListener(button -> {
            List<Memo> originMemoList = memoRecyclerViewAdapter.getMemoList();
            List<Memo> memoList = new ArrayList<>();
            for(Memo m : originMemoList)
                if(m.isDone())
                    memoList.add(m);
            originMemoList.removeAll(memoList);
            AsyncTask.execute(() -> memoDao.delete(memoList));
            memoRecyclerViewAdapter.notifyDataSetChanged();
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
