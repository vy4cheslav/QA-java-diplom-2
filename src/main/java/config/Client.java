package config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import static config.Constants.*;
import static io.restassured.http.ContentType.JSON;

public class Client {

    public static RequestSpecification spec() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(BASE_URI)
                .setBasePath(BASE_PATH)
                .log(LogDetail.ALL)
                .build();
    }
}