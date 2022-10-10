package com.example.botsinred.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.botsinred.R;
import com.example.botsinred.models.PhoneModel;

import java.util.ArrayList;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {

    private ArrayList<PhoneModel> numbers;
    private Context context;

    private PhoneAdapter.OnViewItemClickListener onViewItemClickListener;

    public PhoneAdapter(ArrayList<PhoneModel> numbers, Context context, PhoneAdapter.OnViewItemClickListener onViewItemClickListener) {
        this.numbers = numbers;
        this.context = context;
        this.onViewItemClickListener = onViewItemClickListener;
    }

    @NonNull
    @Override
    public PhoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phone, parent, false);
        return new PhoneAdapter.ViewHolder(view, onViewItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.ViewHolder holder, int position) {
        PhoneModel phone = numbers.get(position);
        holder.textViewBlockName.setText(phone.getName());
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewBlockName;
        PhoneAdapter.OnViewItemClickListener onViewItemClickListener;

        public ViewHolder(@NonNull View itemView, PhoneAdapter.OnViewItemClickListener onViewItemClickListener) {
            super(itemView);

            textViewBlockName = itemView.findViewById(R.id.textViewBlockName);

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
