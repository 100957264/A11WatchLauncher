package fise.feng.com.beautifulwatchlauncher.fragment;

import android.app.Fragment;
import fise.feng.com.beautifulwatchlauncher.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by qingfeng on 2017/12/14.
 */

public class BottomFragment extends Fragment{


    public static BottomFragment newInstance() {
        Bundle args = new Bundle();
        BottomFragment fragment = new BottomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.bottom_fragment,container,false);
        return view;
    }
}
