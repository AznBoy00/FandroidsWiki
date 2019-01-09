package org.wikipedia.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.wikipedia.database.column.Column;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public final class DbUtil {
    public static String namesCsv(Column<?>... cols) {
        return namesCsv(Arrays.asList(cols));
    }

    public static String namesCsv(Collection<? extends Column<?>> cols) {
        return TextUtils.join(", ", names(cols));
    }

    public static String[] names(Column<?>... cols) {
        return names(Arrays.asList(cols)).toArray(new String[cols.length]);
    }

    public static Collection<String> names(Collection<? extends Column<?>> cols) {
        Collection<String> strs = new ArrayList<>(cols.size());
        for (Column<?> col : cols) {
            strs.add(col.getName());
        }
        return strs;
    }

    public static String[] qualifiedNames(Column<?>... cols) {
        return qualifiedNames(Arrays.asList(cols)).toArray(new String[cols.length]);
    }

    public static Collection<String> qualifiedNames(Collection<? extends Column<?>> cols) {
        Collection<String> strs = new ArrayList<>(cols.size());
        for (Column<?> col : cols) {
            strs.add(col.qualifiedName());
        }
        return strs;
    }

    public static void execSqlTransaction(@NonNull SQLiteDatabase db, @NonNull String statements) {
        try {
            db.beginTransaction();
            for (String statement : statements.split(";")) {
                db.execSQL(statement);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @NonNull public static <T> Collection<T> cursorToCollection(@NonNull DatabaseClient<T> client,
                                                                @NonNull Cursor cursor) {
        Collection<T> ret = new ArrayList<>();
        while (cursor.moveToNext()) {
            ret.add(client.fromCursor(cursor));
        }
        return ret;
    }

    private DbUtil() { }
}
