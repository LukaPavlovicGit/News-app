package com.example.newsapp.utility;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Utility {

    public static boolean notNullAndEmpty(String string){
        return string != null && !string.isEmpty();
    }
    private static String[] getKeywords(String tags){
        return Arrays.stream(tags.split("[,#.|;:-]")).map(String::trim).toArray(String[]::new);
    }
    private static String prettyTagsString(String[] arr){
        StringBuilder sb = new StringBuilder();
        for(int i=0 ; i < arr.length ; i++){
            sb.append(arr[i]);
            if(i != arr.length - 1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static String prettyTagsString(String tags){
        String[] arr = Utility.getKeywords(tags);
        StringBuilder sb = new StringBuilder();
        // every tag has whitespace before and after
        for(int i=0 ; i < arr.length ; i++){
            sb.append(arr[i]);
            if(i != arr.length - 1){
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static String convertToPrettyTagsString(String tags){
        return prettyTagsString(removeDuplicates(getKeywords(tags)));
    }
    public static String[] convertToPrettyTagsArr(String tags){
        return removeDuplicates(getKeywords(tags));
    }

    public static String[] removeDuplicates(String[] tags){
        Set<String> set = new HashSet<>(Arrays.asList(tags));
        return set.toArray(new String[0]);
    }
}
