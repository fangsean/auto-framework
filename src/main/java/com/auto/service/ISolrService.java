package com.auto.service;

import com.auto.entity.SkuSolrModel;
import com.auto.util.EasyResult;
import com.auto.util.FinalResult;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public interface ISolrService {


    /**
     * 添加全部商品数据
     *
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    FinalResult<String> addAllSolrDatas() throws SolrServerException, IOException;

    /**
     * 根据_solr表向solr库中添加商品数据
     *
     * @return FinalResult<String>
     * @throws SolrServerException
     * @throws IOException
     */
    FinalResult<String> addSolrDatasBySolrTable() throws SolrServerException, IOException;

    /**
     * 添加一行商品数据
     *
     * @param id
     * @return FinalResult<String>
     * @throws SolrServerException
     * @throws IOException
     */
    FinalResult<String> addOneSolrData(Long id) throws SolrServerException, IOException;

    /**
     * 根据条件检索solr库
     *
     * @param keyword      条件
     * @param tradeType    贸易类型
     * @param categoryId   一级分类ID
     * @param itemCatId    三级分类id
     * @param brandId      品牌ID
     * @param startItem    起始条数
     * @param endItem      结束条数
     * @param sort         排序字段
     * @param order        排序状态
     * @param returnColumn 需要返回的字段
     * @return EasyResult<List       <       SolrModel>>
     * @throws SolrServerException
     * @throws IOException
     */
    <T> EasyResult<List<SkuSolrModel>> querySolrModelsByCondition(String keyword, Integer tradeType, Long categoryId, Long itemCatId, Long brandId, boolean isPage, Integer startItem, Integer endItem, String sort, String order, String returnColumn) throws SolrServerException, IOException;


    /**
     * 根据ID删除solr库
     *
     * @param id 商品Id
     * @return FinalResult<String>
     * @throws SolrServerException
     * @throws IOException
     */
    FinalResult<String> deleteSolrDataById(Long id) throws SolrServerException, IOException;

    /**
     * 清空索引库
     *
     * @return
     */
    FinalResult<String> clean() throws SolrServerException, IOException;

    /**
     * 根据标签id更新商品
     *
     * @param tagId 标签id
     * @return FinalResult<String>
     * @throws SolrServerException
     * @throws IOException
     */
    FinalResult<String> addOrUpdateTagName(Long tagId) throws SolrServerException, IOException;


}
