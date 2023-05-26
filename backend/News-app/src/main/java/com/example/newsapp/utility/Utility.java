package com.example.newsapp.utility;

import java.util.Arrays;

public class Utility {

    public static boolean notNullAndEmpty(String string){
        return string != null && !string.isEmpty();
    }
    public static String[] getKeywords(String tags){
        return Arrays.stream(tags.split("[,#.|;:-]")).map(String::trim).toArray(String[]::new);
    }
    public static String prettyTags(String tags){
        String[] arr = Utility.getKeywords(tags);
        StringBuilder sb = new StringBuilder();
        // every tag has whitespace before and after
        for(int i=0 ; i < arr.length ; i++){
            sb.append(" " + arr[i] + " ");
            if(i != arr.length - 1){
                sb.append(",");
            }
        }
        System.out.println("PRETTY TAGS: " + sb.toString());
        return sb.toString();
    }
}
