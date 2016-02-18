package com.unbelievable.uetsupport.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Layout;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View.MeasureSpec;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DucAnhZ on 20/11/2015.
 */

public class CommonUtils {
    private static boolean enableDebug = true;

    public static int[] ScreenSize(Context context) {
        int[] size = new int[2];
        DisplayMetrics displaymetrics = context.getResources()
                .getDisplayMetrics();
        size[0] = displaymetrics.widthPixels;
        size[1] = displaymetrics.heightPixels;

        return size;
    }

    public static boolean checkNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static String readStringJsonFromAssetFile(String path,
                                                     Context context) {
        InputStream fis;
        try {
            fis = context.getAssets().open(path);
            StringBuffer fileContent = new StringBuffer("");
            byte[] buffer = new byte[1024];
            int n;
            while ((n = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, n));
            }
            return fileContent.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String timeStringFromLong(long totalSeconds) {
        int seconds = (int) totalSeconds % 60;
        int minutes = (int) (totalSeconds / 60) % 60;
        int hours = (int) totalSeconds / 3600;
        int days = (int) totalSeconds / (24 * 3600);

        StringBuilder builder = new StringBuilder();
        if (days > 0) {
            if (days == 1) {
                builder.append(hours).append(" day");
            } else {
                builder.append(hours).append(" days");
            }

            return builder.toString();
        }

        if (hours > 0) {
            if (hours == 1) {
                builder.append(hours).append(" hour");
            } else {
                builder.append(hours).append(" hours");
            }

        }

        if (minutes > 0) {
            if (minutes == 1) {
                builder.append(minutes).append(" minute");
            } else {
                builder.append(minutes).append(" minutes");
            }
            return builder.toString();
        }

        if (seconds > 0) {
            builder.append(seconds).append(" seconds");
        }

        return builder.toString();
    }

    public static void toggleGPS(Context context, boolean enable) {
        String provider = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps") == enable) {
            return; // the GPS is already in the requested state
        }
        final Intent poke = new Intent();
        poke.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
        poke.setData(Uri.parse("3"));
        context.sendBroadcast(poke);
    }

