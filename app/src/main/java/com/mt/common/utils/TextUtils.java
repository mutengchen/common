package com.mt.common.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//字符串工具类
public class TextUtils {

    //字符串过滤器，可以添加对各种特别的字串的匹配，来过滤对应的字符串
    public static void setEtFilter(EditText et){
        if(et== null) return;

        //表情过滤器
        InputFilter emojiFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern emoji = Pattern.compile(
                        "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };

        //特殊符号过滤器
        InputFilter specialCharFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regexStr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(regexStr);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.matches()) {
                    return "";
                } else {
                    return null;
                }

            }
        };
        et.setFilters(new InputFilter[]{emojiFilter,specialCharFilter});
    }


}
