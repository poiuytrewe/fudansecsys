package com.fudanuniversity.cms.controller;

import com.fudanuniversity.cms.commons.model.JsonResult;
import com.fudanuniversity.cms.commons.util.DateExUtils;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.Date;
import java.util.Map;

/**
 * Created by Xinyue.Tang at 2021-05-03 02:27:52
 */
@Api(tags = "首页")
@RestController
// @CrossOrigin
public class IndexController extends BaseController {

    @ApiOperation(value = "默认", notes = "默认Note")
    @GetMapping("/")
    public JsonResult<?> index() throws Exception {
        Map<String, Object> greeting = Maps.newLinkedHashMap();
        greeting.put("datetime", DateExUtils.formatDatetimeMilli(new Date()));
        greeting.put("address", InetAddress.getLocalHost().getHostAddress());
        return JsonResult.buildSuccess(greeting);
    }
}
