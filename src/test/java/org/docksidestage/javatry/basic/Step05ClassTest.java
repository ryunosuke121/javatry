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
package org.docksidestage.javatry.basic;

import java.time.OffsetTime;

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth.TicketShortMoneyException;
import org.docksidestage.bizfw.basic.buyticket.TicketBuyResult;
import org.docksidestage.bizfw.basic.buyticket.TicketType;
import org.docksidestage.bizfw.basic.common.SystemTimeProvider;
import org.docksidestage.bizfw.basic.common.TimeProvider;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of class. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう) <br>
 *
 * If ambiguous requirement exists, you can determine specification that seems appropriate. <br>
 * (要件が曖昧なところがあれば、適切だと思われる仕様を決めても良いです)
 *
 * @author jflute
 * @author ryunosuke.ito
 */
public class Step05ClassTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          How to Use
    //                                                                          ==========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_class_howToUse_basic() {
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        booth.buyOneDayPassport(7400);
        int sea = booth.getOneDayPassportQuantity();
        log(sea); // your answer? => 9
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_overpay() {
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        booth.buyOneDayPassport(10000);
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => 10000 -> 7400(fix後)
    }
    // お釣りを渡さないお店だ

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_nosales() {
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => null
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_wrongQuantity() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // your answer? => 9 -> 10(fix後)
    }
    // 在庫が減ってしまう

    private Integer doTest_class_ticket_wrongQuantity() {
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        int handedMoney = 7399;
        try {
            booth.buyOneDayPassport(handedMoney);
            fail("always exception but none");
        } catch (TicketShortMoneyException continued) {
            log("Failed to buy one-day passport: money=" + handedMoney, continued);
        }
        return booth.getOneDayPassportQuantity();
    }

    // ===================================================================================
    //                                                                           Let's fix
    //                                                                           =========
    /**
     * Fix the problem of ticket quantity reduction when short money. (Don't forget to fix also previous exercise answers) <br>
     * (お金不足でもチケットが減る問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_ticketQuantityReduction() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // should be max quantity, visual check here

        // #1on1: それぞれの行は間違ってないのに、順番間違えたらミス。

        // #1on1: 宣言型のプログラミングのお話: SQL
        // Springのアノテーション
    }

    /**
     * Fix the problem of sales proceeds increased by handed money. (Don't forget to fix also previous exercise answers) <br>
     * (受け取ったお金の分だけ売上が増えていく問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_salesProceedsIncrease() {
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        booth.buyOneDayPassport(10000);
        Integer sea = booth.getSalesProceeds();
        log(sea); // should be same as one-day price, visual check here
    }

    /**
     * Make method for buying two-day passport (price is 13200). (which can return change as method return value)
     * (TwoDayPassport (金額は13200) も買うメソッドを作りましょう (戻り値でお釣りをちゃんと返すように))
     */
    public void test_class_letsFix_makeMethod_twoday() {
        // uncomment after making the method
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        int money = 14000;
        TicketBuyResult result = booth.buyTwoDayPassport(money);
        Integer sea = booth.getSalesProceeds() + result.getChange();
        log(sea); // should be same as money

        // and show two-day passport quantity here
        log(booth.getTwoDayPassportQuantity());
    }

    /**
     * Recycle duplicate logics between one-day and two-day by e.g. private method in class. (And confirm result of both before and after) <br>
     * (OneDayとTwoDayで冗長なロジックがあったら、クラス内のprivateメソッドなどで再利用しましょう (修正前と修正後の実行結果を確認))
     */
    public void test_class_letsFix_refactor_recycle() {
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        booth.buyOneDayPassport(10000);
        log(booth.getOneDayPassportQuantity(), booth.getSalesProceeds()); // should be same as before-fix
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Now you cannot get ticket if you buy one-day passport, so return Ticket class and do in-park. <br>
     * (OneDayPassportを買ってもチケットをもらえませんでした。戻り値でTicketクラスを戻すようにしてインしましょう)
     */
    public void test_class_moreFix_return_ticket() {
        // uncomment out after modifying the method
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        Ticket oneDayPassport = booth.buyOneDayPassport(10000);
        log(oneDayPassport.getDisplayPrice()); // should be same as one-day price
        log(oneDayPassport.isAlreadyIn()); // should be false
        oneDayPassport.doInPark();
        log(oneDayPassport.isAlreadyIn()); // should be true
    }

    /**
     * Now also you cannot get ticket if two-day passport, so return class that has ticket and change. <br>
     * (TwoDayPassportもチケットをもらえませんでした。チケットとお釣りを戻すクラスを作って戻すようにしましょう)
     */
    public void test_class_moreFix_return_whole() {
        // uncomment after modifying the method
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        int handedMoney = 20000;
        TicketBuyResult buyResult = booth.buyTwoDayPassport(handedMoney);
        Ticket twoDayPassport = buyResult.getTicket();
        int change = buyResult.getChange();
        log(twoDayPassport.getDisplayPrice() + change); // should be same as money
    }

    /**
     * Now you can use only one in spite of two-day passport, so fix Ticket to be able to handle plural days. <br>
     * (TwoDayPassportなのに一回しか利用できません。複数日数に対応できるようにTicketを修正しましょう)
     */
    public void test_class_moreFix_usePluralDays() {
        // your confirmation code here
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        int handedMoney = 20000;
        TicketBuyResult buyResult = booth.buyTwoDayPassport(handedMoney);
        Ticket twoDayPassport = buyResult.getTicket();
        log(twoDayPassport.isAlreadyIn()); // should be false
        twoDayPassport.doInPark();
        log(twoDayPassport.isAlreadyIn()); // should be false
        twoDayPassport.doInPark();
        log(twoDayPassport.isAlreadyIn()); // should be true
    }

    /**
     * Accurately determine whether type of bought ticket is two-day passport or not by if-statemet. (fix Ticket classes if needed) <br>
     * (買ったチケットの種別がTwoDayPassportなのかどうかをif文で正確に判定してみましょう。(必要ならTicketクラスたちを修正))
     */
    public void test_class_moreFix_whetherTicketType() {
        // uncomment when you implement this exercise
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        Ticket oneDayPassport = booth.buyOneDayPassport(10000);
        showTicketIfNeeds(oneDayPassport);
        TicketBuyResult buyResult = booth.buyTwoDayPassport(20000);
        Ticket twoDayPassport = buyResult.getTicket();
        showTicketIfNeeds(twoDayPassport);
    }

    // uncomment when you implement this exercise
    private void showTicketIfNeeds(Ticket ticket) {
        // #1on1: もしgetDays()==2で判定していたら、nightの登場で破綻する話 (2025/10/17)
        // 新機能を追加したら、既存のコードがおかしくなるケース。(既存のコードはさわってないに突然変な動きする)
        if (ticket.getTicketType() == TicketType.TWO_DAY_PASSPORT) { // write determination for two-day passport
            log("two-day passport");
        } else {
            log("other");
        }
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Fix it to be able to buy four-day passport (price is 22400). <br>
     * (FourDayPassport (金額は22400) のチケットも買えるようにしましょう)
     */
    public void test_class_moreFix_wonder_four() {
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());
        int money = 24000;
        TicketBuyResult result = booth.buyFourDayPassport(money);
        Integer sea = booth.getSalesProceeds() + result.getChange();
        log(sea); // should be same as money

        // four-day passport quantity
        log(booth.getFourDayPassportQuantity());
    }

    /**
     * Fix it to be able to buy night-only two-day passport (price is 7400), which can be used at only night. <br>
     * (NightOnlyTwoDayPassport (金額は7400) のチケットも買えるようにしましょう。夜しか使えないようにしましょう)
     */
    public void test_class_moreFix_wonder_night() {
        test_can_buy_nightOnlyTwoDayPassport();

        test_should_enter_park_only_when_in_night_time();
    }

    public void test_can_buy_nightOnlyTwoDayPassport() {
        // done itoryu 修行++: あとちょいの工夫で、TicketBoothで買ったnightOnlyでmockTimeできるはず by jflute (2025/10/17)
        TicketBooth booth = new TicketBooth(new MockTimeProvider(OffsetTime.of(19, 0, 0, 0, OffsetTime.now().getOffset())));
        TicketBuyResult result = booth.buyNightOnlyTwoDayPassport(8000);
        Integer sea = booth.getSalesProceeds() + result.getChange();
        log(sea); // should be same as money
        log(booth.getNightOnlyTwoDayPassportQuantity());
    }

    public void test_should_enter_park_only_when_in_night_time() {
        MockTimeProvider mockTimeProvider = new MockTimeProvider(OffsetTime.of(16, 59, 59, 0, OffsetTime.now().getOffset()));
        Ticket nightOnlyTwoDayPassport = new Ticket(mockTimeProvider, TicketType.NIGHT_ONLY_TWO_DAY_PASSPORT);
        try {
            nightOnlyTwoDayPassport.doInPark();
            fail("should be exception");
        } catch (IllegalStateException e) {
            log(e.getMessage()); // should be night time only ticketというエラーが出るはず
        }
        mockTimeProvider = new MockTimeProvider(OffsetTime.of(17, 0, 0, 0, OffsetTime.now().getOffset()));
        nightOnlyTwoDayPassport = new Ticket(mockTimeProvider, TicketType.NIGHT_ONLY_TWO_DAY_PASSPORT);
        nightOnlyTwoDayPassport.doInPark();
    }

    static class MockTimeProvider implements TimeProvider {
        private final java.time.OffsetTime time;

        public MockTimeProvider(java.time.OffsetTime time) {
            this.time = time;
        }

        @Override
        public java.time.OffsetTime now() {
            return time;
        }
    }

    // ===================================================================================
    //                                                                         Bonus Stage
    //                                                                         ===========
    /**
     * Refactor the code to the best readable code you can think of. <br>
     * (自分の中で思う最高に可読性の高いコードにリファクタリングしてみましょう)
     */
    public void test_class_moreFix_yourRefactoring() {
        // your confirmation code here
    }

    /**
     * Write intelligent JavaDoc comments seriously on the public classes/constructors/methods of the Ticket class. <br>
     * (Ticketクラスのpublicなクラス/コンストラクター/メソッドに、気の利いたJavaDocコメントを本気で書いてみましょう) <br>
     * <br>
     * Seriously → With the intention that the Ticket class (for example) becomes open source and is used by hundreds of people. <br>
     * (本気で → Ticketクラスが(例えば)オープンソースになって何百人の人から利用される想定のつもりで。)
     */
    public void test_class_moreFix_yourSuperJavaDoc() {
        // your confirmation code here
    }

    // ===================================================================================
    //                                                                         Devil Stage
    //                                                                         ===========
    /**
     * If your specification is to share inventory (quantity) between OneDay/TwoDay/...,
     * change the specification to separate inventory for each OneDay/TwoDay/.... <br>
     * (もし、OneDay/TwoDay/...で在庫(quantity)を共有する仕様になってたら、
     * OneDay/TwoDay/...ごとに在庫を分ける仕様に変えてみましょう)
     */
    public void test_class_moreFix_zonedQuantity() {
        // your confirmation code here
    }
}
