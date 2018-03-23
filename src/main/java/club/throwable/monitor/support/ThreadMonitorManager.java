package club.throwable.monitor.support;

import club.throwable.monitor.model.ThreadPoolExecutorMetadata;
import club.throwable.monitor.model.ThreadPoolExecutorUpdateVO;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/3/22 15:39
 */
public interface ThreadMonitorManager {
    /**
     * 注册线程池Bean信息
     *
     * @param beanName           beanName
     * @param threadPoolExecutor threadPoolExecutor
     */
    void registerThreadPoolExecutor(String beanName, ThreadPoolExecutor threadPoolExecutor);

    /**
     * 获取所有线程池的元信息
     *
     * @return List
     */
    List<ThreadPoolExecutorMetadata> getAllMetadata();

    /**
     * 根据bean名称获取线程池的元信息
     *
     * @param beanName beanName
     * @return ThreadPoolExecutorMetadata
     */
    ThreadPoolExecutorMetadata getMetadata(String beanName);

    /**
     * 清空指定beanName的线程池队列
     *
     * @param beanName beanName
     * @return boolean
     */
    boolean purgeTaskQueue(String beanName);

    /**
     * 更新线程池属性
     *
     * @param updateVO updateVO
     * @return boolean
     */
    boolean updateThreadPoolExecutor(ThreadPoolExecutorUpdateVO updateVO);
}
