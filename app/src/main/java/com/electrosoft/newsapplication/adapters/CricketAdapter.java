package com.electrosoft.newsapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.electrosoft.newsapplication.R;
import com.electrosoft.newsapplication.pojos.Constants;

import java.util.ArrayList;

public class CricketAdapter extends RecyclerView.Adapter<CricketAdapter.viewHolder> {

    private Context context;
    private ArrayList<Constants> cricketList;

    public CricketAdapter(Context context, ArrayList<Constants> cricketList) {
        this.context = context;
        this.cricketList = cricketList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.decisn = cricketList.get(position).decisn;
        holder.matchId = cricketList.get(position).matchId;
        holder.datapath = cricketList.get(position).datapath;
        holder.team1_fName = cricketList.get(position).team1_fName;
        holder.team1_id = cricketList.get(position).team1_id;
        holder.team2_id = cricketList.get(position).team2_id;
        holder.batteamid = cricketList.get(position).batteamid;
        holder.bowlteamid = cricketList.get(position).bowlteamid;
        holder.team2_fName = cricketList.get(position).team2_fName;
        holder.mchState = cricketList.get(position).mchState;
        holder.batteamscore_str = cricketList.get(position).batteamscore;
        holder.bowlteamscore_str = cricketList.get(position).bowlteamscore;
//        holder.flag_url1 = "http://i.cricketcb.com/cbzandroid/2.0/flags/team_" + holder.team1_id + ".png";
//        holder.flag_url2 = "http://i.cricketcb.com/cbzandroid/2.0/flags/team_" + holder.team2_id + ".png";
//        Picasso.with(main.getActivity()).load(holder.flag_url1).into(holder.flag1);
//        Picasso.with(main.getActivity()).load(holder.flag_url2).into(holder.flag2);

        holder.srs_str = cricketList.get(position).srs;
        if(cricketList.get(position).overs==null)
            holder.overs.setText("");
        else
            holder.overs.setVisibility(View.GONE);
        holder.status.setText(cricketList.get(position).status);
        holder.mnum.setText(cricketList.get(position).mnum);
        holder.batteamscore.setText(cricketList.get(position).batteamscore);
        holder.team1.setText(cricketList.get(position).team1_sName);
        holder.team2.setText(cricketList.get(position).team2_sName);
        holder.type.setText(cricketList.get(position).type);
        holder.batteamscore.setText(cricketList.get(position).batteamscore);
        holder.bowlteamscore.setText(cricketList.get(position).bowlteamscore);
        holder.vcity.setText(cricketList.get(position).vcity);
        holder.vcountry.setText(cricketList.get(position).vcountry);
        holder.time.setText(cricketList.get(position).stTme);
        int colorPosition = position % holder.bgColors.length;
        holder.bg_listview.setBackgroundResource(holder.bgColors[colorPosition]);
        holder.bowlteamscore.setTextColor(position % 2 == 0 ? Color.parseColor("#91BF17") : Color.parseColor("#3762ff"));
        holder.batteamscore.setTextColor(position % 2 == 0 ? Color.parseColor("#91BF17") : Color.parseColor("#3762ff"));
        holder.srs.setText(cricketList.get(position).srs);
    }

    @Override
    public int getItemCount() {
        return cricketList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView srs, sname, batteamscore, overs, runs, status, mnum, team1, team2, type, bowlteamscore, scnd_sname, srs_dot;
        TextView vcity, vcountry, time, oversright, time_num;
        RelativeLayout bat_score_layout, bowl_score_layout, vcity_vcountry_layout, bg_listview;
        String TW, decisn, team1_fName, team2_fName, team1_id, team2_id, flag_url1, flag_url2, srs_str;
        String mchState, batteamid, bowlteamid, batteamscore_str, bowlteamscore_str;
        String matchId, datapath;
        private final int[] bgColors = new int[]{R.color.match_name_display_color, R.color.colorPrimary};

        public viewHolder(@NonNull View convertView) {
            super(convertView);

            batteamscore = (TextView) convertView.findViewById(R.id.bateamscore);
            srs = (TextView) convertView.findViewById(R.id.srs);
            bg_listview = (RelativeLayout) convertView.findViewById(R.id.layout1);


            overs = (TextView) convertView.findViewById(R.id.overs);
            // holder.time_num = (TextView) convertView.findViewById(R.id.time_num);
            //   holder.runs = (TextView) convertView.findViewById(R.id.runs);
            status = (TextView) convertView.findViewById(R.id.status);
            srs_dot = (TextView) convertView.findViewById(R.id.srs_dot);

            mnum = (TextView) convertView.findViewById(R.id.mnum);
            team1 = (TextView) convertView.findViewById(R.id.team1);
            team2 = (TextView) convertView.findViewById(R.id.team2);
            type = (TextView) convertView.findViewById(R.id.type);
            time = (TextView) convertView.findViewById(R.id.time);
            vcity = (TextView) convertView.findViewById(R.id.grnd);
            vcountry = (TextView) convertView.findViewById(R.id.country);
            bowlteamscore = (TextView) convertView.findViewById(R.id.bowlteamscore);
            batteamscore = (TextView) convertView.findViewById(R.id.bateamscore);
        }
    }
}
