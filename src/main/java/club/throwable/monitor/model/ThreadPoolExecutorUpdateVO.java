package club.throwable.monitor.model;

import lombok.Data;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/3/23 14:59
 */
@Data
public class ThreadPoolExecutorUpdateVO {

    private String beanName;
    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private Long keepAliveSecond;
    private Boolean allowCoreThreadTimeOut;
}
