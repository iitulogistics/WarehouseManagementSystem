<#import "parts/common.ftl" as c>
<#import "parts/nav.ftl" as nav>

<@c.page title="Задачи">
    <@nav.nav/>
    <div class="container">
        <div class="row">
                <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#list">Список моих
                    заданий
                </button>
            <div class="col-lg-12">
                <div id="list" class="collapse">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Задача</th>
                            <th scope="col">Время создания</th>
                            <th scope="col">Приоритет</th>
                            <th scope="col">Выполнить</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list myCurrentTask as task>
                            <tr>
                                <td>${task.getId()}</td>
                                <td>${task.getTask()}</td>
                                <td>${task.getCreated()}</td>
                                <td>${task.getPriority()}</td>
                                <td>
                                    <form action="/task/done" method="post">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <input type="hidden" value="${task.getId()}" name="id">
                                        <button class="btn btn-primary" type="submit">Выполненно</button>
                                    </form>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="row">
            <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#list1">Список
                текуших
                заданий
            </button>

            <div class="col-lg-12">
                <div id="list1" class="collapse">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Задача</th>
                            <th scope="col">Время создания</th>
                            <th scope="col">Приоритет</th>
                            <th scope="col">Взять</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list currentTask as task>
                            <tr>
                                <td>${task.getId()}</td>
                                <td>${task.getTask()}</td>
                                <td>${task.getCreated()}</td>
                                <td>${task.getPriority()}</td>
                                <td>
                                    <form action="/task/takeTask" method="post">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <input type="hidden" value="${task.getId()}" name="task_id">
                                        <button class="btn btn-primary" type="submit">Взять</button>
                                    </form>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</@c.page>