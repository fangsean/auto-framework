package com.auto.service;

import com.auto.util.EasyResult;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.List;

public interface SolrContext {

    /**
     * 向solr库中添加/更新多条数据(更新其实是覆盖id=**的filed)
     *
     * @param solrInputDocuments 参数类型：List，所在包位置：org.apache.solr.common.SolrInputDocument， solr输入流集合
     * @throws SolrServerException
     * @throws IOException
     */
    void addOrUpdateSolrDataList(List<SolrInputDocument> solrInputDocuments) throws SolrServerException, IOException;

    /**
     * 向solr库中添加/更新单条数据(更新其实是覆盖id=**的filed)
     *
     * @param solrInputDocument 所在包位置：org.apache.solr.common.SolrInputDocument，solr输入流集合
     * @throws SolrServerException
     * @throws IOException
     */
    void addOrUpdateSolrData(SolrInputDocument solrInputDocument) throws SolrServerException, IOException;

    /**
     * 检索solr库
     *
     * @param sql          String 查询参数，如： id : 1
     * @param fq           String 过滤条件，如：name : "test",与sql是and关系
     * @param isPage       boolean 是否分页，如果isPage == false, startItem和endItem非必传
     * @param startItem    Integer 起始条数
     * @param endItem      Integer 结束条数
     * @param sort         String 排序字段
     * @param order        String 排序状态，“ASC” 和 “DESC”
     * @param returnColumn String 需要的返回字段，如果没有可以留空
     * @param c            Class<?> 需要被解析成的任意对象
     * @return EasyResult<?> 任意类型
     * @throws SolrServerException
     * @throws IOException
     */
    EasyResult<?> querySolrRepertory(String collection, String sql, String fq, boolean isPage, Integer startItem, Integer endItem, String sort, String order, String returnColumn, Class<?> c) throws SolrServerException, IOException;

    /**
     * 根据Id删除索引库单行记录
     *
     * @param id Long 主键
     * @throws SolrServerException
     * @throws IOException
     */
    void deleteSolrRepertoryById(Long id) throws SolrServerException, IOException;

    /**
     * 根据多个ID删除索引库多行记录
     *
     * @param idList List<Long> 主键集合
     * @throws SolrServerException
     * @throws IOException
     */
    void deleteSolrRepertoryByIdList(List<Long> idList) throws SolrServerException, IOException;

    /**
     * 清空索引库
     *
     * @throws SolrServerException
     * @throws IOException
     */
    void cleanSolrRepertory() throws SolrServerException, IOException;
}

