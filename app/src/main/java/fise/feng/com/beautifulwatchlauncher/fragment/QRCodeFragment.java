package fise.feng.com.beautifulwatchlauncher.fragment;

import fise.feng.com.beautifulwatchlauncher.constant.QRConstant;
import fise.feng.com.beautifulwatchlauncher.manager.StaticManager;
import fise.feng.com.beautifulwatchlauncher.util.BluetoothUtils;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import fise.feng.com.beautifulwatchlauncher.util.QrCodeUtils;
import fise.feng.com.beautifulwatchlauncher.zxing.ZXingUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by qingfeng on 2017/12/20.
 */

public class QRCodeFragment extends Fragment {
    ImageView qrView;
    TextView qrTextView;
    boolean isGenerQRSuccess = false;
    private boolean isViewInited = false;
    public File mQRLocateFile;
    public String mQRLocateFilePath;
    String btAddress = "";
    String btAddressTxt = "";
    String urlAddress = "";
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    public static QRCodeFragment newInstance() {
        Bundle args = new Bundle();
        QRCodeFragment fragment = new QRCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void init(View view){
        qrView = (ImageView) view.findViewById(fise.feng.com.beautifulwatchlauncher.R.id.btn_app_qrcode);
        qrTextView = (TextView) view.findViewById(fise.feng.com.beautifulwatchlauncher.R.id.tv_app_qrcode);

    }
    void setView(String QRCodePath) {
        Bitmap QRCadeBt = BitmapFactory.decodeFile(QRCodePath);
        if (QRCadeBt == null) {
            qrTextView.setText("出现错误");
            return;
        }
        qrView.setImageBitmap(QRCadeBt);
        qrTextView.setText(getActivity().getResources().getString(fise.feng.com.beautifulwatchlauncher.R.string.scan_to_bind));
        LogUtils.d("setView QRCodePath=" + QRCodePath);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(fise.feng.com.beautifulwatchlauncher.R.layout.qrcode_fragment,container,false);
        init(view);
        initQRView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
       boolean isBluetoothOn = BluetoothUtils.turnOnBluetooth();
        BluetoothUtils.setBTDiscoverableTimeout(30);
       if(!isBluetoothOn){
           Toast.makeText(getActivity(),"please turn on BT at fist!",Toast.LENGTH_SHORT).show();
       }

       LogUtils.d("isBluetoothOn =" + isBluetoothOn);
    }

    private void initQRView(){
        btAddress = BluetoothUtils.getBTAddress(getActivity());
        String imeiText = StaticManager.IMEI;
        if(TextUtils.isEmpty(imeiText)){
            imeiText  = "866542020000142";
        }
        btAddressTxt = QRConstant.FISE_STRING + "_" + imeiText + "_" + btAddress;
        LogUtils.d("btAddressTxt=" + btAddressTxt);
        urlAddress  = "http://www.finowatch.com/userfiles/file/BluetoothHelper.apk";
        mQRLocateFile = QrCodeUtils.getTempFile(btAddress);
        mQRLocateFilePath = mQRLocateFile.getAbsolutePath();
        LogUtils.d("filePath " + mQRLocateFilePath);
        singleThreadExecutor.execute(generQr);
    }
    Runnable generQr = new Runnable() {
        @Override
        public void run() {
            LogUtils.d("exeTask tempLogoPath :" + mQRLocateFilePath);
            isGenerQRSuccess = ZXingUtils.createQRImage(urlAddress, 200, 200, null, mQRLocateFilePath);
            LogUtils.d("exeTask isSuccess :" + isGenerQRSuccess);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setView(mQRLocateFilePath);
                }
            });
        }
    };

}
