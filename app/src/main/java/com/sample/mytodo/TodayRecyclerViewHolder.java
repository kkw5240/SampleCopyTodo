package com.sample.mytodo;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sample.mytodo.model.Todo;

public class TodayRecyclerViewHolder extends RecyclerView.ViewHolder {

    private CheckBox checkBox;
    private TextView textView;
    private TextView dateView;

    public TodayRecyclerViewHolder(View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.check_box);
        textView = itemView.findViewById(R.id.text_view);
        dateView = itemView.findViewById(R.id.date_view);
    }

    public void setData(Todo todo) {
        checkBox.setChecked(todo.isFavorite());
        if(todo.isDone()) {
            textView.setText(Html.fromHtml("<s>"+todo.getText()+"</s>"));
        } else {
            textView.setText(todo.getText());
        }
        dateView.setText(todo.getDateString());
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public TextView getTextView() {
        return textView;
    }
}
