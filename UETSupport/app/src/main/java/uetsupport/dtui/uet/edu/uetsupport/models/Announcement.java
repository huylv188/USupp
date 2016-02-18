package uetsupport.dtui.uet.edu.uetsupport.models;

/**
 * Created by huylv on 08/12/2015.
 */
public class Announcement {
    private String announcementTitle;
    private String announcementDesc;
    private String announcementLink;

    public Announcement(String announcementTitle, String announcementDesc, String announcementLink) {
        this.announcementTitle = announcementTitle;
        this.announcementDesc = announcementDesc;
        this.announcementLink = announcementLink;
    }


    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }

    public String getAnnouncementDesc() {
        return announcementDesc;
    }

    public void setAnnouncementDesc(String announcementDesc) {
        this.announcementDesc = announcementDesc;
    }

    public String getAnnouncementLink() {
        return announcementLink;
    }

    public void setAnnouncementLink(String announcementLink) {
        this.announcementLink = announcementLink;
    }
}
