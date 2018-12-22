package com.auto.service.impl;

import com.auto.entity.SkuSolrModel;
import com.auto.service.ISolrService;
import com.auto.util.DataUtil;
import com.auto.util.EasyResult;
import com.auto.util.FinalResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class SolrServiceImpl extends BaseSolr implements ISolrService, EnvironmentAware {

    @Autowired
    private Environment env;

    @Override
    public void setEnvironment(final Environment environment) {
        this.env = environment;
    }

    @PostConstruct
    public void SolrServiceImpl() {
        String solrUrl = env.getProperty("solr.url");
        super.initBaseSolr(solrUrl);
    }

    @Override
    public FinalResult<String> addAllSolrDatas() {

        return null;
    }

    @Override
    public FinalResult<String> addSolrDatasBySolrTable() {
        return null;
    }

    @Override
    public FinalResult<String> addOneSolrData(Long id) {
        return null;
    }

    @Override
    public <T> EasyResult<List<SkuSolrModel>> querySolrModelsByCondition(String keyword, Integer tradeType, Long categoryId, Long itemCatId, Long brandId, boolean isPage, Integer startItem, Integer endItem, String sort, String order, String returnColumn) throws IOException, SolrServerException {

        String condition = " AND status:4 AND isMainSku:1 ";
        String categoryIdCondition = " categoryId:" + categoryId;
        String itemCatIdCondition = " itemCatId:" + itemCatId;
        String brandIdCondition = " brandId:" + brandId;
        String snKeyWord = "";
        String keywordCondition = "";

        if (StringUtils.isNotBlank(keyword)) {
            /** 采用特殊字符串转义方式 */
            keyword = org.apache.solr.client.solrj.util.ClientUtils.escapeQueryChars(keyword);
            StringBuilder keyword2 = new StringBuilder("");
            for (int i = 0; i < keyword.length(); i++) {
                char c = keyword.charAt(i);
                String s = String.valueOf(c);
                if (DataUtil.isLetter(s)) {
                    keyword2.append(s.toLowerCase());
                } else {
                    keyword2.append(s);
                }
            }
            String original = keyword2.toString();
            /** 只处理纯英文字母或者纯数字 **/
            if (DataUtil.isDigit(original) || DataUtil.isLetter(original)) {
                keyword = " *" + original + "* ";
            } else {
                keyword = original + " *";
            }

            snKeyWord = original.trim();
            if (keyword.contains("nq")) {
                keywordCondition = " sn:" + snKeyWord;
            } else {
                keywordCondition = " name:" + keyword;
            }
        }

        String tradeTypeCondition = " tradeType:" + tradeType;

        StringBuilder sb = new StringBuilder("*:*");
        if (StringUtils.isNotBlank(keyword)) {
            sb.append(keywordCondition);
            if (null != categoryId) {
                sb.append(" AND " + categoryIdCondition);
            }
            if (null != itemCatId) {
                sb.append(" AND " + itemCatIdCondition);
            }
            if (null != brandId) {
                sb.append(" AND " + brandIdCondition);
            }
            if (null != tradeType) {
                sb.append(" AND tradeType:" + tradeType);
            }
            sb.append(condition);
        } else {
            StringBuilder sb2 = new StringBuilder("*:*");
            if (null != categoryId) {
                sb2.append(categoryIdCondition);
                if (null != brandId) {
                    sb2.append(" AND " + brandIdCondition);
                }
                if (null != itemCatId) {
                    sb2.append(" AND " + itemCatIdCondition);
                }
                sb2.append(condition);
            } else {
                if (null != brandId) {
                    sb2.append(brandIdCondition);
                }
                if (null != itemCatId) {
                    sb2.append(" AND " + itemCatIdCondition);
                }
                sb2.append(condition);
            }

            if (null != tradeType) {
                sb = new StringBuilder(tradeTypeCondition);
                if (StringUtils.isNotBlank(sb2.toString())) {
                    sb.append(" AND " + sb2.toString());
                }
            } else {
                sb = sb2;
            }
        }

        return (EasyResult<List<SkuSolrModel>>) super.querySolrRepertory("collection1", sb.toString(),
                "", isPage, startItem, endItem, sort, order, returnColumn, SkuSolrModel.class);
    }

    @Override
    public FinalResult<String> deleteSolrDataById(Long id) {
        return null;
    }

    @Override
    public FinalResult<String> clean() {
        return null;
    }

    @Override
    public FinalResult<String> addOrUpdateTagName(Long tagId) {
        return null;
    }
}
