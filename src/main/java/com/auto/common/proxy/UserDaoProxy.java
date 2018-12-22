package com.auto.common.proxy;

import com.auto.service.IUserDao;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author auto.yin<auto.yin @ gmail.com>
 * 2018-07-02
 * @Description: <p></p>
 */
@Slf4j
public class UserDaoProxy implements IUserDao {

    protected final static Logger log = LoggerFactory.getLogger(UserDaoProxy.class);

    private IUserDao target;

    public UserDaoProxy(IUserDao target){
        this.target=target;
    }


    @Override
    public void save() {
        log.info("开始事务...");
        target.save();
        log.info("提交事务...");
    }
}
