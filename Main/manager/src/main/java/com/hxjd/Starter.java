package com.hxjd;


import com.hxjd.listener.MyApplicationFailedEvent;
import com.hxjd.listener.MyApplicationReadyEvent;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

/**
 * @author Nandem on 2017/8/24.
 */
@SpringBootApplication
@MapperScan("com.hxjd.dao")
public class Starter
{
    private final static Logger logger = LoggerFactory.getLogger(Starter.class);

    public static void main(String[] args) throws Exception
    {
        SpringApplication application = new SpringApplication(Starter.class);

        application.addListeners(new MyApplicationReadyEvent(), new MyApplicationFailedEvent());

        application.run(args);
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer()
    {

        return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

            container.addErrorPages(error401Page, error404Page, error500Page);
        });
    }
}
