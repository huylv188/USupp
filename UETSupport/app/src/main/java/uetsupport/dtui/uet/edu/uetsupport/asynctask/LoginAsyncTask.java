package uetsupport.dtui.uet.edu.uetsupport.asynctask;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import uetsupport.dtui.uet.edu.uetsupport.ExamScheduleActivity;
import uetsupport.dtui.uet.edu.uetsupport.LoginActivity;
import uetsupport.dtui.uet.edu.uetsupport.dialog.StaticDialog;
import uetsupport.dtui.uet.edu.uetsupport.models.Course;
import uetsupport.dtui.uet.edu.uetsupport.models.ExamSchedule;
import uetsupport.dtui.uet.edu.uetsupport.models.Examination;
import uetsupport.dtui.uet.edu.uetsupport.models.Schedule;
import uetsupport.dtui.uet.edu.uetsupport.models.User;
import uetsupport.dtui.uet.edu.uetsupport.session.Session;
import uetsupport.dtui.uet.edu.uetsupport.util.Util;

/**
 * Created by huylv on 08/12/2015.
 */
public class LoginAsyncTask extends AsyncTask<Void, Void, Integer> {

    ProgressDialog pd;
    String studentid, password;
    Context context;
    private boolean LOGGING_IN = true;

    private List<String> cookies;
    private HttpURLConnection conn;

    private final String USER_AGENT = "Mozilla/5.0";

    private String cssName = "#content-wrapper > div > div.print-area > table:nth-child(4) > tbody > tr:nth-child(1) > td:nth-child(2) > b";
    private String cssDoB = "#content-wrapper > div > div.print-area > table:nth-child(4) > tbody > tr:nth-child(1) > td:nth-child(4) > b";
    private String cssMSSV = "#content-wrapper > div > div.print-area > table:nth-child(4) > tbody > tr:nth-child(1) > td:nth-child(6) > b";
    private String cssCTDT = "#content-wrapper > div > div.print-area > table:nth-child(4) > tbody > tr:nth-child(2) > td:nth-child(2) > b";
    private String cssKhoa = "#content-wrapper > div > div.print-area > table:nth-child(4) > tbody > tr:nth-child(2) > td:nth-child(4) > b";


    String urlLogin = "http://dangkyhoc.daotao.vnu.edu.vn/dang-nhap";
    String hostLogin ="dangkyhoc.daotao.vnu.edu.vn";
    String refererLogin = "http://dangkyhoc.daotao.vnu.edu.vn/dang-nhap";

    String urlXemtkb = "http://dangkyhoc.daotao.vnu.edu.vn/xem-va-in-ket-qua-dang-ky-hoc/1?layout=main";

    String urlExam = "http://203.113.130.218:50223/congdaotao/module/dsthi_new/";
    String hostExam = "203.113.130.218:50223";
    String refererExam = "http://203.113.130.218:50223/congdaotao/module/dsthi_new/";

    ArrayList<Examination> examinationArrayList;
    public LoginAsyncTask(Context c, String studentid, String password) {
        context = c;
        this.studentid= studentid;
        this.password = password;
        LOGGING_IN=true;
    }

    public LoginAsyncTask(Context c,String studentId){
        context=c;
        this.studentid=studentId;
        LOGGING_IN=false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(LOGGING_IN){
            pd = new ProgressDialog(context);
            pd.setMessage("Logging in...");
            pd.setIndeterminate(true);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }else{

        }

    }

    @Override
    protected Integer doInBackground(Void... params) {

        CookieHandler.setDefault(new CookieManager());

        if(LOGGING_IN) {
            Log.e("cxz","cxz logging in");
            String page = null;
            try {
//                page = GetPageContent(urlLogin);
//                String postParams = getFormParams(page, "12020171", "operationcwal");
//                sendPost(urlLogin, hostLogin,refererLogin , postParams);
//                Document doc = Jsoup.connect(urlXemtkb).get();
                //fake data
                Document doc = Jsoup.parse(Util.readTextFromAsset(context,"xemtkb.html"));

                if (doc.html().contains("chắc chắn muốn")) {
                    prepareUser(doc);
                    return Activity.RESULT_OK;
                }

//            } catch (NetworkErrorException e) {
//                return -2;
//            } catch (TimeoutException e) {
//                return -3;
            } catch (Exception e) {
                Log.e("cxz",e.getClass()+"-"+e.getMessage());
                return -4;
            }
        }else{
            Log.e("cxz","not login");
            String page = null;
            try {
                page = GetPageContent(urlExam);
                String postParams = getFormParams(page, "12020171");
                String html=sendPost(urlExam, hostExam,refererExam, postParams);
//                String html = Jsoup.parse(Util.readTextFromAsset(context,"dsthi.html")).html();
                updateExamSchedule(html);
                Log.e("cxz","load exam done");
                return Util.UPDATE_DONE;
            } catch (NetworkErrorException e) {
                Log.e("cxz","error:"+e.getClass());
                return -2;
            } catch (TimeoutException e) {
                Log.e("cxz","error:"+e.getClass());
                return -3;
            } catch (Exception e) {
                Log.e("cxz","error:"+e.getClass());
                return -4;
            }
        }
        return 0;
    }

