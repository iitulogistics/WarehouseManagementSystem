<#import "parts/nav.ftl" as ui/>
<#import "parts/common.ftl" as c/>
<@c.page>
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
</@c.page>