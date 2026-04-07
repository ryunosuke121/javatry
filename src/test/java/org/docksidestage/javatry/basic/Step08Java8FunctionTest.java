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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.docksidestage.javatry.basic.st8.St8DbFacade;
import org.docksidestage.javatry.basic.st8.St8Member;
import org.docksidestage.javatry.basic.st8.St8Withdrawal;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Java8 functions. <br>
 * Operate as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りに実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author your_name_here
 */
public class Step08Java8FunctionTest extends PlainTestCase {

    // ===================================================================================
    //                                                                              Lambda
    //                                                                              ======
    // -----------------------------------------------------
    //                                              Callback
    //                                              --------
    /**
     * Are all the strings by log() methods in callback processes same? (yes or no) <br>
     * (コールバック処理の中で出力しているログの文字列はすべて同じでしょうか？ (yes or no))
     */
    public void test_java8_lambda_callback_basic() {
        String title = "over";

        log("...Executing named class callback(!?)");
        helpCallbackConsumer(new St8BasicConsumer(title));

        // #1on1: A→B
        //        A←B (A'←B)
        log("...Executing anonymous class callback");
        helpCallbackConsumer(new Consumer<String>() {
            public void accept(String stage) {
                log(stage + ": " + title);
            }
        });

        // #1on1: 推論の話 (2026/03/27)
        log("...Executing lambda block style callback");
        helpCallbackConsumer(stage -> {
            log(stage + ": " + title);
        });

        log("...Executing lambda expression style callback");
        helpCallbackConsumer(stage -> log(stage + ": " + title));

        // your answer? => yes

        // cannot reassign because it is used at callback process
        //title = "wave";
    }

    /**
     * What is order of strings by log(). (write answer as comma-separated) <br>
     * (ログに出力される文字列の順番は？ (カンマ区切りで書き出しましょう))
     */
    public void test_java8_lambda_callback_order() {
        log("harbor");
        helpCallbackConsumer(stage -> {
            log(stage);
        });
        log("lost river");
        // your answer? => harbor,broadway,dockside,hanger,lost river
    }

    private class St8BasicConsumer implements Consumer<String> {

        private final String title;

        public St8BasicConsumer(String title) {
            this.title = title;
        }

        @Override
        public void accept(String stage) {
            log(stage + ": " + title);
        }
    }

    private void helpCallbackConsumer(Consumer<String> oneArgLambda) {
        log("broadway");
        oneArgLambda.accept("dockside");
        log("hangar");
    }

    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_lambda_callback_valueRoad() {
        String label = "number";
        String sea = helpCallbackFunction(number -> {
            return label + ": " + number;
        });
        log(sea); // your answer? => number: 7
    }

    private String helpCallbackFunction(Function<Integer, String> oneArgLambda) {
        return oneArgLambda.apply(7);
    }

    // -----------------------------------------------------
    //                                         Convert Style
    //                                         -------------
    /**
     * Change callback style like this:
     * <pre>
     * o sea: to lambda block style
     * o land: to lambda expression style
     * o piari: to lambda block style
     * </pre>
     * (このようにコールバックスタイルを変えてみましょう:)
     * <pre>
     * o sea: BlockのLambda式に
     * o land: ExpressionのLambda式に
     * o piari: BlockのLambda式に
     * </pre>
     */
    public void test_java8_lambda_convertStyle_basic() {
        helpCallbackSupplier(() -> { // sea
            return "broadway";
        });

        // #1on1: Block or Expression, 見た目を調整する小テクニック (2026/03/27)
        helpCallbackSupplier(() -> "dockside"); // land

        helpCallbackSupplier(() -> { // piari
            return "hangar";
        });
    }

    private void helpCallbackSupplier(Supplier<String> oneArgLambda) {
        String supplied = oneArgLambda.get();
        log(supplied);
    }

    // ===================================================================================
    //                                                                            Optional
    //                                                                            ========
    /**
     * Are the strings by two log() methods same? (yes or no) <br>
     * (二つのlog()によって出力される文字列は同じでしょうか？ (yes or no))
     */
    public void test_java8_optional_concept() {
        St8Member oldmember = new St8DbFacade().oldselectMember(1);
        if (oldmember != null) {
            log(oldmember.getMemberId(), oldmember.getMemberName());
        }
        Optional<St8Member> optMember = new St8DbFacade().selectMember(1);
        if (optMember.isPresent()) {
            St8Member member = optMember.get();
            log(member.getMemberId(), member.getMemberName());
        }
        // your answer? => yes

        // #1on1: Kotlinだったらint?でoptional、JavaにもOptionalあるけど... (2025/08/12)
        // 少なくとも、文法に組み込まれてるか、ただのクラスなのか？の違いがある。
        // Javaだと、徹底するのは難しいので、適所で使うみたいなことが多い印象。
        // くぼ個人の、フレームワークの中でのOptionalの話。
        
        // #1on1: JavaでOptionalが入ったのが2015年あたり (2026/03/27)
        // 30年のうち20年Optionalなかった。
        // Optionalのという発明自体は、もっと昔から。
        // もっと早く導入されなかったのか？？？話
    }

