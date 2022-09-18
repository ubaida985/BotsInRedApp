package com.example.botsinred.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.botsinred.R;
import com.example.botsinred.models.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder > {

    private ArrayList<CategoryModel> categories;
    private Context context;

    private CategoryAdapter.OnViewItemClickListener onViewItemClickListener;

    public CategoryAdapter(ArrayList<CategoryModel> categories, Context context, CategoryAdapter.OnViewItemClickListener onViewItemClickListener) {
        this.categories = categories;
        this.context = context;
        this.onViewItemClickListener = onViewItemClickListener;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view, onViewItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        CategoryModel category = categories.get(position);
        holder.textViewBlockName.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewBlockName;
        CategoryAdapter.OnViewItemClickListener onViewItemClickListener;

        public ViewHolder(@NonNull View itemView, CategoryAdapter.OnViewItemClickListener onViewItemClickListener) {
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
