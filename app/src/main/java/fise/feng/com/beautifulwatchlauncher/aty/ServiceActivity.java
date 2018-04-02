package fise.feng.com.beautifulwatchlauncher.aty;

import android.bluetooth.BluetoothDevice;

import fise.feng.com.beautifulwatchlauncher.socket.BlueSocketBaseThread;
import fise.feng.com.beautifulwatchlauncher.socket.BluetoothSppHelper;
import fise.feng.com.beautifulwatchlauncher.socket.message.IMessage;
import fise.feng.com.beautifulwatchlauncher.socket.message.ImageMessage;
import fise.feng.com.beautifulwatchlauncher.socket.message.StringMessage;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import fise.feng.com.beautifulwatchlauncher.util.NotificationUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author bingbing
 * @date 16/4/7
 */
public class ServiceActivity extends BaseActivity implements BluetoothSppHelper.BlueSocketListener {

    private EditText mEdit;
    private TextView mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(fise.feng.com.beautifulwatchlauncher.R.layout.activity_service);
        mEdit = (EditText) findViewById(fise.feng.com.beautifulwatchlauncher.R.id.edit);
        mStatus = (TextView) findViewById(fise.feng.com.beautifulwatchlauncher.R.id.text);
        BluetoothSppHelper.instance().setBlueSocketListener(this);
        BluetoothSppHelper.instance().strat();
    }


    @Override
    public void onBlueSocketStatusChange(BlueSocketBaseThread.BlueSocketStatus status, BluetoothDevice remoutDevice) {
        mStatus.setText(status.toString());
    }

    @Override
    public void onBlueSocketMessageReceiver(IMessage message) {
        LogUtils.e("onBlueSocketMessageReceiver message " + message.toString());
        if (message instanceof StringMessage){
            LogUtils.e("onBlueSocketMessageReceiver message " + ((StringMessage)message).toString());
            byte msgType = message.getType();
            switch (msgType){
                case 'N':
                    NotificationUtils.recvMsg((StringMessage) message);
                    break;
                case 'S':
                    //NotificationUtils.recvMsg((StringMessage) message);
                    break;
                    default:
                        LogUtils.e("onBlueSocketMessageReceiver message " + msgType);
                        break;
            }
            Toast.makeText(this, ((StringMessage)message).getContent(), Toast.LENGTH_SHORT).show();
        }else if (message instanceof ImageMessage){
            Toast.makeText(this, ((ImageMessage)message).getContent().getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothSppHelper.instance().stop();
    }

    public void sendText(View view) {
//        LogUtils.d("sendText...........start");
//        NotficationTxt notficationTxt = new NotficationTxt("abc","aaaaa啊啊啊啊啊","19:15","com.xx.aa",R.drawable.ic_light);
//        NotificationUtils.sendMsg(notficationTxt);
//        StringMessage stringMessage = new StringMessage();
//        stringMessage.setContent("为何发送不了呢","");
//        BluetoothSppHelper.instance().write(stringMessage);
    }

}
