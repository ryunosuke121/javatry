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

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.common.SystemTimeProvider;
import org.docksidestage.bizfw.basic.objanimal.*;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkedSound;
import org.docksidestage.bizfw.basic.objanimal.climb.Climbable;
import org.docksidestage.bizfw.basic.objanimal.loud.AlarmClock;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;
import org.docksidestage.bizfw.basic.objanimal.runner.FastRunner;
import org.docksidestage.javatry.basic.st6.dbms.St6MySql;
import org.docksidestage.javatry.basic.st6.dbms.St6PostgreSql;
import org.docksidestage.javatry.basic.st6.os.St6MacOS;
import org.docksidestage.javatry.basic.st6.os.St6OldWindows;
import org.docksidestage.javatry.basic.st6.os.St6OperationSystem;
import org.docksidestage.javatry.basic.st6.os.St6Windows;
import org.docksidestage.unit.PlainTestCase;

// done itoryu [読み物課題] こうはい extends せんぱい by jflute (2025/12/23)
// https://jflute.hatenadiary.jp/entry/20131124/extendsmaster

/**
 * The test of object-oriented. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author ryunosuke.ito
 */
public class Step06ObjectOrientedTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        About Object
    //                                                                        ============
    // -----------------------------------------------------
    //                                        Against Object
    //                                        --------------
    /**
     * Fix several mistakes (except simulation) in buying one-day passport and in-park process. <br>
     * (OneDayPassportを買って InPark する処理の中で、(simulationを除いて)間違いがいくつかあるので修正しましょう)
     */
    public void test_objectOriented_aboutObject_againstObject() {
        //
        // [ticket booth info]
        //
        // simulation: actually these variables should be more wide scope
        int oneDayPrice = 7400;
        int quantity = 10;
        Integer salesProceeds = 0;

        //
        // [buy one-day passport]
        //
        // simulation: actually this money should be from customer
        int handedMoney = 10000;
        if (quantity <= 0) {
            throw new IllegalStateException("Sold out");
        }
        if (handedMoney < oneDayPrice) {
            throw new IllegalStateException("Short money: handedMoney=" + handedMoney);
        }
        --quantity;
        salesProceeds += oneDayPrice;

        //
        // [ticket info]
        //
        // simulation: actually these variables should be more wide scope
        int displayPrice = oneDayPrice;
        boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        // done itoryu ここから先、3つ間違いがある by jflute (2025/11/25)
        // #1on1: その場で見つけてもらった、例外メッセージの中 (2025/12/08)
        // ここではTicketに関する処理なので、TicketBoothの情報ではなく、TicketのdisplayPrice
        // メッセージのUnitTestをする現場も少ないので、こういうのは本番まで残りやすい。
        //
        // [do in park now!!!]
        //
        // simulation: actually this process should be called by other trigger
        if (alreadyIn) {
            throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice);
        }
        alreadyIn = true;

        //
        // [final process]
        //
        saveBuyingHistory(quantity, salesProceeds, displayPrice, alreadyIn);
    }

    // #1on1: int, int, int...問題 (2025/11/25)
    //
    // まず作り手として、極力こういうの作らないようにする意識。
    // オブジェクトを活用、Value Objectを活用。(より構造化することでint,intを無くす)
    // 小テクニックとして、booleanを真ん中に混ぜるなどなど...色々工夫
    //
    // 一方で、呼び出し側としては...
    // 単純には、指差し確認をする。
    // ただ、全部で指差し確認をするかと言ったら？さすがに時間が掛かるのでしない。
    // じゃあ、指差し確認をするところ、しないところってどう線引されるのか？
    // 「saveBuyingHistory()のところだけ指差し確認をする」ってできるか？
    // 同じ型が並んでると怖いって感じがする by いとりゅうさん
    // 怖いという感情がとても大切 by jflute
    //
    // 経験から、色々なバグを見てきて、こういうところにバグがよく潜んでる、ってのを知ってる。
    // 同じ体験しても、なぜバグったのか？バグった構造とかを気にしたかどうか？その積み重ねをしてきたかどうか？
    // それによって個人差が生まれる。「なので、振り返りが大事」 by いとりゅうさん
    // 自分がよく間違うポイントっていうのも思い出を積み重ねているかどうか？
    //
    // ものづくりスキル。
    private void saveBuyingHistory(int quantity, Integer salesProceeds, int displayPrice, boolean alreadyIn) {
        if (alreadyIn) {
            // simulation: only logging here (normally e.g. DB insert)
            // #1on1: その場で見つけてもらった。引数の順序とか、int間違い、やっぱり怖い (2025/12/08)
            showTicketBooth(quantity, salesProceeds);
            showYourTicket(displayPrice, alreadyIn);
        }
    }

    private void showTicketBooth(int quantity, Integer salesProceeds) {
        log("Ticket Booth: quantity={}, salesProceeds={}", quantity, salesProceeds);
    }

    private void showYourTicket(int displayPrice, boolean alreadyIn) {
        log("Ticket: displayPrice={}, alreadyIn={}", displayPrice, alreadyIn);
    }

    // -----------------------------------------------------
    //                                          Using Object
    //                                          ------------
    /**
     * Read (analyze) this code and compare with the previous test method, and think "what is object?". <br>
     * (このコードを読んで(分析して)、一つ前のテストメソッドと比べて、「オブジェクトとは何か？」を考えてみましょう)
     */
    public void test_objectOriented_aboutObject_usingObject() {
        //
        // [ticket booth info]
        //
        TicketBooth booth = new TicketBooth(new SystemTimeProvider());

        // *booth has these properties:
        //int oneDayPrice = 7400;
        //int quantity = 10;
        //Integer salesProceeds = null;

        //
        // [buy one-day passport]
        //
        // if step05 has been finished, you can use this code by jflute (2019/06/15)
        Ticket ticket = booth.buyOneDayPassport(10000);
        //        booth.buyOneDayPassport(10000); // as temporary, remove if you finished step05
        //        Ticket ticket = new Ticket(new SystemTimeProvider(), TicketType.ONE_DAY_PASSPORT); // also here

        // *buyOneDayPassport() has this process:
        //        if (quantity <= 0) {
        //            throw new TicketSoldOutException("Sold out");
        //        }
        //        if (handedMoney < oneDayPrice) {
        //            throw new TicketShortMoneyException("Short money: handedMoney=" + handedMoney);
        //        }
        //        --quantity;
        //        salesProceeds = handedMoney;

        // *ticket has these properties:
        //int displayPrice = oneDayPrice;
        //boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        ticket.doInPark();

        // *doInPark() has this process:
        //if (alreadyIn) {
        //    throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice);
        //}
        //alreadyIn = true;

        //
        // [final process]
        //
        saveBuyingHistory(booth, ticket);
    }

    private void saveBuyingHistory(TicketBooth booth, Ticket ticket) {
        if (ticket.isAlreadyIn()) {
            // only logging here (normally e.g. DB insert)
            doShowTicketBooth(booth);
            doShowYourTicket(ticket);
        }
    }

    private void doShowTicketBooth(TicketBooth booth) {
        log("Ticket Booth: quantity={}, salesProceeds={}", booth.getOneDayPassportQuantity(), booth.getSalesProceeds());
    }

    private void doShowYourTicket(Ticket ticket) {
        log("Your Ticket: displayPrice={}, alreadyIn={}", ticket.getDisplayPrice(), ticket.isAlreadyIn());
    }

    // write your memo here:
    // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    // what is object?
    // 現実世界のモノや概念をプログラム上でクラスとして表現したもの。
    // 振る舞いをメソッドやプロパティとして持たせることで、責務範囲を明確にし、コードの可読性や変更容易性を高める手法。
    // オブジェクトにロジックをまとめていると、機能が追加されたり変更されたりした場合でも
    // ロジックが散らばっていないので、影響範囲を限定でき、保守性が向上する。
    // また、現実世界に照らし合わせるという基準があることで、開発者にとって理解しやすくなる。
    // _/_/_/_/_/_/_/_/_/_/
    // #1on1: 責務範囲という言葉Good。何がオブジェクトなのか？何がオブジェクトの粒度なのか？ (2025/11/25)
    // step5,step6の流れのコンセプトの話。

    // #1on1: 付け焼き刃で覚えたものを中長期の記憶にするために振り返り話 (2025/11/25)
    // 覚えたことのセオリーを明確にすることで覚えやすくなる。
    // (あと、記憶の探索の話)

    // #1on1: 正しい構造で装飾されたもの、間違った構造で装飾されたもの、は大違い (2025/11/25)
    // 「まだベタ書きの方がマシ」って言われないように。

    // #1on1: javatryのオブジェクト指向のエクササイズのコンセプト (2025/11/25)
    // シンプルな構造で読むトレーニング、体験。それが業務やライブラリのコードを読む第一歩。

    // ===================================================================================
    //                                                              Polymorphism Beginning
    //                                                              ======================
    /**
     * What string is sea and land variable at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_objectOriented_polymorphism_1st_concreteOnly() {
        Dog dog = new Dog();
        BarkedSound sound = dog.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = dog.getHitPoint();
        log(land); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_2nd_asAbstract() {
        Animal animal = new Dog();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_3rd_fromMethod() {
        Animal animal = createAnyAnimal();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }

    private Animal createAnyAnimal() {
        return new Dog();
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_4th_toMethod() {
        Dog dog = new Dog();
        doAnimalSeaLand_for_4th(dog);
    }

    private void doAnimalSeaLand_for_4th(Animal animal) {
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_5th_overrideWithSuper() {
        Animal animal = new Cat();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => nya-
        int land = animal.getHitPoint();
        log(land); // your answer? => 5
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_6th_overriddenWithoutSuper() {
        Animal animal = new Zombie();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => uooo
        int land = animal.getHitPoint();
        log(land); // your answer? => -1
    }

    /**
     * What is happy if you can assign Dog or Cat instance to Animal variable? <br>
     * (Animal型の変数に、DogやCatなどのインスタンスを代入できると何が嬉しいのでしょう？)
     */
    public void test_objectOriented_polymorphism_7th_whatishappy() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is happy?
        // 共通の振る舞いを抽象化できるため、コードの再利用性が向上する。
        // 共通して変更が必要な場合、一箇所の修正で済むため、保守性が高まる。
        // 異なる具体的なクラスに対しても同じインターフェースで操作できるため、柔軟性が増す。
        // _/_/_/_/_/_/_/_/_/_/
        // #1on1: 要点まとまってて良い。多態性、多相性の特徴が表現されている。 (2025/12/08)
        // 日常でもポリモーフィズムを使って便利に会話してる話。
    }

    // ===================================================================================
    //                                                              Polymorphism Interface
    //                                                              ======================
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_dispatch() {
        Loudable loudable = new Zombie();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => uooo
        String land = ((Zombie) loudable).bark().getBarkWord();
        log(land); // your answer? => uooo
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_hierarchy() {
        Loudable loudable = new AlarmClock();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => jiri jiri jiri---
        boolean land = loudable instanceof Animal;
        log(land); // your answer? => false
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_partImpl() {
        Animal seaAnimal = new Cat();
        Animal landAnimal = new Zombie();
        boolean sea = seaAnimal instanceof FastRunner;
        log(sea); // your answer? => true
        boolean land = landAnimal instanceof FastRunner;
        log(land); // your answer? => false
    }

    /**
     * Make Dog class implement FastRunner interface. (the method implementation is same as Cat class) <br>
     * (DogもFastRunnerインターフェースをimplementsしてみましょう (メソッドの実装はCatと同じで))
     */
    public void test_objectOriented_polymorphism_interface_runnerImpl() {
        // your confirmation code here
        Animal dog = new Dog();
        boolean isImplemented = dog instanceof FastRunner;
        log(isImplemented); // should be true
    }

    /**
     * What is difference as concept between abstract class and interface? <br>
     * (抽象クラスとインターフェースの概念的な違いはなんでしょう？)
     */
    public void test_objectOriented_polymorphism_interface_whatisdifference() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is difference?
        // 抽象クラスはサブクラスの共通の基盤を提供するようなもので、犬は動物の一種であるというような所属関係を持つ。
        // インターフェイスは振る舞いを定義するもので、猫やアラーム時計が音を出すという振る舞いを共通化するが、
        // そこに所属関係はない。
        // _/_/_/_/_/_/_/_/_/_/
        // #1on1: is-aの関係と、can-doの関係 (2025/12/08)
        //
        // コンセプトが違う:
        // o オブジェクト指向は、オブジェクト(概念)を中心に整理整頓する考え方
        // o インターフェースは、操作を中心に整理整頓する考え方 (呼び手が大事、AがBを呼ぶの関係性が中心)
        //
        // 似てる部分がある:
        // o オブジェクト指向: ポリモーフィズム、具象クラスの形付け(実装継承)
        // o インターフェース: ポリモーフィズム (のみ、基本的には)
        //
        // なぜインターフェース (最初から) 導入された？
        // パット見、オブジェクト指向が機能を包含しているように見える。
        // もちろん、コンセプトが違うよってのはあるけど、それだけで包含される機能を導入するか？
        //
        // Javaは多重継承ができない。(あえて導入しなかった)
        // 昔、C++にて多重継承ができると、なかなかカオスになった話。
        // Javaのオブジェクト指向は機能制限が掛かっている。
        //
        // そこで、インターフェースくん。こっちは複数定義(複数implements)できる。
        // あれ？したら、多重継承と同じカオスなっちゃわない？
        // インターフェースは、多重ポリモーフィズムはできるけど、多重実装継承はできない。
        // で、カオスに原因は、多重ポリモーフィズムではなく、多重実装継承の方なので、
        // インターフェースなら、複数定義してもそこまで大変ではないだろうという考え方。
        // 多重実装継承できなくて実装が重複してしまいそうなところは、継承じゃなくて委譲で再利用。
        //

        // done jflute 次回1on1, 別のinterfaceの使われ方 (2025/12/08)
        // #1on1:
        // public abstract class AbstractColorBox implements ColorBox {
        // ListとArrayListも同じ構造。
        // メリット:
        // o 呼べるメソッドが見やすい (公開publicメソッド一覧)
        // o 内部用publicメソッドを半隠蔽できる
        // public void initialize() { // 内部用publicメソッド...なのに外部から呼べちゃう
        //    // ここでDB接続の初期化を行うなど
        //    addSpace(new BoxSpace(spaceSize));
        //    addSpace(new BoxSpace(spaceSize));
        //}
        // o ラッパークラスとかダミークラスとか、実装継承したくない具象クラスが作れる
        //
        // ポリモーフィズムと実装継承の機能の「二つがある」というのは「混在している」とも言える。
        // 外交はインターフェースに任せて、内政は抽象クラスがやる。
        // 一方で、抽象クラスもポリモーフィズム使ってないわけではなく、
        // 内部用publicメソッドを呼ぶ時は、抽象クラスによるポリモーフィズムになる。

        // #1on1: インターフェースのdefaultメソッドの話 (2026/01/13)
        // 実装継承やり過ぎると多重継承カオス、でもreturn false;とかのシンプルは便利。

        // #1on1: enumのインターフェースの話 (2026/01/13)
        // DBFluteのCDef.javaを例に。
        // LastaFluteのappclsで要素定義を再利用する話。
        // 現場でのレイヤーごとの同じ概念の区分値の要素構成の違いの吸収方法について。

        // #1on1: dfpropのファイル形式の話 (2026/01/13)
        // JSONが世の中で一般的でない時代に作ったもの。
    }

    // ===================================================================================
    //                                                                 Polymorphism Making
    //                                                                 ===================
    /**
     * Make concrete class of Animal, which is not FastRunner, in "objanimal" package. (implementation is as you like) <br>
     * (FastRunnerではないAnimalクラスのコンクリートクラスをobjanimalパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeConcrete() {
        // your confirmation code here
        Animal animal = new Lion();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => gaoo
        int land = animal.getHitPoint();
        log(land); // 6
    }

    /**
     * Make interface implemented by part of Animal concrete class in new package under "objanimal" package. (implementation is as you like) <br>
     * (Animalクラスの一部のコンクリートクラスだけがimplementsするインターフェースをobjanimal配下の新しいパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeInterface() {
        // your confirmation code here
        Animal animal = new Cat();
        if (animal instanceof Climbable) {
            ((Climbable) animal).climb();
        }

        animal = new Dog();
        if (animal instanceof Climbable) {
            ((Climbable) animal).climb();
        } else {
            log("not climbable");
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Extract St6MySql, St6PostgreSql (basic.st6.dbms)'s process to abstract class (as super class and sub-class) <br>
     * (St6MySql, St6PostgreSql (basic.st6.dbms) から抽象クラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_generalization_extractToAbstract() {
        // your confirmation code here
        St6MySql mySql = new St6MySql();
        log(mySql.buildPagingQuery(10, 20));

        St6PostgreSql postgreSql = new St6PostgreSql();
        log(postgreSql.buildPagingQuery(10, 20));
    }

    /**
     * Extract St6OperationSystem (basic.st6.os)'s process to concrete classes (as super class and sub-class) <br>
     * (St6OperationSystem (basic.st6.os) からコンクリートクラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_specialization_extractToConcrete() {
        // your confirmation code here
        String loginId = "itoryu";
        String relativePath = "document/memo.txt";
        St6OperationSystem os = new St6MacOS(loginId);
        log(os.buildUserResourcePath(relativePath));
        os = new St6Windows(loginId);
        log(os.buildUserResourcePath(relativePath));
        os = new St6OldWindows(loginId);
        log(os.buildUserResourcePath(relativePath));
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Extract Animal's bark() process as BarkingProcess class to also avoid large abstract class. <br>
     * (抽象クラス肥大化を抑制するためにも、Animalのbark()のプロセス(処理)をBarkingProcessクラスとして切り出しましょう)
     */
    public void test_objectOriented_writing_withDelegation() {
        // your confirmation code here
        Animal animal = new Zombie();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => uooo
        int land = animal.getHitPoint();
        log(land); // your answer? => -1
    }

    /**
     * Put barking-related classes, such as BarkingProcess and BarkedSound, into sub-package. <br>
     * (BarkingProcessやBarkedSoundなど、barking関連のクラスをサブパッケージにまとめましょう)
     * <pre>
     * e.g.
     *  objanimal
     *   |-barking
     *   |  |-BarkedSound.java
     *   |  |-BarkingProcess.java
     *   |-loud
     *   |-runner
     *   |-Animal.java
     *   |-Cat.java
     *   |-Dog.java
     *   |-...
     * </pre>
     */
    public void test_objectOriented_writing_withPackageRefactoring() {
        // your confirmation code here
        Animal animal = new Zombie();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => uooo
    }

    /**
     * Is Zombie correct as sub-class of Animal? Analyze it in thirty seconds. (thinking only) <br>
     * (ゾンビは動物クラスのサブクラスとして適切でしょうか？30秒だけ考えてみましょう (考えるだけでOK))
     */
    public void test_objectOriented_zoo() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // is it corrent?
        // 独自のプロパティを持っているせいで
        // なんか変なコードになってしまった。なのでもしかしたらサブクラスとして不適切なのかもしれない。
        // でも継承先が独自のプロパティを持つべきではないっていうのはやり過ぎ感あるので、結局よくわからない。
        // _/_/_/_/_/_/_/_/_/_/
        // TODO jflute 次回1on1でふぉろー (2026/01/27)
    }
}
