package org.docksidestage.bizfw.basic.objanimal.barking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarkingProcess {

    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);

    protected Runnable downHitPointFunction;
    protected String barkWord;

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
