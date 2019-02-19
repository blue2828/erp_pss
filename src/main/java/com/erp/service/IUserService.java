package com.erp.service;

import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import com.erp.entity.User;
import java.util.List;

public interface IUserService {
    List<User> getAllUser(PageEntity page, User user);
    List<User> getUserByUserNameOrId (User user);
    List<User> getUserById(int id);
    public int countAllUser(PageEntity pageEntity, User user);
    public String getUserImg(String id);
    int deleteUser (String[]  ids);
    int addUser(User user);
    int freshImgHeader(User user);
    int updateUserInfo(User user, boolean onlyUpdatePwd);
    int queryIdByEmpId(int empId);
    User isExistUser(String userOrder);
    int deleteUser_Role(String[] uids);
    int insertIntoUser_Role(int userId, int roleId);
    int deleteUser_RoleByEachOther(int userId, int roleId);
    String getMaxUserOrder();
    List<User> getUserByOrder(String order);
}
