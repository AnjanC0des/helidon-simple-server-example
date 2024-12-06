package com.example.myproject;
import java.util.List;
import java.util.ArrayList;
public class Message{
                private List<String> recipients;
                private String sender;
                private String message;

                public List<String> getRecipients(){
                        return this.recipients;
                }
                public String getSender(){
                        return this.sender;
                }
                public String getMessage(){
                        return this.message;
                }
                public void setRecipients(ArrayList<String>recipients){
                        this.recipients=recipients;
                }
                public void setSender(String sender){
                        this.sender=sender;
                }
                public void setMessage(String message){
                        this.message=message;
                }
                public boolean validate(){
                        return recipients!=null && !recipients.isEmpty() &&
                                sender!=null && !sender.isEmpty() &&
                                message!=null && !message.isEmpty();
                }
}