    /**
     * Are the strings by two log() methods same? (yes or no) <br>
     * (二つのlog()によって出力される文字列は同じでしょうか？ (yes or no))
     */
    public void test_java8_optional_ifPresent() {
        Optional<St8Member> optMember = new St8DbFacade().selectMember(1);
        if (optMember.isPresent()) {
            St8Member member = optMember.get();
            log(member.getMemberId(), member.getMemberName());
        }
        optMember.ifPresent(member -> {
            log(member.getMemberId(), member.getMemberName());
        });
        // your answer? => yes
    }

    /**
     * What string is sea, land, piari, bonvo, dstore, amba variables at the method end? <br>
     * (メソッド終了時の変数 sea, land, piari, bonvo, dstore, amba の中身は？)
     */
    public void test_java8_optional_map_flatMap() {
        St8DbFacade facade = new St8DbFacade();

        // traditional style
        St8Member oldmemberFirst = facade.oldselectMember(1);
        String sea;
        if (oldmemberFirst != null) {
            St8Withdrawal withdrawal = oldmemberFirst.oldgetWithdrawal();
            if (withdrawal != null) {
                sea = withdrawal.oldgetPrimaryReason();
                if (sea == null) {
                    sea = "*no reason1: the PrimaryReason was null";
                }
            } else {
                sea = "*no reason2: the Withdrawal was null";
            }
        } else {
            sea = "*no reason3: the selected Member was null";
        }

        Optional<St8Member> optMemberFirst = facade.selectMember(1);

        // map style
        String land = optMemberFirst.map(mb -> mb.oldgetWithdrawal())
                .map(wdl -> wdl.oldgetPrimaryReason())
                .orElse("*no reason: someone was not present");

        // flatMap style
        String piari = optMemberFirst.flatMap(mb -> mb.getWithdrawal())
                .flatMap(wdl -> wdl.getPrimaryReason())
                .orElse("*no reason: someone was not present");

        // flatMap and map style
        String bonvo = optMemberFirst.flatMap(mb -> mb.getWithdrawal())
                .map(wdl -> wdl.oldgetPrimaryReason())
                .orElse("*no reason: someone was not present");

        String dstore = facade.selectMember(2)
                .flatMap(mb -> mb.getWithdrawal())
                .map(wdl -> wdl.oldgetPrimaryReason())
                .orElse("*no reason: someone was not present");

        String amba = facade.selectMember(3)
                .flatMap(mb -> mb.getWithdrawal())
                .flatMap(wdl -> wdl.getPrimaryReason())
                .orElse("*no reason: someone was not present");

        int defaultWithdrawalId = -1;
        Integer miraco = facade.selectMember(2).flatMap(mb -> mb.getWithdrawal()).map(wdl -> wdl.getWithdrawalId()) // ID here
                .orElse(defaultWithdrawalId);

        log(sea); // your answer? => music
        log(land); // your answer? => music
        log(piari); // your answer? => music
        log(bonvo); // your answer? => music
        log(dstore); // your answer? => *no reason: someone was not present
        log(amba); // your answer? => *no reason: someone was not present
        log(miraco); // your answer? => 12
        
        // #1on1 map()/flatMap, flatMapのflatは？ (2026/03/27)
        // 二重配列をどうのこうの？ by itoryuさん
        // // map() and flatMap()
        // https://dbflute.seasar.org/ja/manual/topic/programming/java/java8/mapandflat.html
        // flatの意味がわかったぞー
        // このエクササイズのベタ書きとmap/flatMapのお話。
        // どこで途切れたか？を気にしなくて良い場合と気にしたい場合。
    }

