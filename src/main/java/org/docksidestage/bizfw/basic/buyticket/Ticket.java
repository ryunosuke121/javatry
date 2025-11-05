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

// done ito javadocにauthor追加お願いしますー by jflute (2025/09/25)

import java.time.LocalTime;
import java.time.OffsetTime;

import org.docksidestage.bizfw.basic.common.TimeProvider;

// #1on1: NxBatchRecorderのコードコメントを参考に一緒に読んでみた (2025/10/28)
// (あと、会社のコード、OSSの自分のコード、ケースによってコメントの書き方も変わる)

/**
 * @author jflute
 * @author ryunosuke.ito
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final TimeProvider timeProvider;
    private final TicketType ticketType;
    private int entryCount = 0;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    // done itoryu 引数を変えたことで、step6のクラスで影響が出ているので、正しく動作するように(step6を)修正しましょう by jflute (2025/10/17)
    /***
     * @param timeProvider 時間を管理するクラス。内部で現在時刻を取得するために使用される。 (例：入門ゲートでの入場時刻の検証)
     * @param ticketType チケットの種類。チケットの価格や有効日数、ナイトチケットかどうかの情報を持つ。
     */
    public Ticket(TimeProvider timeProvider, TicketType ticketType) {
        this.timeProvider = timeProvider;
        this.ticketType = ticketType;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    // #1on1: 入門ゲートという概念 by itoryuさん
    /**
     * 入場ゲートでの入場処理を行います。<br>
     * このメソッドは、入場可能かどうかを検証し、入場回数を更新します。<br>
     */
    public void doInPark() {
        validateEntryConditions();
        ++entryCount;
    }

    // done itoryu validよりはvalidateの方が直感的かなと (動詞始まり) by jflute (2025/10/17)
    private void validateEntryConditions() {
        if (entryCount >= ticketType.getDays()) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + getDisplayPrice());
        }
        // done itoryu 他にもnightなチケット種別が増えた時、ここに条件を増やさないといけない by jflute (2025/10/17)
        if (ticketType.isNightOnlyTicket() && !isNightTime(timeProvider.now())) {
            throw new IllegalStateException("Night time only ticket");
        }
    }

    private boolean isNightTime(OffsetTime time) {
        // TODO done itoryu チケット種別で、18時始まりのnightOnlyが追加されたらどうする？ by jflute (2025/10/28)
        // done itoryu 17時の表現が2回登場して、かつ長いのでちょっとstatementの区切りがわかりづらいので... by jflute (2025/10/17)
        // IntelliJで変数抽出してみましょう。(control+Tからのメニュー);
        LocalTime localTime = time.toLocalTime();
        return localTime.isAfter(this.ticketType.getNightStartTime()) || localTime.equals(this.ticketType.getNightStartTime());
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    // #1on1: getterに関しては、ほぼ直感的なメソッドになるので...jfluteだったら、説明部分だけを省略しちゃいます。
    // TODO done itoryu 説明部分は省略でOK by jflute (2025/10/28)
    /**
     * @return ディスプレイ用の価格
     */
    public int getDisplayPrice() {
        return ticketType.getPrice();
    }

    /**
     * チケットの種類を取得します。
     * @return チケットの種類
     */
    public TicketType getTicketType() {
        return ticketType;
    }

    // #1on1: いったん、使い切ったかどうか？という解釈で。
    // alreadyIn という既存変数が、機能追加によって曖昧になるケース
    // #1on1: OSSのdeprecatedの運用話
    // isAlreadyIn()の名前を変えても問題はないでしょう。(renameの機能を使えば)
    /**
     * チケットがすでに使用されているかどうかを判定します。
     * もし、チケットの入場回数が有効日数以上であれば、trueを返します。
     * @return チケットがすでに使用されている場合はtrue、そうでない場合はfalse
     */
    public boolean isAlreadyIn() {
        return entryCount >= ticketType.getDays();
    }
}
