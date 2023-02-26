package com.geo.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Api("geo，对地理信息的访问")
public class GeoService {

    private final static String CITY = "city";
    @Autowired
    private RedisTemplate redisTemplate;

    public String geoAdd() {
        Map<String, Point> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put("天安门",new Point(116.403963,39.915119));
        memberCoordinateMap.put("故宫",new Point(116.403414,39.924091));
        memberCoordinateMap.put("长城",new Point(116.024067,40.362639));
        redisTemplate.opsForGeo().add(CITY, memberCoordinateMap);
        return memberCoordinateMap.toString();
    }

    @ApiOperation("//获取经纬度坐标")
    public Point position(String member) {
        List<Point> list = redisTemplate.opsForGeo().position(CITY, member);
        return list.get(0);
    }

    @ApiOperation("geohash算法生成的base32编码值")
    public String hash(String member) {
        List<String> list = redisTemplate.opsForGeo().hash(CITY, member);
        return list.get(0);
    }

    @ApiOperation("获取两地之间的距离")
    public Distance distance(String member1, String member2) {
        return redisTemplate.opsForGeo().distance(CITY, member1, member2, RedisGeoCommands.DistanceUnit.KILOMETERS);
    }

    @ApiOperation("查找附近的地点")
    public GeoResults radiusByxy() {
        //通过经度，纬度查找附近的,北京王府井位置116.418017,39.914402
        Circle circle = new Circle(116.418017, 39.914402, Metrics.KILOMETERS.getMultiplier());
        // 返回50条
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortDescending().limit(50);

        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = redisTemplate.opsForGeo().radius(CITY, circle, args);

        return geoResults;
    }

    public GeoResults radiusByMember() {
        //通过地方查找附近
        Circle circle = new Circle(116.418017, 39.914402, Metrics.KILOMETERS.getMultiplier());
        return redisTemplate.opsForGeo().radius(CITY, circle);
    }
}
