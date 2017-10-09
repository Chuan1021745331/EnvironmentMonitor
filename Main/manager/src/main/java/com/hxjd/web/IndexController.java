package com.hxjd.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Nandem on 2017/8/25.
 */
@Controller
public class IndexController
{
    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/index")
    public String index()
    {
        return "/index";
    }
}