    private void updateExamSchedule(String html) {
        Document doc = Jsoup.parse(html);
        User u = Session.getCurrentUser();
        int numOfCourse = doc.select("#dsthi-grid > table > tbody").first().children().size();

        Log.e("cxz", "u=" + u.getFullName() + " " + numOfCourse);
        String constCSS = "#dsthi-grid > table > tbody > tr:nth-child(";
        examinationArrayList = new ArrayList<>();

        for(int i=1;i<numOfCourse+1;i++){
            String lopMonHoc = doc.select(constCSS+i+") > td:nth-child(7)").text();
            String tenMonHoc = doc.select(constCSS+i+") > td:nth-child(8)").text();
            int soBaoDanh = Integer.valueOf(doc.select(constCSS + i + ") > td:nth-child(6)").text());
            String ngayThi = doc.select(constCSS+i+") > td:nth-child(9)").text();
            String gioThi = doc.select(constCSS + i + ") > td:nth-child(10)").text();
            String phongThi = doc.select(constCSS + i + ") > td:nth-child(12)").text();
            String hinhThucThi = doc.select(constCSS + i + ") > td:nth-child(14)").text();

//            Log.e("cxz",ngayThi);
            Examination e = new Examination(lopMonHoc,tenMonHoc,soBaoDanh,ngayThi,gioThi,phongThi,hinhThucThi);
            examinationArrayList.add(e);
//            Log.e("cxz", tenMonHoc);
        }
        ExamSchedule es = new ExamSchedule(examinationArrayList);
        u.setExamSchedule(es);
    }

    private Schedule getSchedule(Document doc) {
        int SOHANG = 0;
        String csshangxcot1 = "#content-wrapper > div > div.print-area > table:nth-child(6) > tbody > tr:nth-child("+(SOHANG+2)+") > td:nth-child(1)";
        Element elehangxcot1 = doc.select(csshangxcot1).first();
        String text  = elehangxcot1.text();
        do{
//            System.out.println(text+"cxz");
            SOHANG+=1;
            csshangxcot1 = "#content-wrapper > div > div.print-area > table:nth-child(6) > tbody > tr:nth-child("+(SOHANG+2)+") > td:nth-child(1)";
            elehangxcot1=doc.select(csshangxcot1).first();
            if(elehangxcot1 !=null){
                text = elehangxcot1.text();
            }
        }while(elehangxcot1!=null);

        SOHANG -=1;
        ArrayList<Course> courseArrayList= new ArrayList<>();
        for(int i=0;i<SOHANG;i++){
            Course c = new Course();
            for(int j=0;j<10;j++){
                String csshangxcoty = "#content-wrapper > div > div.print-area > table:nth-child(6) > tbody > tr:nth-child("+(i+2)+") > td:nth-child("+(j+1)+")";
                switch (j){
                    case 0:
                        c.setStt(Integer.valueOf(doc.select(csshangxcoty).first().text()));
                        break;
                    case 1:
                        c.setMaMonHoc(doc.select(csshangxcoty).first().text());
                        break;
                    case 2:
                        c.setTenMonHoc(doc.select(csshangxcoty).first().text());
                        break;
                    case 3:
                        c.setSoTinChi(Integer.valueOf(doc.select(csshangxcoty).first().text()));
                        break;
                    case 4:
                        c.setTrangThai(doc.select(csshangxcoty).first().text());
                        break;
                    case 5:
                        c.setHocPhi(c.getSoTinChi()*202000);
                        break;
                    case 6:
                        c.setLopMonHoc(doc.select(csshangxcoty).first().text());
                        break;
                    case 7:
                        c.setThu(doc.select(csshangxcoty).first().text());
                        break;
                    case 8:
                        c.setTiet(doc.select(csshangxcoty).first().text());
                        break;
                    case 9:
                        c.setGiangDuong(doc.select(csshangxcoty).first().text());
                        break;
                    default:
                        break;
                }
            }
            c.chuanHoa();
//            Log.e("cxz",c.toString());
            courseArrayList.add(c);
        }
        Schedule s = new Schedule(courseArrayList);
        return s;
    }

