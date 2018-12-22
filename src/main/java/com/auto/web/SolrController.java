package com.auto.web;

import com.auto.entity.SkuSolrModel;
import com.auto.service.ISolrService;
import com.auto.util.EasyResult;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * 代码生成器，参考源码测试用例：
 * <p>
 * /mybatis-plus/src/test/java/com/baomidou/mybatisplus/test/generator/MysqlGenerator.java
 */
@RestController
@RequestMapping("/solr")
public class SolrController {

    @Autowired
    private ISolrService solrService;

    /**
     * 增删改查 CRUD
     * http://localhost:8080/solr/search
     */
    @GetMapping("/search")
    public EasyResult<List<SkuSolrModel>> test2(String keyword) {
        Integer tradeType = null;
        Long categoryId = null;
        Long itemCatId = null;
        Long brandId = null;
        boolean isPage = true;
        Integer startItem = 0;
        Integer endItem = 10;
        String sort = null;
        String order = null;
        String returnColumn = null;

        EasyResult<List<SkuSolrModel>> listEasyResult = null;
        try {
            listEasyResult = solrService.querySolrModelsByCondition(keyword, tradeType, categoryId, itemCatId, brandId, isPage, startItem, endItem, sort, order, returnColumn);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listEasyResult;
    }

}