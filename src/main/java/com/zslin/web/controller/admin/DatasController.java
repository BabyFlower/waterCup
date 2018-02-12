package com.zslin.web.controller.admin;




import com.google.common.collect.Lists;
import com.zslin.web.bean.DatatableRequest;
import com.zslin.web.bean.DatatableResponse;
import com.zslin.web.model.Senddata;
import com.zslin.web.model.Site;
import com.zslin.web.service.ISenddataService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ji on 2017/3/24.
 */
@RestController
@RequestMapping("/datas")
public class DatasController {
    @Autowired
    private ISenddataService senddataService;
    private Map data;
    private Page<Senddata> page;
    @RequestMapping(value="/{tableId}",method= RequestMethod.GET)
    public ResponseEntity<?> getdatas(Model model, @PathVariable String tableId, @ModelAttribute DatatableRequest request, String sid, String did) {

     if (tableId.equals("table3")){
         Pageable pageable = new PageRequest(0 / 10, 10);
         page = senddataService.findByD_id(pageable, Integer.valueOf(did));
         List<Senddata> senddatas=page.getContent();
         data = new LinkedHashMap();
         for(Senddata plot1:senddatas)
         {
             data.put(plot1.getId()+"",plot1);
         }
         return ResponseEntity.ok(data);

     }else {

         if (tableId.equals("table1")) {
             Pageable pageable = request.getPageable();
             page = senddataService.findByD_id(pageable, Integer.valueOf(did));
         } else if (tableId.equals("table2")) {
             Pageable pageable = new PageRequest(0 / 1, 1);
             page = senddataService.findByD_id(pageable, Integer.valueOf(did));
         }
         DatatableResponse response = new DatatableResponse();
         response.setDraw(request.getDraw());
         response.setRecordsTotal(page.getTotalElements());
         response.setRecordsFiltered(page.getTotalElements());
         response.setData(Lists.newArrayList(page).stream().map(history -> {
             List<Object> columns = new ArrayList<>();
             columns.add(history.getD_id());
             columns.add(history.getSuwd());
             columns.add(history.getSuyl());
             columns.add(history.getJsll());
             columns.add(history.getCsll());
             columns.add(history.getJstds());
             columns.add(history.getCstds());
             columns.add(history.getPh());
             columns.add(history.getYl());
             columns.add(history.getYlv());
             columns.add(history.getYcy());
             columns.add(history.getZd());
             columns.add(history.getSentem());
             return columns;
         }).collect(Collectors.toList()));
         return ResponseEntity.ok(response);
     }
    }
}
