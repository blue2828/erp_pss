package com.erp.contorller.SystemUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.erp.activemq.JmsProducer;
import com.erp.entity.Employee;
import com.erp.entity.PageEntity;
import com.erp.entity.Role;
import com.erp.entity.User;
import com.erp.entity.extraEntity.userInfoForExcel;
import com.erp.service.IPermissionService;
import com.erp.service.IUserService;
import com.erp.utils.ExcelUtil;
import com.erp.utils.FileUtil;
import com.erp.utils.StringUtil;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RequestMapping("/sys/usr")
@Controller
@CrossOrigin
@Scope("prototype")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private StringUtil stringUtil;
    @Autowired
    private ExcelUtil excelUtil;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private JmsProducer jmsProducer;
    /**
     * @detials 验证登录
     * @param request
     * @param userNameOrId
     * @param password
     * @param rememberMe
     * @return
     */
    @RequestMapping("/validateUser")
    @ResponseBody
    public Map<String, Object> validateUser (@RequestParam(required = false) boolean fromLoginVue, HttpServletRequest request,
                                             String userNameOrId, String password, boolean rememberMe) {
        Map<String, Object> map = new HashMap<String, Object>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userNameOrId, password, rememberMe);
        User tempUser = userUtilInController(userNameOrId);
        List<User> requiredList = userService.getUserByUserNameOrId(tempUser);
        if (requiredList.size() <=  0) {
            if (StringUtil.isEmpty(requiredList.get(0).getPassword()))
                map.put("msg", "密码错误");
            map.put("msg", "账号不存在");
            map.put("success", false);
            return map;
        }
        String msg = null;
        try {
            subject.login(token);
        }catch (UnknownAccountException ue) {
            logger.error("UnknownAccountException => 账号不存在");
            msg = "账号不存在";
            if (!fromLoginVue) {
                Destination destination = new ActiveMQQueue("pushMsg");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("errMsg", "尚未登录或者登录超时");
                jmsProducer.sendMessage(destination, jsonObject.toJSONString());
            }

        }catch (IncorrectCredentialsException ie) {
            logger.error("IncorrectCredentialsException => 密码不正确");
            msg = "密码不正确";
        }catch (LockedAccountException le) {
            logger.error("LockedAccountException => 锁定的账号");
            msg = "锁定的账号";
        }catch (DisabledAccountException de) {
            logger.error("DisabledAccountException => 禁用的账号");
            msg = "禁用的账号";
        }catch (ExcessiveAttemptsException ee) {
            logger.error("ExcessiveAttemptsException => 过期的凭证");
            msg = "过期的凭证";
        }catch (UnauthorizedException ue) {
            logger.error("AuthorizationException => 没有权限操作");
            msg = "没有权限操作";
        }catch (NullPointerException ne) {
            logger.error("NullPointerException => 账号不存在");
            msg = "账号不存在";
        }
        map.put("msg", msg);
        map.put("success", null == msg ? true : false);
        return map;
    }

    /**
     * 判断是否已经登录
     * @return
     */
    @RequestMapping("/isLogined")
    @ResponseBody
    public Map<String, Object> isLogined () {
        Map<String, Object> map = new HashMap<String, Object>();
        Subject subject = SecurityUtils.getSubject();
        boolean isLogined = false;
        if (null != subject.getPrincipal())
            isLogined = true;
        else
            isLogined = false;
        map.put("isLogined", isLogined);
        return map;
    }

    /**
     * 获取全部的用户信息
     * 要求权限user:select
     * @param pageEntity
     * @return
     */
    @RequestMapping("/getAllUser")
    @ResponseBody
    @RequiresPermissions("user:select")
    public JSONObject getAllUser(PageEntity pageEntity, String searchAccount) {
        JSONObject jsonObject = new JSONObject();
        User user = new User();
        if (searchAccount.matches("^[\\d]+$"))
            user.setUserOrder(searchAccount);
        else
            user.setUserName(searchAccount);
        List<User> list = userService.getAllUser(pageEntity, user);
        int countUser = userService.countAllUser(pageEntity, user);
        jsonObject.put("userInfo", list);
        jsonObject.put("total", countUser);
        return jsonObject;
    }
    /**
     * 获取用户头像
     */
    @RequestMapping("/getUserImg")
    @ResponseBody
    public String getUserImg (@RequestParam(required = false) String idOrName, @RequestParam(required = false) boolean fromIndexVue) {
        String imgBase64 = "";
        BufferedInputStream bufferedInputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        InputStream inputStream = null;
        byte[] base64Bytes = null;
        try {
            if (null == idOrName && fromIndexVue) {
                Subject subject = SecurityUtils.getSubject();
                idOrName = (String) subject.getPrincipal();
            }
            String imgHeader = userService.getUserImg(idOrName);
            File file = new File(imgHeader);
            int temp = 0;
            byte[] bytes = new byte[1024];
            byteArrayOutputStream = new ByteArrayOutputStream();
            if (file.exists() && null != file) {
                inputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(inputStream);
                while ((temp = bufferedInputStream.read(bytes)) != -1) {
                    byteArrayOutputStream.write(bytes, 0, temp);
                    byteArrayOutputStream.flush();
                }
                base64Bytes = byteArrayOutputStream.toByteArray();
            }else
                base64Bytes = new byte[0];
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != byteArrayOutputStream)
                    byteArrayOutputStream.close();
                if (null != bufferedInputStream)
                    bufferedInputStream.close();
                if (null != inputStream)
                    inputStream.close();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        imgBase64 = JSON.toJSONString(base64Bytes);
        return imgBase64;
    }
    /**
     *删除用户
     */
    @RequestMapping("/deleteUser")//获取当前用户信息
    @ResponseBody
    @RequiresPermissions("user:delete")
    public Map<String, Object> deleteUser(String ids) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String [] idArr = ids.split(",");
        String errMsg = "用户信息删除失败";
        List<String> idsList = Arrays.asList(idArr);
        Subject subject = SecurityUtils.getSubject();
        String idOrName = (String) subject.getPrincipal();
        User targetUser = userUtilInController(idOrName);
        List<User> userList = userService.getUserByUserNameOrId(targetUser);
        if (userList.size() > 0) {
            boolean isContained = idsList.contains(String.valueOf(userList.get(0).getId()));
            if (isContained) {
                resultMap.put("success", false);
                resultMap.put("errMsg", "删除数据包含本人，不可删除！");
                return resultMap;
            }
        }
        int flag = userService.deleteUser(idArr);
        int deleteUserRoleFlag = userService.deleteUser_Role(idArr);
        boolean isSuccess = false;
        switch (flag) {
            case 0 :
                if (deleteUserRoleFlag == 0)
                    logger.info("从用户表删除用户失败");
                else
                    logger.info("从用户权限表删除记录失败");
                errMsg = "删除失败";
                logger.error("删除");
                isSuccess = false;
                break;
            default :
                if (deleteUserRoleFlag == 0) {
                    logger.info("从用户权限表删除记录失败");
                    isSuccess = false;
                    errMsg = "删除失败";
                }else
                    isSuccess = true;
        }
        resultMap.put("success", isSuccess);
        resultMap.put("errMsg", errMsg);
        return resultMap;
    }

    @RequestMapping("/getUserByIdOrName")
    @ResponseBody
    public Map<String, Object> getUserByIdOrName(String idOrName) {
        Map<String, Object> map = new HashMap<String, Object> ();
        User user = userUtilInController(idOrName);
        List<User> resultUser = userService.getUserByUserNameOrId(user);
        map.put("resultUser", resultUser.size() > 0 ? resultUser.get(0) : null);
        return map;
    }

    @RequestMapping("/getCurrentUser")
    @ResponseBody
    public Map<String, Object> getCurrentUser () {
        Map<String, Object> map = new HashMap<String, Object> ();
        Subject subject = SecurityUtils.getSubject();
        String idOrName = (String) subject.getPrincipal();
        User user = userUtilInController(idOrName);
        List<User> resultUser = userService.getUserByUserNameOrId(user);
        map.put("currentUser", resultUser.size() > 0 ? resultUser.get(0) : null);
        return map;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public Map<String, Object> logout () {
        Subject subject = SecurityUtils.getSubject();
        Map<String, Object> map = new HashMap<String, Object>();
        boolean isCompleted = false;
        try {
            subject.logout();
            isCompleted = true;
        }catch (Exception e) {
            isCompleted = false;
            e.printStackTrace();
        }
        map.put("success", isCompleted);
        return map;
    }

    @RequestMapping("/saveUserInfo")
    @ResponseBody
    @RequiresPermissions(value = { "user:edit", "user:add" }, logical = Logical.OR)
    public Map<String, Object> saveUserInfo (@RequestParam(value = "file", required = false) MultipartFile multipartFile,  User user,
                                             @RequestParam(required = false) String updaterId, boolean isEdit, @RequestParam(required = false) String employeeId,
                                             boolean onlyUpdatePwd, String roleIds) {
        Map<String, Object> resultMap = new HashMap<String, Object> ();
        StringBuffer errMsg = new StringBuffer();
        boolean saveImg = false;
        boolean isTransferToDisked = false;
        int flag = 0;
        if (!StringUtil.isEmpty(employeeId))
            user.setEmployee(new Employee(Integer.parseInt(employeeId)));
        User isExistUser = userService.isExistUser(user.getUserOrder()); //判断要输入框要修改的用户编号是否存在用户
        if (null != isExistUser) { //如果存在
            if (isExistUser.getId() != user.getId()) {
                resultMap.put("success", false);
                resultMap.put("errMsg", "该用户编号已经被占用");
                return resultMap;
            }
        }
        int resultId = userService.queryIdByEmpId(StringUtil.isEmpty(employeeId) ? 0 : Integer.parseInt(employeeId));
        if (resultId !=0 && resultId != user.getId()) {
            resultMap.put("success", false);
            resultMap.put("errMsg", "一个用户只能关联一个员工");
            return resultMap;
        }
        if (null != multipartFile) { //如果有上传的头像
            String storePath = "d:/fileUpload";
            FileUtil.checkDirOrMk(storePath);
            String fileName = multipartFile.getOriginalFilename();
            fileName = storePath.concat(File.separator).concat(FileUtil.mkFileName(fileName));
            File targetFile = new File(fileName);
            try {
                multipartFile.transferTo(targetFile);
                isTransferToDisked = true;
            }catch (IOException e) {
                logger.info("头像保存到磁盘失败");
                errMsg.append("头像上传失败");
                isTransferToDisked = false;
                e.printStackTrace();
            }finally {
                user.setImgHeader(fileName);
                if (isEdit) {
                    int freshRsNum = userService.freshImgHeader(user);
                    switch (freshRsNum) {
                        case 0 :
                            errMsg.append("头像上传失败");
                            logger.info("头像地址保存到数据库失败");
                            saveImg = false;
                            if (isTransferToDisked) {
                                if (null == targetFile)
                                    new File (fileName).delete();
                                else
                                    targetFile.delete();
                            }
                            break;
                        default:
                            saveImg = true;
                    }
                }
            }
        }
        String[] roleIdArr = roleIds.split(",");
        List<User> thisUserList = userService.getUserById(user.getId());
        for (User u : thisUserList) {
            List<Role> listRole = u.getRoleList();
            for (Role role : listRole) {
                for (String roleId : roleIdArr) {
                    if (!String.valueOf(role.getId()).equals(roleId)) {
                        int innerFlag = userService.deleteUser_RoleByEachOther(user.getId(), role.getId());
                        switch (innerFlag) {
                            case 0 :
                                logger.info("从用户权限表删除记录失败，roleId=".concat(String.valueOf(role.getId()))
                                        .concat("uid=").concat(String.valueOf(user.getId())));
                                break;
                        }
                    }
                }
            }
        }

        user.setUpdateTime(stringUtil.formatStrTimeToDate(stringUtil.getCurrentTimeStr(), "yyyy-MM-dd HH:mm:ss"));
        List<User> updater = new ArrayList<>();
        updater.add(new User(StringUtil.isEmpty(updaterId) ? 0 : Integer.parseInt(updaterId)));
        user.setUser(updater);
        String maxUserOrder = "0";
        if (isEdit)
            flag = userService.updateUserInfo(user, onlyUpdatePwd);
        else {
            maxUserOrder = userService.getMaxUserOrder();
            maxUserOrder = StringUtil.formatDecimalStrToIntStr(maxUserOrder);
            user.setUserOrder(maxUserOrder);
            flag = userService.addUser(user);
        }
        switch (flag) {
            case 0 :
                errMsg.append(StringUtil.isEmpty(errMsg.toString()) ? "信息保存失败" : ",信息保存失败");
                break;
        }
        int temp = 0;
        for (String id : roleIdArr) {
            int innerFlag = 0;
            if (!isEdit) {
                List<User> list = userService.getUserByOrder(maxUserOrder);
                if (list.size() > 0)
                    innerFlag = userService.insertIntoUser_Role(list.get(0).getId(), Integer.parseInt(id));
            }else
                innerFlag = userService.insertIntoUser_Role(user.getId(), Integer.parseInt(id));
            switch (innerFlag) {
                case 1 :
                    temp ++;
                    break;
            }
        }
        if (roleIdArr.length != temp) {
            logger.error("部分或全部角色保存失败");
            errMsg.append(StringUtil.isEmpty(errMsg.toString()) ? "部分或全部角色保存失败" : ",部分或全部角色保存失败");
        }
        resultMap.put("success", roleIdArr.length == temp && flag > 0 && (multipartFile == null ? true : isTransferToDisked) &&
                (multipartFile == null ? true : isEdit ? saveImg : true) );
        resultMap.put("errMsg", errMsg.toString());
        return resultMap;
    }

    public static User userUtilInController (String idOrName) {
        boolean flag = false;
        if (!StringUtil.isEmpty(idOrName))
            flag = idOrName.matches("^[\\d]+$");
        else
            flag = false;
        User user = null;
        if (flag)
            user = new User(Integer.parseInt(idOrName));
        else
            user = new User(idOrName);
        return user;
    }

    /**
     * 导出选中的用户信息到excel表
     */
    @RequestMapping(path = "/exportUserInfoToExcel")
    @RequiresPermissions("user:export")
    @ResponseBody
    public void exportUserInfoToExcel (String data, HttpServletResponse response) {
        String[] cellTitleName = { "用户账号", "用户名", "员工编号", "信息修改人", "信息修改时间" };
        int[] cw = {30, 20, 30, 20, 30};
        BufferedOutputStream out = null;
        OutputStream outputStream = null;
        List<userInfoForExcel> excelInfos = JSON.parseArray(data.substring(data.indexOf("=") + 1), userInfoForExcel.class);
        HSSFWorkbook wb = excelUtil.getExcelWb("用户信息", "用户信息", cw, cellTitleName, excelInfos);
        try {
            response.setHeader("content-disposition", "attachment;filename=".concat(URLEncoder.encode("用户信息表_", "utf-8").concat(stringUtil.getCurrentTimeStr()).concat(".xls")));
            response.setContentType("application/msexcel");
            outputStream = response.getOutputStream();
            out = new BufferedOutputStream(outputStream);
            wb.write(outputStream);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != outputStream)
                    outputStream.close();
                if (null != out)
                    out.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
