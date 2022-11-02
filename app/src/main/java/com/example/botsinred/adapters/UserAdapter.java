package com.example.botsinred.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.botsinred.R;
import com.example.botsinred.models.UserModel;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final ArrayList<UserModel> users;
    private final Context context;

    private final UserAdapter.OnViewItemClickListener onViewItemClickListener;

    public UserAdapter(ArrayList<UserModel> users, Context context, UserAdapter.OnViewItemClickListener onViewItemClickListener) {
        this.users = users;
        this.context = context;
        this.onViewItemClickListener = onViewItemClickListener;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new UserAdapter.ViewHolder(view, onViewItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        UserModel user = users.get(position);
        holder.textViewUserName.setText(user.getUsername());
        holder.textViewUserID.setText(user.getUserID());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnViewItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        UserAdapter.OnViewItemClickListener onViewItemClickListener;
        private final TextView textViewUserName;
        private final TextView textViewUserID;

        public ViewHolder(@NonNull View itemView, UserAdapter.OnViewItemClickListener onViewItemClickListener) {
            super(itemView);

            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewUserID = itemView.findViewById(R.id.textViewUserID);

            itemView.setOnClickListener(this);
            this.onViewItemClickListener = onViewItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onViewItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}