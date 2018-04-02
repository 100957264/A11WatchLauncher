package fise.feng.com.beautifulwatchlauncher.socket.util;


import fise.feng.com.beautifulwatchlauncher.socket.message.IMessage;
import fise.feng.com.beautifulwatchlauncher.socket.message.ImageMessage;
import fise.feng.com.beautifulwatchlauncher.socket.message.StringMessage;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * @author bingbing
 * @date 16/7/2
 */
public class TypeUtils {

    //byte 数组与 long 的相互转换
    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }


    /**
     * 从流里面读取
     *
     * @return
     * @throws IOException
     */
    public static IMessage readHeader(InputStream inputStream) throws IOException {
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (true) {
            int data = inputStream.read();
            Log.d("BLUE","data:"+data + "");
            if (data == 0x0A) {   //\r  不增加
                Log.d("readHeader", "sss");
            } else if (data == 0x0D) { //\n
                if (arrayList.size() < 10) {
                    arrayList.clear();
                    continue;
                }
                byte[] buffer = new byte[arrayList.size()];
                for (int i = 0; i < arrayList.size(); i++) {
                    buffer[i] = arrayList.get(i).byteValue();
                }

                IMessage iMessage = parseHeader(buffer);
                if (iMessage != null) {
                    Log.d("message",iMessage.toString());
                    return iMessage;
                } else {
                    arrayList.clear();
                }
            } else {
                if (arrayList.size() == 0) {
                    if (data != IMessage.HEADER) {
                        continue;
                    }
                }
                arrayList.add(data);
            }
        }
    }


    public static IMessage parseHeader(byte[] header) {
        if (header.length < 10) return null;

        IMessage message = null;
        byte tempType = header[1];

        if (tempType == IMessage.TYPE_BYTE) {
            message = new ImageMessage();
        } else if (tempType == IMessage.TYPE_String) {
            message = new StringMessage();
        } else {
            return null;
        }

        message.setType(tempType);

        byte[] length = new byte[8];
        System.arraycopy(header, 2, length, 0, 8);
        long mLength = TypeUtils.bytesToLong(length);
        String mExtend = new String(header, 10, header.length - 10);
        message.setLength(mLength);
        message.setExtend(mExtend);
        return message;
    }
}
