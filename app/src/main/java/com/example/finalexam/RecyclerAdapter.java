package com.example.finalexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private LayoutInflater mInflater;
    private Context context;
    private LinkedList<Task> linkedList;

    public RecyclerAdapter(Context context, LinkedList<Task> linkedList) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.linkedList = linkedList;
    }

    @Override
    @NonNull
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.row, parent, false);
        return new RecyclerViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Task mCurrent = linkedList.get(position);
        holder.taskTitleView.setText(mCurrent.get_name());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        holder.taskDateView.setText(format.format(mCurrent.get_date()));
        holder.taskCompleteView.setChecked(mCurrent.is_completed());
        holder.id = mCurrent.get_id();
    }

    @Override
    public int getItemCount() {
        return linkedList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView taskTitleView;
        private TextView taskDateView;
        private CheckBox taskCompleteView;
        private RecyclerAdapter adapter;
        private int id;

        public RecyclerViewHolder(View itemView, RecyclerAdapter adapter) {
            super(itemView);
            this.adapter = adapter;

            taskTitleView = itemView.findViewById(R.id.rowTitle);
            taskDateView = itemView.findViewById(R.id.rowDate);
            taskCompleteView = itemView.findViewById(R.id.rowCheckBox);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (!taskCompleteView.isChecked()) {
                view.setTag(id);
                ((MainActivity) context).openDetailActivity(view);
            }
        }
    }
}
