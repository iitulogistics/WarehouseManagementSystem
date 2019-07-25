<#import "parts/nav.ftl" as ui/>
<#import "parts/modalViews.ftl" as modal/>
<#import "parts/common.ftl" as c/>
<#import "parts/login.ftl" as l>

<@c.page title="Продукты">
    <@ui.nav/>
    <div class="main_content col-lg-12">
        <div class="">
            <h2>Список товаров</h2>
            <div class="search_form">
                <form method="post">
                    <div class="form-group">
                        <label for="search_prod_by_name1">Наименование товара</label>
                        <#--                      ${last_search}-->
                        <input type="text" class="form-control" id="search_prod_by_name" name="name"
                               value="${last_search}"
                               placeholder="Enter product name">
                        <small id="emailHelp" class="form-text text-muted">Напишите наименования продукта для
                            дальнейшего
                            поиска
                        </small>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-primary">Поиск</button>
                </form>
            </div>

            <div class="row">
                <div class="col">
                    <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#addProductModalView">
                        Добавить продукт
                    </button>
                </div>
            </div>

            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Наименование товара</th>
                    <th scope="col">Количество на складе</th>
                    <th scope="col">Количество на этапе отправке</th>
                    <th scope="col">Ожидается прибытие</th>
                    <th scope="col">Цена</th>
                    <th scope="col">Стоимость</th>
                </tr>
                </thead>
                <tbody>
                <#list products as product>
                    <tr>
                        <th scope="col">${product.getId()}</th>
                        <th>${product.getProduct_name()}</th>
                        <td>${product.getCount_on_warehouse()} шт.</td>
                        <td>${product.getCount_on_shipping()} шт.</td>
                        <td>${product.getCount_expected()} шт.</td>
                        <td>${product.getPrice()} тг.</td>
                        <td>${product.getPrice() * product.getCount_on_warehouse()} тг.</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
    <@modal.addProductModalView/>
</@c.page>