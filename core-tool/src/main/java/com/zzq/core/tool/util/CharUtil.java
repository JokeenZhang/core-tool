package com.zzq.core.tool.util;

import androidx.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CharUtil {
    private CharUtil() {

    }

    /**
     * 普通字符串转换为HTML格式编码
     *
     * @param value 普通字符串
     * @return 返回转换后的编码
     */
    public static String string2Html(String value) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(charsEscape(value), "UTF-8");
        return encode;
    }

    /**
     * 特殊字符转义
     *
     * @param value 需要转义的字符串
     * @return 返回转义后的字符串
     */
    public static String charsEscape(@NonNull String value) {
        StringBuilder sb = new StringBuilder();
        //查询字符串一般不会太长，挨个遍历也花费不了多少时间
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            // 这些字符是查询语法的一部分，必须转义
//            if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')'
//                    || c == ':' || c == '^' || c == '[' || c == ']' || c == '\"'
//                    || c == '{' || c == '}' || c == '~' || c == '*' || c == '?'
//                    || c == '|' || c == '&' || c == ';' || c == '/' || c == '.'
//                    || c == '$' || Character.isWhitespace(c)) {
//                sb.append('\\');
//            }

            if (c == '\\' || c == '~' || c == '`' || c == '!' || c == '@' || c == '#' || c == '$' || c == '%' || c == '^'
                    || c == '&' || c == '*' || c == '(' || c == ')' || c == '^' || c == '_' || c == '-' || c == '+'
                    || c == '=' || c == '{' || c == '}' || c == '[' || c == ']' || c == '"' || c == '\'' || c == '|'
                    /*|| c == '\||'*/ || c == '?' || c == '<' || c == '>' || c == '.' || c == '/'


                    || Character.isWhitespace(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
