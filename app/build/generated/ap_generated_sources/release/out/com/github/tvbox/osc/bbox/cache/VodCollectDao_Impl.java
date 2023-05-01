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
public final class VodCollectDao_Impl implements VodCollectDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VodCollect> __insertionAdapterOfVodCollect;

  private final EntityDeletionOrUpdateAdapter<VodCollect> __deletionAdapterOfVodCollect;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public VodCollectDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVodCollect = new EntityInsertionAdapter<VodCollect>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `vodCollect` (`id`,`vodId`,`updateTime`,`sourceKey`,`name`,`pic`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VodCollect value) {
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
        if (value.name == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.name);
        }
        if (value.pic == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.pic);
        }
      }
    };
    this.__deletionAdapterOfVodCollect = new EntityDeletionOrUpdateAdapter<VodCollect>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `vodCollect` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, VodCollect value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from vodCollect where `id`=?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final VodCollect record) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfVodCollect.insertAndReturnId(record);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final VodCollect record) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfVodCollect.handle(record);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public List<VodCollect> getAll() {
    final String _sql = "select * from vodCollect  order by updateTime desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfVodId = CursorUtil.getColumnIndexOrThrow(_cursor, "vodId");
      final int _cursorIndexOfUpdateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "updateTime");
      final int _cursorIndexOfSourceKey = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceKey");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPic = CursorUtil.getColumnIndexOrThrow(_cursor, "pic");
      final List<VodCollect> _result = new ArrayList<VodCollect>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final VodCollect _item;
        _item = new VodCollect();
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
        if (_cursor.isNull(_cursorIndexOfName)) {
          _item.name = null;
        } else {
          _item.name = _cursor.getString(_cursorIndexOfName);
        }
        if (_cursor.isNull(_cursorIndexOfPic)) {
          _item.pic = null;
        } else {
          _item.pic = _cursor.getString(_cursorIndexOfPic);
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
  public VodCollect getVodCollect(final int id) {
    final String _sql = "select * from vodCollect where `id`=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfVodId = CursorUtil.getColumnIndexOrThrow(_cursor, "vodId");
      final int _cursorIndexOfUpdateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "updateTime");
      final int _cursorIndexOfSourceKey = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceKey");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPic = CursorUtil.getColumnIndexOrThrow(_cursor, "pic");
      final VodCollect _result;
      if(_cursor.moveToFirst()) {
        _result = new VodCollect();
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
        if (_cursor.isNull(_cursorIndexOfName)) {
          _result.name = null;
        } else {
          _result.name = _cursor.getString(_cursorIndexOfName);
        }
        if (_cursor.isNull(_cursorIndexOfPic)) {
          _result.pic = null;
        } else {
          _result.pic = _cursor.getString(_cursorIndexOfPic);
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
  public VodCollect getVodCollect(final String sourceKey, final String vodId) {
    final String _sql = "select * from vodCollect where `sourceKey`=? and `vodId`=?";
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
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPic = CursorUtil.getColumnIndexOrThrow(_cursor, "pic");
      final VodCollect _result;
      if(_cursor.moveToFirst()) {
        _result = new VodCollect();
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
        if (_cursor.isNull(_cursorIndexOfName)) {
          _result.name = null;
        } else {
          _result.name = _cursor.getString(_cursorIndexOfName);
        }
        if (_cursor.isNull(_cursorIndexOfPic)) {
          _result.pic = null;
        } else {
          _result.pic = _cursor.getString(_cursorIndexOfPic);
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
