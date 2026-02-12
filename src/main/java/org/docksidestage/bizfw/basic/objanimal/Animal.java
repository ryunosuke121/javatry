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
package org.docksidestage.bizfw.basic.objanimal;

import org.docksidestage.bizfw.basic.objanimal.barking.BarkedSound;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkingProcess;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;

// #1on1: プログラミングデザインは、部屋の肩付け(模様替え)と同じ感覚 (2025/12/23)

// TODO itoryu javatryのjavadoc流に、authorの追加を。全体的にstep6のクラス見直してください by jflute (2026/01/27)

/**
 * The object for animal(動物).
 * @author jflute, ryunosuke.ito
 */
public abstract class Animal implements Loudable {
    // TODO done itoryu もうすでにunused by jflute (2026/01/27)

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected int hitPoint; // is HP

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Animal() {
        hitPoint = getInitialHitPoint();
    }

    protected int getInitialHitPoint() {
        return 10; // as default
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    public BarkedSound bark() {
        return createBarkingProcess().execute();
    }

    protected BarkingProcess createBarkingProcess() {
        // #1on1: コールバック少しだけstep8の先取り (2026/01/27)
        // 無名インナークラスから、Lambda式から、メソッド参照。
        // 無名のオブジェクトだからこそ隠すことができている。(限定的な仲介役を作ることができる)
        // #1on1: オブジェクト指向とコールバック方式のコラボ (2026/01/27)
        // (IntelliJで、downHitPoint補完したらメソッド参照になっただけだったので仕組みがわかってよかった by itoryuさん)
        // Lambda式はJava8から。(2015年あたり) → step8でじっくり予定。
        // メソッド参照のjfluteの好みの話。Scalaの省略すごい話と絡めて。
        // #1on1: Javaのvarのお話も (2026/01/27)
        return new BarkingProcess(this::downHitPoint, getBarkWord());
    }

    // #1on1: JDBCドライバーとは？ (2026/01/27)
    // RDBにアクセスするためのインターフェースとその実装ライブラリ。
    // O/RマッパーとJDBCドライバーの役割の違い。

    // done itoryu 修行++: 内部用メソッドがpublicにせざるをえなくなったけど... by jflute (2025/12/23)
    // なんとかして、protectedに戻しましょう。(カプセル化としてpublicは良いことではない)
    // (ここは...downHitPoint()とはレベルが全然違うシンプルな話)
    // #1on1: ColorBoxインターフェースところの話とつなげたのは素晴らしい (2026/01/13)
    // ただ、ここの修行は、半隠蔽ではなく、完全隠蔽で（＾＾／。
    // #1on1: こっちは参照のみなので、単なる「引数/戻り値デザイン」 (2026/01/27)
    protected abstract String getBarkWord();

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    // done itoryu 修行#: 同じように内部用メソッドがpublicにせざるをえなくなったけど... by jflute (2025/12/23)
    // なんとかして、protectedに戻しましょう。(こっちは内部を壊せるメソッドなので絶対にpublicにしたくない)
    // (ここは難しいので、stepを進めながら、思いついたときにやるような感じでOK)
    protected void downHitPoint() {
        --hitPoint;
        if (hitPoint <= 0) {
            throw new IllegalStateException("I'm very tired, so I want to sleep" + getBarkWord());
        }
    }

    // ===================================================================================
    //                                                                               Loud
    //                                                                              ======
    @Override
    public String soundLoudly() {
        return bark().getBarkWord();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getHitPoint() {
        return hitPoint;
    }
}
