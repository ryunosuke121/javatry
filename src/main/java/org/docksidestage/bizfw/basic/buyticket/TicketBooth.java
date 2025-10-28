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

import static org.docksidestage.bizfw.basic.buyticket.TicketType.*;

import java.util.HashMap;
import java.util.Map;

import org.docksidestage.bizfw.basic.common.TimeProvider;

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
    // #1on1: Quantityクラスを使ったもう一個のやり方の話 (2025/10/28)
    // TODO itoryu timeProviderは受け取った主軸のデータということで一番上に by jflute (2025/10/28)
    // (Ticketクラスの変数の並びがとても自然なので、そっちに合わせる)
    private final TimeProvider timeProvider;
    private final Map<TicketType, Integer> ticketStockMap = new HashMap<>();
    private Integer salesProceeds; // null allowed: until first purchase
    
    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
        initTicketStockMap();
    }

    private void initTicketStockMap() {
        // #1on1: MAXが個別にならない割り切りの場合の実装:
        //for (TicketType ticketType : TicketType.values()) {
        //    ticketStockMap.put(ticketType, MAX_QUANTITY);
        //}
        // 一方で、MAXが個別に変わる可能性を想定して列挙してるのはそれはそれで良い。
        // ただ、getterのところで追加し忘れたときの例外ハンドリングをしておきましょう。
        //
        // 一方で一方で、maxをTicketTypeに持たせちゃってもいいかも。
        //for (TicketType ticketType : TicketType.values()) {
        //    ticketStockMap.put(ticketType, ticketType.getMaxQuantity());
        //}
        // 在庫の概念は TicketBooth にもたせておきたい by itoryu
        //
        ticketStockMap.put(ONE_DAY_PASSPORT, MAX_QUANTITY);
        ticketStockMap.put(TWO_DAY_PASSPORT, MAX_QUANTITY);
        ticketStockMap.put(FOUR_DAY_PASSPORT, MAX_QUANTITY);
        ticketStockMap.put(NIGHT_ONLY_TWO_DAY_PASSPORT, MAX_QUANTITY);
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
    // done ito javadoc, @returnを追加で (日本語でOK) by jflute (2025/09/25)
    // done itoryu @param に説明を (Eclipseだと警告になっている) by jflute (2025/10/17)
    /**
     *
     * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
     * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
     * @throws TicketShortMoneyException 買うのに金額が足りなかったら
     * @return 購入した1日パスポートのチケット
     */
    public Ticket buyOneDayPassport(Integer handedMoney) {
        // done itoryu すでにresultを作る処理が含まれているので、そこからticketだけ取得してもいいかなと by jflute (2025/10/17)
        // 仮に、無駄処理を発生させないようにできたとしても、大した処理ではないので気にしなくてもいいレベルかと。
        // (もし、calculateChange()がDB見たりRemoteAPI呼び出ししてたりとかしたら話は別ですが)
        return doBuyPassport(ONE_DAY_PASSPORT, handedMoney).getTicket();
    }

    // done ito doBuyがresultを戻してしまってもいいのでは？ by jflute (2025/09/25)
    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        return doBuyPassport(TWO_DAY_PASSPORT, handedMoney);
    }

    public TicketBuyResult buyFourDayPassport(Integer handedMoney) {
        return doBuyPassport(FOUR_DAY_PASSPORT, handedMoney);
    }

    public TicketBuyResult buyNightOnlyTwoDayPassport(Integer handedMoney) {
        return doBuyPassport(TicketType.NIGHT_ONLY_TWO_DAY_PASSPORT, handedMoney);
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
        return new TicketBuyResult(new Ticket(this.timeProvider, ticketType), change);
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
        // done ito 修行++: switch case (分岐) なしで実現したいところですが...最後で by jflute (2025/09/12)
        // #1on1: しばらく耐えてください (step5の最後まではとりあえずこれで)
        // TODO itoryu stockに種別を追加し忘れたときのために、例外ハンドリング入れておきましょう by jflute (2025/10/28)
        return ticketStockMap.get(ticketType);
    }

    private void setTicketQuantity(TicketType ticketType, int newQuantity) {
        ticketStockMap.put(ticketType, newQuantity);
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
        return ticketStockMap.get(ONE_DAY_PASSPORT);
    }

    public int getTwoDayPassportQuantity() {
        return ticketStockMap.get(TWO_DAY_PASSPORT);
    }

    public int getFourDayPassportQuantity() {
        return ticketStockMap.get(FOUR_DAY_PASSPORT);
    }

    public int getNightOnlyTwoDayPassportQuantity() {
        return ticketStockMap.get(NIGHT_ONLY_TWO_DAY_PASSPORT);
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
