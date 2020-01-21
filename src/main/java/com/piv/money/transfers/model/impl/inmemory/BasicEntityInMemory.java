package com.piv.money.transfers.model.impl.inmemory;

import com.piv.money.transfers.model.BasicEntity;

import java.util.Date;

/**
 * Created by Ivan on 18.01.2020.
 */
public class BasicEntityInMemory implements BasicEntity {
    private String id;
    private boolean locked;
    private Date lastChangeDate;

    public BasicEntityInMemory(String id) {
        this.id = id;
        this.locked = false;
        this.lastChangeDate = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Date getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(Date lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicEntityInMemory that = (BasicEntityInMemory) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
