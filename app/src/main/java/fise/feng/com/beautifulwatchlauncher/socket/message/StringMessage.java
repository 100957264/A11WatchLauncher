package fise.feng.com.beautifulwatchlauncher.socket.message;

import fise.feng.com.beautifulwatchlauncher.socket.util.TypeUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by xubingbing on 2017/8/10.
 */

public class StringMessage implements IMessage<String> {
    private String mContent;
    private byte[] contentByte;
    private String mExtend;
    private long mLength;
    private byte mType = TYPE_String;

    @Override
    public byte getType() {
        return mType;
    }

    @Override
    public void setType(byte type) {
        mType = type;
    }

    @Override
    public long getLength() {
        return mLength;
    }

    @Override
    public void setLength(long length) {
        mLength = length;
    }

    @Override
    public String getContent() {
        return mContent;
    }

    @Override
    public void setExtend(String extend) {
        mExtend = extend;
    }


    @Override
    public void setContent(String content, String extend) {
        contentByte = content.getBytes(Charset.forName("UTF-8"));
        mLength = contentByte.length;
        if (TextUtils.isEmpty(extend)) {
            mExtend = "";
        } else {
            mExtend = extend;
        }
    }

    @Override
    public void parseContent(InputStream inputStream) throws IOException {
        contentByte = new byte[(int) mLength];
        inputStream.read(contentByte,0, (int) mLength);
        mContent = new String(contentByte, Charset.forName("UTF-8"));
    }



    @Override
    public void writeContent(OutputStream outputStream) throws IOException {
        outputStream.write(creatHeader());
        outputStream.write(0xA);
        outputStream.write(0xD);
        outputStream.write(contentByte);
    }

    @Override
    public byte[] creatHeader() {
        byte[] extend = mExtend.getBytes();
        byte[] length = TypeUtils.longToBytes(getLength());
        byte[] header = new byte[10 + extend.length];
        header[0] = HEADER;                                         //魔数
        header[1] = getType();                                     //类型
        System.arraycopy(length, 0, header, 2, 8);     //长度
        System.arraycopy(extend, 0, header, 10, extend.length);     //扩展信息
        return header;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mContent);
        dest.writeByteArray(this.contentByte);
        dest.writeString(this.mExtend);
        dest.writeLong(this.mLength);
        dest.writeByte(this.mType);
    }

    public StringMessage() {
    }

    protected StringMessage(Parcel in) {
        this.mContent = in.readString();
        this.contentByte = in.createByteArray();
        this.mExtend = in.readString();
        this.mLength = in.readLong();
        this.mType = in.readByte();
    }

    public static final Parcelable.Creator<StringMessage> CREATOR = new Parcelable.Creator<StringMessage>() {
        @Override
        public StringMessage createFromParcel(Parcel source) {
            return new StringMessage(source);
        }

        @Override
        public StringMessage[] newArray(int size) {
            return new StringMessage[size];
        }
    };

    @Override
    public String toString() {
        return "StringMessage{" +
                "mContent='" + mContent + '\'' +
                ", contentByte=" + Arrays.toString(contentByte) +
                ", mExtend='" + mExtend + '\'' +
                ", mLength=" + mLength +
                ", mType=" + mType +
                '}';
    }
}
