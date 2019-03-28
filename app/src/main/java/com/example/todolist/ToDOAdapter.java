package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ToDOAdapter extends ListAdapter< ToDoListData,ToDOAdapter.ToDoViewHolder> {

    private onItemClickListner itemClickListner;

    protected ToDOAdapter() {
        super(diffCallback);
    }

    public static DiffUtil.ItemCallback<ToDoListData>  diffCallback = new DiffUtil.ItemCallback<ToDoListData>() {
        @Override
        public boolean areItemsTheSame(@NonNull ToDoListData oldItem, @NonNull ToDoListData newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ToDoListData oldItem, @NonNull ToDoListData newItem) {
            return oldItem.getWhat_to_do().equals(newItem.getWhat_to_do())
                    && oldItem.getWhen_to_do().equals(newItem.getWhen_to_do())
                    && oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_inflatter , parent , false);
        return new ToDoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        ToDoListData Currentdata = getItem(position);
        holder.textView.setText(Currentdata.getWhat_to_do());
        holder.textView1.setText(Currentdata.getWhen_to_do());
        holder.textView2.setText(String.valueOf(Currentdata.getPriority()));

    }

    public ToDoListData getNote(int position)
    {
        return getItem(position);
    }
    class ToDoViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;
        TextView textView1;
        TextView textView2;
        public ToDoViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_whattodo);
            textView1 = itemView.findViewById(R.id.tv_whentodo);
            textView2 = itemView.findViewById(R.id.tv_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (itemClickListner != null && position != RecyclerView.NO_POSITION) {
                        itemClickListner.onItemClick(getItem(position));
                    }
                }
            });

        }
    }

    public interface onItemClickListner
    {
        void onItemClick(ToDoListData data);
    }

    public void setItemClickListner(onItemClickListner listner)
    {
        itemClickListner = listner;
    }

}
