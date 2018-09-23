import config from 'config';
import { authHeader } from '../_helpers';

export const stockService = {
    addStock,
    getStocks
};

function getStocks() {
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };

    return fetch(`${config.apiUrl}/stocks/all`, requestOptions).then(handleResponse);
}

function addStock(stock) {
    const requestOptions = {
        method: 'POST',
        headers: Object.assign({}, authHeader(), {'Content-Type': 'application/json'}),
        body: JSON.stringify(stock)
    };

    return fetch(`${config.apiUrl}/stocks/save`, requestOptions).then(handleResponse);
}

function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        if (!response.ok) {
            if (response.status === 401) {
                // auto logout if 401 response returned from api
                logout();
                //check if this is needed ?
               // location.reload(true);
            }

            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }

        return data;
    });
}