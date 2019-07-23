<#import "parts/nav.ftl" as ui/>
<#import "parts/common.ftl" as c/>
<@c.page>
    <@ui.nav/>
    <#list stillages as stillage>
        <div class="col-lg-12">
            <h4>Стилаж №${stillage[0]}</h4>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Номер полки</th>
                    <th scope="col">Список контейнеров</th>
                    <th scope="col">Максимальное количество контейнеров</th>
                </tr>
                </thead>
                <tbody>
                <#list stillage[1] as shelf>
                    <tr>
                        <th scope="row">${shelf[0]}</th>
                        <td>${shelf[1]}</td>
                        <td>${shelf[2]} шт.</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </#list>
</@c.page>