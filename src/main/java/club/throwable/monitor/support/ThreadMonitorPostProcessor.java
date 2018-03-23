package club.throwable.monitor.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/3/22 15:38
 */
public class ThreadMonitorPostProcessor implements BeanPostProcessor {

    @Autowired
    private ThreadMonitorManager threadMonitorManager;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ThreadPoolExecutor) {
            threadMonitorManager.registerThreadPoolExecutor(beanName, (ThreadPoolExecutor) bean);
        }
        return bean;
    }
}
