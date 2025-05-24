package org.webbee.models;

public enum TransactionType {
    INQUIRY("balance inquiry"),
    TRANSFERRED("transferred"),
    WINHDREW("withdrew"),
    FINAL("final balance"),
    UNKNOWN("unkown");

    private String code;

    TransactionType(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
