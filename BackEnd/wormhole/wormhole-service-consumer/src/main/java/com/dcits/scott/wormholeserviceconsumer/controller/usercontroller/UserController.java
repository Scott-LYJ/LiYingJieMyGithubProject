package com.dcits.scott.wormholeserviceconsumer.controller.usercontroller;

import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.support.result.ResultInfo;
import com.dcits.scott.auth.authuser.AuthUserService;
import com.dcits.scott.wormholeserviceconsumer.ExtendFunction;
import com.dcits.scott.wormholeserviceconsumer.config.fastdfs.FastDFSClientUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FastDFSClientUtil dfsClient;

    //查询所有用户
    @HystrixCommand(fallbackMethod = "querySysUserListError",ignoreExceptions=HystrixBadRequestException.class)
    @PostMapping("/querySysUserList")
    public ResultInfo<AuthUser> querySysUserList(@RequestBody Map<String,Object> map) throws Exception {
        ResultInfo<AuthUser> resultInfo;
        //获取列表
        List<AuthUser> authUsers = authUserService.selectPage(map);
        //获取总条数
        Integer count = authUserService.selectCount(map);
        resultInfo = new ResultInfo(ResultInfo.OK,"查询成功",authUsers,null,count,"成功");
        return resultInfo;
    }
    public ResultInfo<AuthUser> querySysUserListError(Map<String,Object> ma,Throwable e){
        ResultInfo<AuthUser> resultInfo = new ResultInfo(ResultInfo.ERROR,"查询失败,出现异常",e.getMessage());
        return resultInfo;
    }
    //删除选中的用户
    @HystrixCommand(fallbackMethod = "delSysUserByUserIdError",ignoreExceptions=HystrixBadRequestException.class)
    @PostMapping("/delSysUserByUserId")
    public Result<String> delSysUserByUserId(@RequestBody Map<String,Object> map) throws Exception {
        String ids = String.valueOf(map.get("ids"));
        String str[] = ids.split(",");
        List<String> strings =  Arrays.asList(str);
        map.put("ids",strings);
        authUserService.delete(map);
        Result<String> result = new Result(ResultInfo.OK,"删除成功",null);
        return result;
    }
    public Result<String> delSysUserByUserIdError(Map<String,Object> map,Throwable e){
        Result<String> result = new Result(ResultInfo.OK,"删除失败，出现异常",e.getMessage());
        return result;
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
        String s="";
        //将文件放入fastdfs的storage
        try {
             s= dfsClient.uploadFile(picture);
            String fileUrl1 = dfsClient.uploadFile(picture);
            request.setAttribute("msg", "成功上传文件，  '" + fileUrl1 + "'");
            picture.transferTo(targetFile);
            System.out.println("上传成功");
            //将文件在服务器的存储路径返回
            return new Result(Result.OK,"上传成功",s);
        } catch (IOException e) {
            System.out.println("上传失败");
            return new Result(Result.ERROR, "上传失败,出现异常","上传失败");
        }
    }

    //新增提交
    @HystrixCommand(fallbackMethod = "addSubmitError",ignoreExceptions=HystrixBadRequestException.class)
    @PostMapping("/addSubmit")
    public Result<String> addSubmit(@RequestBody Map<String,Object> map) throws Exception {
        //加密密码
        String hashAlgorithName = "MD5";
        String password =String.valueOf(map.get("password"));
        int hashIterations = 1024;//加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes(String.valueOf(map.get("name")));
        Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        map.put("password",obj);
        //创建对象
        AuthUser authUser = new AuthUser();
        authUser.setName(String.valueOf(map.get("name")));
        authUser.setPassword(String.valueOf(obj));
        authUser.setPhone(String.valueOf(map.get("phone")));
        authUser.setAddress(String.valueOf(map.get("address")));
        authUser.setAvatar(String.valueOf(map.get("avatar")));
        authUserService.insert(authUser);
        return new Result<>(Result.OK,"添加成功",authUser.getId()+"");
    }
    public Result<String> addSubmitError(Map<String,Object> map ,Throwable e){
        return new Result<>(Result.ERROR,"添加失败，出现异常",e.getMessage());
    }

    @HystrixCommand(fallbackMethod = "updateUserError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/updateUser")
    public Result<String> updateUser(@RequestBody Map<String,Object> map) throws Exception {
        AuthUser authUser  = new AuthUser();
        if (map.get("picture")!=null){
            AuthUser authUser1 = authUserService.selectById(Integer.parseInt(String.valueOf(map.get("id"))));
            if(!"null".equals(authUser1.getAvatar())) dfsClient.delFile(authUser1.getAvatar());
        }
        String isLocked = String.valueOf(map.get("isLocked"));
        if ("0".equals(isLocked)){
            //密码未修改
            authUser.setId(Integer.parseInt(String.valueOf(map.get("id"))) );
            authUser.setName(String.valueOf(map.get("name")));
            authUser.setAvatar(String.valueOf(map.get("picture")));
            authUser.setAddress(String.valueOf(map.get("address")));
            authUser.setPhone(String.valueOf(map.get("phone")));
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
        }
        authUserService.update(authUser);
        return new Result<>(Result.OK,"修改成功");
    }
    Result<String> updateUserError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"修改失败,出现异常",e.getMessage());
    }
    //校验密码
    @HystrixCommand(fallbackMethod = "checkPasswordError",ignoreExceptions = HystrixBadRequestException.class)
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
            return new Result<>(Result.OK,"校验成功","");
        }
        return new Result<>(Result.ERROR,"校验失败","yes");
    }
    public Result<String> checkPasswordError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"校验失败出现异常",e.getMessage());
    }
}
