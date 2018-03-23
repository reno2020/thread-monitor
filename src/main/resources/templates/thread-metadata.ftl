<#assign base = context.contextPath>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thread Monitor</title>
    <style>

        .metadata-content .metadata-table {
            text-align: center;
        }
    </style>
    <script src="${base}/jquery.min.js"></script>
</head>
<body>

<div class="metadata-content">
    <h2 align="center">${applicationName}线程池监控</h2>
    <table id="metadata-table" class="metadata-table" align="center" border="2">
        <thead>
        <tr>
            <td>Bean名称</td>
            <td>核心线程数</td>
            <td>最大程数</td>
            <td>线程存活时间(秒)</td>
            <td>是否允许核心线程超时</td>
            <td>拒绝策略</td>
            <td>阻塞任务总数</td>
            <td>任务总数</td>
            <td>已完成任务总数</td>
            <td>活跃线程数</td>
            <td>最大线程池容量</td>
            <td>线程池容量</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
         <#if metadata?exists>
           <#list metadata as data>
                <tr class="metadata-tr">
                    <td>${data.beanName}</td>
                    <td>${data.corePoolSize}</td>
                    <td>${data.maximumPoolSize}</td>
                    <td>${data.keepAliveSecond}</td>
                    <td>${data.allowCoreThreadTimeOut?string("true","false ")}</td>
                    <td>${data.rejectedExecutionPolicy}</td>
                    <td>${data.blockingTaskCount}</td>
                    <td>${data.taskCount}</td>
                    <td>${data.completedTaskCount}</td>
                    <td>${data.activeCount}</td>
                    <td>${data.largestPoolSize}</td>
                    <td>${data.poolSize}</td>
                    <td>
                        <button id="purge" onclick="purge(this)">清空任务队列</button>
                    </td>
                </tr>
           </#list>
         </#if>
        </tbody>
    </table>
</div>

<script type="text/javascript">

    function purge(node) {
        var tr = node.parentNode.parentNode;
        var firstTd = tr.getElementsByTagName("td")[0];
        var id = firstTd.innerHTML;
        $.ajax({
            url: '${base}/monitor/purge-taskqueue',
            data: {
                beanName: id
            },
            method: 'POST',
            dataType: 'json',
            success: function (data) {
                alert("result --> " + data)
            }
        })
    }

</script>

</body>
</html>