    private void prepareUser(Document doc) {
        Element svName= doc.select(cssName).first();
        Element svDob = doc.select(cssDoB).first();
        Element svMSSV = doc.select(cssMSSV).first();
        Element svCTDT = doc.select(cssCTDT).first();
        Element svKhoa = doc.select(cssKhoa).first();

        String fullName = svName.text();
        String DoB = svDob.text();
        long studentId = Long.valueOf(svMSSV.text());
        String faculty = svCTDT.text();
        String khoa = svKhoa.text();

        User u = new User(password,fullName, DoB, studentId, faculty, khoa);

        u.setSchedule(getSchedule(doc));

        saveToSP(u);
        Session.setUser(u);
    }

    private void saveToSP(User u) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String value = gson.toJson(u);
//        Log.e("cxz",value);
        Util.LOGGED_IN=true;
        editor.putString("User", value);
        editor.commit();
    }

    private String sendPost(String url,String host,String referer, String postParams) throws Exception {

        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();
        // Acts like a browser
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Host", host);
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "vi,en-US;q=0.8,en;q=0.6");
        for (String cookie : this.cookies) {
            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
        }
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Referer", referer);
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length",
                Integer.toString(postParams.length()));
        conn.setConnectTimeout(20000);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        // Send post request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();

    }

    private String GetPageContent(String url) throws Exception {
        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "vi,en-US;q=0.8,en;q=0.6");
        if (cookies != null) {
            for (String cookie : this.cookies) {
                conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
            }
        }
//        int responseCode = conn.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        setCookies(conn.getHeaderFields().get("Set-Cookie"));
        return response.toString();
    }

    public String getFormParams(String html, String username, String password)
            throws UnsupportedEncodingException {

//        System.out.println("Extracting form's data...");

        Document doc = Jsoup.parse(html);

        // Google form id
        // Element loginform = doc.getElementById("gaia_loginform");
        Elements inputElements = doc.getElementsByTag("input");
        List<String> paramList = new ArrayList<String>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("LoginName"))
                value = username;
            else if (key.equals("Password"))
                value = password;
            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }

        // build parameters list
        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }
        return result.toString();
    }

    public String getFormParams(String html, String studentId)
            throws UnsupportedEncodingException {

//        System.out.println("Extracting form's data...");


        Document doc = Jsoup.parse(html);

        // Google form id
        // Element loginform = doc.getElementById("gaia_loginform");
        Elements inputElements = doc.getElementsByTag("input");
        List<String> paramList = new ArrayList<String>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("keysearch"))
                value = studentId;
            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }

        // build parameters list
        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }
        return result.toString();
    }

    public List<String> getCookies() {
        return cookies;
    }

    public void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

    @Override
    protected void onPostExecute(Integer s) {
        super.onPostExecute(s);
        if(LOGGING_IN) {
            pd.dismiss();
            switch (s) {
                case 0:
                    StaticDialog.showAlertDialog(context, "Wrong id or password!");
                    break;
                case Activity.RESULT_OK:
                    ((LoginActivity) context).setResult(s);
                    ((LoginActivity) context).finish();
                    break;
                case -2:
                    StaticDialog.showAlertDialog(context, "Network error!");
                    break;
                case -3:
                    StaticDialog.showAlertDialog(context, "Timeout error!");
                    break;
                case -4:
                    StaticDialog.showAlertDialog(context, "Exception error!");
                    break;
                default:
                    StaticDialog.showAlertDialog(context, "Unknown error! code = " + s);
                    break;

            }
        }else{
        }

    }

    private void updateView(ExamScheduleActivity examActivity) {
        examActivity.tvMaLopMonHoc[1].setText(examinationArrayList.get(0).getLopMonHoc());
    }

    private String GetPageContent1(String url) throws Exception {
        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "vi,en-US;q=0.8,en;q=0.6");
        if (cookies != null) {
            for (String cookie : this.cookies) {
                conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
            }
        }
        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        setCookies(conn.getHeaderFields().get("Set-Cookie"));
        return response.toString();
    }

    public String getFormParams1(String html, String username, String password)
            throws UnsupportedEncodingException {

        System.out.println("Extracting form's data...");

        Document doc = Jsoup.parse(html);
        Elements inputElements = doc.getElementsByTag("input");
        List<String> paramList = new ArrayList<String>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");
            if (key.equals("LoginName"))
                value = username;
            else if (key.equals("Password"))
                value = password;
            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }
        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }
        return result.toString();
    }

    private void sendPost1(String url, String postParams) throws Exception {

        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Host", "dangkyhoc.daotao.vnu.edu.vn");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "vi,en-US;q=0.8,en;q=0.6");
        for (String cookie : this.cookies) {
            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
        }
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Referer",
                "http://dangkyhoc.daotao.vnu.edu.vn/dang-nhap");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length",
                Integer.toString(postParams.length()));

        conn.setDoOutput(true);
        conn.setDoInput(true);

        // Send post request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + postParams);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
    }
}
