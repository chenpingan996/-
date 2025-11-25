package com.stu.xiaoguo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * 自定义 Swagger 接口文件的配置
 *
 * @author luxiaoguo
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.stu.xiaoguo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api 信息
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("小果用户中心")
                .description("小果用户中心接口文档")
                .termsOfServiceUrl("https://www.baidu.com")
                .contact(new Contact("小果", "https://www.baidu.com", "xiaoguo@163.com"))
                .version("1.0")
                .build();
    }
}
