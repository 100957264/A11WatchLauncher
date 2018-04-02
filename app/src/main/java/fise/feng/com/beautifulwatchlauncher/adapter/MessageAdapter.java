package fise.feng.com.beautifulwatchlauncher.adapter;

import android.content.Context;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.entity.NotificationEntity;
import fise.feng.com.beautifulwatchlauncher.util.TimeUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by qingfeng on 2017/12/27.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    Context mContext;
    List<NotificationEntity> mNotificationList;
    LayoutInflater inflater;
    public MessageAdapter(Context context, List<NotificationEntity> notificationList){
        this.mContext = context;
        this.mNotificationList = notificationList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_messages,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
       viewHolder.smallIcon.setImageResource(R.drawable.icon_notification);
       viewHolder.title.setText(mNotificationList.get(i).getTitle());
        DateFormat format = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String timeStr = TimeUtils.millis2String(mNotificationList.get(i).time,format);
       viewHolder.time.setText(timeStr);
       viewHolder.content.setText(mNotificationList.get(i).content);
    }

    @Override
    public int getItemCount() {
        return mNotificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView smallIcon;
        TextView title;
        TextView content;
        TextView time;
        public ViewHolder(View itemView) {
            super(itemView);
            this.smallIcon = itemView.findViewById(R.id.item_message_icon);
            this.title = itemView.findViewById(R.id.item_message_title);
            this.content = itemView.findViewById(R.id.item_message_content);
            this.time = itemView.findViewById(R.id.item_message_time);
        }
    }
}
