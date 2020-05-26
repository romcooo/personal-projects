package com.romco.bracketeer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class JavaTest {
    @PostConstruct
    public void test() {
        log.info("java test here");
    }
}
