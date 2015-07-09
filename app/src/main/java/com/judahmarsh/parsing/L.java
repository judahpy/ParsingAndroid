package com.judahmarsh.parsing;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by judah_000 on 09/07/2015.
 * Used to show parsing in log cat
 * as this is just a xml test using DOM
 * not using device
 */
public class L {

    public static void m(String msg){
        Log.d("Judah", msg);
    }

    public static void s(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
