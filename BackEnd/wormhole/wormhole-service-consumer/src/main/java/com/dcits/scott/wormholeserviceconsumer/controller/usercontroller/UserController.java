package com.dcits.scott.wormholeserviceconsumer.controller.usercontroller;

import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.support.result.ResultInfo;
import com.dcits.scott.auth.authuser.AuthUserService;
import com.dcits.scott.wormholeserviceconsumer.ExtendFunction;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    AuthUserService authUserService;
    //查询所有用户
    @PostMapping("/querySysUserList")
    public Map<String,Object> querySysUserList(@RequestBody Map<String,Object> map){

        Object object = map.get("cdt");
        if (object!=null){
            String date = ExtendFunction.changeDate(object);
            map.put("cdt",date);
        }
        //获取列表
        List<AuthUser> authUsers = authUserService.querySysUserList(map);
        //获取总条数
        Integer count = authUserService.selectCount();

        ResultInfo<AuthUser> resultInfo = new ResultInfo();
        resultInfo = new ResultInfo("SUCCESS","查询成功",authUsers,null,count,"");
        Map<String,Object> map1 = new HashMap<>();
        map1.put("result",resultInfo);
        return map1;

    }
    //删除选中的用户
    @PostMapping("/delSysUserByUserId")
    public void delSysUserByUserId(@RequestBody Map<String,Object> map){
        String ids = String.valueOf(map.get("ids"));
        String str[] = ids.split(",");
        List<String> strings =  Arrays.asList(str);
        map.put("ids",strings);
        authUserService.delSysUserByUserId(map);
    }
    //上传图片
    /**
     * 文件上传
     * @param picture
     * @param request
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("picture") MultipartFile picture, HttpServletRequest request){
        //获取文件在服务器的储存位置
       // String path = request.getSession().getServletContext().getRealPath("/upload");
        String path = "E:/ScottProject/LiYingJieMyGithubProject/image/upload/user";
        File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdir();
        }

        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        System.out.println("原始文件名称：" + originalFileName);

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型：" + type);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;
        System.out.println("新文件名称：" + fileName);

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);
        //项目url，这里可以使用常量或者去数据字典获取相应的url前缀；
        String fileUrl="http://localhost:8080";
        //文件获取路径
        fileUrl = fileUrl + request.getContextPath() + "/img/" + fileName;

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            System.out.println("上传成功");
            //将文件在服务器的存储路径返回
            return new Result("200","上传成功","/picture/user/" + fileName);
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
            return new Result("500", "上传失败","上传失败");
        }
    }

    //新增提交
    @PostMapping("/addSubmit")
    public Result<String> addSubmit(@RequestBody Map<String,Object> map){
        //加密密码
        String hashAlgorithName = "MD5";
        String password =String.valueOf(map.get("password"));
        int hashIterations = 1024;//加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes(String.valueOf(map.get("name")));
        Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        map.put("password",obj);
        //获取系统时间cdt
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
        map.put("cdt",sdf.format(d));
//        try {
//            authUserService.addUser(map);
//        }catch (Exception e){
//            return new Result<String>("500","添加失败");
//        }
        //创建对象
        AuthUser authUser = new AuthUser();
        authUser.setName(String.valueOf(map.get("name")));
        authUser.setPassword(String.valueOf(obj));
        authUser.setPhone(String.valueOf(map.get("phone")));
        authUser.setAddress(String.valueOf(map.get("address")));
        authUser.setAvatar(String.valueOf(map.get("avatar")));
        authUser.setCdt(d);
        authUser.setUdt(d);
        authUserService.addUser(authUser);
        return new Result<>("200","添加成功",authUser.getId()+"");
    }



    @PostMapping("/updateUser")
    public Result<String> updateUser(@RequestBody Map<String,Object> map){
        AuthUser authUser  = new AuthUser();
        String isLocked = String.valueOf(map.get("isLocked"));
        if ("0".equals(isLocked)){
            //密码未修改
            authUser.setId(Integer.parseInt(String.valueOf(map.get("id"))) );
            authUser.setName(String.valueOf(map.get("name")));
            authUser.setAvatar(String.valueOf(map.get("picture")));
            authUser.setAddress(String.valueOf(map.get("address")));
            authUser.setPhone(String.valueOf(map.get("phone")));
            //获取系统时间udt
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
            authUser.setUdt(d);
        }
        else {
            //密码加密
            String hashAlgorithName = "MD5";
            String password = String.valueOf(map.get("password"));
            int hashIterations = 1024;//加密次数
            ByteSource credentialsSalt = ByteSource.Util.bytes(String.valueOf(map.get("name")));
            Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
            System.out.println(obj);
            authUser.setPassword(String.valueOf(obj));
            //密码修改
            authUser.setId(Integer.parseInt(String.valueOf(map.get("id"))) );
            authUser.setName(String.valueOf(map.get("name")));
            authUser.setAvatar(String.valueOf(map.get("picture")));
            authUser.setAddress(String.valueOf(map.get("address")));
            authUser.setPhone(String.valueOf(map.get("phone")));
            //获取系统时间udt
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
            authUser.setUdt(d);
        }
        authUserService.updateUser(authUser);
        return new Result<>("200","修改成功");
    }
    //校验密码
    @PostMapping("/checkPassword")
    public Result<String> checkPassword(@RequestBody Map<String,Object> map){
        String name = String.valueOf(map.get("name"));
        String orignPassword = String.valueOf(map.get("password"));
        //MD5加密
        String hashAlgorithName = "MD5";
        String password = orignPassword;
        int hashIterations = 1024;//加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes(name);
        Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        String psd = obj+"";
        AuthUser authUser = new AuthUser();
        authUser.setName(name);
        AuthUser authUser1 =authUserService.selectPasswordByName(authUser);
        if (psd.trim().equals(authUser1.getPassword().trim())){
            return new Result<>("200","校验成功","");
        }
        else return new Result<>("500","校验失败","");

    }

}
