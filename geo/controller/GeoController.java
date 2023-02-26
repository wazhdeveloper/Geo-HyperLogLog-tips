package com.geo.controller;

import com.geo.service.GeoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "美团地图位置附近的酒店推送GEO")
public class GeoController {

    @Autowired
    private GeoService geoService;

    @ApiOperation("添加坐标geoadd")
    @RequestMapping(value = "/geoadd", method = RequestMethod.GET)
    public String geoAdd() {
        return geoService.geoAdd();
    }

    @ApiOperation("获取经纬度坐标geopos")
    @RequestMapping(value = "/geopos", method = RequestMethod.GET)
    public Point position(String member) {
        return geoService.position(member);
    }

    @ApiOperation("获取经纬度生成的base32编码值geohash")
    @RequestMapping(value = "/geohash", method = RequestMethod.GET)
    public String hash(String member) {
        return geoService.hash(member);
    }

    @ApiOperation("获取两个给定位置之间的距离")
    @RequestMapping(value = "/geodist", method = RequestMethod.GET)
    public Distance distance(String member1, String member2) {
        return geoService.distance(member1, member2);
    }

    @ApiOperation("通过经度纬度查找北京王府井附近的")
    @RequestMapping(value = "/georadius", method = RequestMethod.GET)
    public GeoResults radiusByxy() {
        return geoService.radiusByxy();
    }

    @ApiOperation("通过地方查找附近,本例写死天安门作为地址,作为家庭作业")
    @RequestMapping(value = "/georadiusByMember", method = RequestMethod.GET)
    public GeoResults radiusByMember() {
        return geoService.radiusByMember();
    }
}
