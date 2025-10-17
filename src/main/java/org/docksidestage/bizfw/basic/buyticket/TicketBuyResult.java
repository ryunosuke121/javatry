package org.docksidestage.bizfw.basic.buyticket;

// done ito javadocお願いします by jflute (2025/09/25)

/**
 * @author ryunosuke.ito
 */
public class TicketBuyResult {
    private final Ticket ticket;
    private final int change;

    /**
     * @param ticket 購入したチケット
     * @param change お釣り
     */
    public TicketBuyResult(Ticket ticket, int change) {
        this.ticket = ticket;
        this.change = change;
    }

    /**
     * @return 購入したチケット
     */
    public Ticket getTicket() {
        return ticket;
    }

    // #1on1: 昔のシステムはローマ字使ってたところもある話 (2025/10/17)
    // 英語を使うようになったけど、あんまり馴染みのない単語を使うと結局つらいのでコメントで補足する。

    // #1on1: テストメソッド名で日本語を使うか？
    // o けっこう他の現場では日本語使ってるところ多い印象 by jflute
    // o mainコードはキーワード英語だけど、testメソッド名は自然言語表現が求められる
    // o 英語がそこまで得意じゃない日本語なチーム構成なら日本語の方が間違いは少ないだろう
    // o 英語の中に突然日本語が出てきて目立つという必殺技でもある
    // o 記憶としては2005年くらいからUnitTestのメソッド名は日本語で書こう習慣あった
    //   (業務の仕様書が英語じゃなく日本語なのであれば、テストの表現も日本語が相性が良い)
    //   (UnitTestが仕様書という感覚なので)
    /**
     * @return お釣り
     */
    public int getChange() {
        return change;
    }
}
