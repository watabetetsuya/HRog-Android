package jp.co.goalist.hrog.hrog;

import android.view.Menu;

/**
 * Created by RicoShiota on 2016/05/25.
 */
public enum MenuTabButtonEnum {
    TOP,SPECIAL,REPORT,IR,RELEASE;

    public static int indexOfEnum(MenuTabButtonEnum target) {
        MenuTabButtonEnum[] values = values();
        for (int i=0; i < values.length; i++) {
            MenuTabButtonEnum value = values[i];
            if(value.equals(target)){
                return i;
            }
        }
        return 0;
    }

    public static MenuTabButtonEnum enumOfIndex(int num) {
        MenuTabButtonEnum[] values = values();
        if(num < 0 || num >= values.length){
            return null;
        }
        MenuTabButtonEnum en = values[num];
        return en;
    }
}

