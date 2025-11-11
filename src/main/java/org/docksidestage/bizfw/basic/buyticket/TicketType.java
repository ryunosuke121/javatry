package org.docksidestage.bizfw.basic.buyticket;

// #1on1: enumクラス、変なインスタンス作られない by いとさん
// privateのコンストラクターなので、変なインスタンスを作ることができないようになっている。
// Java5 (2005くらい) から導入された文法。でも最初の10年はenumなかった。
// ↓これを簡単に書けるようにようにした文法と言っても良い。
//class TicketType {
//    public static final TicketType ONE_DAY = new TicketType(7400);
//    public static final TicketType TWO_DAY = new TicketType(13200);
//    
//    public final int price;
//    private TicketType(int price) {
//        this.price = price;
//    }
//}
// ↑この説明のおかげで、いとりゅーさんの勘違いが発見できた (インスタンスは限定されている) (2025/09/12)
// enumは暗黙的にabstractなEnumクラスを継承している、だからこそ別のクラスを継承できない。

// done ito せっかくなのでjavadocを by jflute (2025/09/12)

import java.time.LocalTime;

/**
 * The type of ticket for entering the park.
 * <pre>
 * This enum defines the types of tickets available for purchase, along with their respective prices.
 * </pre>
 * @author ryunosuke.ito
 */
public enum TicketType {
    // done itoryu //技を使ってみてください by jflute (2025/10/28)
    ONE_DAY_PASSPORT(7400, 1, false, null), // 1日券
    TWO_DAY_PASSPORT(13200, 2, false, null), // 2日券
    FOUR_DAY_PASSPORT(22400, 4, false, null), // 4日券
    NIGHT_ONLY_TWO_DAY_PASSPORT(7400, 2, true, LocalTime.of(17, 0, 0)); // ナイト2日券

    // done ito publicでもfinalだからそこまで悪くはないけど... by jflute (2025/09/25)
    // 内部の変数のリファクタリングをしたくなったときに、しづらくなっちゃう可能性はある。
    // この場合のカプセル化は、enumの利用者が複数が想定されて、わりと広範囲になるかもしれないので。
    // かつ、入れ物クラスってわけじゃなく、ロジックが変化していきそうなもの。
    // (逆にpublicフィールドで割り切る例: LastaFluteのJSON対応クラスなど)
    private final int price;
    private final int days;
    private final boolean isNightOnlyTicket;
    private final LocalTime nightStartTime; // 17時以降

    TicketType(int price, int days, boolean isNightOnlyTicket, LocalTime nightStartTime) {
        this.price = price;
        this.days = days;
        this.isNightOnlyTicket = isNightOnlyTicket;
        this.nightStartTime = nightStartTime;
    }

    // ===================================================================================
    //                                                                              Getter
    //                                                                            ========
    public int getDays() {
        return days;
    }

    public int getPrice() {
        return price;
    }

    // done itoryu booleanはgetじゃなくisでやる慣習あり by jflute (2025/10/28)
    //  e.g. isNightOnlyTicket()
    public boolean isNightOnlyTicket() {
        return isNightOnlyTicket;
    }

    public LocalTime getNightStartTime() {
        return nightStartTime;
    }
}
