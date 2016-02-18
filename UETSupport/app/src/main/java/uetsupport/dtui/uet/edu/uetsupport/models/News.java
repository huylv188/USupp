package uetsupport.dtui.uet.edu.uetsupport.models;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huylv on 20/11/2015.
 */
public class News {
    String titleNews;
    String descNews;
    String imageLink;
    String contentNewsLink;

    public int resThumb;
    public int tn;

    public News() {
    }

    public News(int tn,int resThumb) {
        this.tn=tn;
        this.resThumb = resThumb;
    }

    public News(String titleNews, String descNews, String contentNewsLink){
        this.titleNews = titleNews;
        this.descNews = descNews;
        this.contentNewsLink = contentNewsLink;
    }

    public News(String titleNews, String descNews, String imageLink, String contentNewsLink) {
        this.titleNews = titleNews;
        this.descNews = descNews;
        this.imageLink = imageLink;
        this.contentNewsLink = contentNewsLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getTitleNews() {
        return titleNews;
    }

    public void setTitleNews(String titleNews) {
        this.titleNews = titleNews;
    }

    public String getDescNews() {
        return descNews;
    }

    public void setDescNews(String descNews) {
        this.descNews = descNews;
    }

    public String getContentNewsLink() {
        return contentNewsLink;
    }

    public void setContentNewsLink(String contentNewsLink) {
        this.contentNewsLink = contentNewsLink;
    }
}
