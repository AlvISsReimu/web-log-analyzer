package com.alviss.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharPosUtil {

    public static int getCharPos(String s, String target, int num) {
        Matcher matcher = Pattern.compile(target).matcher(s);
        int index = 0;
        while (matcher.find()) {
            index++;
            if (index == num)
                break;
        }
        return matcher.start();
    }

}
