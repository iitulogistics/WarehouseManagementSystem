<#macro addProductModalView>
    <div class="modal fade" id="addProductModalView" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Добавление продукта</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="/product/addProductByParam" method="post" id="addProduct">
                        <div class="form-group">
                            <label for="search_prod_by_name1">Наименование товара</label>
                            <input required type="text" class="form-control"
                                   name="product_name"
                                   placeholder="Enter product name">
                            <small class="form-text text-muted">Напишите наименования продукта</small>

                            <div class="form-group row">
                                <div class="col-lg-4">
                                    <input required type="number" step="0.001" class="form-control"

                                           name="length"
                                    >
                                    <small class="form-text text-muted">Напишите длину продукта в метрах
                                    </small>
                                </div>
                                <div class="col-lg-4">
                                    <input required type="number" step="0.001" class="form-control"
                                           name="height"
                                    >
                                    <small class="form-text text-muted">Напишите высоту продукта в метрах
                                    </small>
                                </div>
                                <div class="col-lg-4">
                                    <input required type="number" step="0.001" class="form-control"
                                           name="width"
                                    >
                                    <small class="form-text text-muted">Напишите ширину продукта в метрах
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
                                    <small class="form-text text-muted">Выберите тип товара</small>
                                </div>

                                <div class="col-lg-4">
                                    <input required type="number" step="0.001" class="form-control"
                                           name="weight">

                                    <small class="form-text text-muted">Напишите вес продукта в кг.
                                    </small>
                                </div>

                                <div class="col-lg-4">
                                    <input required type="number" step="0.001" class="form-control"
                                           name="price">

                                    <small class="form-text text-muted">Напишите цену продукта в тг.
                                    </small>
                                </div>
                            </div>
                        </div>
<#--                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
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

<#macro addCell>
    <div class="modal fade" id="addCell" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Добавить ячейку</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="/stillage/addStillageByRequests" method="post" id="addCellForm">
                        <div class="form-group">
                            <div class="form-group">
                                <label>Напишите расположение ячейки</label>
                            </div>
                            <div class="form-group row">
                                <div class="col-lg-4">
                                    <input required type="number" class="form-control"
                                           name="stillage_index"
                                    >
                                    <small class="form-text text-muted">Номер стеллажа
                                    </small>
                                </div>
                                <div class="col-lg-4">
                                    <input required type="number" class="form-control"
                                           name="shelf_index">
                                    <small class="form-text text-muted">Номер полки
                                    </small>
                                </div>
                                <div class="col-lg-4">
                                    <input required type="number" class="form-control"
                                           name="cell_index">
                                    <small class="form-text text-muted">Номер ячейки
                                    </small>
                                </div>
                            </div>

                            <div class="form-group">
                                <label>Напишите размер ячейки</label>
                            </div>
                            <div class="form-group row">
                                <div class="col-lg-4">
                                    <input required type="number" step="0.001" class="form-control"
                                           name="width">
                                    <small class="form-text text-muted">Ширина</small>
                                </div>

                                <div class="col-lg-4">
                                    <input required type="number" step="0.001" class="form-control"
                                           name="length">

                                    <small class="form-text text-muted">Длина
                                    </small>
                                </div>

                                <div class="col-lg-4">
                                    <input required type="number" step="0.001" class="form-control"
                                           name="height">

                                    <small class="form-text text-muted">Высота
                                    </small>
                                </div>
                            </div>
                            <div class="form-group row">
                                <div class="col-lg-6">
                                    <input required type="number" step="0.001" class="form-control"
                                           name="max_weight">
                                    <small class="form-text text-muted">Максимально допустимый вес</small>
                                </div>

                                <div class="col-lg-6">
                                    <input required type="number" class="form-control"
                                           name="max_count_object">

                                    <small class="form-text text-muted">Мак. кол-во товара на полке
                                    </small>
                                </div>
                            </div>
                            <div class="form-group">
                                <select id="tokens" class="selectpicker form-control" multiple data-live-search="true"
                                        name="products">
                                    <option value="fragile">Хрупкий товар</option>
                                    <option value="food">Продукты питания</option>
                                    <option value="technics">Техника</option>
                                </select>

                                <small class="form-text text-muted">Тип товара для хранение
                                </small>
                            </div>
                        </div>
<#--                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button form="addCellForm" type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</#macro>