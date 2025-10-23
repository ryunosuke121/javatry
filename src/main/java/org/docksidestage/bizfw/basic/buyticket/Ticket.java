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

import java.time.OffsetTime;

import org.docksidestage.bizfw.basic.common.TimeProvider;

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
    // TODO done itoryu 引数を変えたことで、step6のクラスで影響が出ているので、正しく動作するように(step6を)修正しましょう by jflute (2025/10/17)
    public Ticket(TimeProvider timeProvider, TicketType ticketType) {
        this.timeProvider = timeProvider;
        this.ticketType = ticketType;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    // #1on1: 入門ゲートという概念 by itoryuさん
    public void doInPark() {
        validateEntryConditions();
        ++entryCount;
    }

    // TODO done itoryu validよりはvalidateの方が直感的かなと (動詞始まり) by jflute (2025/10/17)
    private void validateEntryConditions() {
        if (entryCount >= ticketType.getDays()) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + getDisplayPrice());
        }
        // TODO done itoryu 他にもnightなチケット種別が増えた時、ここに条件を増やさないといけない by jflute (2025/10/17)
        if (ticketType.getIsNightOnlyTicket() && !isNightTime(timeProvider.now())) {
            throw new IllegalStateException("Night time only ticket");
        }
    }

    private boolean isNightTime(OffsetTime time) {
        // TODO done itoryu 17時の表現が2回登場して、かつ長いのでちょっとstatementの区切りがわかりづらいので... by jflute (2025/10/17)
        // IntelliJで変数抽出してみましょう。(control+Tからのメニュー)
        OffsetTime startTime = OffsetTime.of(17, 0, 0, 0, OffsetTime.now().getOffset());
        return time.isAfter(startTime) || time.equals(startTime);
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() {
        return ticketType.getPrice();
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    // #1on1: いったん、使い切ったかどうか？という解釈で。
    // alreadyIn という既存変数が、機能追加によって曖昧になるケース
    // #1on1: OSSのdeprecatedの運用話
    // isAlreadyIn()の名前を変えても問題はないでしょう。(renameの機能を使えば)
    public boolean isAlreadyIn() {
        return entryCount >= ticketType.getDays();
    }
}
