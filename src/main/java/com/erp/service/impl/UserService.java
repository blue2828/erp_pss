package com.erp.service.impl;

import com.erp.dao.IUserDao;
import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import com.erp.entity.User;
import com.erp.service.IUserService;
import com.erp.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userService")
public class UserService implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Override
    public List<User> getAllUser(PageEntity page, User user) {
        List<User> userList = new ArrayList<User>();
        try {
            userList = userDao.getAllUser(page, user);
        }catch (Exception e) {
            userList.clear();
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public List<User> getUserByUserNameOrId(User user) {
        List<User> resultUser = new ArrayList<User>();
        try {
            resultUser = userDao.getUserByUserNameOrId(user);
        }catch (Exception e) {
            resultUser.clear();
            e.printStackTrace();
        }
        return resultUser;
    }

    @Override
    public List<User> getUserById(int id) {
        List<User> resultUser = new ArrayList<User>();
        try {
            resultUser = userDao.getUserById(id);
        }catch (Exception e) {
            resultUser.clear();
            e.printStackTrace();
        }
        return resultUser;
    }

    @Override
    public int countAllUser(PageEntity pageEntity, User user) {
        int count = 0;
        try {
            count = userDao.countAllUser(pageEntity, user);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public String getUserImg(String idOrName) {
        String imgHeader = "";
        try {
            imgHeader = userDao.getUserImg(idOrName) == null ? "" : userDao.getUserImg(idOrName);
        }catch (Exception e) {
            imgHeader = "";
            e.printStackTrace();
        }
        return imgHeader;
    }

    @Override
    @Transactional
    public int deleteUser(String[]  ids) {
        int flag = 0;
        try{
             flag = userDao.deleteUser(ids);
        }catch(Exception e){
            flag = -1;
            e.printStackTrace();
        }
        return flag;

    }

    @Override
    @Transactional
    public int addUser(User user) {
        int flag = 0;
        try {
            flag = userDao.addUser(user);
        }catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    @Transactional
    public int freshImgHeader(User user) {
        int num = 0;
        try {
            num = userDao.freshImgHeader(user);
        }catch (Exception e) {
            num = 0;
            e.printStackTrace();
        }
        return num;
    }

    @Override
    @Transactional
    public int updateUserInfo(User user, boolean onlyUpdatePwd) {
        int flag = 0;
        try {
            flag = userDao.updateUserInfo(user, onlyUpdatePwd);
        }catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public int queryIdByEmpId(int empId) {
        int id = 0;
        try {
            id = userDao.queryIdByEmpId(empId);
        }catch (Exception e) {
            id = 0;
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public User isExistUser(String userOrder) {
        User isExist = null;
        try {
            isExist = userDao.isExistUser(userOrder);
        }catch (Exception e) {
            isExist = null;
            e.printStackTrace();
        }
        return isExist;
    }

    @Override
    @Transactional
    public int deleteUser_Role(String[] uids) {
        int flag = 0;
        try {
            flag = userDao.deleteUser_Role(uids);
        }catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    @Transactional
    public int insertIntoUser_Role(int userId, int roleId) {
        int flag = 0;
        List<User> targetUserList = this.getUserById(userId);
        Set<Integer> roleIdSet = new HashSet<Integer> ();
        for (User user : targetUserList) {
            List<Role> roleList = user.getRoleList();
            for(Role role : roleList) {
                roleIdSet.add(role.getId());
            }
        }
        try {
            for(int id : roleIdSet) {
                if (String.valueOf(id).equals(String.valueOf(roleId)))
                    return 1;
            }
            flag = userDao.insertIntoUser_Role(userId, roleId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    @Transactional
    public int deleteUser_RoleByEachOther(int userId, int roleId) {
        int flag = 0;
        try {
            flag = userDao.deleteUser_RoleByEachOther(userId, roleId);
        }catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public String getMaxUserOrder() {
        String maxUserOrder = "0";
        try {
            maxUserOrder = userDao.getMaxUserOrder();
            if (StringUtil.isEmpty(maxUserOrder))
                maxUserOrder = "201801";
        }catch (Exception e) {
            maxUserOrder = "0";
            e.printStackTrace();
        }
        return maxUserOrder;
    }

    @Override
    public List<User> getUserByOrder(String order) {
        List<User> list = new ArrayList<User>();
        try {
            list = userDao.getUserByOrder(order);
        }catch (Exception e) {
            list.clear();
            e.printStackTrace();
        }
        return list;
    }


}
