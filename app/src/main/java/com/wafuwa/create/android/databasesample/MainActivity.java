package com.wafuwa.create.android.databasesample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int MINUS1 = -1;
    public static final String NSTR = "";

    int _sweetId = MINUS1;
    String _sweetName = NSTR;
    TextView _tvSweetName;
    Button _btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _tvSweetName = findViewById(R.id.tvSweetName);
        _btnSave = findViewById(R.id.btnSave);
        ListView lvSweet = findViewById(R.id.lvSweet);

        lvSweet.setOnItemClickListener((parent, view, position, id) -> {
            _sweetId = position;
            _sweetName = (String) parent.getItemAtPosition(position);
            _tvSweetName.setText(_sweetName);
            _btnSave.setEnabled(true);

            DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
            try (SQLiteDatabase db = helper.getWritableDatabase()) {
                String sql = "SELECT * FROM sweetmemo WHERE _id = " + _sweetId + ";";
                Cursor cursor = db.rawQuery(sql, null);
                String note = NSTR;
                if (cursor.moveToNext()) {
//                    int idxNote = cursor.getColumnIndex("note");
//                    note = cursor.getString(idxNote);
                    note = cursor.getString(2);
                }
                cursor.close();
                EditText etNote = findViewById(R.id.etNote);
                etNote.setText(note);
            }
            helper.close();
        });
    }

    public void onSaveButtonClick(View view) {
        EditText etNote = findViewById(R.id.etNote);
        String note = etNote.getText().toString();

        DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
        try (SQLiteDatabase db = helper.getWritableDatabase()) {
            String sqlDelete = "DELETE FROM sweetmemo WHERE _id = ?;";
            SQLiteStatement stmt = db.compileStatement(sqlDelete);
            stmt.bindLong(1, _sweetId);
            stmt.executeUpdateDelete();
            stmt.close();

            String sqlInsert = "INSERT INTO sweetmemo (_id, name, note) VALUES (?, ?, ?);";
            stmt = db.compileStatement(sqlInsert);
            stmt.bindLong(1, _sweetId);
            stmt.bindString(2, _sweetName);
            stmt.bindString(3, note);
            stmt.executeInsert();
            stmt.close();
        }
        helper.close();

        _tvSweetName.setText(getString(R.string.tv_name));
        etNote.setText(NSTR);
        _btnSave.setEnabled(false);
    }
}