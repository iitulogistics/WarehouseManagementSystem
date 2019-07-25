<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page title="Авторизация">
    <div class="row">
        <div class="col-lg-6 offset-lg-3">
            <@l.login path="/login" title="Авторизоваться"/>
            <a href="/registration">Registration</a>
        </div>
    </div>
</@c.page>