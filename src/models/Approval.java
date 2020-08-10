package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "approvals")
@Entity
@NamedQueries({
        @NamedQuery(name = "getAllApprovals", query = "SELECT a FROM Approval AS a ORDER BY a.id DESC"),
        @NamedQuery(name = "getApprovalsCount", query = "SELECT COUNT(a) FROM Report AS a"),
})

public class Approval {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Lob
    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "approval_status", nullable = false)
    private Integer approval_status;

    @Column(name = "approval_flag")
    private Integer approval_flag;

    @OneToOne(mappedBy = "approval")
    private Report report;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getApproval_status() {
        return approval_status;
    }

    public void setApproval_status(Integer approval_status) {
        this.approval_status = approval_status;
    }

    public Integer getApproval_flag() {
        return approval_flag;
    }

    public void setApproval_flag(Integer approval_flag) {
        this.approval_flag = approval_flag;
    }

}
