package net.qiujuer.web.italker.push.bean.db;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户的model，对应数据库
 */
@Entity
@Table(name = "TB_USER")
public class User {

    @Id
    @PrimaryKeyJoinColumn
    //主键生成的存储类型为uuid
    @GeneratedValue(generator = "uuid")
    //把uuid的生成器定义成uuid2，uuid2是常规的uuid toString
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @Column(nullable = false,updatable = false)
    private String id;

    @Column(nullable = false,unique = true,length = 128)
    private String name;

    @Column(nullable = false,unique = true,length = 62)
    private String phone;

    @Column(nullable = false)
    private String password;

    //头像允许为空
    private String portrait;

    private String description;

    @Column(nullable = false)
    private int sex = 0;

    //token可以拉取用户信息，所以必须唯一
    @Column(unique = true)
    private String token;

    //用于推送的id
    private String pushId;

    //定义为创建时间戳，创建时自动写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，更新时自动写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    //最后一次收到消息的时间
    private LocalDateTime lastReceiveAt = LocalDateTime.now();

    //我关注的人
    @JoinColumn(name = "originId")
    //定义为懒加载
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<UserFollow> following = new HashSet<UserFollow>();

    //关注我的人
    @JoinColumn(name = "targetId")
    //定义为懒加载
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<UserFollow> followers = new HashSet<UserFollow>();

    //我所有创建的群
    @JoinColumn(name = "ownerId")
    //懒加载集合，尽可能的不加载具体信息
    //当访问groups.size()时，仅仅查询数量，不加载具体的Group数据
    //只有当遍历Group时，才加载Group数据
    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<Group>();

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
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

    public LocalDateTime getLastReceiveAt() {
        return lastReceiveAt;
    }

    public void setLastReceiveAt(LocalDateTime lastReceiveAt) {
        this.lastReceiveAt = lastReceiveAt;
    }

    public Set<UserFollow> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserFollow> following) {
        this.following = following;
    }

    public Set<UserFollow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserFollow> followers) {
        this.followers = followers;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
