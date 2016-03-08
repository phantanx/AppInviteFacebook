package com.numetriclabz.fbfriendslist.model;

/**
 * Created by phantanx on 05/03/2016.
 */
public class User {
    private String inviteToken;
    private String name;
    private String avatarUrl;

    public User(String name, String avatarUrl, String inviteToken) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.inviteToken = inviteToken;
    }

    public String getInviteToken() {
        return inviteToken;
    }

    public void setInviteToken(String inviteToken) {
        this.inviteToken = inviteToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
