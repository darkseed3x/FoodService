//package ru.vvzl.fs.fs.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import ru.vvzl.fs.fs.config.FeignClients;
//import ru.vvzl.fs.fs.config.Config;
//import ru.vvzl.fs.rs.model.AssetResponse;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//public class ConsulController {
//
//    @Autowired
//    private Config config;
//    @Autowired
//    private FeignClients feignClients;
//
//    @RequestMapping("/get-props")
//    public List<String> home() {
//        List<String> list = new ArrayList<>();
//        for (String s: config.getNames()){
//            try {
//                feignClients.getClient(s).getMenu();
//                list.add(s);
//            }catch (Exception e){
//            }
//        }
//        list.get(1).hashCode();
//        return list;
//    }
//    @RequestMapping("/getMenus")
//    public List<List<AssetResponse>> homed() {
//        List<List<AssetResponse>> list = new ArrayList<>();
//        for (String s: config.getNames()){
//            try {
//
//                list.add(feignClients.getClient(s).getMenu().getBody());
//            }catch (Exception e){
//            }
//        }
//        return list;
//    }
//}
