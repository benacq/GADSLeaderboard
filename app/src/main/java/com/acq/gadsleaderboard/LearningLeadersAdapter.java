package com.acq.gadsleaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acq.gadsleaderboard.Models.LearningLeadersModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LearningLeadersAdapter extends RecyclerView.Adapter<LearningLeadersAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<LearningLeadersModel> mLeadersModels;

    LearningLeadersAdapter(Context context, List<LearningLeadersModel> learningLeadersModels){
        this.mLayoutInflater = LayoutInflater.from(context);
        mLeadersModels = learningLeadersModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.learning_leaders_custom_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String learnerName = mLeadersModels.get(position).getName();
        String learnerHours = Integer.toString(mLeadersModels.get(position).getHours()) +" learning hours, ";
        String learnerCountry = mLeadersModels.get(position).getCountry();
        String badgeUrl = mLeadersModels.get(position).getBadgeUrl();

        Picasso.get()
                .load(badgeUrl)
                .placeholder(R.drawable.loading_opaque)
                .into(holder.learnerBadge);

        holder.learnerName.setText(learnerName);
        holder.learnerHours.setText(learnerHours);
        holder.learnerCountry.setText(learnerCountry);
    }

    @Override
    public int getItemCount() {
        return mLeadersModels.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView learnerName, learnerHours, learnerCountry;
        ImageView learnerBadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            learnerName = itemView.findViewById(R.id.learner_name);
            learnerHours = itemView.findViewById(R.id.learner_detail);
            learnerCountry = itemView.findViewById(R.id.learner_country);
            learnerBadge = itemView.findViewById(R.id.learner_badge);

        }
    }
}
