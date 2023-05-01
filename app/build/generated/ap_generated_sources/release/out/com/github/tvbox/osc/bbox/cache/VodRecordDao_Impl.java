package com.github.tvbox.osc.bbox.cache;

import android.database.Cursor;
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

@SuppressWarnings({"unchecked", "deprecation"})
public final class VodRecordDao_Impl implements VodRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VodRecord> __insertionAdapterOfVodRecord;

  private final EntityDeletionOrUpdateAdapter<VodRecord> __deletionAdapterOfVodRecord;

  private final SharedSQLiteStatement __preparedStmtOfReserver;

  public VodRecordDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVodRecord = new EntityInsertionAdapter<VodRecord>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `vodRecord` (`id`,`vodId`,`updateTime`,`sourceKey`,`dataJson`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VodRecord value) {
        stmt.bindLong(1, value.getId());
        if (value.vodId == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.vodId);
        }
        stmt.bindLong(3, value.updateTime);
        if (value.sourceKey == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.sourceKey);
        }
        if (value.dataJson == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.dataJson);
        }
      }
    };
    this.__deletionAdapterOfVodRecord = new EntityDeletionOrUpdateAdapter<VodRecord>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `vodRecord` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VodRecord value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__preparedStmtOfReserver = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM vodRecord where id NOT IN (SELECT id FROM vodRecord ORDER BY updateTime desc LIMIT ?)";
        return _query;
      }
    };
  }

  @Override
  public long insert(final VodRecord record) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfVodRecord.insertAndReturnId(record);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final VodRecord record) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfVodRecord.handle(record);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int reserver(final int size) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfReserver.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, size);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfReserver.release(_stmt);
    }
  }

  @Override
  public List<VodRecord> getAll(final int size) {
    final String _sql = "select * from vodRecord order by updateTime desc limit ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, size);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfVodId = CursorUtil.getColumnIndexOrThrow(_cursor, "vodId");
      final int _cursorIndexOfUpdateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "updateTime");
      final int _cursorIndexOfSourceKey = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceKey");
      final int _cursorIndexOfDataJson = CursorUtil.getColumnIndexOrThrow(_cursor, "dataJson");
      final List<VodRecord> _result = new ArrayList<VodRecord>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VodRecord _item;
        _item = new VodRecord();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        if (_cursor.isNull(_cursorIndexOfVodId)) {
          _item.vodId = null;
        } else {
          _item.vodId = _cursor.getString(_cursorIndexOfVodId);
        }
        _item.updateTime = _cursor.getLong(_cursorIndexOfUpdateTime);
        if (_cursor.isNull(_cursorIndexOfSourceKey)) {
          _item.sourceKey = null;
        } else {
          _item.sourceKey = _cursor.getString(_cursorIndexOfSourceKey);
        }
        if (_cursor.isNull(_cursorIndexOfDataJson)) {
          _item.dataJson = null;
        } else {
          _item.dataJson = _cursor.getString(_cursorIndexOfDataJson);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public VodRecord getVodRecord(final String sourceKey, final String vodId) {
    final String _sql = "select * from vodRecord where `sourceKey`=? and `vodId`=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (sourceKey == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sourceKey);
    }
    _argIndex = 2;
    if (vodId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, vodId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfVodId = CursorUtil.getColumnIndexOrThrow(_cursor, "vodId");
      final int _cursorIndexOfUpdateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "updateTime");
      final int _cursorIndexOfSourceKey = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceKey");
      final int _cursorIndexOfDataJson = CursorUtil.getColumnIndexOrThrow(_cursor, "dataJson");
      final VodRecord _result;
      if(_cursor.moveToFirst()) {
        _result = new VodRecord();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        if (_cursor.isNull(_cursorIndexOfVodId)) {
          _result.vodId = null;
        } else {
          _result.vodId = _cursor.getString(_cursorIndexOfVodId);
        }
        _result.updateTime = _cursor.getLong(_cursorIndexOfUpdateTime);
        if (_cursor.isNull(_cursorIndexOfSourceKey)) {
          _result.sourceKey = null;
        } else {
          _result.sourceKey = _cursor.getString(_cursorIndexOfSourceKey);
        }
        if (_cursor.isNull(_cursorIndexOfDataJson)) {
          _result.dataJson = null;
        } else {
          _result.dataJson = _cursor.getString(_cursorIndexOfDataJson);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getCount() {
    final String _sql = "select count(*) from vodRecord";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
