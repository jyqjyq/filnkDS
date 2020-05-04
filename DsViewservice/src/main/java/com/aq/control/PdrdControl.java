package com.aq.control;

import com.aq.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 */
@Controller
@RequestMapping("pdrd")
public class PdrdControl {

    @Autowired
    private RedisService redisService;

    @RequestMapping("listzjPdrd")
    public String listzjPdrd(Model model, int topnum){
        Map<String,List<String>> map = redisService.getAllData("pingdaord");
        Set<Map.Entry<String, List<String>>> set = map.entrySet();
        Map<Long,String> sortmap = new TreeMap<Long,String>(new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                return Integer.valueOf((long)o2-(long)o1+"");
            }
        });
        for(Map.Entry<String,List<String>> entry :set){
            String pindaoid = entry.getKey();
            List<String> list = entry.getValue();
            long total = 0l;
            for(String o : list){
                total += Long.valueOf(o);
            }
            if(sortmap.get(total)!=null){
                String pindaoidtemp = sortmap.get(total);
                sortmap.put(total,pindaoidtemp+","+pindaoid);
            }else {
                sortmap.put(total,pindaoid);
            }
        }
        int temptotal = 0;
        Set<Map.Entry<Long,String>> sortmapset = sortmap.entrySet();
        List<String> result = new ArrayList<String>();
        for(Map.Entry<Long,String> entry :sortmapset){
            String pindaoid = entry.getValue();
            String [] temp = pindaoid.split(",");
            temptotal += temp.length;
            if(temptotal >= topnum){
                int sy = temptotal - topnum;
                int tempqz = temp.length - sy-1;
                for(int i=0;i<=tempqz;i++){
                    result.add(temp[i]);
                }
                break;
            }else{
                for(String tempinner :temp){
                    result.add(tempinner);
                }

            }

        }
        model.addAttribute("result",result);
        System.out.println("hello listzjPdrd");
        return "dsrdlist";
    }


}