    /**
     * What string is sea variables at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_optional_orElseThrow() {
        Optional<St8Member> optMember = new St8DbFacade().selectMember(2);
        St8Member member = optMember.orElseThrow(() -> {
            return new IllegalStateException("over");
        });
        
        String sea = "the";
        try {
            String reason = member.getWithdrawal().map(wdl -> wdl.oldgetPrimaryReason()).orElseThrow(() -> {
                return new IllegalStateException("wave");
            });
            sea = reason;
        } catch (IllegalStateException e) {
            sea = e.getMessage();
        }
        log(sea); // your answer? => wave
        
        // done jflute 次回1on1, orElseThrow()ジレンマのお話 (2026/03/27)
        // orElseThrow()自体のお話を振り返り。
        // 似たメソッドとしては、実は optMember.get(); がいる。
        // 組み込みの例外を投げるのか？自分で例外を組み立てるのか？の違い。
        //
        // orElseThrow()の使い所:
        // やっていることはなければ例外なので、チェックなしnullPoと何が違う？
        // orElseThrow()自分で例外を組み立てるのか？の違い。
        // どのみち例外で落としてることには変わりがない。
        // つまり、戻り値がなかったら論外(バグor業務例外)と言っても良いような場面。
        //
        // チェックなしnullPoで落とすのは何が微妙？
        //  → 想定してないバグだと思っちゃう by itoryu
        // このケースで落ちた場合は、デバッグする可能性が大。
        // ってときに、nullPoだとデバッグ情報がなさ過ぎ。(スタックトレースも原因箇所とズレるし)
        //
        // optMember.get(); (問答無用get()) で落とすのは何が微妙？
        //   → 同じくメッセージをいじれない by itoryu
        // 情報量がほぼnullPoとかわらない。
        // なので、(isPresent()なしの) 問答無用get() は、教科書的にはやらない方が良いと言われる。
        // Optionalのコンセプトを壊してしまうから。
        //
        // だから、(教科書的には) orElseThrow() を使いましょう。
        // (業務的に絶対存在するという場面であれば)
        //
        // ↑このセオリーがわかっていれば、
        // orElseThrow(() -> new IllegalStateException("over"));
        // みたいな orElseThrow() が微妙であることが理解できるはず。
        //
        // よもやま: 許される実装じゃなくてその場で論理的に最適な実装 (2026/04/07)
        //
        //
        // 必ず存在する場面のOptionalが、よく発生するケースの場合...
        // orElseThrow()でまじめにthrowしていくのが確かに面倒ではある。
        // (Optionalを使わない実装だった時、チェックしないレベルのもの)
        //
        // A. ちゃんと教科書通り orElseThrow() をまじめに実装していく (合ってない問答無用get()を防ぎたい)
        // B. もうこのケースなら問答無用get()でもいいのでは？ (Cを防ぎたい)
        // (C. 許されるために orElseThrow() を使うけど、形骸化orElseThrow()になる)
        //
        // DBFluteのConditionBeanの例で例えてみた。特にリレーションシップの関連テーブルのget。
        // フレームワークがthrowする方が、よりデバッグ情報量がある話。
        // DBFluteのOptionalの話。
        //
        // Java10から、orElseThrow(引数なし)というメソッドが追加された。
        // get()と全く処理が同じ。
        // "B" の人からすると、get()は怒られるのでorElseThrow(引数なし)使おっと、になる。
        //
        // #1on1: 文法だけの論理じゃなく、現実感の論理を混ぜて物事を考えることができると良い (2026/04/07)
    }

    // ===================================================================================
    //                                                                          Stream API
    //                                                                          ==========
    /**
     * What string is sea, land variables at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_java8_stream_concept() {
        List<St8Member> memberList = new St8DbFacade().selectMemberListAll();
        List<String> oldfilteredNameList = new ArrayList<>();
        for (St8Member member : memberList) {
            if (member.getWithdrawal().isPresent()) {
                oldfilteredNameList.add(member.getMemberName());
            }
        }
        String sea = oldfilteredNameList.toString();
        log(sea); // your answer? => [broadway, dockside]

        List<String> filteredNameList = memberList.stream() //
                .filter(mb -> mb.getWithdrawal().isPresent()) //
                .map(mb -> mb.getMemberName()) //
                .collect(Collectors.toList());
        String land = filteredNameList.toString();
        log(land); // your answer? => [broadway, dockside]
    }

    // TODO jflute 次回1on1, Stream API (2026/04/07)
    /**
     * What string is sea, variables at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_stream_map_flatMap() {
        List<St8Member> memberList = new St8DbFacade().selectMemberListAll();
        int sea = memberList.stream()
                .filter(mb -> mb.getWithdrawal().isPresent())
                .flatMap(mb -> mb.getPurchaseList().stream())
                .filter(pur -> pur.getPurchaseId() > 100)
                .mapToInt(pur -> pur.getPurchasePrice())
                .distinct()
                .sum();
        log(sea); // your answer? => 600
    }

    // *Stream API will return at Step12 again, it's worth the wait!
}
