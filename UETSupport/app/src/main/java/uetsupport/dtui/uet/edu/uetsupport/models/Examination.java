package uetsupport.dtui.uet.edu.uetsupport.models;

/**
 * Created by huylv on 10/12/2015.
 */
public class Examination {
    private String lopMonHoc;
    private String tenMonHoc;
    private int soBaoDanh;
    private String ngayThi;
    private String gioThi;
    private String phongThi;
    private String hinhThucThi;

    boolean alarmOn=true;

    public boolean isAlarmOn() {
        return alarmOn;
    }

    public void setAlarmOn(boolean alarmOn) {
        this.alarmOn = alarmOn;
    }

    public Examination(String lopMonHoc, String tenMonHoc, int soBaoDanh, String ngayThi, String gioThi, String phongThi, String hinhThucThi) {
        this.lopMonHoc = lopMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.soBaoDanh = soBaoDanh;
        this.ngayThi = ngayThi;
        this.gioThi = gioThi;
        this.phongThi = phongThi;
        this.hinhThucThi = hinhThucThi;
    }

    public String getLopMonHoc() {
        return lopMonHoc;
    }

    public void setLopMonHoc(String lopMonHoc) {
        this.lopMonHoc = lopMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public int getSoBaoDanh() {
        return soBaoDanh;
    }

    public void setSoBaoDanh(int soBaoDanh) {
        this.soBaoDanh = soBaoDanh;
    }

    public String getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(String ngayThi) {
        this.ngayThi = ngayThi;
    }

    public String getGioThi() {
        return gioThi;
    }

    public void setGioThi(String gioThi) {
        this.gioThi = gioThi;
    }

    public String getPhongThi() {
        return phongThi;
    }

    public void setPhongThi(String phongThi) {
        this.phongThi = phongThi;
    }

    public String getHinhThucThi() {
        return hinhThucThi;
    }

    public void setHinhThucThi(String hinhThucThi) {
        this.hinhThucThi = hinhThucThi;
    }
}
