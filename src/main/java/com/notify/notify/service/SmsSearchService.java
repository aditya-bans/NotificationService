package com.notify.notify.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notify.notify.model.request.SearchTextRequest;
import com.notify.notify.model.request.SearchTimeRequest;
import com.notify.notify.constants.elasticsearch.ElasticsearchConstants;
import com.notify.notify.model.SmsDataForElasticsearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SmsSearchService {

    private final ObjectMapper mapper;
    private final RestHighLevelClient client;

    public Boolean indexMessage(SmsDataForElasticsearch smsDataForElasticsearch) {
        try {
            String messageAsString = mapper.writeValueAsString(smsDataForElasticsearch);
            IndexRequest request = new IndexRequest(ElasticsearchConstants.ELASTICSEARCH_INDEX_FOR_SMS);
            request.id(smsDataForElasticsearch.getId());
            request.source(messageAsString, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return true;
        } catch (Exception ex) {
            log.error("Get following error while indexing sms in elasticsearch: {}", ex.getMessage(), ex);
            return false;
        }
    }

    public List<SmsDataForElasticsearch> searchSmsForTimeRange(SearchTimeRequest searchTimeRequest) {
        try {
            QueryBuilder searchQuery = QueryBuilders.matchQuery(ElasticsearchConstants.PHONENUMBER_FIELD, searchTimeRequest.getPhoneNumber());
            QueryBuilder dateQuery = QueryBuilders.rangeQuery(ElasticsearchConstants.TIMESTAMP_FIELD).gte(searchTimeRequest.getStartTime()).lte(searchTimeRequest.getEndTime());
            BoolQueryBuilder boolQuery = new BoolQueryBuilder();
            boolQuery.filter(searchQuery);
            boolQuery.filter(dateQuery);
            SearchSourceBuilder builder = new SearchSourceBuilder().query(boolQuery);
            builder.sort(ElasticsearchConstants.TIMESTAMP_FIELD, SortOrder.ASC);
            SearchRequest request = new SearchRequest(ElasticsearchConstants.ELASTICSEARCH_INDEX_FOR_SMS);
            request.source(builder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] searchHits = response.getHits().getHits();
            List<SmsDataForElasticsearch> smsDataForElasticsearch = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                smsDataForElasticsearch.add(
                        mapper.readValue(hit.getSourceAsString(), SmsDataForElasticsearch.class)
                );
            }
            return smsDataForElasticsearch;
        } catch (Exception ex) {
            log.error("Get following error while searching sms between a time interval in elasticsearch: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<SmsDataForElasticsearch> searchSmsForText(SearchTextRequest searchTextRequest) {
        try {
            QueryBuilder textQuery = QueryBuilders.matchQuery(ElasticsearchConstants.TEXT_FIELD, searchTextRequest.getText()).operator(Operator.AND);
            SearchSourceBuilder builder = new SearchSourceBuilder().query(textQuery);
            builder.sort(ElasticsearchConstants.TIMESTAMP_FIELD, SortOrder.ASC);
            SearchRequest request = new SearchRequest(ElasticsearchConstants.ELASTICSEARCH_INDEX_FOR_SMS);
            request.source(builder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] searchHits = response.getHits().getHits();
            List<SmsDataForElasticsearch> mssgs = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                mssgs.add(
                        mapper.readValue(hit.getSourceAsString(), SmsDataForElasticsearch.class)
                );
            }
            return mssgs;
        } catch (Exception ex) {
            log.error("Get following error while searching sms containing a given text in elasticsearch: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }
}
