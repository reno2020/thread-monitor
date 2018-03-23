package club.throwable.monitor.support;

import club.throwable.monitor.model.ThreadPoolExecutorMetadata;
import club.throwable.monitor.model.ThreadPoolExecutorUpdateVO;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/3/22 15:40
 */
public class InMemoryThreadMonitorManager implements ThreadMonitorManager {

    private final ConcurrentMap<String, ThreadPoolExecutor> executorBeans = new ConcurrentHashMap<>();

    @Override
    public void registerThreadPoolExecutor(String beanName, ThreadPoolExecutor threadPoolExecutor) {
        synchronized (executorBeans) {
            executorBeans.putIfAbsent(beanName, threadPoolExecutor);
        }
    }

    @Override
    public List<ThreadPoolExecutorMetadata> getAllMetadata() {
        List<ThreadPoolExecutorMetadata> metadataList = new ArrayList<>(executorBeans.values().size());
        synchronized (executorBeans) {
            for (Map.Entry<String, ThreadPoolExecutor> entry : executorBeans.entrySet()) {
                ThreadPoolExecutor executor = entry.getValue();
                ThreadPoolExecutorMetadata metadata = new ThreadPoolExecutorMetadata();
                metadata.setRejectedExecutionPolicy(ClassUtils.getShortName(executor.getRejectedExecutionHandler().getClass()));
                metadata.setBeanName(entry.getKey());
                metadata.setCorePoolSize(executor.getCorePoolSize());
                metadata.setMaximumPoolSize(executor.getMaximumPoolSize());
                metadata.setKeepAliveSecond(executor.getKeepAliveTime(TimeUnit.SECONDS));
                metadata.setAllowCoreThreadTimeOut(executor.allowsCoreThreadTimeOut());
                metadata.setBlockingTaskCount(executor.getQueue().size());
                metadata.setTaskCount(executor.getTaskCount());
                metadata.setCompletedTaskCount(executor.getCompletedTaskCount());
                metadata.setActiveCount(executor.getActiveCount());
                metadata.setLargestPoolSize(executor.getLargestPoolSize());
                metadata.setPoolSize(executor.getPoolSize());
                metadataList.add(metadata);
            }
        }
        return metadataList;
    }

    @Override
    public ThreadPoolExecutorMetadata getMetadata(String beanName) {
        synchronized (executorBeans) {
            ThreadPoolExecutor executor = executorBeans.get(beanName);
            if (null != executor) {
                ThreadPoolExecutorMetadata metadata = new ThreadPoolExecutorMetadata();
                metadata.setRejectedExecutionPolicy(ClassUtils.getShortName(executor.getRejectedExecutionHandler().getClass()));
                metadata.setBeanName(beanName);
                metadata.setCorePoolSize(executor.getCorePoolSize());
                metadata.setMaximumPoolSize(executor.getMaximumPoolSize());
                metadata.setKeepAliveSecond(executor.getKeepAliveTime(TimeUnit.SECONDS));
                metadata.setAllowCoreThreadTimeOut(executor.allowsCoreThreadTimeOut());
                metadata.setBlockingTaskCount(executor.getQueue().size());
                metadata.setTaskCount(executor.getTaskCount());
                metadata.setCompletedTaskCount(executor.getCompletedTaskCount());
                metadata.setActiveCount(executor.getActiveCount());
                metadata.setLargestPoolSize(executor.getLargestPoolSize());
                metadata.setPoolSize(executor.getPoolSize());
                return metadata;
            }
        }
        return null;
    }

    @Override
    public boolean purgeTaskQueue(String beanName) {
        synchronized (executorBeans) {
            ThreadPoolExecutor executor = executorBeans.get(beanName);
            if (null != executor) {
                BlockingQueue<Runnable> taskQueue = executor.getQueue();
                if (!taskQueue.isEmpty()) {
                    taskQueue.clear();
                }
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean updateThreadPoolExecutor(ThreadPoolExecutorUpdateVO updateVO) {
        synchronized (executorBeans) {
            ThreadPoolExecutor executor = executorBeans.get(updateVO.getBeanName());
            if (null != executor) {
                if (null != updateVO.getCorePoolSize()) {
                    executor.setCorePoolSize(updateVO.getCorePoolSize());
                }
                if (null != updateVO.getMaximumPoolSize()) {
                    executor.setMaximumPoolSize(updateVO.getMaximumPoolSize());
                }
                if (null != updateVO.getKeepAliveSecond()) {
                    executor.setKeepAliveTime(updateVO.getKeepAliveSecond(), TimeUnit.SECONDS);
                }
                if (null != updateVO.getAllowCoreThreadTimeOut()) {
                    executor.allowCoreThreadTimeOut(updateVO.getAllowCoreThreadTimeOut());
                }
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
