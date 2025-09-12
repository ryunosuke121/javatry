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

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of method. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author ryunosuke.ito
 */
public class Step04MethodTest extends PlainTestCase {

    // ===================================================================================
    //                                                                         Method Call
    //                                                                         ===========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_method_call_basic() {
        String sea = supplySomething();
        log(sea); // your answer? => over
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_call_many() {
        String sea = functionSomething("mystic");
        consumeSomething(supplySomething());
        runnableSomething();
        log(sea); // your answer? => mysmys
    }

    private String functionSomething(String name) {
        String replaced = name.replace("tic", "mys");
        log("in function: {}", replaced);
        return replaced;
    }

    private String supplySomething() {
        String sea = "over";
        log("in supply: {}", sea);
        return sea;
    }

    private void consumeSomething(String sea) {
        log("in consume: {}", sea.replace("over", "mystic"));
    }

    private void runnableSomething() {
        String sea = "outofshadow";
        log("in runnable: {}", sea);
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_object() {
        St4MutableStage mutable = new St4MutableStage();
        int sea = 904;
        boolean land = false;
        helloMutable(sea - 4, land, mutable);
        if (!land) {
            sea = sea + mutable.getStageName().length();
        }
        log(sea); // your answer? => 910
    }

    private int helloMutable(int sea, Boolean land, St4MutableStage piari) {
        sea++;
        land = true;
        piari.setStageName("mystic");
        return sea;
    }

    private static class St4MutableStage {

        private String stageName;

        public String getStageName() {
            return stageName;
        }

        public void setStageName(String stageName) {
            this.stageName = stageName;
        }
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private int inParkCount;
    private boolean hasAnnualPassport;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_instanceVariable() {
        hasAnnualPassport = true;
        int sea = inParkCount;
        offAnnualPassport(hasAnnualPassport);
        for (int i = 0; i < 100; i++) {
            goToPark();
        }
        ++sea;
        sea = inParkCount;
        log(sea); // your answer? => 100
    }

    private void offAnnualPassport(boolean hasAnnualPassport) {
        hasAnnualPassport = false;
    }

    private void goToPark() {
        if (hasAnnualPassport) {
            ++inParkCount;
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    // write instance variables here
    private boolean availableLogging = true;
    /**
     * Make private methods as followings, and comment out caller program in test method:
     * <pre>
     * o replaceAwithB(): has one argument as String, returns argument replaced "A" with "B" as String 
     * o replaceCwithB(): has one argument as String, returns argument replaced "C" with "B" as String 
     * o quote(): has two arguments as String, returns first argument quoted by second argument (quotation) 
     * o isAvailableLogging(): no argument, returns private instance variable "availableLogging" initialized as true (also make it separately)  
     * o showSea(): has one argument as String argument, no return, show argument by log()
     * </pre>
     * (privateメソッドを以下のように定義して、テストメソッド内の呼び出しプログラムをコメントアウトしましょう):
     * <pre>
     * o replaceAwithB(): 一つのString引数、引数の "A" を "B" に置き換えたStringを戻す 
     * o replaceCwithB(): 一つのString引数、引数の "C" を "B" に置き換えたStringを戻す 
     * o quote(): 二つのString引数、第一引数を第二引数(引用符)で囲ったものを戻す 
     * o isAvailableLogging(): 引数なし、privateのインスタンス変数 "availableLogging" (初期値:true) を戻す (それも別途作る)  
     * o showSea(): 一つのString引数、戻り値なし、引数をlog()で表示する
     * </pre>
     */
    public void test_method_making() {
        // use after making these methods
        String replaced = replaceCwithB(replaceAwithB("ABC"));
        String sea = quote(replaced, "'");
        if (isAvailableLogging()) {
            showSea(sea);
        }
    }

    // done ito [いいね] privateメソッドの定義順序が呼び出す順でわかりやすい by jflute (2025/08/28)
    // #1on1: よくあるパターンとしては、ただ時系列に一番下に追加するのが多い
    // まあクラスが小さくてカテゴライズも存在しないとかであればまだしも...
    // そうでなくてもただ一番下にってことも多い。おじゃまします感!?
    // でも、追加修正する人が、クラスの全体バランスを配慮する責任が(均等に)あると思って欲しい。
    // @authorを付けてもらうっていうのも、その(小さな)配慮の一つ話。

    // #1on1: 既存をちょいリファクタリングしたくなったとき、ブランチどうする問題？ by itoさん
    // o チームでの決め事に従う、決め事がなければ決める
    //  i がんがんチケットブランチに含めるパターン
    //  i きっちり別のブランチに分けるパターン (別タスク: Issue登録？)
    //     → 優先度が低くなって年単位で放置されやすい
    //     → 別タスクだけど続けてすぐやるくらいじゃないとなかなか成立しない
    //     → チームによっては週1でリファクタリングタイムを設けてるところも(理想的)
    //  i よほど差分が見にくくなければある程度OKパターン
    //  i 決め事もなければ決めようともしないチームパターン (人に依る)
    //     → 人に依る: まあ多くの人が怖くてスルーする
    // (色々とお話しました)

    // done ito [読み物課題] リファクタリングという行為が好きか？ by jflute (2025/08/28)
    // https://jflute.hatenadiary.jp/entry/20220328/loverefactor
    // 読みました！多分自分は好きではない方かなと思いました笑
    // でも多分片付け嫌いだからというよりも、アーキテクチャやコードの可読性の話がシンプルに知識不足で苦手っていう理由な感覚があります
    // done ito [ふぉろー] そうですね、セオリーが身についてくると楽しくなってくるというのはあると思います！ by jflute (2025/09/11)
    // ぜひ、javatryでそのへんにフォーカス当てていきましょう！(^^そういうつもりでフォローしてきますね。

    // write methods here
    private String replaceAwithB(String str) {
        return str.replace("A", "B");
    }

    private boolean isAvailableLogging() {
        return availableLogging;
    }

    private void showSea(String sea) {
        log(sea);
    }

    private String replaceCwithB(String str) {
        return str.replace("C", "B");
    }

    // done ito [いいね] quotationという引数名がGood by jflute (2025/08/28)
    private String quote(String str, String quotation) {
        return quotation + str + quotation;
    }
}
