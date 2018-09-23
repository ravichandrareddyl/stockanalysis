import React from 'react';
import { Router, Route , Switch, withRouter } from 'react-router-dom';
import { connect } from 'react-redux';

import { history } from '../_helpers';
import { alertActions } from '../_actions';
import { PrivateRoute, AppHeader } from '../_components';
import { HomeContainer } from '../containers';
import { LoginContainer } from '../LoginContainer';
import { Layout, notification } from 'antd';
const { Content } = Layout;
import './App.css'

class App extends React.Component {
    constructor(props) {
        super(props);

        const { dispatch } = this.props;
        history.listen((location, action) => {
            // clear alert on location change
            dispatch(alertActions.clear());
        });
    }

    render() {
        const { alert, user } = this.props;

        return (
            <Layout className="app-container">
                <AppHeader isAuthenticated={ user ? true : false}
                currentUser={user} />
                <Content className = "app-content">
                    <div className="container"> 
                        <Switch>
                            <PrivateRoute exact path="/" component={HomeContainer} />
                            <Route path="/login" component={LoginContainer} /> 
                        </Switch>
                    </div>
                </Content>
            </Layout>
        );
    }
}

function mapStateToProps(state) {
    const { alert, authentication } = state;
    const { user } = authentication;
    return {
        alert,
        user
    };
}

const connectedApp = connect(mapStateToProps)(withRouter(App));
export { connectedApp as App }; 