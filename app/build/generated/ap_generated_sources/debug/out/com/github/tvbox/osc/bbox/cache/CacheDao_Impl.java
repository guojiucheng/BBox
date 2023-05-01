package com.github.tvbox.osc.bbox.cache;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class CacheDao_Impl implements CacheDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Cache> __insertionAdapterOfCache;

  private final EntityDeletionOrUpdateAdapter<Cache> __deletionAdapterOfCache;

  private final EntityDeletionOrUpdateAdapter<Cache> __updateAdapterOfCache;

  public CacheDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCache = new EntityInsertionAdapter<Cache>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `cache` (`key`,`data`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cache value) {
        if (value.key == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.key);
        }
        if (value.data == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindBlob(2, value.data);
        }
      }
    };
    this.__deletionAdapterOfCache = new EntityDeletionOrUpdateAdapter<Cache>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `cache` WHERE `key` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cache value) {
        if (value.key == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.key);
        }
      }
    };
    this.__updateAdapterOfCache = new EntityDeletionOrUpdateAdapter<Cache>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `cache` SET `key` = ?,`data` = ? WHERE `key` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cache value) {
        if (value.key == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.key);
        }
        if (value.data == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindBlob(2, value.data);
        }
        if (value.key == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.key);
        }
      }
    };
  }

  @Override
  public long save(final Cache cache) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfCache.insertAndReturnId(cache);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final Cache cache) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfCache.handle(cache);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int update(final Cache cache) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfCache.handle(cache);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public Cache getCache(final String key) {
    final String _sql = "select *from cache where `key`=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (key == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, key);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfKey = CursorUtil.getColumnIndexOrThrow(_cursor, "key");
      final int _cursorIndexOfData = CursorUtil.getColumnIndexOrThrow(_cursor, "data");
      final Cache _result;
      if(_cursor.moveToFirst()) {
        _result = new Cache();
        if (_cursor.isNull(_cursorIndexOfKey)) {
          _result.key = null;
        } else {
          _result.key = _cursor.getString(_cursorIndexOfKey);
        }
        if (_cursor.isNull(_cursorIndexOfData)) {
          _result.data = null;
        } else {
          _result.data = _cursor.getBlob(_cursorIndexOfData);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
