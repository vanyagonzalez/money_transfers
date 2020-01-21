package com.piv.money.transfers.model;

import java.util.Date;

/**
 * Created by Ivan on 18.01.2020.
 */
public interface BasicEntity {
    String getId();

    void setId(String id);

    boolean isLocked();

    void setLocked(boolean locked);

    Date getLastChangeDate();

    void setLastChangeDate(Date date);
}
