package fise.feng.com.beautifulwatchlauncher.fragment;

import fise.feng.com.beautifulwatchlauncher.R;
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

public class PhoneFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    Switch phoneSwitch;

    public static PhoneFragment newInstance() {
        Bundle args = new Bundle();
        PhoneFragment fragment = new PhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phone_settings, container, false);
        phoneSwitch = view.findViewById(R.id.phone_switch);
        phoneSwitch.setOnCheckedChangeListener(this);
        phoneSwitch.setChecked(PreferControler.instance().getPhonewitch());
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       boolean isOn = buttonView.isChecked() ? true : false;
        PreferControler.instance().setPhonewitch(isOn);
    }

}
