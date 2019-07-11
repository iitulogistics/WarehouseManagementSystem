<#import "nav.ftl" as ui/>
<!DOCTYPE html>
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

    <title>Statistics</title>
</head>

<body>
<@ui.nav/>

<div class="main_content">
    <div class="col-lg-12">
        <div class="stillage_statistics">
            <h2>Статистика по стилажам</h2>

            <table class="table table-striped">
                <thead>
                <th colspan="2" class="col">На складе ${count} стеллажных ячеек</th>
                </thead>
                <tbody>
                <tr>
                    <td>свободных ячеек:</td>
                    <td>${looseStillage}</td>
                </tr>
                <tr>
                    <td>полусвободных ячеек:</td>
                    <td>${withContainer}</td>
                </tr>
                <tr>
                    <td>стеллажей занято:</td>
                    <td>${busy}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>