package com.fudanuniversity.cms.framework.error;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Xinyue.Tang at 2020-10-15 14:40:42
 *
 * <pre>
 * Using @RequestMapping On Interface Methods
 * A common pitfall when working with annotated controller classes happens when applying functionality that requires creating a proxy for the controller object (e.g. @Transactional methods).
 * Usually you will introduce an interface for the controller in order to use JDK dynamic proxies.
 * To make this work you must move the @RequestMapping annotations to the interface as well as the mapping mechanism can only "see" the interface exposed by the proxy.
 * Alternatively, you could activate proxy-target-class="true" in the configuration for the functionality applied to the controller (in our transaction scenario in <tx:annotation-driven />).
 * Doing so indicates that CGLIB-based subclass proxies should be used instead of interface-based JDK proxies.
 * For more information on various proxying mechanisms see Section 8.6, “Proxying mechanisms”.
 * </pre>
 *
 * @see <a href='https://docs.spring.io/spring-framework/docs/3.1.x/spring-framework-reference/html/mvc.html#mvc-ann-requestmapping'>Using @RequestMapping On Interface Methods</a>
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public interface PlatformErrorController {

    @RequestMapping
    ResponseEntity<Map<String, Object>> error(HttpServletRequest request);
}