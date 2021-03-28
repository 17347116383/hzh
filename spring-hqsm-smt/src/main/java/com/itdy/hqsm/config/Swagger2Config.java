package com.itdy.hqsm.config;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Function;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    // 定义分隔符,配置Swagger多包
    private static final String splitor = ";";

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage("com.itdy.hqsm.modes.controller" + splitor + "com.itdy.hqsm.security.hqsm.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("hzh", "http://127.0.0.1:8081", "huangzh@itdayang.com");
        return new ApiInfo("系统API接口",           //大标题
                "系统API接口",               //小标题
                "2.0.0",                                   //版本
                "http://127.0.0.1:8081",        //服务条款URL
                contact,                                           //作者
                "系统",                                  //链接显示文字
                "http://127.0.0.1:8081",               //网站链接
                new ArrayList<>()
        );
    }

    /**
     * 重写basePackage方法，使能够实现多包访问，复制贴上去
     *
     * @param basePackage 基础包
     * @return <springfox.documentation.RequestHandler>
     * @author
     * @date
     */
    public Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }


    /**
     *
     * @param basePackage
     * @return
     */
    private Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
/*
@Api：用在类上，说明该类的作用
@ApiOperation：用在方法上，说明方法的作用
@ApiImplicitParams：用在方法上包含一组参数说明
@ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
paramType：参数放在哪个地方
header-->请求参数的获取：@RequestHeader
query-->请求参数的获取：@RequestParam
path（用于restful接口）-->请求参数的获取：@PathVariable
body（不常用）
form（不常用）
name：参数名
dataType：参数类型
required：参数是否必须传
value：参数的意思
defaultValue：参数的默认值
@ApiResponses：用于表示一组响应
@ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
code：数字，例如400
message：信息，例如"请求参数没填好"
response：抛出异常的类
@ApiModel：描述一个Model的信息（这种一般用在post创建的时候，使用@RequestBody这样的场景，请求参数无法使用@ApiImplicitParam注解进行描述的时候）
* */
}