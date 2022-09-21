package com.example.botsinred.adapters;

import android.content.Context;
import android.media.TimedText;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.botsinred.R;
import com.example.botsinred.models.ScheduleModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder > {

    private ArrayList<ScheduleModel> schedules;
    private Context context;

    private OnViewItemClickListener onViewItemClickListener;

    public ScheduleAdapter(ArrayList<ScheduleModel> schedules, Context context, OnViewItemClickListener onViewItemClickListener) {
        this.schedules = schedules;
        this.context = context;
        this.onViewItemClickListener = onViewItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view, onViewItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleModel schedule = schedules.get(position);
        holder.textViewDoseName.setText(schedule.getName());
        holder.textViewDoseTime.setText(schedule.getTime());
        holder.textViewDoseQty.setText("Number of Categories: " + Integer.toString(schedule.getCategories().size()));
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewDoseName, textViewDoseQty, textViewDoseTime;
        OnViewItemClickListener onViewItemClickListener;

        public ViewHolder(@NonNull View itemView, OnViewItemClickListener onViewItemClickListener) {
            super(itemView);

            textViewDoseName = itemView.findViewById(R.id.textViewDoseName);
            textViewDoseQty = itemView.findViewById(R.id.textViewDoseQty);
            textViewDoseTime = itemView.findViewById(R.id.textViewDoseTime);

            itemView.setOnClickListener(this);
            this.onViewItemClickListener = onViewItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onViewItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnViewItemClickListener{
        void onItemClick( int position );
    }
}
