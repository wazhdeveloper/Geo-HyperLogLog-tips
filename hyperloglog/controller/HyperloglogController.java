package com.hyperloglog.controller;

import com.hyperloglog.service.HyperloglogService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class HyperloglogController {

    @Autowired
    private HyperloglogService hyperloglogService;

    @ApiOperation("获得ip去重复后的uv访问量")
    @RequestMapping(value = "/uv", method = RequestMethod.GET)
    public long uv() {
        return hyperloglogService.uv();
    }
}
