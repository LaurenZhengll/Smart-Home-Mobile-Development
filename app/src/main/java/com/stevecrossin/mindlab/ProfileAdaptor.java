package com.stevecrossin.mindlab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileAdaptor extends RecyclerView.Adapter<ProfileAdaptor.ProfileViewHolder> {
    private List<ProfileItem> profileList;
    private Context context;

    // Pass the ProfileItem array into the constructor
    public ProfileAdaptor(List<ProfileItem> profileList, Context context) {
        this.profileList = profileList;
        this.context = context;
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {
        public TextView profileText;
        public TextView profileInfo;
        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            profileText = itemView.findViewById(R.id.profileText);
            profileInfo = itemView.findViewById(R.id.profileInfo);
        }
    }

    @NonNull
    @Override
    public ProfileAdaptor.ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new item view with empty textView by inflating
        View itemView = LayoutInflater.from(context).inflate(R.layout.profile_item, parent, false);
        return new ProfileAdaptor.ProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdaptor.ProfileViewHolder holder, int position) {
        // get element from dataset at this position
        holder.profileText.setText(profileList.get(position).getProfileQues());
        holder.profileInfo.setText(profileList.get(position).getProfileInfo());
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

}
