package com.auto.service.impl;

import com.auto.service.IUserDao;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jsen.yin<jsen.yin @ gmail.com>
 * 2018-07-02
 * @Description: <p></p>
 */
@Slf4j
public class UserDao implements IUserDao {
    protected final static Logger log = LoggerFactory.getLogger(UserDao.class);

    @Override
    public void save() {
        log.info("save data");
    }
}
