package com.example.mynotes.tools;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {
    private String id;
    private String body;
    private String title;
    private Date date;

    public Note(String title, String body, String id, Date date) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.id = id;
    }

    protected Note(Parcel in) {
        title = in.readString();
        body = in.readString();
        date = new Date(in.readLong());
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
        date = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public boolean isEmpty() {
        return title.equals("") && body.equals("");
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeLong(date.getTime());
    }

}
