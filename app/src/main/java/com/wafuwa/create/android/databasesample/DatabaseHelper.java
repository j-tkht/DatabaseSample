package com.wafuwa.create.android.databasesample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sweetmemo.db";         // DB名
    private static final int DATABASE_VERSION = 1;                       // DBバージョン

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //データベースがない時だけ動く
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE sweetmemo (");
        sb.append("_id INTEGER PRIMARY KEY,");
        sb.append("name TEXT,");
        sb.append("note TEXT");
        sb.append(");");
        String sql = sb.toString();

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }
}

/*
［トランザクション］
    db.beginTransaction();
    try {
        「DB処理」
        db.setTransactionSuccessful();
    } finally {
        db.endTransaction();
    }

［フィールド定義］
形式： 列(フィールド名)　型　属性
●列(フィールド名)
    主キーは _id で記述、他は任意
    ただし、日本語は不可
●型
    TEXT            テキスト
    INTEGER         符号付き整数
    REAL            浮動小数点
    BLOB            バイナリデータ
    NULL            NULL
    (注)DATE型はないので、テキスト or 通算日時で管理する
●属性
    NOT NULL        NULL不可(値が必須)
    PRIMARY KEY     主キー
    AUTOINCREMENT   1からの通番

［データ参照・構築］
(前提)エミュレータが立ち上がっていること
(操作)
    Studioの右下にある「Device File Explorer」から
    /data/data/パッケージ名/databases/DB名
(データ参照とデータ構築)
    選択後に SaveAs でコピー(デスクトップ等)し、ツールにて参照する
    pushは、databasesフォルダから右クリック(Upload)で整形済みDBを置換する
*/
