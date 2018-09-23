import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { userActions, stockActions, modelActions} from '../_actions';
import { LoadingIndicator, StockList, StockForm } from '../_components';
import { Button, Row, Col } from 'antd';
import { createLoadingSelector } from '../_services';
import './Home.css';
class HomeContainer extends React.Component {
    constructor (props) {
        super(props);
        this.saveFormRef = this.saveFormRef.bind(this);
        this.showModal = this.showModal.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
        this.handleCreate = this.handleCreate.bind(this);
    }
    
    showModal() {
        this.props.dispatch(modelActions.showModel());  
    }

    handleCancel(){
        this.props.dispatch(modelActions.hideModel());
    }

    handleCreate() {
        const form = this.formRef.props.form;
        form.validateFields((err, values) => {
            if (err) {
                return;
            }
            console.log('Received values of form: ', values);
            form.resetFields();
            this.props.dispatch(modelActions.hideModel());
            this.props.dispatch(stockActions.addStock(values));
        });
    }

    saveFormRef(formRef) {
        this.formRef = formRef;
    }

    componentDidMount() {
        this.props.dispatch(stockActions.getStocks());
    }

    // shouldComponentUpdate(nextProps) {
    //     const shouldUpdate = this.props.refresh != nextProps.refresh;
    //     return shouldUpdate;
    // }

    render() {
        const { stocks, isLoading, visible, refresh } = this.props;
        if(isLoading) {
            return <LoadingIndicator />
        }
        return (
            <div className="home-container">
                <Row>
                    <Col span={24}>
                        <Button type="primary" onClick={this.showModal}>Add Stock</Button>
                        <StockForm
                        wrappedComponentRef={this.saveFormRef}
                        visible={visible}
                        onCancel={this.handleCancel}
                        onCreate={this.handleCreate}
                        />
                    </Col>
                </Row>
                <Row>
                    <Col span={24}><StockList stocks={stocks}/></Col>
                </Row>
            </div>
        );
    }
}

const loadingSelector = createLoadingSelector(['STOCKS_GETS', 'ADD_STOCK']);

function mapStateToProps(state) {
    const { stocks, model } = state;
    const { visible } = model;

    return {
        isLoading: loadingSelector(state),
        stocks: stocks.items,
        visible,
        refresh: stocks.refresh
    };
}

const connectedHomeContainer = connect(mapStateToProps)(HomeContainer);
export { connectedHomeContainer as HomeContainer };