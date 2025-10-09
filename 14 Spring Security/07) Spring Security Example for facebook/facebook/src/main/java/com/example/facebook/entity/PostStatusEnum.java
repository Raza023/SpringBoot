package com.example.facebook.entity;

public enum PostStatusEnum {

    PENDING("#Pending"),
    APPROVED("#Approved"),
    REJECTED("#Rejected");

    private final String value;

    PostStatusEnum(String value) {
        this.value = value;
    }

    /**
     * Get PostStatusEnum Value.
     *
     * @param value String
     * @return String
     */
    public static String fromValue(String value) {
        for (PostStatusEnum postStatusEnum : values()) {
            if (postStatusEnum.value.equalsIgnoreCase(value)) {
                return postStatusEnum.value;
            }
        }
        throw new IllegalArgumentException(value);
    }
}
