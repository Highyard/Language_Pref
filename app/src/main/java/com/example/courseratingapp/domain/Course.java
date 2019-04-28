package com.example.courseratingapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Course implements Parcelable {

    private float currentOverallRating;
    private float newRating;
    private float averageRating;
    private int averageCount;

    public Course(){

    }

    public Course(float currentOverallRating, float newRating,float averageRating, int averageCount) {
        this.currentOverallRating = currentOverallRating;
        this.newRating = newRating;
        this.averageRating = averageRating;
        this.averageCount = averageCount;
    }

    protected Course(Parcel in) {
        currentOverallRating = in.readFloat();
        newRating = in.readFloat();
        averageRating = in.readFloat();
        averageCount = in.readInt();
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public float getCurrentOverallRating() {
        return currentOverallRating;
    }

    public void setCurrentOverallRating(float currentOverallRating) {
        this.currentOverallRating = currentOverallRating;
    }

    public float getNewRating() {
        return newRating;
    }

    public void setNewRating(float newRating) {
        this.newRating = newRating;
    }

    public int getAverageCount() {return averageCount;}

    public void setAverageCount(int averageCount) {this.averageCount = averageCount;}

    public float getAverageRating () {return averageRating;}

    public void setAverageRating(float averageRating) {this.averageRating = averageRating;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(currentOverallRating);
        dest.writeFloat(newRating);
        dest.writeFloat(averageRating);
        dest.writeInt(averageCount);
    }
}