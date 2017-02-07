package javabean;

import java.io.Serializable;

/**
 * Created by p on 2016/11/14.
 * 本地视频的载体
 */
public class Location_Film_Bean implements Serializable {

    public String filmName;
    public long filmTime;
    public long filmSize;
    public String filmAddress;
    public String filmArtist;

    public Location_Film_Bean() {
    }

    public Location_Film_Bean(String filmName, long filmTime, long filmSize, String filmAddress, String filmArtist) {
        this.filmName = filmName;
        this.filmTime = filmTime;
        this.filmSize = filmSize;
        this.filmAddress = filmAddress;
        this.filmArtist = filmArtist;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public long getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(long filmTime) {
        this.filmTime = filmTime;
    }

    public long getFilmSize() {
        return filmSize;
    }

    public void setFilmSize(long filmSize) {
        this.filmSize = filmSize;
    }

    public String getFilmAddress() {
        return filmAddress;
    }

    public void setFilmAddress(String filmAddress) {
        this.filmAddress = filmAddress;
    }

    public String getFilmArtist() {
        return filmArtist;
    }

    public void setFilmArtist(String filmArtist) {
        this.filmArtist = filmArtist;
    }

    @Override
    public String toString() {
        return "Location_Film_Bean{" +
                "filmName='" + filmName + '\'' +
                ", filmTime=" + filmTime +
                ", filmSize=" + filmSize +
                ", filmAddress='" + filmAddress + '\'' +
                ", filmArtist='" + filmArtist + '\'' +
                '}';
    }
}
