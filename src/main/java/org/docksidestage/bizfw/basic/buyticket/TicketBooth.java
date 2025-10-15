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

// done ito author追加お願いします by jflute (2025/09/12)

import static org.docksidestage.bizfw.basic.buyticket.TicketType.ONE_DAY_PASSPORT;
import static org.docksidestage.bizfw.basic.buyticket.TicketType.TWO_DAY_PASSPORT;

/**
 * @author jflute
 * @author ryunosuke.ito
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // done ito quantity と twoDayPassportQuantity の違いは何ですか？と聞かれたら？ by jflute (2025/09/12)
    //
    // #1on1: おかげさまで、良いブログ書けました。ありがとうございます。 (2025/09/25)
    // // 既存コードちょい直したい、いつやる？
    // https://jflute.hatenadiary.jp/entry/20250913/whenrefactor
    //
    // 1秒でもいいから判断するステップを踏みたい話。
    //
    // ついでに:
    // // 応援してる "A" にもデメリットはあるよ
    // https://jflute.hatenadiary.jp/entry/20181008/yourademerit
    //
    private int oneDayPassportQuantity = MAX_QUANTITY;
    private int twoDayPassportQuantity = MAX_QUANTITY;
    private int fourDayPassportQuantity = MAX_QUANTITY;
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
    // TODO done ito javadoc, @returnを追加で (日本語でOK) by jflute (2025/09/25)
    /**
     *
     * @param handedMoney
     * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
     * @throws TicketShortMoneyException 買うのに金額が足りなかったら
     * @return 購入した1日パスポートのチケット
     */
    public Ticket buyOneDayPassport(Integer handedMoney) {
        doBuyPassport(ONE_DAY_PASSPORT, handedMoney);
        return new Ticket(ONE_DAY_PASSPORT);
    }

    // TODO done ito doBuyがresultを戻してしまってもいいのでは？ by jflute (2025/09/25)
    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        return doBuyPassport(TWO_DAY_PASSPORT, handedMoney);
    }

    public TicketBuyResult buyFourDayPassport(Integer handedMoney) {
        return doBuyPassport(TicketType.FOUR_DAY_PASSPORT, handedMoney);
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

    // done ito publicのbuyに対して実処理のbuyなわけですが、先頭文字を変える習慣があります by jflute (2025/09/12)
    // (this.bu... で補完紛れで若干わかりにくくなるし、会話上もbuyメソッドって言った時どっちを指す？)
    // e.g. doBuyPassport(), internalBuyPassport() など
    private TicketBuyResult doBuyPassport(TicketType ticketType, Integer handedMoney) {
        // done ito せっかくなので、ショートカットを使った上で、流れリファクタリングしてみましょう by jflute (2025/09/12)
        int quantity = getTicketQuantity(ticketType);
        validatePurchaseCondition(ticketType, handedMoney, quantity);
        setTicketQuantity(ticketType, quantity - 1);
        increaseSalesProceeds(ticketType);
        int change = calculateChange(handedMoney);
        return new TicketBuyResult(new Ticket(ticketType), change);
    }

    private static void validatePurchaseCondition(TicketType ticketType, Integer handedMoney, int quantity) {
        if (quantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < ticketType.getPrice()) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
    }

    private void increaseSalesProceeds(TicketType ticketType) {
        if (salesProceeds != null) {
            salesProceeds += ticketType.getPrice();
        } else {
            salesProceeds = ticketType.getPrice();
        }
    }

    private int calculateChange(Integer handedMoney) {
        return handedMoney - salesProceeds;
    }

    private int getTicketQuantity(TicketType ticketType) {
        // TODO ito 修行++: switch case (分岐) なしで実現したいところですが...最後で by jflute (2025/09/12)
        // #1on1: しばらく耐えてください (step5の最後まではとりあえずこれで)
        switch (ticketType) {
        case ONE_DAY_PASSPORT:
            return oneDayPassportQuantity;
        case TWO_DAY_PASSPORT:
            return twoDayPassportQuantity;
        case FOUR_DAY_PASSPORT:
            return fourDayPassportQuantity;
        default:
            throw new IllegalStateException("Unknown passport: " + ticketType);
        }
    }

    private void setTicketQuantity(TicketType ticketType, int newQuantity) {
        switch (ticketType) {
        case ONE_DAY_PASSPORT:
            oneDayPassportQuantity = newQuantity;
            break;
        case TWO_DAY_PASSPORT:
            twoDayPassportQuantity = newQuantity;
            break;
        case FOUR_DAY_PASSPORT:
            fourDayPassportQuantity = newQuantity;
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
    public int getOneDayPassportQuantity() {
        return oneDayPassportQuantity;
    }

    public int getTwoDayPassportQuantity() {
        return twoDayPassportQuantity;
    }

    public int getFourDayPassportQuantity() {
        return fourDayPassportQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
