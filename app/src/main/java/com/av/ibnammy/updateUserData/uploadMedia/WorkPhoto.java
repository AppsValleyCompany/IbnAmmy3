package com.av.ibnammy.updateUserData.uploadMedia;

/**
 * Created by Mina on 5/2/2018.
 */

public class WorkPhoto {
    private  String path  ;
    private int pos;
    private boolean fromServer;

    public WorkPhoto(String path, int pos, boolean fromServer) {
        this.path = path;
        this.pos = pos;
        this.fromServer = fromServer;
    }

    public String getPath() {
        return path;
    }

    public int getPos() {
        return pos;
    }

    public boolean isFromServer(){
        return fromServer;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setFromServer(boolean fromServer) {
        this.fromServer = fromServer;
    }
}
