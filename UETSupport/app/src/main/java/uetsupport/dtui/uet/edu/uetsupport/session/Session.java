package uetsupport.dtui.uet.edu.uetsupport.session;

import uetsupport.dtui.uet.edu.uetsupport.models.User;

/**
 * Created by huylv on 08/12/2015.
 */
public class Session {
    private static User user;

    public static void setUser(User user) {
        Session.user=user;
    }

    public static void removeUser(){
        if(user!=null){
            user =null;
        }
    }

    public static User getCurrentUser(){
        return user;
    }
}
