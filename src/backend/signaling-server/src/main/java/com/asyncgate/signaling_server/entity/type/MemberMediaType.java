package com.asyncgate.signaling_server.entity.type;

public enum MemberMediaType {
    AUDIO("AUDIO"),
    MEDIA("MEDIA"),
    DATA("DATA");

    private final String type; // 문자열 값을 저장할 필드

    MemberMediaType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}