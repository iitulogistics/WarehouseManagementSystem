<#import "nav.ftl" as ui/>
<#import "modalViews.ftl" as modal/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="css/style.css">

    <title>MainScene</title>
</head>
<body>
<@ui.nav/>
<div class="main_content col-lg-12">
    <div class="">

        <h2>Список товаров</h2>
        <div class="search_form">
            <form method="post">
                <div class="form-group">
                    <label for="search_prod_by_name1">Наименование товара</label>
                    <#--                      ${last_search}-->
                    <input type="text" class="form-control" id="search_prod_by_name" name="name" value="${last_search}"
                           placeholder="Enter product name">
                    <small id="emailHelp" class="form-text text-muted">Напишите наименования продукта для дальнейшего
                        поиска
                    </small>
                </div>
                <button type="submit" class="btn btn-primary">Поиск</button>
            </form>
        </div>

        <div class="row">
            <div class="col">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addProductModalView">
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
<script src="js/bootstrap.min.js"></script>
</body>
</html>