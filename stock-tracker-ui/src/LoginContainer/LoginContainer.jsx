import React from 'react';
import { connect } from 'react-redux';
import { LoginForm } from './LoginForm';
import { Form } from 'antd';
import { userActions } from '../_actions';
import { LoadingIndicator } from '../_components';
import { createLoadingSelector } from '../_services';
import './Login.css';

class LoginContainer extends React.Component {
    render() {
        const AntWrappedLoginForm = Form.create()(LoginForm)
        if(this.props.isLoading) {
            return <LoadingIndicator />
        }
        return (
            <div className="login-container">
                <h1 className="page-title">Login</h1>
                <div className="login-content">
                     <AntWrappedLoginForm {...this.props}/>
                </div>
            </div>
        );
    }
}

function mapDispatchToProps(dispatch) {
    return {
        login: (usernameOrEmail, password, loginError) => dispatch(userActions.login(usernameOrEmail, password, loginError))
    }
}
const loadingSelector = createLoadingSelector(['USERS_LOGIN']);
function mapStateToProps(state) {
    return {
        isLoading: loadingSelector(state)
    };
}


const connectedLoginContainer = connect(mapStateToProps, mapDispatchToProps)(LoginContainer);
export { connectedLoginContainer as LoginContainer }; 