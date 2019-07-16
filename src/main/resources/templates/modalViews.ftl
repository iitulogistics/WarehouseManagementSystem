<#macro addProductModalView>
    <div class="modal fade" id="addProductModalView" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <h3>Добавление продукта</h3>
                    <form action="/addProductByParam" method="post" id="addProduct">
                        <div class="form-group">
                            <label for="search_prod_by_name1">Наименование товара</label>
                            <input type="text" class="form-control" id="search_prod_by_name" name="product_name"
                                   placeholder="Enter product name">
                            <small id="emailHelp" class="form-text text-muted">Напишите наименования продукта</small>

                            <div class="form-group row">
                                <div class="col-lg-4">
                                    <input type="number" class="form-control" id="search_prod_by_name" name="length"
                                    >
                                    <small id="emailHelp" class="form-text text-muted">Напишите длину продукта в метрах
                                    </small>
                                </div>
                                <div class="col-lg-4">
                                    <input type="number" class="form-control" id="search_prod_by_name" name="height"
                                    >
                                    <small id="emailHelp" class="form-text text-muted">Напишите высоту продукта в метрах
                                    </small>
                                </div>
                                <div class="col-lg-4">
                                    <input type="number" class="form-control" id="search_prod_by_name" name="width"
                                    >
                                    <small id="emailHelp" class="form-text text-muted">Напишите ширину продукта в метрах
                                    </small>
                                </div>
                            </div>

                            <div class="form-group row">
                                <div class="col-lg-4">
                                    <select class="form-control" name="type_product">
                                        <option value="fragile">Хрупкий товар</option>
                                        <option value="food">Продукты питания</option>
                                        <option value="technics">Техника</option>
                                    </select>
                                    <small id="emailHelp" class="form-text text-muted">Выберите тип товара</small>
                                </div>

                                <div class="col-lg-4">
                                    <input type="number" class="form-control" id="search_prod_by_name" name="weight"
                                    >
                                    <small id="emailHelp" class="form-text text-muted">Напишите вес продукта в кг.</small>
                                </div>

                                <div class="col-lg-4">
                                    <input type="number" class="form-control" id="search_prod_by_name" name="price"
                                    >
                                    <small id="emailHelp" class="form-text text-muted">Напишите цену продукта в тг.</small>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button form="addProduct" type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</#macro>