<#import "parts/nav.ftl" as ui/>
<#import "parts/common.ftl" as c/>
<@c.page>
    <@ui.nav/>
    <div class="main_content">
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
                                <input required type="number" class="form-control" id="search_prod_by_name"
                                       name="stillage_index" value="stillage_index"
                                       placeholder="Enter stillage index">
                            </div>
                            <div class="col-lg-6">
                                <input required type="number" class="form-control" id="search_prod_by_name"
                                       name="shelf_index" value="shelf_index"
                                       placeholder="Enter shelf index">
                            </div>
                        </div>

                    </div>
                    <button type="submit" class="btn btn-primary">поставить на полку</button>
                </form>
            </div>
        </div>
    </div>
</@c.page>