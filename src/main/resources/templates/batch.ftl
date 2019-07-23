<#import "parts/nav.ftl" as ui/>
<#import "parts/common.ftl" as c/>
<@c.page>
    <@ui.nav/>
    <div class="order col-lg-7">
        <form method="post" action="/batch/addBatch">
            <div class="form-group">
                <label for="exampleInputEmail1"></label>
                <input required type="number" class="form-control" id="inputOrder" aria-describedby="emailHelp"
                       placeholder="Enter count product" name="count">

                <input required type="text" class="form-control" id="inputOrder" aria-describedby="emailHelp"
                       placeholder="Enter name company" name="company_name">

                <select name="id_product" class="form-control" size="1" required>
                    <#list products as product>
                        <option value="${product.getId()}">${product.getProduct_name()}</option>
                    </#list>
                </select>

            </div>
            <button type="submit" class="btn btn-primary">Сделать заказ</button>
        </form>
    </div>

    <div class="col-lg-12">
        <div class="search_form">
            <form method="get">
                <div class="form-group">
                    <label for="exampleInputEmail1"></label>
                    <input type="product_name" class="form-control" name="company_name" id="exampleInputEmail1"
                           aria-describedby="emailHelp"
                           placeholder="Enter company name">
                    <small id="emailHelp" class="form-text text-muted">Напишите наименования компании для которой
                        сформирован заказ
                    </small>
                </div>
                <button type="submit" class="btn btn-primary">Поиск</button>
            </form>
        </div>

        <h2>Список заказов</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Id</th>
                <th scope="col">Наименование компании</th>
                <th scope="col">Список товаров</th>
                <th scope="col">Стоимость заказа</th>
            </tr>
            </thead>
            <tbody>
            <#list batches as batch>
                <tr>
                    <th scope="row">${batch?index + 1}</th>
                    <td>${batch[0]}</td>
                    <td>${batch[1]}</td>
                    <td>${batch[2]} тг</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>