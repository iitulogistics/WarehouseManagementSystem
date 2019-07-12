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

    <title>Stillage</title>
</head>
<body>
<@ui.nav/>
<div class="main_content">
    <h2>Кол-во контейнеров которые: ${count_containers} шт.</h2>
    <div class="col-lg-12">
            <div class="order col-lg-7">
                <form method="post" action="/store/putOnShelfByIndexes">
                    <div class="form-group">
                        <select name="id_container" class="form-control" size="1" required>
                            <#list containers as container>
                                <option value="${container[0]}">${container[1]}</option>
                            </#list>
                        </select>

                        <br>
                        <div class="row">
                            <div class="col-lg-6">
                                <input type="number" class="form-control" id="search_prod_by_name" name="stillage_index" value="stillage_index"
                                       placeholder="Enter stillage index">
                            </div>
                            <div class="col-lg-6">
                                <input type="number" class="form-control" id="search_prod_by_name" name="shelf_index" value="shelf_index"
                                       placeholder="Enter shelf index">
                            </div>
                        </div>

                    </div>
                    <button type="submit" class="btn btn-primary">поставить на полку</button>
                </form>
            </div>
    </div>
</div>

</div>
</body>
</html>