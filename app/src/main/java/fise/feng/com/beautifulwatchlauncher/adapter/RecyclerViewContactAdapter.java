package fise.feng.com.beautifulwatchlauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.entity.SosNumberEntity;

/**
 * Created by qingfeng on 2018/1/3.
 */

public class RecyclerViewContactAdapter extends RecyclerView.Adapter<RecyclerViewContactAdapter.ViewHolder>{
    Context context;
    List<SosNumberEntity> mContactInfoList;
    LayoutInflater inflater;
    public RecyclerViewContactAdapter(Context context, List<SosNumberEntity> contactInfoList){
       this.context = context;
       this.mContactInfoList = contactInfoList;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_contact,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.contactIcon.setImageResource(R.drawable.icon_avater);
        String text = mContactInfoList.get(position).name != null ?mContactInfoList.get(position).name : mContactInfoList.get(position).number;
        holder.contactName.setText(text);
        holder.contactNumber.setText(mContactInfoList.get(position).number);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mContactInfoList == null ? 0: mContactInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView contactIcon;
        TextView contactName;
        TextView contactNumber;

        public ViewHolder(View view) {
            super(view);
            this.contactIcon = (ImageView)view.findViewById(R.id.item_contact_icon);
            this.contactName = (TextView) view.findViewById(R.id.item_contact_name);
            this.contactNumber =(TextView) view.findViewById(R.id.item_contact_number);
        }
    }
}
