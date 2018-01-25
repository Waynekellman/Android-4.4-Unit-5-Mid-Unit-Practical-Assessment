package com.nyc.android_44_unit_5_mid_unit_practical_assessment.conroller;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nyc.android_44_unit_5_mid_unit_practical_assessment.DetailsActivity;
import com.nyc.android_44_unit_5_mid_unit_practical_assessment.R;
import com.nyc.android_44_unit_5_mid_unit_practical_assessment.model.Results;
import com.squareup.picasso.Picasso;

/**
 * Created by Wayne Kellman on 1/24/18.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Results[] results;

    public UserAdapter(Results[] results) {
        this.results = results;
    }
    public void setAdapter(Results[] results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public UserAdapter() {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(holder.view.getContext()).load(results[position].getPicture().getMedium()).into(holder.userImage);
        String name = results[position].getName().getTitle() + " " + results[position].getName().getFirst() + " " + results[position].getName().getLast();
        holder.userName.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                String userString = new Gson().toJson(results[position]);
                intent.putExtra("userAsJson", userString);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView userImage;
        private TextView userName;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.user_image);
            userName = itemView.findViewById(R.id.user_name);
            view = itemView;
        }
    }
}
