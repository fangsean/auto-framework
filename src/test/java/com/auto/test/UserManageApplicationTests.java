package com.auto.test;

import com.auto.common.mertics.JProfile;
import com.auto.concurrence.FileDownExecutor;
import com.auto.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Iterator;
import java.util.ServiceLoader;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@ServletComponentScan(basePackages = {"com.auto"})
public class UserManageApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("hello world");
    }

    @Test
    public void execute() {
        JProfile.enter("test");
        try {
            FileDownExecutor executor = FileDownExecutor.init();
            executor.breakDown4Thread();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            JProfile.release();
        }
    }

    @Test
    public void loader(){
        Class<IUserService> service = IUserService.class;
        ServiceLoader<IUserService> load = ServiceLoader.load(service);

        Iterator<IUserService> iterator = load.iterator();
        while (iterator.hasNext()){
            IUserService next = iterator.next();
            System.out.println(next.selectListBySQL());
        }
    }

}
