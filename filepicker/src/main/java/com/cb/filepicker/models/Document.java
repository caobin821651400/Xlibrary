package com.cb.filepicker.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.cb.filepicker.utils.FilePickerUtils;

import java.io.File;



/**
 * Created by caobin on 29/07/16.
 */
public class Document implements Parcelable {
    private static final long serialVersionUID = 5755021761564268839L;
    private String mimeType;
    private String size;
    private FileType fileType;
    private int id;
    private String name;
    private String path;

    public Document(int id, String title, String path) {
        this.id = id;
        this.name = title;
        this.path = path;
    }

    protected Document(Parcel in) {
        mimeType = in.readString();
        size = in.readString();
        fileType = in.readParcelable(FileType.class.getClassLoader());
        id = in.readInt();
        name = in.readString();
        path = in.readString();
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;

        Document document = (Document) o;

        return id == document.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTitle() {
        return new File(this.path).getName();
    }

    public void setTitle(String title) {
        this.name = title;
    }

    public boolean isThisType(String[] types) {
        return FilePickerUtils.contains(types, this.path);
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mimeType);
        dest.writeString(size);
        dest.writeParcelable(fileType, flags);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(path);
    }
}
