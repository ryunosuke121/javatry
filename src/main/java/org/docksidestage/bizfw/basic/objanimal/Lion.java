package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    protected String getBarkWord() {
        return "gaoo";
    }

    public void run() {
        logger.debug("...Running now");
        downHitPoint();
    }

    @Override
    protected void downHitPoint() {
        super.downHitPoint();
        if (hitPoint % 4 == 0) {
            super.downHitPoint();
        }
    }
}
