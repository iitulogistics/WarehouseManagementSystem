<#import "parts/nav.ftl" as ui/>
<#import "parts/common.ftl" as c/>
<@c.page title="Хранение">
    <@ui.nav/>
    <div class="main_content">
        <div class="col-lg-12 optimization">
            <form action="/store/optimization" method="post">
                <div class="form-group">
<#--                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
                    <button type="submit" class="btn btn-primary">Оптимизировать склад</button>
                </div>
            </form>
        </div>
        <div class="col-lg-12 inventorization">
            <form action="/store/inventory" method="post">
                <div class="form-group">
<#--                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
                    <button type="submit" class="btn btn-primary">Произвести инвентаризацию склада</button>
                </div>
            </form>
        </div>
    </div>
</@c.page>