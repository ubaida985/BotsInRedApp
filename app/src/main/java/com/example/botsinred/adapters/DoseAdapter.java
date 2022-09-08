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

    ArrayList<DoseModel> arrayListDoses;
    Context context;

    public DoseAdapter(ArrayList<DoseModel> arrayListDoses, Context context) {
        this.arrayListDoses = arrayListDoses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewDay, textViewDate, textViewMonth, textViewDoseName, textViewDoseDesc, textViewDoseQty, textViewDoseTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDay = itemView.findViewById(R.id.textViewDay);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewMonth = itemView.findViewById(R.id.textViewMonth);
            textViewDoseName = itemView.findViewById(R.id.textViewDoseName);
            textViewDoseDesc = itemView.findViewById(R.id.textViewDoseDesc);
            textViewDoseQty = itemView.findViewById(R.id.textViewDoseQty);
            textViewDoseTime = itemView.findViewById(R.id.textViewDoseTime);
        }
    }
}
