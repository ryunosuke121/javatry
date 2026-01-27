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

import org.docksidestage.bizfw.basic.objanimal.barking.BarkingProcess;

/**
 * The object for zombie(ゾンビ).
 * @author jflute
 */
public class Zombie extends Animal {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final ZombieDiary zombieDiary = new ZombieDiary();

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Zombie() {
    }

    @Override
    protected int getInitialHitPoint() {
        return -1; // magic number for infinity hit point
    }

    public static class ZombieDiary {

        private int breatheInCount;

        public void countBreatheIn() {
            ++breatheInCount;
        }

        public int getBreatheInCount() {
            return breatheInCount;
        }
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    @Override
    protected BarkingProcess createBarkingProcess() {
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // #1on1: [いいね]具象クラスと具象クラスのポリモーフィズムをうまく使っている (2025/12/23)
        //
        // ZombieBarkingProcess extends BarkingProcess
        //
        // Animalでは、BarkingProcess で扱ってるけど、
        // Zombieの場合は実体が ZombieBarkingProcess になる。 
        // _/_/_/_/_/_/_/_/
        // TODO itoryu せっかくなので、引数を準備するコードをコピーしないで済むようにしましょう by jflute (2026/01/27)
        // downHitPointのコールバックやbarkWordの準備の仕方に依存してAnimalと同期をしないといけなくなってる。
        return new BarkingProcess(this::downHitPoint, getBarkWord()) {
            @Override
            protected void breatheIn() {
                super.breatheIn();
                zombieDiary.countBreatheIn();
            }
        };
    }
    
    // #1on1: ↑のzombieDiaryの参照が、ちょっとトリッキーに見える(by itoryuさん)との話 (2025/12/23)
    // 確かに、二段上のリソースを直接参照している感はある。ただ、よく使われるやり方ではある。
    // もし、これを避けたい場合は、以下のやり方:
    //
    // インスタンス所属インナークラスパターン:
    // o 多段ではなく、段階を踏んでzombieDiaryを受け取ってる感じ
    // 
    //    public class ZombieBarkingProcess extends BarkingProcess {
    //        
    //        public ZombieBarkingProcess(Animal animal) {
    //            super(animal);
    //        }
    //        
    //        @Override
    //        protected void breatheIn() {
    //            super.breatheIn();
    //            zombieDiary.countBreatheIn();
    //        }
    //    }
    //
    // 独立インナークラスパターン:
    // o 実質、ファイルを分けた独立クラスとほぼ同じ扱い。
    // o こっちだと、引数でリレーしてる感ある。(そうしたい場合はこちら)
    //    public static class ZombieBarkingProcess extends BarkingProcess {
    //
    //        private final ZombieDiary zombieDiary;
    //        
    //        public ZombieBarkingProcess(Animal animal, ZombieDiary zombieDiary) {
    //            super(animal);
    //            this.zombieDiary = zombieDiary;
    //        }
    //
    //        @Override
    //        protected void breatheIn() {
    //            super.breatheIn();
    //            zombieDiary.countBreatheIn();
    //        }
    //    }

    @Override
    public String getBarkWord() {
        return "uooo"; // what in English?
    }

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    @Override
    public void downHitPoint() {
        // do nothing, infinity hit point
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public ZombieDiary getZombieDiary() {
        return zombieDiary;
    }
}
