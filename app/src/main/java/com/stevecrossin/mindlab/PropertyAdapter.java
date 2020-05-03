package com.stevecrossin.mindlab;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PropertyAdapter  extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>{
    // Store a member variable for the property
    // As the Property is a keyword we use "com.example.customlistviewdemo" to refer to that
    private List<DeviceBean> propertyList;
    private Context context;
    // Provide a suitable constructor (depends on the kind of dataset)
    // Pass the property array into the constructor
    public PropertyAdapter(List<com.stevecrossin.mindlab.DeviceBean> propertyList, Context context) {
        this.propertyList = propertyList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    // Provide a direct reference to each of the views within a data item
    public class PropertyViewHolder extends RecyclerView.ViewHolder
    {   // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView avatarImageView;
        public TextView statusTextView, priorityTextView;
        // Create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public PropertyViewHolder (View view)
        {   super(view);
            avatarImageView = view.findViewById(R.id.avatar);
            statusTextView = view.findViewById(R.id.status);
            priorityTextView = view.findViewById(R.id.priority);
        }
    }

    @NonNull
    @Override //we inflate the row layout returning an instance of the class PropertyViewHolder
    public PropertyAdapter.PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(context).inflate(R.layout.property_layout, parent, false);
        return new PropertyAdapter.PropertyViewHolder(itemView);
    }

    @Override  // Populating data into the item through holder
    public void onBindViewHolder (PropertyViewHolder holder, int position )
    {   // get element from your dataset (Property) at this position
        // replace the contents of the view with that element
        holder.avatarImageView.setImageResource(propertyList.get(position).getImage());
        holder.statusTextView.setText(propertyList.get(position).getStatus());
        holder.priorityTextView.setText(propertyList.get(position).getPriority());
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount(){
        return propertyList.size();
    }
}
