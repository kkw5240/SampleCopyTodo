package com.sample.mytodo;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sample.mytodo.model.Memo;
import com.sample.mytodo.model.Todo;

public class MemoRecyclerViewHolder extends RecyclerView.ViewHolder {

    private CheckBox checkBox;
    private TextView textView;
    private TextView dateView;

    public MemoRecyclerViewHolder(View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.check_box);
        textView = itemView.findViewById(R.id.text_view);
        dateView = itemView.findViewById(R.id.date_view);
    }

    public void setData(Memo memo) {
        checkBox.setChecked(memo.isFavorite());
        if(memo.isDone()) {
            textView.setText(Html.fromHtml("<s>"+memo.getText()+"</s>"));
        } else {
            textView.setText(memo.getText());
        }
        dateView.setText(memo.getDateString());
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public TextView getTextView() {
        return textView;
    }
}
