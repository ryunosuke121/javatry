package org.docksidestage.bizfw.basic.objanimal.barking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarkingProcess {

    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);

    // #1on1: Animal依存がなくなったので汎用的になった (2026/01/27)
    // #1on1: Runnable のお話 (2026/01/27)
    // TODO itoryu ここまできたらfinal付けてしっかりimmutableにしましょう by jflute (2026/01/27)
    // #1on1: Javaでのimmutableのさじ加減の話も (DBFluteでのfinalのお話も) (2026/01/27)
    protected final Runnable downHitPointFunction;
    protected final String barkWord;

    public BarkingProcess(Runnable downHitPointFunction, String barkWord) {
        this.downHitPointFunction = downHitPointFunction;
        this.barkWord = barkWord;
    }

    public BarkedSound execute() {
        breatheIn();
        prepareAbdominalMuscle();
        return doBark(barkWord);
    }

    protected void breatheIn() {
        logger.debug("...Breathing in for barking");
        downHitPointFunction.run();
    }

    protected void prepareAbdominalMuscle() {
        logger.debug("...Using my abdominal muscle for barking");
        downHitPointFunction.run();
    }

    protected BarkedSound doBark(String barkWord) {
        downHitPointFunction.run();
        return new BarkedSound(barkWord);
    }
}
