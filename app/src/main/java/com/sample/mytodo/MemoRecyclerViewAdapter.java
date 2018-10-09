package com.sample.mytodo;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sample.mytodo.model.Memo;
import com.sample.mytodo.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class MemoRecyclerViewAdapter extends RecyclerView.Adapter<MemoRecyclerViewHolder> {
    private List<Memo> memoList = new ArrayList<>();

    public List<Memo> getMemoList() {
        return memoList;
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    @Override
    public MemoRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memo, parent, false);
        final MemoRecyclerViewHolder viewHolder = new MemoRecyclerViewHolder(v);

        viewHolder.getCheckBox().setOnCheckedChangeListener((buttonView, isChecked) -> {
            AsyncTask.execute(() -> {
                Memo memo = memoList.get(viewHolder.getAdapterPosition());
                memo.setFavorite(isChecked);
                MyApplication.getDb().memoDao().update(memo);
            });
        });

        viewHolder.getTextView().setOnClickListener(view -> {
            AsyncTask.execute(() -> {
                Memo memo = memoList.get(viewHolder.getAdapterPosition());
                memo.setDone(!memo.isDone());
                viewHolder.itemView.post(() -> viewHolder.setData(memo));
                MyApplication.getDb().memoDao().update(memo);
            });
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemoRecyclerViewHolder holder, int position) {
        holder.setData(memoList.get(position));
    }
}
