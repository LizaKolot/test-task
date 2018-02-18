package proj.test.com.articles.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article implements Parcelable{

    private long id;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String path;

    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("source")
    @Expose
    private String source;


    public Article(long id, String title, String path, String publishedDate, String source) {
        this.id = id;
        this.title = title;
        this.path = path;
        this.publishedDate = publishedDate;
        this.source = source;
    }

    public Article(){}


    public long getId() {
        return id;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int describeContents() {
        return 0;
    }


    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(path);
        parcel.writeString(publishedDate);
        parcel.writeString(source);
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {

        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };


    private Article(Parcel parcel) {
        id = parcel.readLong();
        title = parcel.readString();
        path = parcel.readString();
        publishedDate = parcel.readString();
        source = parcel.readString();
    }

}
