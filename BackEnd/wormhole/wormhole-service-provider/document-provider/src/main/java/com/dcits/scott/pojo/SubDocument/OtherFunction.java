package com.dcits.scott.pojo.SubDocument;

import com.dcits.scott.common.CommonDO;

public class OtherFunction extends CommonDO {
    private boolean isDiscuss;
    private boolean isThumbsUp;
    private boolean isThumbsDown;

    public boolean isDiscuss() {
        return isDiscuss;
    }

    public void setDiscuss(boolean discuss) {
        isDiscuss = discuss;
    }

    public boolean isThumbsUp() {
        return isThumbsUp;
    }

    public void setThumbsUp(boolean thumbsUp) {
        isThumbsUp = thumbsUp;
    }

    public boolean isThumbsDown() {
        return isThumbsDown;
    }

    public void setThumbsDown(boolean thumbsDown) {
        isThumbsDown = thumbsDown;
    }
}
