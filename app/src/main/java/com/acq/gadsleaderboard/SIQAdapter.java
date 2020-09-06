package com.acq.gadsleaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acq.gadsleaderboard.Models.IQLeadersModel;
import com.acq.gadsleaderboard.Models.LearningLeadersModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SIQAdapter extends RecyclerView.Adapter<SIQAdapter.ViewHolder> {


    private LayoutInflater mLayoutInflater;
    private List<IQLeadersModel> mLeadersModels;

    SIQAdapter(Context context, List<IQLeadersModel> siqLeadersModel){
        this.mLayoutInflater = LayoutInflater.from(context);
        mLeadersModels = siqLeadersModel;
    }

    @NonNull
    @Override
    public SIQAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.learning_leaders_custom_view, parent, false);
        return new SIQAdapter.ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull SIQAdapter.ViewHolder holder, int position) {
        String learnerName = mLeadersModels.get(position).getName();
        String learnerScore = Integer.toString(mLeadersModels.get(position).getScore()) +" Skill IQ Score, ";
        String learnerCountry = mLeadersModels.get(position).getCountry();
        String badgeUrl = mLeadersModels.get(position).getBadgeUrl();

        Picasso.get()
                .load(badgeUrl)
                .placeholder(R.drawable.loading_opaque)
                .into(holder.learnerBadge);

        holder.learnerName.setText(learnerName);
        holder.learnerScore.setText(learnerScore);
        holder.learnerCountry.setText(learnerCountry);
    }

    @Override
    public int getItemCount() {
        return mLeadersModels.size();
    }


    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView learnerName, learnerScore, learnerCountry;
        ImageView learnerBadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            learnerName = itemView.findViewById(R.id.learner_name);
            learnerScore = itemView.findViewById(R.id.learner_detail);
            learnerCountry = itemView.findViewById(R.id.learner_country);
            learnerBadge = itemView.findViewById(R.id.learner_badge);

        }
    }
}
