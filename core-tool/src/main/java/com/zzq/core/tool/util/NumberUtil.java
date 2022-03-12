package com.zzq.core.tool.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public final class NumberUtil {

    private static DecimalFormat df = new DecimalFormat("#0.00");

    private NumberUtil() {
    }

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(symbols);
    }

    /**
     * 对计算出的浮点型数字，保留两位小数
     *
     * @param data 计算结果，浮点型
     * @return 保留两位小数后的小数
     */
    public static float formatValue(float data) {
        return Float.parseFloat(df.format(data));
    }
}
