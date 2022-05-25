package com.example.bottom_menu;

public class QrModel {
    private final int[] QR_TYPE = {0, 1, 2, 3, 4, 5, 6, 7};
    private int type;
    private boolean isFavorite;
    private String content;

    public QrModel(int type, boolean isFavorite, String content) {
        this.type = type;
        this.isFavorite = isFavorite;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Type: " + type +
                " isFavorite: " + isFavorite +
                " Content: '" + content + '\'';
    }

    public int[] getQR_TYPE() {
        return QR_TYPE;
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
