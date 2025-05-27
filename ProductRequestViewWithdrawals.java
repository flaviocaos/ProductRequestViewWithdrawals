
package br.com.firsti.packages.stock.modules.productRequest.actions;

import static br.com.firsti.packages.stock.common.StockReferences.AVAILABILITY_DATE;
import static br.com.firsti.packages.stock.common.StockReferences.DESTINATION_COLLABORATOR_ID;
import static br.com.firsti.packages.stock.common.StockReferences.DESTINATION_DIVISION_ID;
import static br.com.firsti.packages.stock.common.StockReferences.ID;
import static br.com.firsti.packages.stock.common.StockReferences.MODEL;
import static br.com.firsti.packages.stock.common.StockReferences.NAME;
import static br.com.firsti.packages.stock.common.StockReferences.PRODUCT_ID;
import static br.com.firsti.packages.stock.common.StockReferences.QUANTITY;
import static br.com.firsti.packages.stock.common.StockReferences.STATUS;
import static br.com.firsti.packages.stock.common.StockReferences.TYPE_ID;

import br.com.firsti.languages.LRCore;
import br.com.firsti.languages.LRProduct;
import br.com.firsti.languages.LRStock;
import br.com.firsti.module.actions.AbstractActionList;
import br.com.firsti.module.exceptions.AccessDeniedException;
import br.com.firsti.module.exceptions.ResourceNotFoundException;
import br.com.firsti.module.requests.ActionRequest;
import br.com.firsti.packages.core.entities.Person;
import br.com.firsti.packages.core.modules.resource.classes.ResourceManager;
import br.com.firsti.packages.organization.entities.CompanyDivision;
import br.com.firsti.packages.product.entities.Product;
import br.com.firsti.packages.product.entities.ProductType;
import br.com.firsti.packages.stock.common.StockReferences;
import br.com.firsti.packages.stock.entities.WarehouseWithdrawal;
import br.com.firsti.packages.stock.entities.WarehouseWithdrawal.WarehouseWithdrawalStatus;
import br.com.firsti.packages.stock.modules.productRequest.ModuleProductRequest;
import br.com.firsti.packages.stock.modules.warehouseWithdrawal.ModuleWarehouseWithdrawal;
import br.com.firsti.packages.stock.modules.warehouseWithdrawal.actions.WarehouseWithdrawalView;
import br.com.firsti.persistence.EntityManagerWrapper;
import br.com.firsti.services.websocket.messages.output.elements.DataFormat;
import br.com.firsti.services.websocket.messages.output.elements.ElementRequest;
import br.com.firsti.services.websocket.messages.output.elements.items.Select;
import br.com.firsti.system.LanguageResource;

public class ProductRequestViewWithdrawals extends AbstractActionList<ModuleProductRequest> {

    public ProductRequestViewWithdrawals() throws IllegalArgumentException {
        super(new Builder<ModuleProductRequest>(Access.COMPANY_PRIVATE, TableType.LIST, WarehouseWithdrawal.class)
                .setIconClass("fa-solid fa-dolly")

                .addJoin(createJoin(Product.class, WarehouseWithdrawal.class).on(ID, PRODUCT_ID))
                .addJoin(createJoin(ProductType.class, Product.class).on(ID, TYPE_ID))
                .addJoin(createJoin(CompanyDivision.class, WarehouseWithdrawal.class).on(ID, DESTINATION_DIVISION_ID))
                .addJoin(createJoin(Person.class, WarehouseWithdrawal.class).on(ID, DESTINATION_COLLABORATOR_ID))

                .addColumn(createColumn(StockReferences.PICTURE, LanguageResource.EMPTY, DataFormat.IMAGE).setWidth(32)
                        .addDependency(Product.class, ID)
                        .setOnValueRequest((entityManager, registry) -> {
                            return ResourceManager
                                    .newInstance(entityManager, Product.class, registry.get(Product.class, ID), StockReferences.PICTURE)
                                    .getFirstId();
                        }))
                .addColumn(createColumn(WarehouseWithdrawal.class, AVAILABILITY_DATE, LRStock.AVAILABILITY, DataFormat.DATETIME).setWidth(140))
                .addColumn(createColumn(CompanyDivision.class, NAME, LRCore.DIVISION, DataFormat.TEXT))
                .addColumn(createColumn(Person.class, NAME, LRCore.COLLABORATOR, DataFormat.TEXT))
                .addColumn(createColumn(ProductType.class, NAME, LRProduct.PRODUCT_TYPE, DataFormat.TEXT))
                .addColumn(createColumn(Product.class, MODEL, LRCore.MODEL, DataFormat.TEXT))
                .addColumn(createColumn(WarehouseWithdrawal.class, QUANTITY, LRCore.QUANTITY, DataFormat.DECIMAL).setWidth(120))
                .addColumn(createColumn(WarehouseWithdrawal.class, STATUS, LRCore.STATUS, DataFormat.TEXT).setTranslate(LRStock.class).setWidth(160)
                        .addFormatRule(WarehouseWithdrawalStatus.class))

                .addFilter(createFilter(new Select(STATUS).setLabel(LRCore.STATUS).setTranslate(LRStock.class).addClass("col-12"))
                        .setFilter(FilterOperator.EQUAL, WarehouseWithdrawal.class, STATUS))

                .setOrder(WarehouseWithdrawal.class, AVAILABILITY_DATE, TableOrderType.DESC)
                .setOnClick(ElementRequest.createModalRequest(WarehouseWithdrawalView.class))
                .setSelection(TableSelection.MULTIPLE));
    }

    @Override
    public void onWindowRequest(EntityManagerWrapper entityManager, ActionRequest request, WindowBuilder windowBuilder)
            throws AccessDeniedException, ResourceNotFoundException {
        windowBuilder.getPreloadBuilder()
                .add(STATUS, WarehouseWithdrawalStatus.class);
    }
}
