package io.github.faisal09alam.heycrypto;

public class eachNewsItems {

    private String title;
    private String url;
    private String createdOn;
    private String comments;
    private String likes;
    private String disLikes;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDisLikes() {
        return disLikes;
    }

    public void setDisLikes(String disLikes) {
        this.disLikes = disLikes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public eachNewsItems(String title, String url, String createdOn , String comm , String likes , String disLikes ) {
        this.title = title;
        this.url = url;
        this.createdOn = createdOn;
        this.comments = comm;
        this.likes = likes;
        this.disLikes = disLikes;
    }
}
