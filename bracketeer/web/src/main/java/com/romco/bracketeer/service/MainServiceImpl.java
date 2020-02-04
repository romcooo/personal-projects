package com.romco.bracketeer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MainServiceImpl implements MainService {

    // == fields


    // == constructors
    @Autowired
    public MainServiceImpl() {
    }

    // == methods
    @Override
    public boolean isOn() {
        return true;
    }
}
