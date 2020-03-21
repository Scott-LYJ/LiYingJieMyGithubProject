package com.dcits.scott.apidocument.SubDocument;

import com.dcits.scott.common.CommonDO;

public class OtherFunction extends CommonDO {
    private boolean discuss;
    private boolean thumbsUp;
    private boolean thumbsDown;

    public boolean isDiscuss() {
        return discuss;
    }

    public void setDiscuss(boolean discuss) {
        this.discuss = discuss;
    }

    public boolean isThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(boolean thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public boolean isThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(boolean thumbsDown) {
        this.thumbsDown = thumbsDown;
    }
}
