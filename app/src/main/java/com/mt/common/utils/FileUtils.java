package com.mt.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * Created by cmt on 2019/7/5
 */
public class FileUtils {
    private static final String TAG = "FileUtils";
    //检查SDCard存在并且可以读写
    public static boolean isSDCardState(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    //创建一个文件，如果所在的文件夹不存在的话，顺便创建文件夹
    public static File createFile(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            return null;
        }else{
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 文件转bytep[]
     * @param fileName
     * @return
     */
    public static byte[] fileToByteArray(String fileName){
        File file = new File(fileName);
        byte[] byt = null;
        if(!file.exists())return null;
        try {
            InputStream inputStream = new FileInputStream(file);
            byt = new byte[inputStream.available()];
            inputStream.read(byt);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byt;
    }

    /**
     * byte[] 数组转换成文件
     * @param data
     * @param filePath
     * @return
     */
    public static File byteArrayToFile(byte[] data,String filePath){
        if(data.length<=0||data==null){
            Log.e(TAG,"ByteArrayToFile data is null");
            return null;
        }
        File file = createFile(filePath);
        if(file!=null){
            try {
                OutputStream outputStream = new FileOutputStream(file);
                BufferedOutputStream bufferedOutput = new BufferedOutputStream(outputStream);
                bufferedOutput.write(data);
                bufferedOutput.flush();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    //base64加密
    public static String base64encode(byte[] data){
        if(data==null||data.length<=0) Log.e("base64encode","data  is null");
        String str = new String(Base64.encode(data,Base64.DEFAULT));
        return str;
    }
    //base64解密
    public static String base64Decode(String data){
        if(data==null||data.equals("")) Log.e("base64decode","data  is null");
        String str = new String(Base64.decode(data, Base64.DEFAULT));
        return str;
    }

    /**
     * file to bitmap
     * @param filePath
     * @return
     */
    public static Bitmap fileToBitmap(String filePath){
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        return bitmap;
    }

    public static File BitmapToFile(Bitmap bitmap,int quality,String filePath){
        File file = new File(filePath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return file;
    }

    public static Bitmap byteArrayToBitmap(byte[] b){
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();

    }

    public static Bitmap drawableToBitmap(Drawable drawable){
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        // 取 drawable 的颜色格式,Bitmap.createBitmap 第三个参数
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        //将drawable内容画到画布中
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Drawable bitmapToDrawable(Context context,Bitmap bitmap){
        BitmapDrawable bd= new BitmapDrawable(context.getResources(), bitmap);
        return bd;
    }
}
