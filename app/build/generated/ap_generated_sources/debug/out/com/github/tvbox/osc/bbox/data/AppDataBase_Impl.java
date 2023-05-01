package com.github.tvbox.osc.bbox.data;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.github.tvbox.osc.bbox.cache.CacheDao;
import com.github.tvbox.osc.bbox.cache.CacheDao_Impl;
import com.github.tvbox.osc.bbox.cache.VodCollectDao;
import com.github.tvbox.osc.bbox.cache.VodCollectDao_Impl;
import com.github.tvbox.osc.bbox.cache.VodRecordDao;
import com.github.tvbox.osc.bbox.cache.VodRecordDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDataBase_Impl extends AppDataBase {
  private volatile CacheDao _cacheDao;

  private volatile VodRecordDao _vodRecordDao;

  private volatile VodCollectDao _vodCollectDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `cache` (`key` TEXT NOT NULL, `data` BLOB, PRIMARY KEY(`key`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `vodRecord` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `vodId` TEXT, `updateTime` INTEGER NOT NULL, `sourceKey` TEXT, `dataJson` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `vodCollect` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `vodId` TEXT, `updateTime` INTEGER NOT NULL, `sourceKey` TEXT, `name` TEXT, `pic` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd1c2780b5424f0e960fe2364f63c86b8')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `cache`");
        _db.execSQL("DROP TABLE IF EXISTS `vodRecord`");
        _db.execSQL("DROP TABLE IF EXISTS `vodCollect`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCache = new HashMap<String, TableInfo.Column>(2);
        _columnsCache.put("key", new TableInfo.Column("key", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCache.put("data", new TableInfo.Column("data", "BLOB", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCache = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCache = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCache = new TableInfo("cache", _columnsCache, _foreignKeysCache, _indicesCache);
        final TableInfo _existingCache = TableInfo.read(_db, "cache");
        if (! _infoCache.equals(_existingCache)) {
          return new RoomOpenHelper.ValidationResult(false, "cache(com.github.tvbox.osc.bbox.cache.Cache).\n"
                  + " Expected:\n" + _infoCache + "\n"
                  + " Found:\n" + _existingCache);
        }
        final HashMap<String, TableInfo.Column> _columnsVodRecord = new HashMap<String, TableInfo.Column>(5);
        _columnsVodRecord.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodRecord.put("vodId", new TableInfo.Column("vodId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodRecord.put("updateTime", new TableInfo.Column("updateTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodRecord.put("sourceKey", new TableInfo.Column("sourceKey", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodRecord.put("dataJson", new TableInfo.Column("dataJson", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVodRecord = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVodRecord = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVodRecord = new TableInfo("vodRecord", _columnsVodRecord, _foreignKeysVodRecord, _indicesVodRecord);
        final TableInfo _existingVodRecord = TableInfo.read(_db, "vodRecord");
        if (! _infoVodRecord.equals(_existingVodRecord)) {
          return new RoomOpenHelper.ValidationResult(false, "vodRecord(com.github.tvbox.osc.bbox.cache.VodRecord).\n"
                  + " Expected:\n" + _infoVodRecord + "\n"
                  + " Found:\n" + _existingVodRecord);
        }
        final HashMap<String, TableInfo.Column> _columnsVodCollect = new HashMap<String, TableInfo.Column>(6);
        _columnsVodCollect.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodCollect.put("vodId", new TableInfo.Column("vodId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodCollect.put("updateTime", new TableInfo.Column("updateTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodCollect.put("sourceKey", new TableInfo.Column("sourceKey", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodCollect.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVodCollect.put("pic", new TableInfo.Column("pic", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVodCollect = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVodCollect = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVodCollect = new TableInfo("vodCollect", _columnsVodCollect, _foreignKeysVodCollect, _indicesVodCollect);
        final TableInfo _existingVodCollect = TableInfo.read(_db, "vodCollect");
        if (! _infoVodCollect.equals(_existingVodCollect)) {
          return new RoomOpenHelper.ValidationResult(false, "vodCollect(com.github.tvbox.osc.bbox.cache.VodCollect).\n"
                  + " Expected:\n" + _infoVodCollect + "\n"
                  + " Found:\n" + _existingVodCollect);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d1c2780b5424f0e960fe2364f63c86b8", "9b448f233c27135db2cd48939acff792");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cache","vodRecord","vodCollect");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `cache`");
      _db.execSQL("DELETE FROM `vodRecord`");
      _db.execSQL("DELETE FROM `vodCollect`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CacheDao.class, CacheDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VodRecordDao.class, VodRecordDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VodCollectDao.class, VodCollectDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public CacheDao getCacheDao() {
    if (_cacheDao != null) {
      return _cacheDao;
    } else {
      synchronized(this) {
        if(_cacheDao == null) {
          _cacheDao = new CacheDao_Impl(this);
        }
        return _cacheDao;
      }
    }
  }

  @Override
  public VodRecordDao getVodRecordDao() {
    if (_vodRecordDao != null) {
      return _vodRecordDao;
    } else {
      synchronized(this) {
        if(_vodRecordDao == null) {
          _vodRecordDao = new VodRecordDao_Impl(this);
        }
        return _vodRecordDao;
      }
    }
  }

  @Override
  public VodCollectDao getVodCollectDao() {
    if (_vodCollectDao != null) {
      return _vodCollectDao;
    } else {
      synchronized(this) {
        if(_vodCollectDao == null) {
          _vodCollectDao = new VodCollectDao_Impl(this);
        }
        return _vodCollectDao;
      }
    }
  }
}
