package com.erp.dao.impl;

import com.erp.dao.IUserDao;
import com.erp.entity.PageEntity;
import com.erp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("userDao")
public class UserDao implements IUserDao{
    @Autowired
    private IUserDao userDao;
    @Override
    public List<User> getAllUser(PageEntity page, User user) {
        return userDao.getAllUser(page, user);
    }

    @Override
    public int countAllUser(PageEntity pageEntity, User user) {
        return userDao.countAllUser(pageEntity, user);
    }

    @Override
    public List<User> getUserByUserNameOrId(User user) {
        return userDao.getUserByUserNameOrId(user);
    }

    @Override
    public List<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public String getUserImg(String idOrName) {
        return userDao.getUserImg(idOrName);
    }

    @Override
    public int deleteUser(String[]  ids) {
        return userDao.deleteUser(ids);
    }

    @Override
    public int freshImgHeader(User user) {
        return userDao.freshImgHeader(user);
    }

    @Override
    public int updateUserInfo(User user, boolean onlyUpdatePwd) {
        return userDao.updateUserInfo(user, onlyUpdatePwd);
    }

    @Override
    public int queryIdByEmpId(int empId) {
        return userDao.queryIdByEmpId(empId);
    }

    @Override
    public User isExistUser(String userOrder) {
        return userDao.isExistUser(userOrder);
    }

    @Override
    public int deleteUser_Role(String[] uids) {
        return userDao.deleteUser_Role(uids);
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int insertIntoUser_Role(int userId, int roleId) {
        return userDao.insertIntoUser_Role(userId, roleId);
    }

    @Override
    public int deleteUser_RoleByEachOther(int userId, int roleId) {
        return userDao.deleteUser_RoleByEachOther(userId, roleId);
    }

    @Override
    public String getMaxUserOrder() {
        return userDao.getMaxUserOrder();
    }

    @Override
    public List<User> getUserByOrder(String order) {
        return userDao.getUserByOrder(order);
    }


}
