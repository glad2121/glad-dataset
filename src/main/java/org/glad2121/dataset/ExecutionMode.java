package org.glad2121.dataset;

/**
 * 実行モード。
 * 
 * @author GLAD!!
 */
public enum ExecutionMode {

    /**
     * データファイルがあれば使用し、なければ雛形を生成するモード。
     */
    DEFAULT,

    /**
     * データベースを読み込んで、データファイルの雛形を生成するモード。
     */
    INIT,

    /**
     * データファイルを読み込んで、テストを実行するモード。
     */
    TEST

}
