<#macro nav>
    <nav class="navbar">
        <div class="col-lg-3 logo">
            <img src="img/logo.png" alt="logo">
        </div>

        <div class="col-lg-7 mainList">
            <a href="/product">
                <div class="mainListContent">Продукты</div>
            </a>
            <a href="statistics">
                <div class="mainListContent">Статистика</div>
            </a>

            <a href="batch">
                <div class="mainListContent">Заказы</div>
            </a>

            <a href="store">
                <div class="mainListContent">Хранение товаров</div>
            </a>

            <a href="stillage">
                <div class="mainListContent">Стилажи</div>
            </a>
            <a href="task">
                <div class="mainListContent">Задачи</div>
            </a>
        </div>

        <div class="col-lg-2">
            <div class="row">
                <p class="text-muted">User: ${user.username}</p>
                <form action="/logout" method="post">
                    <div style="margin-left: 10px;">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-primary">Logout</button>
                    </div>
                </form>
            </div>
        </div>
    </nav>
</#macro>