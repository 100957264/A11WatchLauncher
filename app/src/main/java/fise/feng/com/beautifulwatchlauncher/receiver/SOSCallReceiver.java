package fise.feng.com.beautifulwatchlauncher.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Locale;

import fise.feng.com.beautifulwatchlauncher.KApplicationContext;
import fise.feng.com.beautifulwatchlauncher.Prenster.Dao.SosNumberEntityDaoUtil;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.manager.StaticManager;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import fise.feng.com.beautifulwatchlauncher.util.PhoneUtils;
import fise.feng.com.beautifulwatchlauncher.util.ToastUtils;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * @author mare
 * @Description:TODO 紧急呼叫
 * @csdnblog http://blog.csdn.net/mare_blue
 * @date 2017/9/13
 * @time 14:29
 */
public class SOSCallReceiver extends BroadcastReceiver {
    private static final String TAG = "CommonAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果是拨打电话
        String action = intent.getAction();
        LogUtils.e("SOSCallReceiver onReceive " + action);
        LogUtils.e("SOSCallReceiver isSosStart " + StaticManager.instance().isSosStart);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (action.equals(PhoneUtils.ACTION_CALL_SOS)) {
            boolean isIdelState = tm.getCallState() == TelephonyManager.CALL_STATE_IDLE;
            if (!isIdelState) {
                ToastUtils.showShortSafe(context.getString(R.string.previous_call_not_over));
                return;
            }
            StaticManager.instance().outgoingCalls = SosNumberEntityDaoUtil.instance().selectAll();
            if(StaticManager.instance().outgoingCalls == null){
                ToastUtils.showShort(context.getResources().getString(R.string.no_sos_number));
                return;
            }
            int size = StaticManager.instance().outgoingCalls.size();
            LogUtils.e("SOSCallReceiver: size=" + size);
            if (size <= 0) return;
            resetState();
            resetSoSState();
            String num;
            Vibrator vibrator = (Vibrator) KApplicationContext.sContext.getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{100, 50, 500, 100, 200}, -1);

            String firstNum = "";
            boolean isCallded = false;
            for (int i = 0; i < size; i++) {
                num = StaticManager.instance().outgoingCalls.get(i).getNumber();
                if (TextUtils.isEmpty(num)) continue;//跳过
                if (!isCallded) {
                    firstNum = num;
                    isCallded = true;
                }
            }
            if (TextUtils.isEmpty(firstNum)) {
                LogUtils.e("SOS号码不能为空 SOS呼叫失败....");
                ToastUtils.showShortSafe("SOS号码不能为空 SOS呼叫失败");
                return;
            }
            StaticManager.instance().isSosReady = true;
            PhoneUtils.call(firstNum);
        } else if (PhoneUtils.ACTION_CALL_CONNECTED.equals(action)) {
            StaticManager.instance().isOutgoingConnected = true;
            LogUtils.e("SOS接通了 ");
        } else if (PhoneUtils.ACTION_CALL_HANGUP_CAUSE.equals(action)) {
            int hangupCause = intent.getIntExtra(PhoneUtils.EXTRA_KEY_HANGUP_CAUSE, 0);
            LogUtils.e("挂断原因是 " + hangupCause);
            if (!StaticManager.instance().isSosStart) {//不是SOS呼叫 不用监听
                return;
            }
            if (hangupCause == PhoneUtils.EXTRA_FLAG_CALLER_HANGUP) {//是主动挂掉的
                resetSoSState();
                resetState();
            }
        } else if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            LogUtils.e(TAG, "SOSCallReceiver call OUT:" + phoneNumber);
            StaticManager.instance().isSosStart = StaticManager.instance().isSosReady;//SOS开始
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    LogUtils.e(TAG + " 去电 " + "RINGING");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    LogUtils.e(TAG + " 去电 " + "OFFHOOK");
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    LogUtils.e(TAG + " 去电 " + "IDLE");
                    break;
            }
//            LogUtils.e(TAG + " 去电 " + state);
        } else {// 如果是来电
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            switch (tm.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:
                    LogUtils.e(TAG + " 来电 " + "RINGING");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    LogUtils.e(TAG + " 来电 " + "OFFHOOK");
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    LogUtils.e(TAG + " 来电 " + "IDLE");
                    if (StaticManager.instance().isSosStart) {//是SOS报警状态
                        if (StaticManager.instance().isOutgoingConnected) {//接通了
                            LogUtils.e("已经接通过了");
                            resetState();
                            resetSoSState();
                        } else {//没接通
                            LogUtils.e("size " + StaticManager.instance().outgoingCalls.size() + "-- 第" + (StaticManager.instance().mCurrentOutgoingIndex + 1) +
                                    "次 -- 第" + (StaticManager.instance().mNoAnswerCount + 1) + "次循环");
                            StaticManager.instance().mCurrentOutgoingIndex++;//
                            if (StaticManager.instance().mCurrentOutgoingIndex <
                                    StaticManager.instance().outgoingCalls.size()) {
                                LogUtils.e("这轮还没打完接着打");
                            } else {
                                LogUtils.e("这轮打完了接着打下轮...");
                                StaticManager.instance().mNoAnswerCount++;
                                StaticManager.instance().mCurrentOutgoingIndex = 0;
                            }
                            LogUtils.e(StaticManager.instance().outgoingCalls.size() + " -- " + (StaticManager.instance().mCurrentOutgoingIndex + 1) + " -- " + (StaticManager.instance().mNoAnswerCount + 1));
                            if (StaticManager.instance().mNoAnswerCount < StaticManager.instance().sMaxNoAnswerCount) {
                                String nextNum = StaticManager.instance().outgoingCalls.get(StaticManager.instance().mCurrentOutgoingIndex).getNumber();
                                LogUtils.e("SOS还没结束 开始拨打 " + nextNum + ".....");
                                PhoneUtils.call(nextNum);
                            } else {
                                LogUtils.e("打了两遍还没人接听");
                                resetState();
                                resetSoSState();
                            }
                        }
                    }
                    break;
            }
        }
    }

    private void resetState() {
        StaticManager.instance().mNoAnswerCount = 0;
        StaticManager.instance().mCurrentOutgoingIndex = 0;
        StaticManager.instance().outgoingCalls.clear();
    }

    private void resetSoSState() {
        StaticManager.instance().isOutgoingConnected = false;//重置为false
        StaticManager.instance().isSosReady = false;//置开关为false
        StaticManager.instance().isSosStart = false;
    }

}
