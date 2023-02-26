package com.hyperloglog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class HyperloglogService {

    @Resource
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void redis() {
        new Thread(() -> {
           String ip = null;
           Random random = new Random();
            for (int i = 0; i < 200; i++) {
                ip = random.nextInt(256) + "."
                        + random.nextInt(256) + "."
                        + random.nextInt(256) + "."
                        + random.nextInt(256);

                Long hll = redisTemplate.opsForHyperLogLog().add("hll", ip);
                log.info("ip={}, 该ip访问主页的次数为{}",ip,hll);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "t1").start();
    }

    public long uv() {
        return redisTemplate.opsForHyperLogLog().size("hll");
    }
}
