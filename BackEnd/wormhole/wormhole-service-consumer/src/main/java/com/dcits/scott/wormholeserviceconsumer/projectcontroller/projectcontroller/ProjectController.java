package com.dcits.scott.wormholeserviceconsumer.projectcontroller.projectcontroller;

import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.auth.authuser.AuthUserService;
import com.dcits.scott.gateway.pojo.GatewayProjectDO;
import com.dcits.scott.project.gatewayproject.GatewayProjectService;
import com.dcits.scott.support.result.Result;
import com.dcits.scott.wormholeserviceconsumer.config.fastdfs.FastDFSClientUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Reference
    GatewayProjectService gatewayProjectService;

    @Reference
    AuthUserService authUserService;

    @Autowired
    private FastDFSClientUtil dfsClient;
    //查询项目列表
    @HystrixCommand(fallbackMethod = "queryProjectListError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/queryProjectList")
    public Result<List<GatewayProjectDO>> queryProjectList(@RequestBody Map<String,Object> map) throws Exception {
        List<GatewayProjectDO> gatewayProjectDOList = gatewayProjectService.queryProjectList(map);
        for (GatewayProjectDO gatewayProjectDO: gatewayProjectDOList){
            Integer createrid = gatewayProjectDO.getCreaterid();
            AuthUser authUser = authUserService.selectById(createrid);
            gatewayProjectDO.setAuthUser(authUser);
        }
        Integer count = gatewayProjectDOList.size();
        return new Result<List<GatewayProjectDO>>(Result.OK,"查询成功","",gatewayProjectDOList,count);
    }
    public Result<List<GatewayProjectDO>> queryProjectListError(Map<String,Object> map,Throwable e){
        return new Result<List<GatewayProjectDO>>(Result.ERROR,"查询失败"+e.getMessage(),"",null,0);
    }
    //批量删除
    @HystrixCommand(fallbackMethod = "deleteBatchError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/deleteBatch")
    public Result<String> deleteBatch(@RequestBody Map<String,Object> map){
        gatewayProjectService.deleteBatch(map);
        return new Result<>(Result.OK,"删除成功");
    }
    public Result<String> deleteBatchError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"删除失败"+e.getMessage());
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
        String path = "E:/ScottProject/LiYingJieMyGithubProject/image/upload/project";
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
        //
        String s="";
        //将文件放入fastdfs的storage
        try {
            s= dfsClient.uploadFile(picture);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            System.out.println("上传成功");
            //将文件在服务器的存储路径返回
            return new Result(Result.OK,"上传成功",s);
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
            return new Result(Result.ERROR, "上传失败","上传失败");
        }
    }
    //添加项目
    @HystrixCommand(fallbackMethod = "addProjectError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/addProject")
    public Result<String> addProject(@RequestBody Map<String,Object> map){
        gatewayProjectService.insert(map);
         return new Result<>(Result.OK,"添加成功");
    }
    public Result<String> addProjectError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"添加失败"+e.getMessage());
    }
    //修改项目
    @HystrixCommand(fallbackMethod = "updateProjectError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/updateProject")
    public Result<String> updateProject(@RequestBody Map<String,Object> map) throws Exception {
        if (map.get("avatar")!=null && (""!=String.valueOf(map.get("avatar")))){
            GatewayProjectDO gatewayProjectDO= gatewayProjectService.selectByIntId(Integer.parseInt(String.valueOf(map.get("id"))));
            if (gatewayProjectDO.getAvatar()!=null&&(!"".equals(gatewayProjectDO.getAvatar()))){
                dfsClient.delFile(gatewayProjectDO.getAvatar());
            }
        }
        gatewayProjectService.updateByMap(map);
        return new Result<>(Result.OK,"更新成功");
    }
    public Result<String> updateProjectError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"更新失败"+e.getMessage());
    }
    //删除项目
    @HystrixCommand(fallbackMethod = "deleteError",ignoreExceptions = HystrixBadRequestException.class)
    @PostMapping("/delete")
    public Result<String> delete(@RequestBody Map<String,Object> map){
        Integer id = Integer.parseInt(String.valueOf(map.get("id")));
        gatewayProjectService.deleteByPrimaryKey(id);
        return new Result<>(Result.OK,"删除成功");
    }
    public Result<String> deleteError(Map<String,Object> map,Throwable e){
        return new Result<>(Result.ERROR,"删除失败"+e.getMessage());
    }

}
