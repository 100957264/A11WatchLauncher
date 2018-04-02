package fise.feng.com.beautifulwatchlauncher.fragment;

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

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.constant.QRConstant;
import fise.feng.com.beautifulwatchlauncher.manager.StaticManager;
import fise.feng.com.beautifulwatchlauncher.util.BluetoothUtils;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import fise.feng.com.beautifulwatchlauncher.util.QrCodeUtils;
import fise.feng.com.beautifulwatchlauncher.zxing.ZXingUtils;

/**
 * Created by qingfeng on 2017/12/20.
 */

public class DownloadQRCodeFragment extends Fragment {
    ImageView qrView;
    TextView qrTextView;
    boolean isGenerQRSuccess = false;
    private boolean isViewInited = false;
    public File mQRLocateFile;
    public String mQRLocateFilePath;
    String btAddress = "";
    String btAddressTxt = "";
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    public static DownloadQRCodeFragment newInstance() {
        Bundle args = new Bundle();
        DownloadQRCodeFragment fragment = new DownloadQRCodeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void init(View view){
        qrView = (ImageView) view.findViewById(R.id.btn__download_app_qrcode);
        qrTextView = (TextView) view.findViewById(R.id.tv_download_app_qrcode);

    }
    void setView(String QRCodePath) {
        Bitmap QRCadeBt = BitmapFactory.decodeFile(QRCodePath);
        if (QRCadeBt == null) {
            qrTextView.setText("出现错误");
            return;
        }
        qrView.setImageBitmap(QRCadeBt);
        qrTextView.setText(R.string.scan_to_download);
        LogUtils.d("setView QRCodePath=" + QRCodePath);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_qrcode_fragment,container,false);
        init(view);
        initQRView();
        return view;
    }

    private void initQRView(){
        btAddressTxt ="https://pan.baidu.com/s/1jJgDE5o";
        LogUtils.d("btAddressTxt=" + btAddressTxt);
        mQRLocateFile = QrCodeUtils.getTempFile("download");
        mQRLocateFilePath = mQRLocateFile.getAbsolutePath();
        LogUtils.d("filePath " + mQRLocateFilePath);
        singleThreadExecutor.execute(generQr);
    }
    Runnable generQr = new Runnable() {
        @Override
        public void run() {
            LogUtils.d("exeTask tempLogoPath :" + mQRLocateFilePath);
            isGenerQRSuccess = ZXingUtils.createQRImage(btAddressTxt, 200, 200, null, mQRLocateFilePath);
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
