package net.qiujuer.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_GROUP")
public class Group {
    @Id
    @PrimaryKeyJoinColumn
    //主键生成的存储类型为uuid
    @GeneratedValue(generator = "uuid")
    //把uuid的生成器定义成uuid2，uuid2是常规的uuid toString
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @Column(nullable = false,updatable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String picture;

    //定义为创建时间戳，创建时自动写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，更新时自动写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    //群的创建者
    //optional=false:必须有
    //fetch = FetchType.EAGER：加载群信息时，必须加载创建者信息
    //cascade = CascadeType.ALL:所有的更改，都进行更新
    @ManyToOne(optional = false,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerId")
    private User owner;
    @Column(updatable = false,insertable = false,nullable = false)
    private String ownerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
