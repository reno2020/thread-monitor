package club.throwable.monitor.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/3/22 15:47
 */
@ToString
@Data
public class ThreadPoolExecutorMetadata {

    private String beanName;
    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private Long keepAliveSecond;
    private Boolean allowCoreThreadTimeOut;
    private String rejectedExecutionPolicy;

    private Integer blockingTaskCount;
    private Long taskCount;
    private Long completedTaskCount;
    private Integer activeCount;
    private Integer largestPoolSize;
    private Integer poolSize;

}
