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

import java.math.BigDecimal;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of variable. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author ryunosuke.ito
 */
public class Step01VariableTest extends PlainTestCase {

    // ===================================================================================
    //                                                                      Local Variable
    //                                                                      ==============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_variable_basic() { // example, so begin from the next method
        String sea = "mystic";
        log(sea); // your answer? => mystic
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_initial() {
        String sea = "mystic";
        Integer land = 8;
        String piari = null;
        String dstore = "mai";
        sea = sea + land + piari + ":" + dstore;
        log(sea); // your answer? => mystic8:mai
        // mystic8null:maiだった。nullって文字列変換すると"null"になるのか。
        // ここですね
        // public static String valueOf(Object obj) {
        //        return (obj == null) ? "null" : obj.toString();
        // }
        // done ito [いいね] すごっ！そこまで追ってるの素晴らしい！ by jflute (2025/07/15)
        // 昔のインターネット画面とかよく「こんにちは nullさん」とか出てました笑
        // 最近でもメールで null って見たりしましたね。
        // 一方で、ログに出すときとかは何も出ないよりはnullってわかりやすい面も。
        // 些細な違いですが、言語によって細かい挙動が違ったりします。(C#は空文字ですね)
        
        // #1on1: 言語の設計思想の話。ちなみに、C#は、空文字。
        // #1on1: メールテンプレートの管理のジレンマ話、メール開発のジレンマ
        // 削除できないので実は慎重に実装しないといけないものでもある。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_basic() {
        String sea = "mystic";
        String land = "oneman";
        sea = land;
        land = land + "'s dreams";
        log(sea); // your answer? => oneman
        // なんでこうなるかよく知らなかったので調べた
        // Stringは不変(immutable)でland = land + "'s dreams";の時にlandは新しいStringを指すようになる。
        // なので、seaはlandの新しい値を指していないのでonemanのまま。
        // done ito [いいね] なんでこうなるか？を調べる姿勢がとても素晴らしいです by jflute (2025/07/15)
        // 変数に対して = で代入したときは、新しい参照 (land と "'s dreams" をくっつけたもの) に差し替えてるわけですね。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_BigDecimal() {
        BigDecimal sea = new BigDecimal(94);
        BigDecimal land = new BigDecimal(415);
        sea = land;
        sea = land.add(new BigDecimal(1));
        sea.add(new BigDecimal(1));
        log(sea); // your answer? => 416
        // addは新しいBigDecimalを返すだけで呼び出し元のBigDecimalは変わらない。
        // なので、seaはlandの新しい値を指していないので416のまま。
        // done ito [いいね] add()の挙動をよく理解できていますね！ by jflute (2025/07/15)
        // done ito [お試し課題] IDE上で BigDecimal (変数の型宣言の方) にカーソルを当ててみてください by jflute (2025/07/15)
        // クラスのJavaDocが表示されるかと思います。そこに一言目に immutable って書いてあって、
        // BigDecimal も何やっても一度作ったインスタンスそのものは変わらないってことがわかります。
        // #1on1: やってもらって、immutableという文言を見た。
        // #1on1: add()のソースコードリーディング
        // #1on1: staticメソッドの話、オーバーロードメソッドの話
        // #1on1: インスタンスメソッドのクラスメソッド(staticメソッド)
        // #1on1: immutableが何が嬉しいのか？
        // 状態を追うのややこしいから？ by itoさん
        // Good, つまり人都合: 可読性、安全性
        // 中間成果物インスタンスの話 => スペックの向上で気にしなくなった
        // immutableの歴史の話
        // immutableのバランスの話
        // Scala/Kotlinのお話、JVM言語
        // 個人的には、8:2な感覚 => できるだけimmutableだけど、無理しない
        // (組織、個人、チームによって、ちょっと方向性変わるかも)

        // done jflute 1on1にて、もうちょい深堀りしていく予定 (2025/07/15)
        // (↑これはくぼ用のとぅどぅということでそのまま残しておいてください)
        
        // TODO jflute 1on1にて、IntelliJでcontrol+Jの話をするの忘れた (2025/07/18)
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private String instanceBroadway;
    private int instanceDockside;
    private Integer instanceHangar;
    private String instanceMagiclamp;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_String() {
        String sea = instanceBroadway;
        log(sea); // your answer? => null
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_int() {
        int sea = instanceDockside;
        log(sea); // your answer? => 0
        // C言語だとゴミ値が入るけど、javaはちゃんと0で初期化されるんだなあ
        // done ito [ざつだん] ああ、そんなことあったような笑 by jflute (2025/07/15)
        // #1on1 研修でのC言語のお話
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_Integer() {
        Integer sea = instanceHangar;
        log(sea); // your answer? => null
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_via_method() {
        instanceBroadway = "bbb";
        instanceMagiclamp = "magician";
        helpInstanceVariableViaMethod(instanceMagiclamp);
        String sea = instanceBroadway + "|" + instanceDockside + "|" + instanceHangar + "|" + instanceMagiclamp;
        log(sea); // your answer? => bigband|1|null|burn
        // やられた。bigband|1|null|magician
        // 引数に渡したインスタンス変数はコピーされているので、メソッド内で変更しても元のインスタンス変数は変わらないのか
        // intellijがちゃんと教えてくれていた
        // done ito [ふぉろー] そうですね、インスタンス変数(instanceMagiclamp)自体がhelpメソッドに行くわけじゃなく... by jflute (2025/07/15)
        // インスタンス変数が参照しているインスタンス (要は変数の中身) がhelpメソッドに行って、
        // help側は引数と呼ばれる別の変数でそれを受け取るわけですね。(厳密には、アドレスを単に渡しているだけ)
        //
        // intellijがどう教えてくれたのか気になるので1on1のとき教えてください(^^
        // #1on1: ハイライトで教えてくれる、ぜひIDEのそういった表現、着目してください。

        // done jflute 1on1にて、ちょこっと復習予定 (2025/07/15)
    }

    private void helpInstanceVariableViaMethod(String instanceMagiclamp) {
        instanceBroadway = "bigband";
        ++instanceDockside;
        instanceMagiclamp = "burn";
    }

    // ===================================================================================
    //                                                                     Method Argument
    //                                                                     ===============
    // -----------------------------------------------------
    //                                 Immutable Method-call
    //                                 ---------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_immutable_methodcall() {
        String sea = "harbor";
        int land = 415;
        helpMethodArgumentImmutableMethodcall(sea, land);
        log(sea); // your answer? => harbor
        // もう騙されない
        // done ito [いいね] はっはっは by jflute (2025/07/15)
    }

    private void helpMethodArgumentImmutableMethodcall(String sea, int land) {
        ++land;
        String landStr = String.valueOf(land); // is "416"
        sea.concat(landStr);
    }

    // -----------------------------------------------------
    //                                   Mutable Method-call
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_mutable_methodcall() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentMethodcall(sea, land);
        log(sea); // your answer? => harbor416
        // StringBuilderのこと知らなかったけど、メタ的にこうなるよなぁで回答した
        // ちゃんと調べた
        // StringBuilderは可変なクラスで直接変更できる
        // メモリが再確保されないから繰り返し文字操作するときに高速
        // はじめに確保したstringのキャパシティを超えたら自動で再確保してくれるらしい
        // TODO ito [いいね] StringBuilderの中身まで追ってるの素晴らしいです。 by jflute (2025/07/15)
        // 1on1でちょっと一緒にコード読んでみましょう(^^
        
        // TODO jflute 1on1にて、StringBuilderのコードリーディング (2025/07/15)
    }

    private void helpMethodArgumentMethodcall(StringBuilder sea, int land) {
        ++land;
        sea.append(land);
    }

    // -----------------------------------------------------
    //                                   Variable Assignment
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_variable_assignment() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentVariable(sea, land);
        log(sea); // your answer? => harbor
        // intellijがヒントをくれてしまう
        // done ito [かんそう] なるほどー（＞＜。ちょっと問題のやり方を調整しないとだなぁ... by jflute (2025/07/15)
    }

    private void helpMethodArgumentVariable(StringBuilder sea, int land) {
        ++land;
        String seaStr = sea.toString(); // is "harbor"
        sea = new StringBuilder(seaStr).append(land);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Define variables as followings:
     * <pre>
     * o local variable named sea typed String, initial value is "mystic"
     * o local variable named land typed Integer, initial value is null
     * o instance variable named piari typed int, without initial value
     * o show all variables by log() as comma-separated
     * </pre>
     * (変数を以下のように定義しましょう):
     * <pre>
     * o ローカル変数、名前はsea, 型はString, 初期値は "mystic"
     * o ローカル変数、名前はland, 型はInteger, 初期値は null
     * o インスタンス変数、名前はpiari, 型はint, 初期値なし
     * o すべての変数をlog()でカンマ区切りの文字列で表示
     * </pre>
     */
    private int piari;

    public void test_variable_writing() {
        // define variables here
        String sea = "mystic";
        Integer land = null;
        log(sea + "," + land + "," + piari);
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Make your original exercise as question style about variable. <br>
     * (変数についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * ログに出力される文字列は？
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_variable_yourExercise() {
        int number = 10;
        String text = "hello";
        StringBuilder builder = new StringBuilder("hello world");

        text = text + number++;

        log(number + ", " + text + ", " + builder.append(++number).toString());
        // TODO ito やってみた... your answer => "11, hello10, hello world12" by jflute (2025/07/15)
        // さあどうだ...実行！
        // ふぉー、合ってた良かった。ドキドキした。。。
        // インクリメントの演算子の優先順位が遅いというのは昔からJavaで有名な話で...
        // text + number++; は、textと+されて文字列側の話は解決してからインクリメントされるんですよね。
        //
        // なので、けっこう後ろのインクリメントは毛嫌いされて、前のインクリメントを使う人が多いです。
        // 別に一行単発処理であれば、どっちでも全然大丈夫なんですけど、「何が大丈夫で何が大丈夫じゃないか？」
        // ってわすれるので、とにかく前に付けておけば問題ない、みたいな感覚で。
        // 後さらにぼくは、インクリメントは一行単発処理でしか使わないようにしてますね。
        // e.g.
        //  ++number;
    }
}
