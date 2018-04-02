package fise.feng.com.beautifulwatchlauncher.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 作者：Rance on 2016/11/25 16:36
 * 邮箱：rance935@163.com
 */
public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager mFragmentManager;
    List<Fragment> list;
    boolean isNeedDynamicLoad = false;

    public CommonFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mFragmentManager = fm;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    private String getFragmentTag(int viewId, int index) {
        try {
            Class<FragmentPagerAdapter> cls = FragmentPagerAdapter.class;
            Class<?>[] parameterTypes = {int.class, long.class};
            Method method = cls.getDeclaredMethod("makeFragmentName",
                    parameterTypes);
            method.setAccessible(true);
            String tag = (String) method.invoke(this, viewId, index);
            return tag;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void removeFragment(ViewGroup container, int index) {
        String tag = getFragmentTag(container.getId(), index);
        Fragment fragment = mFragmentManager.findFragmentByTag(tag);
        if (fragment == null)
            return;
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.remove(fragment);
        //TODO 避免抛出异常"java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState"
        ft.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
//        removeFragment(container, position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
