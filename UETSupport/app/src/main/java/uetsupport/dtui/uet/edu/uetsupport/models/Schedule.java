package uetsupport.dtui.uet.edu.uetsupport.models;

import java.util.ArrayList;

/**
 * Created by huylv on 08/12/2015.
 */
public class Schedule {
    ArrayList<Course> courseArrayList;

    public Schedule(ArrayList<Course> courseArrayList) {
        this.courseArrayList = courseArrayList;
    }

    public ArrayList<Course> getCourseArrayList() {
        return courseArrayList;
    }

    public void setCourseArrayList(ArrayList<Course> courseArrayList) {
        this.courseArrayList = courseArrayList;
    }

    public Course getCourseByLMH(String lmh){
        for(Course c : courseArrayList){
            if(c.getLopMonHoc().equals(lmh)){
                return c;
            }
        }

        return null;
    }
}
