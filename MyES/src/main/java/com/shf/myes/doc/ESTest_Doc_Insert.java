package com.shf.myes.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class ESTest_Doc_Insert {

    @SneakyThrows
    public static void main(String[] args) {
//        创建ES客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.120.20", 9200))
        );

//        插入数据
        IndexRequest request = new IndexRequest();
        request.index("user").id("1001");

        User user = new User();
        user.setName("zhangsan");
        user.setAge(30);
        user.setSex("男");


//        向es插入数据，必须将数据转换为JSON格式
        ObjectMapper mapper = new ObjectMapper();

//        转换为JSON格式
        String userJSON = mapper.writeValueAsString(user);
        request.source(userJSON, XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        DocWriteResponse.Result result = response.getResult();
        System.out.println(result);


//        关闭ES客户端
        client.close();
    }
}
