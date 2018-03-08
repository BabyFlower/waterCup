package com.zslin.web.bean;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/22.
 */
@Data
public class DatatableResponse {
    Integer draw;
    Long recordsTotal;
    Long recordsFiltered;
    List<List<Object>> data=new ArrayList<>();
}
