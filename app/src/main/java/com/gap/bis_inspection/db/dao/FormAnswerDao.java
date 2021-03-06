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

import com.gap.bis_inspection.db.objectmodel.FormAnswer;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FORM_ANSWER".
*/
public class FormAnswerDao extends AbstractDao<FormAnswer, Long> {

    public static final String TABLENAME = "FORM_ANSWER";

    /**
     * Properties of entity FormAnswer.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property CarId = new Property(2, Long.class, "carId", false, "CAR_ID");
        public final static Property LineId = new Property(3, Long.class, "lineId", false, "LINE_ID");
        public final static Property MinScore = new Property(4, Integer.class, "minScore", false, "MIN_SCORE");
        public final static Property MaxScore = new Property(5, Integer.class, "maxScore", false, "MAX_SCORE");
        public final static Property StartDate = new Property(6, java.util.Date.class, "startDate", false, "START_DATE");
        public final static Property EndDate = new Property(7, java.util.Date.class, "endDate", false, "END_DATE");
        public final static Property StatusEn = new Property(8, Integer.class, "statusEn", false, "STATUS_EN");
        public final static Property FormStatus = new Property(9, Integer.class, "formStatus", false, "FORM_STATUS");
        public final static Property StatusDate = new Property(10, java.util.Date.class, "statusDate", false, "STATUS_DATE");
        public final static Property SendingStatusEn = new Property(11, Integer.class, "sendingStatusEn", false, "SENDING_STATUS_EN");
        public final static Property SendingStatusDate = new Property(12, java.util.Date.class, "sendingStatusDate", false, "SENDING_STATUS_DATE");
        public final static Property XLatitude = new Property(13, String.class, "xLatitude", false, "X_LATITUDE");
        public final static Property YLongitude = new Property(14, String.class, "yLongitude", false, "Y_LONGITUDE");
        public final static Property ServerAnswerInfoId = new Property(15, Long.class, "serverAnswerInfoId", false, "SERVER_ANSWER_INFO_ID");
        public final static Property FormId = new Property(16, long.class, "formId", false, "FORM_ID");
    };

    private DaoSession daoSession;

    private Query<FormAnswer> form_FormAnswerListQuery;

    public FormAnswerDao(DaoConfig config) {
        super(config);
    }
    
    public FormAnswerDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FORM_ANSWER\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"CAR_ID\" INTEGER," + // 2: carId
                "\"LINE_ID\" INTEGER," + // 3: lineId
                "\"MIN_SCORE\" INTEGER," + // 4: minScore
                "\"MAX_SCORE\" INTEGER," + // 5: maxScore
                "\"START_DATE\" INTEGER," + // 6: startDate
                "\"END_DATE\" INTEGER," + // 7: endDate
                "\"STATUS_EN\" INTEGER," + // 8: statusEn
                "\"FORM_STATUS\" INTEGER," + // 9: formStatus
                "\"STATUS_DATE\" INTEGER," + // 10: statusDate
                "\"SENDING_STATUS_EN\" INTEGER," + // 11: sendingStatusEn
                "\"SENDING_STATUS_DATE\" INTEGER," + // 12: sendingStatusDate
                "\"X_LATITUDE\" TEXT," + // 13: xLatitude
                "\"Y_LONGITUDE\" TEXT," + // 14: yLongitude
                "\"SERVER_ANSWER_INFO_ID\" INTEGER," + // 15: serverAnswerInfoId
                "\"FORM_ID\" INTEGER NOT NULL );"); // 16: formId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FORM_ANSWER\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, FormAnswer entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        Long carId = entity.getCarId();
        if (carId != null) {
            stmt.bindLong(3, carId);
        }
 
        Long lineId = entity.getLineId();
        if (lineId != null) {
            stmt.bindLong(4, lineId);
        }
 
        Integer minScore = entity.getMinScore();
        if (minScore != null) {
            stmt.bindLong(5, minScore);
        }
 
        Integer maxScore = entity.getMaxScore();
        if (maxScore != null) {
            stmt.bindLong(6, maxScore);
        }
 
        java.util.Date startDate = entity.getStartDate();
        if (startDate != null) {
            stmt.bindLong(7, startDate.getTime());
        }
 
        java.util.Date endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindLong(8, endDate.getTime());
        }
 
        Integer statusEn = entity.getStatusEn();
        if (statusEn != null) {
            stmt.bindLong(9, statusEn);
        }
 
        Integer formStatus = entity.getFormStatus();
        if (formStatus != null) {
            stmt.bindLong(10, formStatus);
        }
 
        java.util.Date statusDate = entity.getStatusDate();
        if (statusDate != null) {
            stmt.bindLong(11, statusDate.getTime());
        }
 
        Integer sendingStatusEn = entity.getSendingStatusEn();
        if (sendingStatusEn != null) {
            stmt.bindLong(12, sendingStatusEn);
        }
 
        java.util.Date sendingStatusDate = entity.getSendingStatusDate();
        if (sendingStatusDate != null) {
            stmt.bindLong(13, sendingStatusDate.getTime());
        }
 
        String xLatitude = entity.getXLatitude();
        if (xLatitude != null) {
            stmt.bindString(14, xLatitude);
        }
 
        String yLongitude = entity.getYLongitude();
        if (yLongitude != null) {
            stmt.bindString(15, yLongitude);
        }
 
        Long serverAnswerInfoId = entity.getServerAnswerInfoId();
        if (serverAnswerInfoId != null) {
            stmt.bindLong(16, serverAnswerInfoId);
        }
        stmt.bindLong(17, entity.getFormId());
    }

    @Override
    protected void attachEntity(FormAnswer entity) {
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
    public FormAnswer readEntity(Cursor cursor, int offset) {
        FormAnswer entity = new FormAnswer( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // carId
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // lineId
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // minScore
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // maxScore
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)), // startDate
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)), // endDate
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // statusEn
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // formStatus
            cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)), // statusDate
            cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11), // sendingStatusEn
            cursor.isNull(offset + 12) ? null : new java.util.Date(cursor.getLong(offset + 12)), // sendingStatusDate
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // xLatitude
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // yLongitude
            cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15), // serverAnswerInfoId
            cursor.getLong(offset + 16) // formId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, FormAnswer entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCarId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setLineId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setMinScore(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setMaxScore(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setStartDate(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
        entity.setEndDate(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
        entity.setStatusEn(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setFormStatus(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setStatusDate(cursor.isNull(offset + 10) ? null : new java.util.Date(cursor.getLong(offset + 10)));
        entity.setSendingStatusEn(cursor.isNull(offset + 11) ? null : cursor.getInt(offset + 11));
        entity.setSendingStatusDate(cursor.isNull(offset + 12) ? null : new java.util.Date(cursor.getLong(offset + 12)));
        entity.setXLatitude(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setYLongitude(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setServerAnswerInfoId(cursor.isNull(offset + 15) ? null : cursor.getLong(offset + 15));
        entity.setFormId(cursor.getLong(offset + 16));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(FormAnswer entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(FormAnswer entity) {
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
    
    /** Internal query to resolve the "formAnswerList" to-many relationship of Form. */
    public List<FormAnswer> _queryForm_FormAnswerList(long formId) {
        synchronized (this) {
            if (form_FormAnswerListQuery == null) {
                QueryBuilder<FormAnswer> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.FormId.eq(null));
                form_FormAnswerListQuery = queryBuilder.build();
            }
        }
        Query<FormAnswer> query = form_FormAnswerListQuery.forCurrentThread();
        query.setParameter(0, formId);
        return query.list();
    }

}
