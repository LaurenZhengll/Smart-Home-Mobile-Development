package com.stevecrossin.mindlab;

public class NotificationItem {
    private static String Title;
    private static String NotificationContent;
    private static String NotificationDate;

    public NotificationItem(String Title, String NotificationDate, String NotificationContent){
        this.Title = Title;
        this.NotificationContent = NotificationContent;
        this.NotificationDate = NotificationDate;
    }

    public static String getTitle(){
        return Title;
    }


    public static String getNotificationContent(){
        return NotificationContent;
    }

    public static String getNotificationDate(){
        return NotificationDate;
    }

}
