package uetsupport.dtui.uet.edu.uetsupport.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import uetsupport.dtui.uet.edu.uetsupport.MainActivity;

/**
 * Created by huylv on 09/12/2015.
 */
public class Util {
    public static final int UPDATE_DONE = 1;
    public static String SPSETTING = "spSetting";
    public static String SPSETTING_NOTI = "spSettingNoti";
    public static int UPDATEINTERVAL = 3600000;
    public static String SPFIRSTLINKKEY = "firstDateCreatedKey";
    public static String SPFIRSTLINK = "firstDateCreated";
    public static int CURRENT_PAGE_ANNOUNCEMENT = 1;
    public static int CURRENT_PAGE_NEWS = 1;
    public static boolean UPDATE_SLIDER = false;
    public static boolean LOGGED_IN = false;
    public static String urlNews = "http://www2.uet.vnu.edu.vn/coltech/taxonomy/term/101";
    public static String urlAnnouncement = "http://www2.uet.vnu.edu.vn/coltech/taxonomy/term/53";
    public static String domain = "http://www2.uet.vnu.edu.vn";

    public static int LOAD_OK = 1;
    public static int LOAD_ERROR = -1;

    public static String readTextFromAsset(Context context,String fname){
        StringBuffer sb=new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(fname)));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                sb.append(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    return sb.toString();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return null;
    }
//    public static int getWeekDay(String d){
//        if(d.equals("CN")) return 8;
//        if
//    }
}
