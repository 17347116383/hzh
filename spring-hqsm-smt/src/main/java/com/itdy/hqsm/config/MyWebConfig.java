package com.itdy.hqsm.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import javax.annotation.Resource;


/**
 *
 * 拦截配置类   实现   WebMvcConfigurer
 */
@Configuration
public class MyWebConfig  implements WebMvcConfigurer   {



    /**
     *  把拦截器注入容器
     * @return
     */
   /* @Bean
    public MyHandlerInterceptor getMyHandlerInterceptor() {
        return new MyHandlerInterceptor();
    }
*/



    /**
     *   对静态资源拦截  放行
     * @param registry
     */
   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getMyHandlerInterceptor());
        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        //排除静态资源
        addInterceptor.excludePathPatterns("/static/**");
        addInterceptor.excludePathPatterns("/view");
        addInterceptor.excludePathPatterns("/login");
        addInterceptor.excludePathPatterns("/text");
        addInterceptor.excludePathPatterns("/userRegister");
        addInterceptor.excludePathPatterns("/index");
        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }*/


    /**
     *   配置静态资源放行
     * @param registry
     */
   /* @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");//

    }*/


    /**
     *   跨域允许配置
     * @param registry
     */
   /* @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//设置允许跨域的路径
                .allowedOrigins("*")//设置允许跨域请求的域名
                .allowCredentials(true)//是否允许证书 不再默认开启
                .allowedMethods("GET", "POST", "PUT", "DELETE")//设置允许的方法
                .exposedHeaders("access-control-allow-headers",
                        "access-control-allow-methods",
                        "access-control-allow-origin",
                        "access-control-max-age",
                        "X-Frame-Options",
                        "Origin",
                        "Content-Type")
                .maxAge(3600);//跨域允许时间

    }*/







    /**
     * @return 登录验证拦截器
     * 自定义登录验证拦截器
     */
   /* @Bean
    public MyHandlerInterceptor getMyHandlerInterceptor() {
        return new MyHandlerInterceptor();
    }*/

    /**
     * @param registry 配置静态资源放行
     */
  /*  @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }  */

    /**
     * @param registry 添加拦截器
     */
   /* @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加登录处理拦截器，拦截所有请求，登录请求除外
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(needLoginInterceptor());
        //排除配置
        interceptorRegistration.excludePathPatterns("/sys/login.json");
        interceptorRegistration.excludePathPatterns("/charts/**");
        interceptorRegistration.excludePathPatterns("/css/**");
        interceptorRegistration.excludePathPatterns("/easyUi/**");
        interceptorRegistration.excludePathPatterns("/flashPlayer/**");
        interceptorRegistration.excludePathPatterns("/font/**");
        interceptorRegistration.excludePathPatterns("/images/**");
        interceptorRegistration.excludePathPatterns("/js/**");
        interceptorRegistration.excludePathPatterns("/pages/**");
        interceptorRegistration.excludePathPatterns("/plugin/**");
        interceptorRegistration.excludePathPatterns("/index.html");
        interceptorRegistration.excludePathPatterns("/show.html");
        //配置拦截策略
        interceptorRegistration.addPathPatterns("/**");
    }*/



}
