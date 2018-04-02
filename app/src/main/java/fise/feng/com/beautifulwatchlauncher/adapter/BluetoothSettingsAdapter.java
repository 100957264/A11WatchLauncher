package fise.feng.com.beautifulwatchlauncher.adapter;

import android.content.Context;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.entity.SettingsEntity;
import fise.feng.com.beautifulwatchlauncher.manager.StaticManager;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qingfeng on 2017/12/27.
 */

public class BluetoothSettingsAdapter extends RecyclerView.Adapter<BluetoothSettingsAdapter.ViewHolder>{
    Context mContext;
    List<SettingsEntity> mSettingsist;
    LayoutInflater inflater;
    public BluetoothSettingsAdapter(Context context, List<SettingsEntity> settingsEntityList){
        this.mContext = context;
        this.mSettingsist = settingsEntityList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_settings,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
       viewHolder.smallIcon.setImageResource(mSettingsist.get(i).getDrawableId());
       viewHolder.title.setText(mSettingsist.get(i).getTitle());
       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mContext.startActivity(StaticManager.instance().getIntentById(mContext,viewHolder.getAdapterPosition()));
               LogUtils.d("iii=" + viewHolder.getAdapterPosition());
           }
       });
    }

    @Override
    public int getItemCount() {
        return mSettingsist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView smallIcon;
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            this.smallIcon = itemView.findViewById(R.id.item_settings_icon);
            this.title = itemView.findViewById(R.id.item_settings_title);
        }
    }
}
