package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The object for lion(ライオン).
 * @author ryunosuke.ito
 */

public class Lion extends Animal {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(Lion.class);

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Lion() {
    }

    public String getBarkWord() {
        return "gaoo";
    }

    public void run() {
        logger.debug("...Running now");
        downHitPoint();
    }

    @Override
    public void downHitPoint() {
        super.downHitPoint();
        if (hitPoint % 4 == 0) {
            super.downHitPoint();
        }
    }
}
