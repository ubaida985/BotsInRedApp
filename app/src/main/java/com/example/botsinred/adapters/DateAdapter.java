package com.example.botsinred.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.botsinred.R;
import com.example.botsinred.models.DateModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHodler>{

    ArrayList<DateModel> arrayListDates;
    Context context;

    public DateAdapter(ArrayList<DateModel> arrayListDates, Context context) {
        this.arrayListDates = arrayListDates;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_date, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        DateModel date = arrayListDates.get(position);
        holder.textViewDate.setText(date.getDay());
        holder.textViewDay.setText(date.getDayOnDate());
    }

    @Override
    public int getItemCount() {
        return arrayListDates.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{

        private TextView textViewDate, textViewDay;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewDay = itemView.findViewById(R.id.textViewDay);
        }
    }
}
