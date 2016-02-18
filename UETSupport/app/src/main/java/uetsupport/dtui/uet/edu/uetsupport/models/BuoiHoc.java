package uetsupport.dtui.uet.edu.uetsupport.models;

/**
 * Created by huylv on 09/12/2015.
 */
public class BuoiHoc {
    int tietDau;
    int soTiet;
    int thu;

    BuoiHoc(){};

    public BuoiHoc(int tietDau, int soTiet, int thu) {
        this.tietDau = tietDau;
        this.soTiet = soTiet;
        this.thu = thu;
    }

    public int getTietDau() {
        return tietDau;
    }

    public void setTietDau(int tietDau) {
        this.tietDau = tietDau;
    }

    public int getSoTiet() {
        return soTiet;
    }

    public void setSoTiet(int soTiet) {
        this.soTiet = soTiet;
    }

    public int getThu() {
        return thu;
    }

    public void setThu(int thu) {
        this.thu = thu;
    }

    @Override
    public String toString() {
        return "BuoiHoc{" +
                "tietDau=" + tietDau +
                ", soTiet=" + soTiet +
                ", thu=" + thu +
                '}';
    }
}
