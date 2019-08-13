<#import "parts/nav.ftl" as ui/>
<#import "parts/common.ftl" as c/>
<#import "parts/modalViews.ftl" as modal/>
<@c.page title="Ячейки">
    <@ui.nav/>

    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-primary" data-toggle="modal"
                    data-target="#addCell">
                Добавить ячейку
            </button>
        </div>
    </div>
    <div class="col-lg-12">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Ячейка</th>
                <th scope="col">Список контейнеров</th>
                <th scope="col">Максимальное количество контейнеров</th>
            </tr>
            </thead>
            <tbody>
            <#list stillages as stillage>
                <tr>
                    <td>Стелаж №${stillage.getStillage()}<br>
                        Полка №${stillage.getShelf()}<br>
                        Ячейка №${stillage.getCell()}</td>
                    <td>
                        <#list containers as container>
                            <#if container.getCellId() == stillage>
                                ${container.getTypeContainer()} с Id ${container.getId()}: ${container.product.getProduct_name()}
                                ${container.getAmount()} шт.<br>
                                <br>
                            </#if>
                        </#list>
                    </td>
                    <td>${stillage.getMax_count_object()}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <@modal.addCell/>
</@c.page>