<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page title="Регистрация">
    <div class="row">
        <div class="col-lg-6 offset-lg-3">
            <@l.login title="Зарегестрироваться" path="/registration"/>
        </div>
    </div>
</@c.page>