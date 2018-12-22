package com.auto.service.impl;

import com.auto.api.DateAdapter;
import com.auto.api.Flector;
import com.auto.service.SolrContext;
import com.auto.util.EasyResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BaseSolr implements SolrContext {

    /**
     * solr客户端
     */
    private SolrClient SOLR_CLIENT;

    protected BaseSolr() {
    }

    /**
     * solr 服务地址
     */
    public void initBaseSolr(String solrUrl) {
        SOLR_CLIENT = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }

    /**
     * 校验排序状态（ASC  与    DESC）
     *
     * @param order 排序状态  String
     * @return
     */
    private boolean isValidateOrder(String order) {
        if (!order.toUpperCase().equals("ASC") && !order.toUpperCase().equals("DESC")) return false;
        else return true;
    }

    @Override
    public void addOrUpdateSolrDataList(List<SolrInputDocument> solrInputDocuments) throws SolrServerException, IOException {
        SOLR_CLIENT.add(solrInputDocuments);
        SOLR_CLIENT.commit();
    }

    @Override
    public void addOrUpdateSolrData(SolrInputDocument solrInputDocument) throws SolrServerException, IOException {
        SOLR_CLIENT.add(solrInputDocument);
        SOLR_CLIENT.commit();
    }

    @Override
    public EasyResult<?> querySolrRepertory(String collection, String sql, String fq, boolean isPage, Integer startItem, Integer endItem,
                                            String sort, String order, String returnColumn, Class<?> c) throws SolrServerException, IOException {

        ModifiableSolrParams modifiableSolrParams = new ModifiableSolrParams();
        if (StringUtils.isNotBlank(sql)) {
            modifiableSolrParams.add("q", sql);
        } else {
            modifiableSolrParams.add("q", "*:*");
        }
        if (StringUtils.isNotBlank(fq)) {
            modifiableSolrParams.add("fq", fq);
        }
        if (isPage) {
            modifiableSolrParams.add("start", String.valueOf(startItem));
            modifiableSolrParams.add("rows", String.valueOf(endItem));
        } else {
            modifiableSolrParams.add("rows", String.valueOf(Integer.MAX_VALUE));
        }

        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            if (!isValidateOrder(order)) {
                throw new RuntimeException("querySolrLibrary>>>>>>>{ order param invalidate }");
            }
            modifiableSolrParams.add("sort", sort + " " + order);
            modifiableSolrParams.add("sort", "createTime desc");
        }
        if (StringUtils.isNotBlank(returnColumn)) {
            modifiableSolrParams.add("fl", returnColumn);
        }
        modifiableSolrParams.add("wt", "json");
        QueryResponse response = SOLR_CLIENT.query(collection, modifiableSolrParams);
        if (null != response) {
            int total = (int) response.getResults().getNumFound();
            return new EasyResult<>(total, getSolrDocumentList(response, c));
            /*return new EasyResult<>(total, response.getBeans(c));*/
        } else {
            return new EasyResult<>(0, null);
        }

    }

    private <T> List<T> getSolrDocumentList(QueryResponse response, Class<T> clazz) {

        Flector flector = new Flector(clazz);

        int initialCapacity = (int) response.getResults().getNumFound();
        List list = new ArrayList(initialCapacity);

        try {
            SolrDocumentList documents = response.getResults();
            for (SolrDocument solrDocument : documents) {
                Object obj = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(org.apache.solr.client.solrj.beans.Field.class)) {
                        org.apache.solr.client.solrj.beans.Field solrField = field.getAnnotation(org.apache.solr.client.solrj.beans.Field.class);
                        field.setAccessible(true);
                        Object value = null;
                        if (null == solrField.value() || "".equals(solrField.value())) {
                            continue;
                        }
                        String valueStr = solrDocument.get(field.getName()).toString();

                        if (field.getType() == BigDecimal.class) {
                            value = new BigDecimal(valueStr.toString());
                        } else if (field.getType() == Long.class) {
                            value = new Long(valueStr);
                        } else if (field.getType() == Integer.class) {
                            value = new Integer(valueStr);
                        } else if (field.getType() == Double.class) {
                            value = new Double(valueStr);
                        } else if (field.getType() == Date.class) {
                            try {
                                value = new DateAdapter().unmarshal(valueStr);
                            } catch (Exception e) {
                            }
                        }
                        if ("#default".equals(valueStr) || "".equals(valueStr)) {
                            // 如果注解为默认的 采用此属性的name来从solr中获取值
                            field.set(obj, valueStr);
                        } else {
                            // 如果注解为不是默认的 采用此注解上的值来从solr中获取值
                            /*field.set(obj, solrDocument.get(solrField.value()));*/
                            flector.setter(obj, field.getName(), value);
                        }
                    }
                }
                list.add(obj);
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteSolrRepertoryById(Long id) throws SolrServerException, IOException {
        SOLR_CLIENT.deleteById(String.valueOf(id));
        SOLR_CLIENT.commit();
    }

    @Override
    public void deleteSolrRepertoryByIdList(List<Long> idList) throws SolrServerException, IOException {
        List<String> ids = new ArrayList<>();
        idList.stream().forEach(id -> {
            ids.add(String.valueOf(id));
        });
        SOLR_CLIENT.deleteById(ids);
        SOLR_CLIENT.commit();
    }

    @Override
    public void cleanSolrRepertory() throws SolrServerException, IOException {
        SOLR_CLIENT.deleteByQuery("*:*");
        SOLR_CLIENT.commit();
    }
}