    public static String formattedDateTime(long time, String formatString) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        Date date = new Date(time);
        return sdf.format(date);
    }

    public static boolean stringIsValid(String str) {
        if (str != null && !str.trim().equals("")
                && !str.toLowerCase().equals("null")) {
            return true;
        }
        return false;
    }

    public static String getStringIgnoreNullString(String str) {
        if (str != null && str.equals("null")) {
            return "";
        }
        return str;
    }

    public static String getValidString(String str) {
        try {
            if (str == null || str.equals("null")) {
                return "";
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return str;
    }

    public static void copyDirectory(File sourceLocation, File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]), new File(
                        targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    public static String getCurrentTimeWithFormat(String formatString) {
        long currentTime = System.currentTimeMillis();
        return formattedDateTime(currentTime, formatString);
    }

    public static Date getDateFromString(String dateString, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        return null;
    }

    public static String getFormattedTime(long millis, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(millis));
    }

    public static String getDurationTime(long startTimeInMillis) {
        long currentMillisTime = System.currentTimeMillis() - startTimeInMillis;
        long totalSeconds = currentMillisTime / 1000;
        int seconds = (int) totalSeconds % 60;
        int minutes = (int) (totalSeconds / 60) % 60;
        int hours = (int) totalSeconds / 3600;
        int days = (int) totalSeconds / (24 * 3600);

        StringBuilder builder = new StringBuilder();
        String minutesSeconds = String.format("%02d:%02d", minutes, seconds);
        if (hours > 0) {
            String hoursStr = String.format("%02d", hours);
            builder.append(hoursStr).append(":");
        }

        if (days > 0) {
            String daysStr = String.format("%02d", days);
            builder.append(daysStr).append(":");
        }

        builder.append(minutesSeconds);

        return builder.toString();
    }

    public static boolean checkTexViewEllipsized(TextView tv) {
        Layout l = tv.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > 0) {
                if (l.getEllipsisCount(lines - 1) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static long getMillisTimeStr(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            long millis = sdf.parse(time).getTime();
            return millis;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int getListViewHeight(ListView list) {
        ListAdapter adapter = list.getAdapter();

        int listviewHeight = 0;

        list.measure(MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED,
                MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED));

        listviewHeight = list.getMeasuredHeight() * adapter.getCount()
                + (adapter.getCount() * list.getDividerHeight());

        return listviewHeight;
    }

    public static String getBase64StringFromFile(String path) {
        File f = new File(path);
        if (f.exists()) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                FileInputStream fis = new FileInputStream(f);

                byte[] buf = new byte[1024];
                int n;
                while (-1 != (n = fis.read(buf))) {
                    baos.write(buf, 0, n);
                }

                byte[] videoBytes = baos.toByteArray();
                String stringBase64 = Base64.encodeToString(videoBytes,
                        Base64.DEFAULT);
                fis.close();
                baos.close();
                return stringBase64;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void printLogE(Class<?> c, String log) {
        Log.e(c.getSimpleName(), log + "");
    }

    public static void printLogD(Class<?> c, String log) {
        Log.d(c.getSimpleName(), log + "");
    }

    public static void printLogI(Class<?> c, String log) {
        Log.i(c.getSimpleName(), log + "");
    }

    public static void printLogW(Class<?> c, String log) {
        Log.w(c.getSimpleName(), log + "");
    }

    public static void printLogRequest(Class<?> c, String url, String data) {
        if (enableDebug) {
            Log.e(c.getSimpleName(), "url/data: " + url + "/" + data);
        }
    }

    public static File getTempFileFromBitmap(Context context, Bitmap bm) {
        File f = new File(Environment.getExternalStorageDirectory(),
                "tms-temp.png");
        if (bm != null) {
            try {
                FileOutputStream out = new FileOutputStream(f);
                bm.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static Dialog showOkDialog(Context context, String title,
                                      String message, OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", onClickListener);
        builder.show();
        return builder.create();
    }

    public static Dialog showOkCancelDialog(Context context,
                                            String message,String namePositiveButton,String nameNegativeButton, OnClickListener okClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(namePositiveButton, okClickListener);
        builder.setNegativeButton(nameNegativeButton, okClickListener);
        builder.setCancelable(false);
        builder.show();
        return builder.create();
    }

    // Date time
    public static Date getNextDate(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK, days);
        return c.getTime();
    }

    /**
     * @param day - two digits: 01,28
     * @return
     */
    public static String getDayWithSuffix(String day) {
        int secondDigit = Integer.valueOf(day.subSequence(1, 2).toString());
        if (day.charAt(0) == '0') {
            day = day.replace("0", "");
        }

        StringBuilder builder = new StringBuilder();
        switch (secondDigit) {
            case 1:// Day 01, 11, 21, 31
                builder.append(day).append("st");
                break;
            case 2: // Day 22nd
                builder.append(day).append("nd");
                break;
            case 3: // Day 23rd
                builder.append(day).append("rd");
                break;
            default:
                builder.append(day).append("th");
                break;
        }
        return builder.toString();
    }

    public static void sendEmail(Context context, String email, String subject,
                                 String content) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});

        if (stringIsValid(subject)) {
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
        }

        if (stringIsValid(content)) {
            i.putExtra(Intent.EXTRA_TEXT, content);
        }

        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void openUrl(Context context, String url) {
        if (stringIsValid(url)) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            context.startActivity(browserIntent);
        }
    }

    public static String getPhoneNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = tMgr.getLine1Number();
        if (!stringIsValid(phoneNumber)) {
            phoneNumber = tMgr.getSubscriberId();
        }
        Log.e("Phone", phoneNumber + "");
        return phoneNumber;
    }

    public static String getDeviceID(Context context) {
        TelephonyManager mngr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = mngr.getDeviceId();
        return deviceID;
    }

    public static String getUsername(Context context) {
        AccountManager manager = AccountManager.get(context);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");
            if (parts.length > 0 && parts[0] != null)
                return parts[0];
            else
                return null;
        } else
            return null;
    }

    public static String getFileNameFromUrl(String url) {
        int index = url.lastIndexOf('?');
        String filename;
        int length = url.length();
        String extension = url.substring(length - 4, length);
        if (index > 1) {
            filename = url.substring(url.lastIndexOf('/') + 1, index);
        } else {
            filename = url.substring(url.lastIndexOf('/') + 1);
        }

        if (filename == null || "".equals(filename.trim())) {
            filename = UUID.randomUUID() + extension;
        }
        return filename;
    }

    public static boolean checkEmailValid(String email) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    public static String getFormattedTime(String timeString,
                                          String fromPattern, String toPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromPattern);
        try {
            Date date = sdf.parse(timeString);
            sdf = new SimpleDateFormat(toPattern);
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        return null;
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static byte[] convertFileToByteArray(File f) {
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 8];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
            inputStream.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    public static void unzip(File zipFile, File destDir) {
        try {
            FileInputStream fin = new FileInputStream(zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.v("Decompress", "Unzipping " + ze.getName());

                if (ze.isDirectory()) {
                    File f = new File(destDir, ze.getName());
                    if (!f.isDirectory()) {
                        f.mkdirs();
                    }
                } else {
                    FileOutputStream fout = new FileOutputStream(
                            destDir.getAbsolutePath() + "/" + ze.getName());
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }

                    zin.closeEntry();
                    fout.close();
                }

            }
            zin.close();
        } catch (Exception e) {
            Log.e("Decompress", "unzip", e);
        }
    }

    public static String convertStringToSHA256(String text) {
        byte[] data = null;
        MessageDigest disgest = null;
        try {
            disgest = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
        }
        disgest.reset();
        data = disgest.digest(text.getBytes());
        return String.format("%0" + (data.length * 2) + 'x', new BigInteger(1,
                data));
    }
}