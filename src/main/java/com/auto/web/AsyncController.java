package com.auto.web;

import com.auto.base.abstracts.BaseController;
import com.auto.concurrence.conf.ThreadAsyncConfigurer;
import com.auto.concurrence.semaphore.RequestHolder;
import com.auto.enums.BaseEnum;
import com.auto.util.FinalResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("async")
public class AsyncController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    ThreadAsyncConfigurer asyncConfigurer;

    @RequestMapping("/test")
    @ResponseBody
    public Long test() {
        Logger logger = LoggerFactory.getLogger(AsyncController.class);

        return RequestHolder.getId();
    }

    /**
     * Servlet方式实现异步请求
     *
     * @param request
     * @param response
     */
    @RequestMapping("/test2")
    @ResponseBody
    public void todoAsync(HttpServletRequest request, HttpServletResponse response) {

        AsyncContext asyncContext = request.startAsync();
        asyncContext.addListener(new AsyncListener() {

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                logger.info("超时了：");
                //做一些超时后的相关操作
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                // TODO Auto-generated method stub
                logger.info("线程开始");
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                logger.info("发生错误：", event.getThrowable());
            }

            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                logger.info("执行完成");
                //这里可以做一些清理资源的操作

            }
        });
        asyncContext.setTimeout(200);

        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    logger.info("内部线程：" + Thread.currentThread().getName());
                    asyncContext.getResponse().setCharacterEncoding("utf-8");
                    asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
                    asyncContext.getResponse().getWriter().println("这是【异步】的请求返回");
                } catch (Exception e) {
                    logger.error("异常：", e);
                }
                //异步请求完成通知
                //此时整个请求才完成
                //其实可以利用此特性 进行多条消息的推送 把连接挂起。。
                asyncContext.complete();
            }
        });
        //此时之类 request的线程连接已经释放了
        logger.info("线程：" + Thread.currentThread().getName());
    }


    /**
     * Spring方式实现异步请求
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/test3")
    @ResponseBody
    public Callable<String> callable(HttpServletRequest request, HttpServletResponse response) {
        // 这么做的好处避免web server的连接池被长期占用而引起性能问题，
        // 调用后生成一个非web的服务线程来处理，增加web服务器的吞吐量。
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                /*Thread.sleep(3 * 1000L);*/
                return "小单 - " + System.currentTimeMillis();
            }
        };
    }


    @RequestMapping("/webAsyncTask")
    public WebAsyncTask<String> webAsyncTask() {
        logger.info("外部线程：" + Thread.currentThread().getName());
        WebAsyncTask<String> result = new WebAsyncTask<String>(2000L, new Callable<String>() {

            @Override
            public String call() throws Exception {
                logger.info("内部线程：" + Thread.currentThread().getName());
                return "WebAsyncTask!!!";
            }
        });
        result.onTimeout(new Callable<String>() {

            @Override
            public String call() throws Exception {
                // TODO Auto-generated method stub
                return "WebAsyncTask超时!!!";
            }
        });
        result.onCompletion(new Runnable() {

            @Override
            public void run() {
                //超时后 也会执行此方法
                logger.info("WebAsyncTask执行结束");
            }
        });
        return result;
    }


    @ExceptionHandler(Exception.class)
    public FinalResult<?> handleAllException(Exception ex) {
        FinalResult finalResult = new FinalResult(BaseEnum.ERROR.getCode(), ex.getMessage(), false);
        finalResult.setData(null);
        return finalResult;
    }

    @RequestMapping("asynctask")
    public DeferredResult<FinalResult<?>> deferredResult() {
        logger.info(String.format("外部线程：%s %s", Thread.currentThread().getId(), Thread.currentThread().getName()));
        long milliSeconds = 200L;
        //设置超时时间
        DeferredResult<FinalResult<?>> result = new DeferredResult(milliSeconds);
        //处理超时事件 采用委托机制
        result.onTimeout(() -> {
            System.out.println("异步调用执行超时！thread id is : " + Thread.currentThread().getId());
            FinalResult finalResult = new FinalResult(BaseEnum.ERROR.getCode(), "超时!", false);
            result.setResult(finalResult);
        });
        //完成后
        //超时后 也会执行此方法
        result.onCompletion(() -> logger.info("调用完成"));
        asyncConfigurer.getAsyncExecutor()
                .execute(() -> {
                    Thread.setDefaultUncaughtExceptionHandler((t,e)->{
                    });
                            //处理业务逻辑
                            logger.info("内部线程：" + Thread.currentThread().getName());
                            FinalResult finalResult = new FinalResult(BaseEnum.SUCCESS.getCode(), BaseEnum.SUCCESS.getReason(), true);
                            //返回结果
                            result.setResult(finalResult);
                        }
                );
        return result;
    }


}
