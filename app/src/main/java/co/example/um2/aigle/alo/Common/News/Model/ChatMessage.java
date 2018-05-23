package co.example.um2.aigle.alo.Common.News.Model;

/**
 * Created by dewispelaere on 23/05/18.
 */

public class ChatMessage {
    private String content;
    private String author;

    public ChatMessage(String c, String a){
        this.content = c;
        this.author = a;
    }

    public void setContent(String c){
        this.content = c;
    }

    public String getContent(){
        return this.content;
    }

    public void setAuthor(String a){
        this.author = a;
    }

    public String getAuthor() { return  this.author; }
}
