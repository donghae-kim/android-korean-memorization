package org.techtown.northkorean_memorization;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> arrayList;
    private Context context;

    public UserAdapter(ArrayList<User> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false);
        UserViewHolder holder = new UserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.userName.setText(arrayList.get(position).getUserName());
        holder.userScore.setText(arrayList.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView user_profile;
        TextView userName;
        TextView userScore;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.user_profile = itemView.findViewById(R.id.user_profile);
            this.userName = itemView.findViewById(R.id.rv_name);
            this.userScore = itemView.findViewById(R.id.rv_score);
        }
    }
}
