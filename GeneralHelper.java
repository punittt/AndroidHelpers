package com.finoit.networkingstudy.Helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by EMP272 on 8/4/2016.
 */
public class GeneralHelper {

    public static String GENERAL_STRING_DATA = "stringData";
    public static void beginActivity(Context context, Class activity){
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    public static void beginActivity(Context context, Class activity, String data){
        Intent intent = new Intent(context, activity);
        intent.putExtra(GENERAL_STRING_DATA, data);
        context.startActivity(intent);
    }

    public static void beginActivity(Context context, Class activity, Boolean clearBackstack){
        Intent intent = new Intent(context, activity);
        if (clearBackstack){
            context.startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }


    public static void beginActivity(Context context, Class activity, String data,
                                     Boolean clearBackstack){
        Intent intent = new Intent(context, activity);
        intent.putExtra(GENERAL_STRING_DATA, data);
        if (clearBackstack){
            context.startActivity(intent);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        else {
            context.startActivity(intent);
        }
    }
}
