package net.qiujuer.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 群和用户之间是多对多的关系，这里使用的中间bean，将多对多，拆分为：1对多
 */
@Entity
@Table(name = "TB_GROUP_MEMBER")
public class GroupMember {
    public static final int NOTIFY_LEVEL_INVALID = -1;  //默认不接受通知
    public static final int NOTIFY_LEVEL_NONE = 0;  //默认通知
    public static final int NOTIFY_LEVEL_CLOSE = 1; //接受消息不提示

    public static final int PERMISSION_TYPE_NONE = 0;   //普通用户
    public static final int PERMISSION_TYPE_ADMIN = 1;  //管理员
    public static final int PERMISSION_TYPE_ADMIN_SU = 100; //创建者

    @Id
    @PrimaryKeyJoinColumn
    //主键生成的存储类型为uuid
    @GeneratedValue(generator = "uuid")
    //把uuid的生成器定义成uuid2，uuid2是常规的uuid toString
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @Column(nullable = false,updatable = false)
    private String id;

    //别名
    private String alias;

    //消息通知级别
    @Column(nullable = false)
    private int notifyLevel = NOTIFY_LEVEL_NONE;

    //权限级别
    @Column(nullable = false)
    private int permissionLevel =PERMISSION_TYPE_NONE;

    //定义为创建时间戳，创建时自动写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，更新时自动写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();


    //对应的用户；一个用户，可以在多个群中，担任群成员
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private User user;

    @Column(updatable = false,insertable = false,nullable = false)
    private String userId;

    //对应的群；一个群，可以有多个群成员
    @JoinColumn(name = "groupId")
    @ManyToOne(optional = false,fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Group group;

    @Column(updatable = false,insertable = false,nullable = false)
    private String groupId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(int notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
