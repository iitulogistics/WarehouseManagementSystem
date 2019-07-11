<#import "nav.ftl" as ui/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>

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
<div class="order col-lg-7">
    <form method="post" action="/batch/addBatch">
        <div class="form-group">
            <label for="exampleInputEmail1"></label>
            <input type="number" class="form-control" id="inputOrder" aria-describedby="emailHelp"
                   placeholder="Enter count product" name="count">

            <input type="text" class="form-control" id="inputOrder" aria-describedby="emailHelp"
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
    <h2>Список заказов</h2>
    <div class="search_form">
        <form method="post">
            <div class="form-group">
                <label for="exampleInputEmail1"></label>
                <input type="product_name" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                       placeholder="Enter company name">
                <small id="emailHelp" class="form-text text-muted">Напишите наименования компании для которой
                    сформирован заказ
                </small>
            </div>
            <button type="submit" class="btn btn-primary">Поиск</button>
        </form>
    </div>
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
</body>
</html>