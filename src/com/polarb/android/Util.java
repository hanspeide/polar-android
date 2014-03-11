package com.polarb.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.view.Display;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Util {
    public static Point getScreenSize(Activity activity) {
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            Display display = activity.getWindowManager().getDefaultDisplay();
            display.getSize(size);
        } else{
            size.x = activity.getWindowManager().getDefaultDisplay().getWidth();
            size.y = activity.getWindowManager().getDefaultDisplay().getHeight();
        }

        return size;
    }

    public static Pair<Integer, Integer> getPollImageSize(Activity activity){
        int imageWidth = Util.getScreenSize(activity).x * 90 / 100;
        int imageHeight = imageWidth * 67 / 100;

        return new Pair<Integer, Integer>(imageWidth, imageHeight);
    }

    public static String getStringFromInputStream(InputStream inputStream) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;

        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }

        return outputStream.toString();
    }
}
