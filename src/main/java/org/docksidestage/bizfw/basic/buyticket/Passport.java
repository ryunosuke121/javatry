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

// TODO ito せっかくなのでjavadocを by jflute (2025/09/12)
public enum Passport {
    ONE_DAY_PASSPORT(7400), TWO_DAY_PASSPORT(13200);

    public final int price;

    Passport(int price) {
        this.price = price;
    }
}
