package uetsupport.dtui.uet.edu.uetsupport.models;

import java.util.ArrayList;

/**
 * Created by huylv on 10/12/2015.
 */
public class ExamSchedule {
    ArrayList<Examination> examinationArrayList;

    public ExamSchedule(ArrayList<Examination> examinationArrayList) {
        this.examinationArrayList = examinationArrayList;
    }

    public ArrayList<Examination> getExaminationArrayList() {
        return examinationArrayList;
    }

    public void setExaminationArrayList(ArrayList<Examination> examinationArrayList) {
        this.examinationArrayList = examinationArrayList;
    }
}
