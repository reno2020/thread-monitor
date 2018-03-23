package club.throwable.monitor.web;

import club.throwable.monitor.model.ThreadPoolExecutorMetadata;
import club.throwable.monitor.model.ThreadPoolExecutorUpdateVO;
import club.throwable.monitor.support.ThreadMonitorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2018/3/22 16:15
 */
@Controller
public class ThreadMonitorController {

    @Autowired
    private ThreadMonitorManager threadMonitorManager;

    @Autowired
    @Qualifier(value = "zeroLevelThreadPoolExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private Environment environment;

    @GetMapping(value = "/monitor/thread")
    public ModelAndView monitor() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("thread-metadata");
        modelAndView.addObject("metadata", threadMonitorManager.getAllMetadata());
        modelAndView.addObject("applicationName", environment.getProperty("spring.application.name","UNKNOWN_APPLICATION"));
        return modelAndView;
    }

    @GetMapping(value = "/monitor/thread/metadata", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<ThreadPoolExecutorMetadata> getThreadPoolExecutorMetadata() {
        return threadMonitorManager.getAllMetadata();
    }

    @PostMapping(value = "/monitor/thread/purge-taskqueue", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Boolean> purgeTaskqueue(@RequestParam(value = "beanName") String beanName) {
        return ResponseEntity.ok(threadMonitorManager.purgeTaskQueue(beanName));
    }

    @PostMapping(value = "/monitor/thread/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Boolean> update(@RequestBody ThreadPoolExecutorUpdateVO updateVO){
        return ResponseEntity.ok(threadMonitorManager.updateThreadPoolExecutor(updateVO));
    }

    @GetMapping(value = "/monitor/execute")
    @ResponseBody
    public String execute() {
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(500);
            } catch (InterruptedException e) {
                //ignore
            }
        });
        return "success";
    }
}
