package org.docksidestage.javatry.basic.st6.dbms;

public abstract class St6Dbms {

    /**
     * ページサイズとページ番号に基づいてオフセットを計算し、ページング用のクエリ文字列を構築する
     *
     * @param pageSize 1ページあたりの取得レコード数
     * @param pageNumber ページ番号
     * @return 指定されたパラメータで構築されたページング用クエリ文字列
     */
    public String buildPagingQuery(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1); // 共通の計算
        return doBuildPagingQuery(pageSize, offset); // 固有の処理を呼び出し
    }

    protected abstract String doBuildPagingQuery(int pageSize, int offset);
}
