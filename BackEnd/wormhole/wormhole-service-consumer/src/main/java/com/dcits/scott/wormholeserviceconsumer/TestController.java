package com.dcits.scott.wormholeserviceconsumer;

import com.dcits.scott.TestInterface;
import com.dcits.scott.admin.pojo.AuthUser;
import com.dcits.scott.auth.authuser.AuthUserService;
import org.I0Itec.zkclient.ZkClient;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.apache.zookeeper.CreateMode;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TestController {
    //test
    @Reference
    AuthUserService authUserService;






    private static final ApplicationConfig application = new ApplicationConfig("user_provider");
//   @Reference
//    TestInterface testInterface;
//    @Reference
//   AuthUserService authUserService;

    //    @GetMapping("/hello")
//    public String sayHello(){
//        String s = testInterface.testInterface("1");
//        return s;
//    }
    @GetMapping("/test")
    public String test(@RequestParam(required = true) Integer id){
        System.out.println("============"+id+"-------------");
        String s = authUserService.testMybatis(id);
        System.out.println(s+"ssssssssssssssssssssssssss");
        return s;
    }
    @GetMapping("/h")
    public String test() {

        return "redirect:/hello";
    }

    //    @RequestMapping("/getAccount")
//    @ResponseBody
//    public String getAccount() {
//        Subject subject = SecurityUtils.getSubject();
//        AuthUser currentUser = (AuthUser) subject.getPrincipal();
//        System.out.println("登录用户姓名：" + currentUser.getName());
//        return currentUser.getName()+"    "+subject.getSession().getId();
//    }
    @RequiresPermissions("/auth/user/list")
    @GetMapping("test1")
    public String test1() {
        return "admin用户有此接口权限";
    }

    @RequiresPermissions("/test2")
    @GetMapping("test2")
    public String test2() {
        return "admin用户无此接口权限";
    }

    @GetMapping("test3")
    public String test3() {
        return "登录后可以任意访问此接口";
    }


    public static Object genericInvoke(String interfaceClass, String methodName, List<Map<String, Object>> parameters) {

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application);
        RegistryConfig registry = new RegistryConfig("192.168.81.128;2181/dubbo");
        reference.setRegistry(registry);
        reference.setInterface(interfaceClass); // 接口名
        reference.setGeneric(true); // 声明为泛化接口
//        reference.setVersion("1.0.0");
//        reference.setCheck(false);

        //ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
        //需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        //API方式编程时，容易忽略此问题。
        //这里使用dubbo内置的简单缓存工具类进行缓存

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        //  GenericService genericService = reference.get();


        // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用

        int len = parameters.size();
        String[] invokeParamTyeps = new String[len];
        Object[] invokeParams = new Object[len];
        for (int i = 0; i < len; i++) {
            invokeParamTyeps[i] = parameters.get(i).get("ParamType") + "";
            invokeParams[i] = parameters.get(i).get("Object");
        }
        return genericService.$invoke(methodName, invokeParamTyeps, invokeParams);
    }

    public static void main1(String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        list.add(map);
      //  Object o = genericInvoke("com.dcits.scott.userprovider.TestInterfaceImpl", "testInterface", list);
      //  System.out.println(o);


        //
        // 普通编码配置方式
        ApplicationConfig application = new ApplicationConfig();
        application.setName("user_provider");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://192.168.81.128:2181");

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setVersion("1.0.0");
        reference.setInterface("com.dcits.scott.userprovider.TestInterfaceImpl");
        reference.setGeneric(true); // 声明为泛化接口

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);

        // 基本类型以及Date,List,Map等不需要转换，直接调用
        Object result = genericService.$invoke("testInterface", new String[]{"java.lang.String"},
                new Object[]{ "100123"});
        removeClassFeild(result);

        System.out.println(result);

        //
    }

    private static void removeClassFeild(Object object) {
        if (object instanceof Map) {
            Map feildValueMap = (Map) object;
            if (feildValueMap.containsKey("Class") || feildValueMap.containsKey("class")) {
                feildValueMap.remove("Class");
                feildValueMap.remove("class");
            }
            Set<Map.Entry> set = feildValueMap.entrySet();
            Iterator<Map.Entry> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                Object value = entry.getValue();
                if (value instanceof Map) {
                    removeClassFeild(value);
                } else {
                    return;
                }
            }
        }


    }


    public static void main(String[] args) {



    }


}
