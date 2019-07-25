<#macro login path title>
    <br>
    <h4 class="align-content-center">${title}</h4>
    <form method="post" action="${path}">
        <div class="form-group">
            <label for="inputOrder"></label>
            <input required type="text" class="form-control" id="inputOrder" aria-describedby="emailHelp"
                   placeholder="Enter user name" name="username">
        </div>
        <div class="form-group">
            <input required type="password" class="form-control" id="inputOrder" aria-describedby="emailHelp"
                   placeholder="Enter password" name="password">

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </div>

        <button type="submit" class="btn btn-primary" value="Sign In">Sign In</button>
    </form>
</#macro>