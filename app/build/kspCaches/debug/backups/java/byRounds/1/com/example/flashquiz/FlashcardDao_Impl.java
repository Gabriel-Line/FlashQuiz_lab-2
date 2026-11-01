package com.example.flashquiz;

import android.database.Cursor;
import androidx.annotation.NonNull;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FlashcardDao_Impl implements FlashcardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Flashcard> __insertionAdapterOfFlashcard;

  public FlashcardDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFlashcard = new EntityInsertionAdapter<Flashcard>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `flashcard_table` (`id`,`question`,`answer`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Flashcard entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getQuestion());
        statement.bindString(3, entity.getAnswer());
      }
    };
  }

  @Override
  public void insertCard(final Flashcard flashcard) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFlashcard.insert(flashcard);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Flashcard> getAllCards() {
    final String _sql = "SELECT * FROM flashcard_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
      final int _cursorIndexOfAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
      final List<Flashcard> _result = new ArrayList<Flashcard>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Flashcard _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpQuestion;
        _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
        final String _tmpAnswer;
        _tmpAnswer = _cursor.getString(_cursorIndexOfAnswer);
        _item = new Flashcard(_tmpId,_tmpQuestion,_tmpAnswer);
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
