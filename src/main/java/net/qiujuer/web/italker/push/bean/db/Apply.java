package net.qiujuer.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户申请表
 */
@Entity
@Table(name = "TB_APPLY")
public class Apply {
    public static final int TYPE_ADD_USER = 1;
    public static final int TYPE_ADD_GROUP = 2;

    @Id
    @PrimaryKeyJoinColumn
    //主键生成的存储类型为uuid
    @GeneratedValue(generator = "uuid")
    //把uuid的生成器定义成uuid2，uuid2是常规的uuid toString
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @Column(nullable = false,updatable = false)
    private String id;

    //描述
    @Column(nullable = false)
    private String description;

    //附件
    @Column(columnDefinition = "TEXT")
    private String attach;

    //申请的类型
    @Column(nullable = false)
    private int type;

    //申请目标id，type:TYPE_ADD_USER --> User.id;type:TYPE_ADD_GROUP --> Group.id;
    @Column(nullable = false)
    private String targetId;

    //申请人，可为空，系统人员
    @ManyToOne
    @JoinColumn(name = "applicantId")
    private User applicant;

    @Column(insertable = false,updatable = false)
    private String applicantId;

    //定义为创建时间戳，创建时自动写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，更新时自动写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
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
}
