package net.qiujuer.web.italker.push.service;

import com.google.common.base.Strings;
import net.qiujuer.web.italker.push.bean.api.base.ResponseModel;
import net.qiujuer.web.italker.push.bean.api.user.UpdateInfoModel;
import net.qiujuer.web.italker.push.bean.card.UserCard;
import net.qiujuer.web.italker.push.bean.db.User;
import net.qiujuer.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户信息处理的Service

 */
// 127.0.0.1/api/user/...
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService extends BaseService {

    @GET
    @Path("/test")
    public String test(){
            return  "access to test";
    }


    // 用户信息修改接口
    // 返回自己的个人信息
    @PUT
    //@Path("") //127.0.0.1/api/user 不需要写，就是当前目录
    public ResponseModel<UserCard> update(UpdateInfoModel model) {
        if (!UpdateInfoModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();
        // 更新用户信息
        self = model.updateToUser(self);
        self = UserFactory.update(self);
        // 构架自己的用户信息
        UserCard card = new UserCard(self, true);
        // 返回
        return ResponseModel.buildOk(card);
    }


    //拉取联系人
    @GET
    @Path("/contact")
    public ResponseModel<List<UserCard>> contact(){
        User self = getSelf();

        //拿到我的联系人
        List<User> users = UserFactory.contacts(self);

        //转换为UserCard
        List<UserCard> userCards = users.stream().map(user -> {
            return new UserCard(user,true);
        }).collect(Collectors.toList());

        return ResponseModel.buildOk(userCards);
    }

    /**
     * 关注人操作，
     * 简化为双方同时关注
     * @param followId  被关注人
     * @return
     */
    @PUT
    @Path("/follow/{followId}")
    public ResponseModel<UserCard> follow(@PathParam("followId") String followId){
        User user = getSelf();

        //不能关注自己
        if(user.getId().equalsIgnoreCase(followId) || Strings.isNullOrEmpty(followId)){
            return ResponseModel.buildParameterError();
        }

        //被关注人
        User followUser =  UserFactory.findById(followId);
        if(followUser == null){
            return ResponseModel.buildNotFoundUserError(null);
        }

        //关注
        followUser = UserFactory.follow(user,followUser,null);
        if(followUser == null){
            return ResponseModel.buildServiceError();
        }

        /*
         *  TODO: 2018/5/14 通知我的关注的人我关注了ta
         */
        
        return ResponseModel.buildOk(new UserCard(followUser,true));
    }

    /**
     *  获取用户信息
     * @param id    用户id
     * @return UserCard
     */
    @GET
    @Path("{id}")
    public ResponseModel<UserCard>  getUser(@PathParam("id") String id){
        if(Strings.isNullOrEmpty(id)){
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();
        if(self.getId().equalsIgnoreCase(id)){
            return ResponseModel.buildOk(new UserCard(self,true));
        }

        User user = UserFactory.findById(id);
        if(user==null){
            return ResponseModel.buildNotFoundUserError(id);
        }else{
            boolean isFollow = UserFactory.getUserFollow(self,user) != null;
            return ResponseModel.buildOk(new UserCard(user,isFollow));
        }
    }


    /**
     * 搜索用户，并非仅仅搜索联系人，还有未关注的人
     * @param name  用户名
     * @return  List<UserCard>
     *     http://127.0.0.1:8080/api/user/search/{name}
     */
    @GET
    @Path("/search/{name:(.*)?}")   //名字为任意字符，可以为空
    public ResponseModel<List<UserCard>> search(@DefaultValue("") @PathParam("name") String name){
        User self = getSelf();

        //搜索所有数据
        final List<User> users = UserFactory.search(name);

        //我的联系人
        final  List<User> contacts = UserFactory.contacts(self);


        //转换为UserCard
        List<UserCard> userCards = users.stream().map(user -> {
            //判断这个人是不是我自己，或者这个人在我的联系人当中
            boolean isFollow = user.getId().equalsIgnoreCase(self.getId())
                    || contacts.stream().anyMatch(contactUser-> contactUser.getId().equalsIgnoreCase(user.getId()));

            return new UserCard(user,isFollow);
        }).collect(Collectors.toList());

        return ResponseModel.buildOk(userCards);
    }

}
