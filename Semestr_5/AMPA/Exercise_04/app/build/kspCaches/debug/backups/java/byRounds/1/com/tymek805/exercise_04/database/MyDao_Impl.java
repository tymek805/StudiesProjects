package com.tymek805.exercise_04.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MyDao_Impl implements MyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MyItem> __insertionAdapterOfMyItem;

  private final TransportTypeConverter __transportTypeConverter = new TransportTypeConverter();

  private final EntityDeletionOrUpdateAdapter<MyItem> __deletionAdapterOfMyItem;

  private final EntityDeletionOrUpdateAdapter<MyItem> __updateAdapterOfMyItem;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public MyDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMyItem = new EntityInsertionAdapter<MyItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `items` (`id`,`destination`,`subText`,`rating`,`time`,`type`,`abroad`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MyItem entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getDestination() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getDestination());
        }
        if (entity.getSubText() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubText());
        }
        statement.bindDouble(4, entity.getRating());
        statement.bindLong(5, entity.getTime());
        final String _tmp;
        if (entity.getTransportType() == null) {
          _tmp = null;
        } else {
          _tmp = __transportTypeConverter.fromTransportType(entity.getTransportType());
        }
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        final int _tmp_1 = entity.getChecked() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
      }
    };
    this.__deletionAdapterOfMyItem = new EntityDeletionOrUpdateAdapter<MyItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `items` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MyItem entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfMyItem = new EntityDeletionOrUpdateAdapter<MyItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `items` SET `id` = ?,`destination` = ?,`subText` = ?,`rating` = ?,`time` = ?,`type` = ?,`abroad` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MyItem entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getDestination() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getDestination());
        }
        if (entity.getSubText() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSubText());
        }
        statement.bindDouble(4, entity.getRating());
        statement.bindLong(5, entity.getTime());
        final String _tmp;
        if (entity.getTransportType() == null) {
          _tmp = null;
        } else {
          _tmp = __transportTypeConverter.fromTransportType(entity.getTransportType());
        }
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        final int _tmp_1 = entity.getChecked() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM items";
        return _query;
      }
    };
  }

  @Override
  public long insert(final MyItem item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfMyItem.insertAndReturnId(item);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final MyItem item) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total += __deletionAdapterOfMyItem.handle(item);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateItem(final MyItem item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfMyItem.handle(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<MyItem> getAllData() {
    final String _sql = "SELECT * FROM items ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfDestination = CursorUtil.getColumnIndexOrThrow(_cursor, "destination");
      final int _cursorIndexOfSubText = CursorUtil.getColumnIndexOrThrow(_cursor, "subText");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfTransportType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
      final int _cursorIndexOfChecked = CursorUtil.getColumnIndexOrThrow(_cursor, "abroad");
      final List<MyItem> _result = new ArrayList<MyItem>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final MyItem _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpDestination;
        if (_cursor.isNull(_cursorIndexOfDestination)) {
          _tmpDestination = null;
        } else {
          _tmpDestination = _cursor.getString(_cursorIndexOfDestination);
        }
        final String _tmpSubText;
        if (_cursor.isNull(_cursorIndexOfSubText)) {
          _tmpSubText = null;
        } else {
          _tmpSubText = _cursor.getString(_cursorIndexOfSubText);
        }
        final float _tmpRating;
        _tmpRating = _cursor.getFloat(_cursorIndexOfRating);
        final int _tmpTime;
        _tmpTime = _cursor.getInt(_cursorIndexOfTime);
        final TransportType _tmpTransportType;
        final String _tmp;
        if (_cursor.isNull(_cursorIndexOfTransportType)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getString(_cursorIndexOfTransportType);
        }
        if (_tmp == null) {
          _tmpTransportType = null;
        } else {
          _tmpTransportType = __transportTypeConverter.toTransportType(_tmp);
        }
        final boolean _tmpChecked;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfChecked);
        _tmpChecked = _tmp_1 != 0;
        _item = new MyItem(_tmpId,_tmpDestination,_tmpSubText,_tmpRating,_tmpTime,_tmpTransportType,_tmpChecked);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
