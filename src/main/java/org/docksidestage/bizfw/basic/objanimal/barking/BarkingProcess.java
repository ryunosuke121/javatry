package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarkingProcess {

    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);

    protected final Animal animal;

    public BarkingProcess(Animal animal) {
        this.animal = animal;
    }

    public BarkedSound execute() {
        breatheIn();
        prepareAbdominalMuscle();
        String barkWord = getBarkWord();
        return doBark(barkWord);
    }

    protected void breatheIn() {
        logger.debug("...Breathing in for barking");
        animal.downHitPoint();
    }

    protected void prepareAbdominalMuscle() {
        logger.debug("...Using my abdominal muscle for barking");
        animal.downHitPoint();
    }

    protected String getBarkWord() {
        return animal.getBarkWord();
    }

    protected BarkedSound doBark(String barkWord) {
        animal.downHitPoint();
        return new BarkedSound(barkWord);
    }
}
