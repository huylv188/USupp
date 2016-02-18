package com.unbelievable.uetsupport.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.unbelievable.uetsupport.R;

/**
 * Created by DucAnhZ on 20/11/2015.
 */
public class UETSupportUtils {
    public static void showDialogServerProblem(Activity activity) {
        try {
            CommonUtils.showOkDialog(activity,
                    activity.getString(R.string.dialog_title_error),
                    activity.getString(R.string.dialog_content_server_problem),
                    null);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public static void showDialogNetworkProblem(Activity activity) {
        CommonUtils
                .showOkDialog(
                        activity,
                        activity.getString(R.string.dialog_title_common),
                        activity.getString(R.string.dialog_title_content_network_problem),
                        null);
    }

    public static boolean networkConnected(Activity activity) {
        if (CommonUtils.checkNetwork(activity)) {
            return true;
        } else {
            // Show msg
            showDialogNetworkProblem(activity);
            return false;
        }
    }

    public static void hideKeyboard(Activity activity, View view) {
        final InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static File persistImage(Context context, Bitmap bitmap, String name) {
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
        }
        return imageFile;
    }

    public static Bitmap resizeBitmap(Uri uri, Context context) {
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();
            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Bitmap b = null;
            in = context.getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);
                int height = b.getHeight();
                int width = b.getWidth();
                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;
                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();
            return b;
        } catch (IOException e) {
            return null;
        }
    }

    public static int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */

        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }




}
