package com.zslin.web.bean;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;


/**
 * Created by Administrator on 2017/6/22.
 */
@Data
public class DatatableRequest {
    Integer draw;
    Integer start;
    Integer length;
    public Pageable getPageable(){
        return  new PageRequest(start/length,length);
    }
}
