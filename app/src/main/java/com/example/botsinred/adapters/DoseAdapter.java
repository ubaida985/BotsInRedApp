package com.example.botsinred.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.botsinred.R;
import com.example.botsinred.models.DoseModel;

import java.util.ArrayList;

public class DoseAdapter extends RecyclerView.Adapter<DoseAdapter.ViewHolder>{

    private ArrayList<DoseModel> arrayListDoses;
    private Context context;

    private OnViewItemClickListener onViewItemClickListener;

    public DoseAdapter(ArrayList<DoseModel> arrayListDoses, Context context, OnViewItemClickListener onViewItemClickListener) {
        this.arrayListDoses = arrayListDoses;
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
        DoseModel dose = arrayListDoses.get(position);
        holder.textViewDay.setText(dose.getDay());
        holder.textViewDate.setText(dose.getDate());
        holder.textViewMonth.setText(dose.getMonth());
        holder.textViewDoseName.setText(dose.getDoseName());
        holder.textViewDoseDesc.setText(dose.getDoseDescription());
        holder.textViewDoseQty.setText(Integer.toString(dose.getDoseQuantity()));
        holder.textViewDoseTime.setText(dose.getHours() + ":" + dose.getMinutes());
    }

    @Override
    public int getItemCount() {
        return arrayListDoses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewDay, textViewDate, textViewMonth, textViewDoseName, textViewDoseDesc, textViewDoseQty, textViewDoseTime;
        private OnViewItemClickListener onViewItemClickListener;
        public ViewHolder(@NonNull View itemView, OnViewItemClickListener onViewItemClickListener) {
            super(itemView);

            textViewDay = itemView.findViewById(R.id.textViewDay);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewMonth = itemView.findViewById(R.id.textViewMonth);
            textViewDoseName = itemView.findViewById(R.id.textViewDoseName);
            textViewDoseDesc = itemView.findViewById(R.id.textViewDoseDesc);
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
