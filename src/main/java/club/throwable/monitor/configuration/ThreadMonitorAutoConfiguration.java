package club.throwable.monitor.configuration;

import club.throwable.monitor.support.InMemoryThreadMonitorManager;
import club.throwable.monitor.support.ThreadMonitorManager;
import club.throwable.monitor.support.ThreadMonitorPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/3/22 16:08
 */
@Configuration
public class ThreadMonitorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadMonitorManager threadMonitorManager() {
        return new InMemoryThreadMonitorManager();
    }

    @Bean
    public ThreadMonitorPostProcessor threadMonitorPostProcessor() {
        return new ThreadMonitorPostProcessor();
    }

    @Bean(value = "zeroLevelThreadPoolExecutor")
    public ThreadPoolExecutor zeroLevelThreadPoolExecutor() {
        return new ThreadPoolExecutor(5, 10, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(50));
    }

    @Bean(value = "oneLevelThreadPoolExecutor")
    public ThreadPoolExecutor oneLevelThreadPoolExecutor() {
        return new ThreadPoolExecutor(10, 10, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));
    }


    @Bean(value = "springThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor springThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(15);
        executor.setMaxPoolSize(15);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setQueueCapacity(150);
        return executor;
    }
}
