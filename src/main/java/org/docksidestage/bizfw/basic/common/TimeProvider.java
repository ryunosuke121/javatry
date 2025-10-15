package org.docksidestage.bizfw.basic.common;

import java.time.OffsetTime;

public interface TimeProvider {
    OffsetTime now();
}
