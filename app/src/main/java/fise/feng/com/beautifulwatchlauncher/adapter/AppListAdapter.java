package fise.feng.com.beautifulwatchlauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.entity.AppEntity;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;

/**
 * Created by qingfeng on 2018/2/6.
 */

public class AppListAdapter extends BaseAdapter {
    private List<AppEntity> mAppInfo = null;

    LayoutInflater infater = null;
    Context mContext;
    public AppListAdapter(Context context, List<AppEntity> apps){
        infater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAppInfo  = apps;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mAppInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        FiseViewHolder holder = null;
        if(convertView == null || convertView.getTag() == null){
            view = infater.inflate(R.layout.items,null);
            holder = new FiseViewHolder(view);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (FiseViewHolder)convertView.getTag();
        }
        final AppEntity appInfo = (AppEntity) getItem(position);
        holder.appIcon.setImageDrawable(appInfo.getAppIcon());
        holder.appName.setText(appInfo.getAppName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mContext.startActivity(appInfo.getIntent());
                }catch (Exception e){
                    LogUtils.e("e =" + e);
                }
            }
        });

        return view;
    }
    class FiseViewHolder{
        ImageView appIcon;
        TextView appName;
        public FiseViewHolder(View view){
            this.appIcon = (ImageView)view.findViewById(R.id.image);
            this.appName = (TextView) view.findViewById(R.id.text);
        }
    }
}
