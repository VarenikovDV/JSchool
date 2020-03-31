package soc.network.photo;

import java.net.URL;

public class Photo {
    private int id;
    private URL url;

    public Photo(int id, URL url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }



}
