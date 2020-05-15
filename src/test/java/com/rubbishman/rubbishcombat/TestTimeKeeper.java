package com.rubbishman.rubbishcombat;

import com.rubbishman.rubbishRedux.internal.timekeeper.TimeKeeper;

public class TestTimeKeeper extends TimeKeeper {
    TestTimeKeeper(long nowTime) {
        this.nowTime = nowTime;
        this.elapsedTime = 0;
    }

    public void progressTime() {}

    public void setNewTime(long newNowTime) {
        elapsedTime = newNowTime - nowTime;
        nowTime = newNowTime;
    }
}
