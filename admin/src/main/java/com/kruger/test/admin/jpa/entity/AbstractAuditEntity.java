package com.kruger.test.admin.jpa.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractAuditEntity {

    @Column(name = "CREATE_BY")
    private Long createBy;

    @Column(name = "CREATE_AT")
    private LocalDateTime createAt;

    @Column(name = "UPDATE_AT")
    private LocalDateTime updateAt;

    @Column(name = "UPDATE_BY")
    private Long updateBy;

    @Column(name = "DELETE_AT")
    private LocalDateTime deleteAt;

    @Column(name = "DELETE_BY")
    private Long deleteBy;

    @Column(name = "DELETE")
    private Boolean delete;

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(LocalDateTime deleteAt) {
        this.deleteAt = deleteAt;
    }

    public Long getDeleteBy() {
        return deleteBy;
    }

    public void setDeleteBy(Long deleteBy) {
        this.deleteBy = deleteBy;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    

}