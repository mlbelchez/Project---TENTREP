package ph.edu.apc.roadtweetfinal;

import java.util.Date;

/**
 * Created by School on 11/30/2016.
 */

public class Tweets {

    private String tweetText;
    private String tweetUser;
    private long tweetTime;
    private String tweetLocation;
    private String tweetImage;

    public Tweets(String tweetImage, String tweetLocation, String tweetText, String tweetUser) {
        this.tweetImage = tweetImage;
        this.tweetLocation = tweetLocation;
        this.tweetText = tweetText;
        this.tweetUser = tweetUser;
        tweetTime = new Date().getTime();
    }

    public Tweets(){

    }

    public String getTweetImage() {
        return tweetImage;
    }
    public void setTweetImage(String tweetImage) {
        this.tweetImage = tweetImage;
    }

    public String getTweetLocation() {
        return tweetLocation;
    }
    public void setTweetLocation(String tweetLocation) {
        this.tweetLocation = tweetLocation;
    }

    public String getTweetText() {
        return tweetText;
    }
    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public String getTweetUser() {
        return tweetUser;
    }
    public void setTweetUser(String tweetUser) {
        this.tweetUser = tweetUser;
    }

    public long getTweetTime() {
        return tweetTime;
    }
    public void setTweetTime(long tweetTime) {
        this.tweetTime = tweetTime;
    }

}
