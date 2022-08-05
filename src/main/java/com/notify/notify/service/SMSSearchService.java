package com.notify.notify.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notify.notify.model.Message;
import com.notify.notify.model.request.TextSearchRequest;
import com.notify.notify.model.request.TimeSearchRequest;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SMSSearchService {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private RestHighLevelClient client;

    public Boolean indexMessage(Message message)
    {
        try {
            String messageAsString = mapper.writeValueAsString(message);
            IndexRequest request = new IndexRequest("messages");
            request.id(message.getId());
            request.source(messageAsString, XContentType.JSON);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return true;
        }
        catch (Exception ex)
        {
            log.error("Get following error while indexing sms in elasticsearch: {}",ex.getMessage(),ex);
            return false;
        }
    }

    public List<Message> timeRangeSearch(TimeSearchRequest timeSearchRequest)
    {
        try {
            QueryBuilder searchQuery = QueryBuilders.termQuery("phone_number", timeSearchRequest.getPhoneNumber());
            QueryBuilder dateQuery = QueryBuilders.rangeQuery("time").gte(timeSearchRequest.getStartTime()).lte(timeSearchRequest.getEndTime());
            BoolQueryBuilder boolQuery = new BoolQueryBuilder();
            boolQuery.filter(searchQuery);
            boolQuery.filter(dateQuery);
            SearchSourceBuilder builder = new SearchSourceBuilder().query(boolQuery);
            builder.sort("time", SortOrder.ASC);
            SearchRequest request = new SearchRequest("messages");
            request.source(builder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] searchHits = response.getHits().getHits();
            List<Message> mssgs = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                mssgs.add(
                        mapper.readValue(hit.getSourceAsString(), Message.class)
                );
            }
            return mssgs;
        }
        catch (Exception ex)
        {
            log.error("Get following error while searching sms between a time interval in elasticsearch: {}",ex.getMessage(),ex);
            return Collections.emptyList();
        }
    }

    public List<Message> textSearch(TextSearchRequest textSearchRequest)
    {
        try {
            QueryBuilder textQuery = QueryBuilders.matchQuery("message",textSearchRequest.getText()).operator(Operator.AND);
            SearchSourceBuilder builder = new SearchSourceBuilder().query(textQuery);
            builder.sort("time", SortOrder.ASC);
            SearchRequest request = new SearchRequest("messages");
            request.source(builder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] searchHits = response.getHits().getHits();
            List<Message> mssgs = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                mssgs.add(
                        mapper.readValue(hit.getSourceAsString(), Message.class)
                );
            }
            return mssgs;
        }
        catch (Exception ex)
        {
            log.error("Get following error while searching sms containing a given text in elasticsearch: {}",ex.getMessage(),ex);
            return Collections.emptyList();
        }
    }
}
