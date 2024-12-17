package com.cat.tgbot.bean.user;

import lombok.Data;

@Data
public class ReChat {
    int id;
    int Cid;
    int Rid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return Cid;
    }

    public void setCid(int cid) {
        Cid = cid;
    }

    public int getRid() {
        return Rid;
    }

    public void setRid(int rid) {
        Rid = rid;
    }
}
