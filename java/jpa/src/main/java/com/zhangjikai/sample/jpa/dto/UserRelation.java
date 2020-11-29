package com.zhangjikai.sample.jpa.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Jikai Zhang
 * @date 2020-11-28
 */
@Entity
public class UserRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long userId;
    private long fanId;
    private long timestamp;
    
    
    public UserRelation() {
    }
    
    public UserRelation(long userId, long fanId, long timestamp) {
        this.userId = userId;
        this.fanId = fanId;
        this.timestamp = timestamp;
    }
    
    public long getUserId() {
        return userId;
    }
    
    public long getFanId() {
        return fanId;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
}
