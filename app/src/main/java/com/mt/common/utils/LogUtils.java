package com.mt.common.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Created by cmt on 2019/7/5
 */
public class LogUtils {

    //记录日志到手机根目录指定文件
    public static void setLog(String log){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "MRA_ERROR_LOG/MRA_API_LOG"+ File.separator + df.format(date) + "_api_log.txt");
        File pfile = file.getParentFile();
        if(!pfile.exists()) {
            pfile.mkdirs();
        }
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            PrintStream fos = new PrintStream(new FileOutputStream(file, true));
            fos.println();
            fos.println(df2.format(date)+log);
            fos.flush();
            fos.close();
        } catch (IOException ex) {
        }
    }

}
