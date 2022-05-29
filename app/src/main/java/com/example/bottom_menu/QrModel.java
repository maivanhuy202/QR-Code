package com.example.bottom_menu;


public class QrModel {

    private final String title;
    private final String dateTime;
    private int type;
    private boolean isFavorite;
    private String content;
    /*QR Type
    1: contact
    2: email
    3: message
    4: url
    5: text
    6: phone number
    7: wifi
    8: default
     */


    public QrModel(int type, String dateTime, String title, boolean isFavorite, String content) {
        this.type = type;
        this.dateTime = dateTime;
        this.title = title;
        this.isFavorite = isFavorite;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Type: " + type +
                " isFavorite: " + isFavorite +
                " Content: '" + content + '\'';
    }


    public String getTitle() {
        return title;
    }

    public String getDateTime() {
        return dateTime;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
