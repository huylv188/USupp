package uetsupport.dtui.uet.edu.uetsupport.models;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by huylv on 08/12/2015.
 */
public class Course {
    int stt;
    String maMonHoc;
    String tenMonHoc;
    int soTinChi;
    String trangThai;
    long hocPhi;
    String lopMonHoc;
    String thu;
    String tiet;
    String giangDuong;
    ArrayList<BuoiHoc> cacBuoiHoc;

    boolean alarmOn=true;

    public Course(){};

    public Course(int stt, String maMonHoc, String tenMonHoc, int soTinChi, String trangThai, long hocPhi, String lopMonHoc, String thu, String tiet, String giangDuong) {
        this.stt = stt;
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.soTinChi = soTinChi;
        this.trangThai = trangThai;
        this.hocPhi = hocPhi;
        this.lopMonHoc = lopMonHoc;
        this.thu = thu;
        this.tiet = tiet;
        this.giangDuong = giangDuong;
    }

    public boolean isAlarmOn() {
        return alarmOn;
    }

    public void setAlarmOn(boolean alarmOn) {
        this.alarmOn = alarmOn;
    }

    public ArrayList<BuoiHoc> getCacBuoiHoc() {
        return cacBuoiHoc;
    }

    public void setCacBuoiHoc(ArrayList<BuoiHoc> cacBuoiHoc) {
        this.cacBuoiHoc = cacBuoiHoc;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public long getHocPhi() {
        return hocPhi;
    }

    public void setHocPhi(long hocPhi) {
        this.hocPhi = hocPhi;
    }

    public String getLopMonHoc() {
        return lopMonHoc;
    }

    public void setLopMonHoc(String lopMonHoc) {
        this.lopMonHoc = lopMonHoc;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getTiet() {
        return tiet;
    }

    public void setTiet(String tiet) {
        this.tiet = tiet;
    }

    public String getGiangDuong() {
        return giangDuong;
    }

    public void setGiangDuong(String giangDuong) {
        this.giangDuong = giangDuong;
    }


    public void chuanHoa(){

        cacBuoiHoc = new ArrayList<>();
        for(int i=0;i<thu.length();i++){
            if(Character.isDigit(thu.charAt(i))){
                BuoiHoc bh = new BuoiHoc();
                bh.setThu(thu.charAt(i) - 48);
//                Log.e("cxz","cxz"+thu.charAt(i)+" "+Integer.valueOf(thu.charAt(i)));
                int tietDau = tiet.charAt(cacBuoiHoc.size() * 6) -48;
                int soTiet = tiet.charAt(cacBuoiHoc.size() * 6+4) -48 - tietDau;
                bh.setTietDau(tietDau);
                bh.setSoTiet(soTiet);
//                Log.e("cxz", bh.toString());
                cacBuoiHoc.add(bh);
            }
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "stt=" + stt +
                ", maMonHoc='" + maMonHoc + '\'' +
                ", tenMonHoc='" + tenMonHoc + '\'' +
                ", soTinChi=" + soTinChi +
                ", trangThai='" + trangThai + '\'' +
                ", hocPhi=" + hocPhi +
                ", lopMonHoc='" + lopMonHoc + '\'' +
                ", thu='" + thu + '\'' +
                ", tiet='" + tiet + '\'' +
                ", giangDuong='" + giangDuong + '\'' +
                ", cacBuoiHoc=" + cacBuoiHoc +
                '}';
    }
}
