package com.gap.bis_inspection.db.objectmodel;

import com.gap.bis_inspection.db.dao.DaoSession;
import com.gap.bis_inspection.db.dao.FormItemAnswerDao;
import com.gap.bis_inspection.db.dao.FormQuestionDao;
import com.gap.bis_inspection.db.dao.FormQuestionGroupDao;
import com.gap.bis_inspection.db.dao.FormQuestionGroupFormDao;
import com.gap.bis_inspection.db.dao.FormTempDao;

import java.util.List;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "FORM_QUESTION_GROUP".
 */
public class FormQuestionGroup {

    private Long id;
    private Long groupId;
    private String groupName;
    private Long formId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient FormQuestionGroupDao myDao;

    private List<FormQuestion> formQuestionList;
    private List<FormItemAnswer> formItemAnswerList;
    private List<FormTemp> formTempList;
    private List<FormQuestionGroupForm> formQuestionGroupFormId;

    public FormQuestionGroup() {
    }

    public FormQuestionGroup(Long id) {
        this.id = id;
    }

    public FormQuestionGroup(Long id, Long groupId, String groupName, Long formId) {
        this.id = id;
        this.groupId = groupId;
        this.groupName = groupName;
        this.formId = formId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFormQuestionGroupDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<FormQuestion> getFormQuestionList() {
        if (formQuestionList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FormQuestionDao targetDao = daoSession.getFormQuestionDao();
            List<FormQuestion> formQuestionListNew = targetDao._queryFormQuestionGroup_FormQuestionList(id);
            synchronized (this) {
                if(formQuestionList == null) {
                    formQuestionList = formQuestionListNew;
                }
            }
        }
        return formQuestionList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetFormQuestionList() {
        formQuestionList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<FormItemAnswer> getFormItemAnswerList() {
        if (formItemAnswerList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FormItemAnswerDao targetDao = daoSession.getFormItemAnswerDao();
            List<FormItemAnswer> formItemAnswerListNew = targetDao._queryFormQuestionGroup_FormItemAnswerList(id);
            synchronized (this) {
                if(formItemAnswerList == null) {
                    formItemAnswerList = formItemAnswerListNew;
                }
            }
        }
        return formItemAnswerList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetFormItemAnswerList() {
        formItemAnswerList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<FormTemp> getFormTempList() {
        if (formTempList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FormTempDao targetDao = daoSession.getFormTempDao();
            List<FormTemp> formTempListNew = targetDao._queryFormQuestionGroup_FormTempList(id);
            synchronized (this) {
                if(formTempList == null) {
                    formTempList = formTempListNew;
                }
            }
        }
        return formTempList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetFormTempList() {
        formTempList = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<FormQuestionGroupForm> getFormQuestionGroupFormId() {
        if (formQuestionGroupFormId == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FormQuestionGroupFormDao targetDao = daoSession.getFormQuestionGroupFormDao();
            List<FormQuestionGroupForm> formQuestionGroupFormIdNew = targetDao._queryFormQuestionGroup_FormQuestionGroupFormId(id);
            synchronized (this) {
                if(formQuestionGroupFormId == null) {
                    formQuestionGroupFormId = formQuestionGroupFormIdNew;
                }
            }
        }
        return formQuestionGroupFormId;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetFormQuestionGroupFormId() {
        formQuestionGroupFormId = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
