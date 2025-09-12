/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

// TODO ito author追加お願いします by jflute (2025/09/12)
/**
 * @author jflute
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // TODO ito quantity と twoDayPassportQuantity の違いは何ですか？と聞かれたら？ by jflute (2025/09/12)
    private int quantity = MAX_QUANTITY;
    private int twoDayPassportQuantity = MAX_QUANTITY;
    private Integer salesProceeds; // null allowed: until first purchase

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // /**
    // * 1Dayパスポートを買う、パークゲスト用のメソッド。
    // * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    // * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    // * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    // */
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public void buyOneDayPassport(Integer handedMoney) {
        buyPassport(Passport.ONE_DAY_PASSPORT, handedMoney);
    }

    public int buyTwoDayPassport(Integer handedMoney) {
        int change = buyPassport(Passport.TWO_DAY_PASSPORT, handedMoney);
        return change;
    }

    // #1on1: jfluteの流れを重視するリファクタリングのライブコーディング (2025/09/12)
    //private int buyPassport(Passport passport, Integer handedMoney) {
    //    int quantity = getPassportQuantity(passport);
    //    assertQuantityExists(quantity);
    //    assertEnoughMoney(passport, handedMoney);
    //    setPassportQuantity(passport, quantity - 1);
    //    calculateSalesProceeds(passport);
    //    return calculateChange(handedMoney);
    //}
    
    // TODO ito publicのbuyに対して実処理のbuyなわけですが、先頭文字を変える習慣があります by jflute (2025/09/12)
    // (this.bu... で補完紛れで若干わかりにくくなるし、会話上もbuyメソッドって言った時どっちを指す？)
    // e.g. doBuyPassport(), internalBuyPassport() など
    private int buyPassport(Passport passport, Integer handedMoney) {
        // TODO ito せっかくなので、ショートカットを使った上で、流れリファクタリングしてみましょう by jflute (2025/09/12)
        int quantity = getPassportQuantity(passport);
        if (quantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < passport.price) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        setPassportQuantity(passport, quantity - 1);
        if (salesProceeds != null) {
            salesProceeds += passport.price;
        } else {
            salesProceeds = passport.price;
        }
        return handedMoney - salesProceeds;
    }
    
    private int getPassportQuantity(Passport passport) {
        // TODO ito 修行++: switch case (分岐) なしで実現したいところですが...最後で by jflute (2025/09/12)
        // #1on1: しばらく耐えてください (step5の最後まではとりあえずこれで)
        switch (passport) {
        case ONE_DAY_PASSPORT:
            return quantity;
        case TWO_DAY_PASSPORT:
            return twoDayPassportQuantity;
        default:
            throw new IllegalStateException("Unknown passport: " + passport);
        }
    }

    private void setPassportQuantity(Passport passport, int newQuantity) {
        switch (passport) {
        case ONE_DAY_PASSPORT:
            quantity = newQuantity;
            break;
        case TWO_DAY_PASSPORT:
            twoDayPassportQuantity = newQuantity;
            break;
        }
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantity;
    }

    public int getTwoDayPassportQuantity() {
        return twoDayPassportQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
