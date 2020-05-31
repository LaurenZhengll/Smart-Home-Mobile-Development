package com.stevecrossin.mindlab;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<NotificationItem> mData;

    public NotificationAdapter(ArrayList<NotificationItem>listItem){
        this.mData = listItem;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent,false);

    return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationItem listItems = mData.get(position);

       holder.Title.setText(NotificationItem.getTitle());
        holder.NotificationContent.setText(NotificationItem.getNotificationContent());
        holder.NotificationDate.setText(NotificationItem.getNotificationDate());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView view;
        TextView Title;
        TextView NotificationDate;
        TextView NotificationContent;

        ViewHolder(@NonNull View view) {
            super(view);
            Title = (TextView) view.findViewById(R.id.Title);
            NotificationDate = (TextView) view.findViewById(R.id.NotificationDate);
            NotificationContent = (TextView) view.findViewById(R.id.NotificationContent);


        }
    }
}
