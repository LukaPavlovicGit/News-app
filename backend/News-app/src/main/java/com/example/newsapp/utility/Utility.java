package com.example.newsapp.utility;

import java.util.Arrays;

public class Utility {

    public static boolean notNullAndEmpty(String string){
        return string != null && !string.isEmpty();
    }
    public static String[] getKeywords(String tags){
        return Arrays.stream(tags.split("[,#.|;:-]")).map(String::trim).toArray(String[]::new);
    }
}
