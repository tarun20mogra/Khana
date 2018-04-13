package com.example.tarun.khana;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tarun on 4/12/2018.
 */

public class SeekerHistoryListAdapter extends RecyclerView.Adapter<SeekerHistoryListAdapter.HistoryListAdapterHolder> {
    private SeekerHistory context;
    private ArrayList<GetSeekerHistoryInfo> getSeekerHistoryInfos ;
    public SeekerHistoryListAdapter(SeekerHistory history, ArrayList<GetSeekerHistoryInfo> historyInfos){
        context = history;
        getSeekerHistoryInfos = historyInfos;

    }
    @NonNull
    @Override
    public HistoryListAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.seeker_history_card_list_view,parent,false);
        SeekerHistoryListAdapter.HistoryListAdapterHolder historyListAdapterHolder = new SeekerHistoryListAdapter.HistoryListAdapterHolder(v);
        return historyListAdapterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapterHolder holder, int position) {
        holder.dishName.setText(getSeekerHistoryInfos.get(position).dish_name_ordered);
        holder.dishQuanttity.setText(getSeekerHistoryInfos.get(position).dish_quantity_ordered);
        holder.date.setText(getSeekerHistoryInfos.get(position).date_order_made);

    }

    @Override
    public int getItemCount() {
        return getSeekerHistoryInfos.size();
    }

    public static class HistoryListAdapterHolder extends RecyclerView.ViewHolder{
        TextView dishName, dishQuanttity, date;
        public HistoryListAdapterHolder(View itemView) {
            super(itemView);
            dishName = (TextView) itemView.findViewById(R.id.dishNameHistorySeeker);
            dishQuanttity = (TextView) itemView.findViewById(R.id.quantityHistorySeeker);
            date = (TextView) itemView.findViewById(R.id.dateHistorySeeker);
        }
    }
}
