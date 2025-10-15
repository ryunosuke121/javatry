package org.docksidestage.bizfw.basic.common;

import java.time.OffsetTime;

public class SystemTimeProvider implements TimeProvider {
    /**
     * 現在の時刻を返す
     * @return
     */
    @Override
    public OffsetTime now() {
        return OffsetTime.now();
    }
}
