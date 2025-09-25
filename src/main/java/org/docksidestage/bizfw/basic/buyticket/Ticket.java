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

// TODO ito javadocにauthor追加お願いしますー by jflute (2025/09/25)
/**
 * @author jflute
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final TicketType ticketType;
    private int entryCount = 0;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Ticket(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void doInPark() {
        if (entryCount >= ticketType.days) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + getDisplayPrice());
        }
        ++entryCount;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() {
        return ticketType.price;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    // #1on1: いったん、使い切ったかどうか？という解釈で。
    // alreadyIn という既存変数が、機能追加によって曖昧になるケース
    // #1on1: OSSのdeprecatedの運用話
    // isAlreadyIn()の名前を変えても問題はないでしょう。(renameの機能を使えば)
    public boolean isAlreadyIn() {
        return entryCount >= ticketType.days;
    }
}
