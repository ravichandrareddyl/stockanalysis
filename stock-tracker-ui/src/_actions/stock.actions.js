import { stockConstants } from '../_constants';
import { stockService } from '../_services';
import { alertActions } from './';
import { history } from '../_helpers';

export const stockActions = {
    getStocks,
    addStock
};

function addStock(stock) {
    return dispatch => {
        dispatch(request());

        stockService.addStock(stock)
        .then(
            response => {
                dispatch(getStocks());
                return dispatch(success(response));
            },
            error => dispatch(failure(error))
        );

    };

    function request() { return { type: stockConstants.ADD_STOCK_REQUEST}}
    function success(response) { return { type: stockConstants.ADD_STOCK_SUCCESS, response}}
    function failure(error) { return { type: stockConstants.ADD_STOCK_FAILURE, error}}
}

function getStocks() {
    return dispatch => {
        dispatch(request());

        stockService.getStocks()
            .then(
                stocks => dispatch(success(stocks)),
                error => dispatch(failure(error))
            );
    };

    function request() { return { type: stockConstants.GETSTOCKS_REQUEST } }
    function success(items) { return { type: stockConstants.GETSTOCKS_SUCCESS, items } }
    function failure(error) { return { type: stockConstants.GETSTOCKS_FAILURE, error } }
}