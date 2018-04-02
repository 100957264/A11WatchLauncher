package fise.feng.com.beautifulwatchlauncher.fragment;

import fise.feng.com.beautifulwatchlauncher.manager.PreferControler;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;


/**
 * Created by qingfeng on 2018/1/3.
 */

public class NotificationFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    Switch notiSwitch;

    public static NotificationFragment newInstance() {
        Bundle args = new Bundle();
        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(fise.feng.com.beautifulwatchlauncher.R.layout.notification_settings, container, false);
        notiSwitch = view.findViewById(fise.feng.com.beautifulwatchlauncher.R.id.noti_switch);
        notiSwitch.setOnCheckedChangeListener(this);
        notiSwitch.setChecked(PreferControler.instance().getNotiwitch());
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       boolean isOn = buttonView.isChecked() ? true : false;
        PreferControler.instance().setNOTIwitch(isOn);
    }

}
