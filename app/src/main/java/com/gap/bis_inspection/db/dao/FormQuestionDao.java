package com.gap.bis_inspection.db.dao;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

import com.gap.bis_inspection.db.objectmodel.FormQuestion;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FORM_QUESTION".
*/
public class FormQuestionDao extends AbstractDao<FormQuestion, Long> {

    public static final String TABLENAME = "FORM_QUESTION";

    /**
     * Properties of entity FormQuestion.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Question = new Property(1, String.class, "question", false, "QUESTION");
        public final static Property AnswerTypeEn = new Property(2, Integer.class, "answerTypeEn", false, "ANSWER_TYPE_EN");
        public final static Property AnswerInt = new Property(3, Integer.class, "answerInt", false, "ANSWER_INT");
        public final static Property AnswerStr = new Property(4, String.class, "answerStr", false, "ANSWER_STR");
        public final static Property ServerAnswerId = new Property(5, Long.class, "serverAnswerId", false, "SERVER_ANSWER_ID");
        public final static Property InputValuesDefault = new Property(6, String.class, "inputValuesDefault", false, "INPUT_VALUES_DEFAULT");
        public final static Property FormId = new Property(7, long.class, "formId", false, "FORM_ID");
        public final static Property FormQuestionGroupId = new Property(8, long.class, "formQuestionGroupId", false, "FORM_QUESTION_GROUP_ID");
    };

    private DaoSession daoSession;

    private Query<FormQuestion> form_FormQuestionListQuery;
    private Query<FormQuestion> formQuestionGroup_FormQuestionListQuery;

    public FormQuestionDao(DaoConfig config) {
        super(config);
    }
    
    public FormQuestionDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FORM_QUESTION\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"QUESTION\" TEXT," + // 1: question
                "\"ANSWER_TYPE_EN\" INTEGER," + // 2: answerTypeEn
                "\"ANSWER_INT\" INTEGER," + // 3: answerInt
                "\"ANSWER_STR\" TEXT," + // 4: answerStr
                "\"SERVER_ANSWER_ID\" INTEGER," + // 5: serverAnswerId
                "\"INPUT_VALUES_DEFAULT\" TEXT," + // 6: inputValuesDefault
                "\"FORM_ID\" INTEGER NOT NULL ," + // 7: formId
                "\"FORM_QUESTION_GROUP_ID\" INTEGER NOT NULL );"); // 8: formQuestionGroupId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FORM_QUESTION\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, FormQuestion entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String question = entity.getQuestion();
        if (question != null) {
            stmt.bindString(2, question);
        }
 
        Integer answerTypeEn = entity.getAnswerTypeEn();
        if (answerTypeEn != null) {
            stmt.bindLong(3, answerTypeEn);
        }
 
        Integer answerInt = entity.getAnswerInt();
        if (answerInt != null) {
            stmt.bindLong(4, answerInt);
        }
 
        String answerStr = entity.getAnswerStr();
        if (answerStr != null) {
            stmt.bindString(5, answerStr);
        }
 
        Long serverAnswerId = entity.getServerAnswerId();
        if (serverAnswerId != null) {
            stmt.bindLong(6, serverAnswerId);
        }
 
        String inputValuesDefault = entity.getInputValuesDefault();
        if (inputValuesDefault != null) {
            stmt.bindString(7, inputValuesDefault);
        }
        stmt.bindLong(8, entity.getFormId());
        stmt.bindLong(9, entity.getFormQuestionGroupId());
    }

    @Override
    protected void attachEntity(FormQuestion entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public FormQuestion readEntity(Cursor cursor, int offset) {
        FormQuestion entity = new FormQuestion( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // question
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // answerTypeEn
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // answerInt
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // answerStr
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // serverAnswerId
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // inputValuesDefault
            cursor.getLong(offset + 7), // formId
            cursor.getLong(offset + 8) // formQuestionGroupId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, FormQuestion entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setQuestion(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAnswerTypeEn(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setAnswerInt(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setAnswerStr(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setServerAnswerId(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setInputValuesDefault(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setFormId(cursor.getLong(offset + 7));
        entity.setFormQuestionGroupId(cursor.getLong(offset + 8));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(FormQuestion entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(FormQuestion entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "formQuestionList" to-many relationship of Form. */
    public List<FormQuestion> _queryForm_FormQuestionList(long formId) {
        synchronized (this) {
            if (form_FormQuestionListQuery == null) {
                QueryBuilder<FormQuestion> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.FormId.eq(null));
                form_FormQuestionListQuery = queryBuilder.build();
            }
        }
        Query<FormQuestion> query = form_FormQuestionListQuery.forCurrentThread();
        query.setParameter(0, formId);
        return query.list();
    }

    /** Internal query to resolve the "formQuestionList" to-many relationship of FormQuestionGroup. */
    public List<FormQuestion> _queryFormQuestionGroup_FormQuestionList(long formQuestionGroupId) {
        synchronized (this) {
            if (formQuestionGroup_FormQuestionListQuery == null) {
                QueryBuilder<FormQuestion> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.FormQuestionGroupId.eq(null));
                formQuestionGroup_FormQuestionListQuery = queryBuilder.build();
            }
        }
        Query<FormQuestion> query = formQuestionGroup_FormQuestionListQuery.forCurrentThread();
        query.setParameter(0, formQuestionGroupId);
        return query.list();
    }

}
