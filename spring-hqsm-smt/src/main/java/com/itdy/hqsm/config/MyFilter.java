package com.itdy.hqsm.config;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itdy.hqsm.elasticsearch.ElasticsearchConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**过滤器
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/9/21
 * @Author Administrator
 */
@WebFilter(urlPatterns = "/*", filterName = "MyFilter")
public class MyFilter  implements Filter {

      // final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MyFilter.class);
      private static final Logger logger = LoggerFactory.getLogger(MyFilter.class);
       private static final String[] excludePathPatterns =
               { "/shrio/tolog", "/shrio/index" ,"/shrio/addUser" ,"/shrio/userRegister","/shrio/user" };


       /**
       *  过滤器初始化
       */
       public void init(FilterConfig arg0) throws ServletException {
        System.out.println("----------->过滤器初始化");
       }





          /**
           * * * 过滤器实现
           *
           */
          public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {
              //设置跨域参数
             /* HttpServletResponse response = (HttpServletResponse) res;
              response.setHeader("Access-Control-Allow-Origin", "*");
              response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT,OPTIONS, DELETE");
              response.setHeader("Access-Control-Max-Age", "3600");
              response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
              chain.doFilter(req, res);*/

             // System.out.printf("---------》过滤器实现\n");
		      // 在请求处理之前进行调用（Controller方法调用之前）,返回true才会继续往下执行，返回false取消当前请求
			//	boolean isFilter = false;
			//	HttpServletRequest request = (HttpServletRequest) servletRequest;
			//	HttpServletResponse response = (HttpServletResponse) servletResponse;
				// 不拦截登陆和注册
			//	String url = request.getRequestURI();
				/*if (Arrays.asList(excludePathPatterns).contains(url)) {
                    filterChain.doFilter(request, response);
					return;
				}*/
			 // String tokenCode = request.getHeader("Token");
             // System.out.println("----tokenCode--------->" + tokenCode);
				/*if (isFilter) {
                    filterChain.doFilter(request, response);
				}*/

     //  System.out.println("---------------->"+((HttpServletRequest) servletRequest).getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
       }


    /**
     *   过滤器销毁了
     */
    public void destroy() {
        System.out.println("--------->过滤器销毁了");
    }



              /* String tokenCode = request.getHeader("Token");
              System.out.println("----================================----"+tokenCode);
              Cookie[] cookies = request.getCookies();
              for (Cookie  cookie: cookies){
                  System.out.println("----11----jsessionid<<<<<<<<<<<<<<<<<"+cookie.getName());
                  System.out.println("----21----"+cookie.getComment());
                  System.out.println("----31----"+cookie.getDomain());
                  System.out.println("----41----"+cookie.getPath());
                  System.out.println("----51----"+cookie.getValue());
                  System.out.println("----61----"+cookie.getSecure());
                  System.out.println("----71----"+cookie.getVersion());
              }
              System.out.println("---8-----"+request.changeSessionId());
              System.out.println("---91-----"+request.getAuthType());
              System.out.println("--101------"+request.getContextPath());
              System.out.println("--111------"+request.getHeader("token"));
              System.out.println("--121------"+request.getMethod());
              System.out.println("---131-----"+request.getPathInfo());
              System.out.println("---141-----"+request.getPathTranslated());
              System.out.println("---151-----"+request.getRequestURI());
              System.out.println("---161-----"+request.getServletPath());
              System.out.println("---171-----"+request.getCharacterEncoding());
              System.out.println("----181----"+request.getContentType());
              System.out.println("---191-----"+request.getLocalName());
              System.out.println("---201-----"+request.getDateHeader(""));
              System.out.println("---211-----"+request.getHeaderNames().toString());
              System.out.println("---221-----"+request.getRequestedSessionId());
              System.out.println("---231-----"+request.getHttpServletMapping().toString());
              System.out.println("---241-----<<<<<<<<<<"+request.getSession().toString());*/
}
