package com.mt.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mt.common.common.BaseApplication;

import org.androidannotations.api.sharedpreferences.SharedPreferencesCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by calgon on 2017/10/25.
 */

public class SharePreferencesUtils {
    /**
     * 保存在手机里面的名字
     */
    public static final String FILE_NAME = "mma_data";
    //记录app内常用的数据
    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharePreferencesUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    /***
     * 单例获取sp，暂时不用dagger2
     * @return
     */
    public static SharePreferencesUtils getInstance() {
        return new SharePreferencesUtils(BaseApplication.getContext());
    }

    /**
     * 保存数据的方法，拿到数据保存数据的基本类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           键的值
     * @param defaultObject 默认值
     * @return
     */

    public Object get(String key, Object defaultObject) {
        //TODO 获取的时候先要进行一个手机唯一标识的检验，防止用户通过复制文件，来进行违规的操作
//        if(sharedPreferences.getString("phone_uuid","error").equals("error")||sharedPreferences.getString("phone_uuid","error").equals(getUUId())){
//            return null;
//        }
        if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultObject);
        } else {
            return sharedPreferences.getString(key, null);
        }

    }

    //不需要用户授予权限，可以获取跟IMEI类似的uuid
    public static String getUUId(){
        String uuid = "35";
        uuid = uuid+ Build.BOARD.length()%10 +
                Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 +
                Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 +
                Build.HOST.length()%10 +
                Build.ID.length()%10 +
                Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 +
                Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 +
                Build.TYPE.length()%10 +
                Build.USER.length()%10 +
                Build.SERIAL
        ;
        return uuid;
    }
    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有的数据
     */
    public void clear() {
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }
    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    //获取list数据
    public <T> List<T> getListData(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();
        String json = sharedPreferences.getString(key, "");
        if (!json.equals("") && json.length() > 0) {
            Gson gson = new Gson();
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
        }
        return list;
    }

    public <T> void putDto(String key, T data){
        Gson gson = new Gson();
        String temp  =gson.toJson(data);
        editor.putString(key,temp);
        SharedPreferencesCompat.apply(editor);
    }

    public <T> T getDto(String key, Class<T> cls){
        String temp = sharedPreferences.getString(key,"");
        T s = GsonUtils.jsonToObject(temp,cls);
        return s;
    }

    //保存list数据
    public <T> boolean putListData(String key, List<T> list) {
        boolean result;
        String type;
        if(list.size()==0||list==null){
            type = "other";
        }else{
            type = list.get(0).getClass().getSimpleName();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JsonArray array = new JsonArray();
        try {
            switch (type) {
                case "Boolean":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Boolean) list.get(i));
                    }
                    break;
                case "Long":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Long) list.get(i));
                    }
                    break;
                case "Float":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Float) list.get(i));
                    }
                    break;
                case "String":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((String) list.get(i));
                    }
                    break;
                case "Integer":
                    for (int i = 0; i < list.size(); i++) {
                        array.add((Integer) list.get(i));
                    }
                    break;
                default:
                    Gson gson = new Gson();
                    for (int i = 0; i < list.size(); i++) {
                        JsonElement obj = gson.toJsonTree(list.get(i));
                        array.add(obj);
                    }
                    break;
            }
            editor.putString(key, array.toString());
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

}
