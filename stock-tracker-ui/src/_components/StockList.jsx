import React from 'react';
import { Table } from 'antd';

const historyColumns = [
    { title: 'History Id', dataIndex: 'stockHistId', key: 'stockHistId' },
    { title: 'Market Price', dataIndex: 'marketPrice', key: 'marketPrice' },
    { title: 'Square Off', dataIndex: 'squareOff', key: 'squareOff' },
    { title: 'Sell Off', dataIndex: 'sellOff', key: 'sellOff' },
    { title: 'Status', dataIndex: 'status', key: 'status' },
];

const columns = [
    { title: 'Name', dataIndex: 'name', key: 'name' },
    { title: 'Operation', dataIndex: 'operation', key: 'operation' },
    { title: 'Price', dataIndex: 'price', key: 'price' },
    { title: 'Percent', dataIndex: 'percent', key: 'percent' },
    { title: 'Tracking Status', dataIndex: 'trackingStatus', key: 'trackingStatus' }
];

const getHistory = (hist) => {
    return hist ? hist.map((h, idx) => Object.assign({}, h, {key: idx})) : [];
};

class StockList extends React.Component {
    // shouldComponentUpdate(nextProps) {
    //     const shouldUpdate = this.props.refresh != nextProps.refresh;
    //     return shouldUpdate;
    // }

    render() {
        const { stocks } = this.props;
        //console.log('stocks===>'+ stocks.length());
        const stocksDs = stocks ? stocks.map((stock, idx) => {
            return {
                key: idx,
                name: stock.stock,
                percent: stock.percent,
                operation: stock.opertion,
                history: getHistory(stock.history),
                alertStatus: stock.alertStatus,
                trackingStatus: stock.trackingStatus,
                price: stock.price
            };
        }): [];


        return (
            <Table 
            dataSource={stocksDs}
            expandedRowRender={stock => <Table dataSource={stock.history} columns={historyColumns}/>} 
            columns={columns} 
            />
        );
    }
}

export {StockList as StockList};
