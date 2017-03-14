package uk.ac.warwick.java.cs126.services;
//an element to store a Hashtag and its occurences
public class HashtagElement{
    private final String hashtag;
    private int occurence;
    public HashtagElement(String hashtag){
        this.hashtag = hashtag;
        this.occurence = 1;
    }
    public HashtagElement(String hashtag, int occurence){
        this.hashtag = hashtag;
        this.occurence = occurence;
    }
    public String getHashtag() {
        return hashtag;
    }

    public int getOccurence() {
        return occurence;
    }
    public void updateOccurence(){
        this.occurence++;
    }
}