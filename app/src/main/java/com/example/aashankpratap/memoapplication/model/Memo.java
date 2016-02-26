package com.example.aashankpratap.memoapplication.model;

/**
 * Pojo class for memo
 */
public class Memo {
    private String mTag;
    private String mContent;

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ Tag: ").append(mTag).append(" Content: ").append(mContent).append(" ]");
        return sb.toString();
    }
}
