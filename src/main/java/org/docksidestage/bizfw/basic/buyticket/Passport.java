package org.docksidestage.bizfw.basic.buyticket;

public enum Passport {
    ONE_DAY_PASSPORT(7400), TWO_DAY_PASSPORT(13200);

    public final int price;

    Passport(int price) {
        this.price = price;
    }
}
