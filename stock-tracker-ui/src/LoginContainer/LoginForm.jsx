import React from 'react';
import { Link } from 'react-router-dom';
import { Form, Input, Button, Icon, notification } from 'antd';
const FormItem = Form.Item;

class LoginForm extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleLoginError = this.handleLoginError.bind(this);
    }

    handleLoginError(e) {
        notification.error({
            message: 'Demo App',
            description: e
        });
    }

    handleSubmit(event) {
        event.preventDefault();
        // this.setState({ submitted: true });
        this.props.form.validateFields((err, values) => {
            if (!err) {
                const { usernameOrEmail, password } = values;
                this.props.login(usernameOrEmail, password, this.handleLoginError);
            }

        })
    }

    render() {
        const { getFieldDecorator } = this.props.form;
        return (
            <Form onSubmit={this.handleSubmit} className="login-form">
                <FormItem>
                    {getFieldDecorator('usernameOrEmail', {
                        rules: [{ required: true, message: 'Please input your username or email!' }],
                    })(
                        <Input
                            prefix={<Icon type="user" />}
                            size="large"
                            name="usernameOrEmail"
                            placeholder="Username or Email" />
                    )}
                </FormItem>
                <FormItem>
                    {getFieldDecorator('password', {
                        rules: [{ required: true, message: 'Please input your Password!' }],
                    })(
                        <Input
                            prefix={<Icon type="lock" />}
                            size="large"
                            name="password"
                            type="password"
                            placeholder="Password" />
                    )}
                </FormItem>
                <FormItem>
                    <Button type="primary" htmlType="submit" size="large" className="login-form-button">Login</Button>
                    Or <Link to="/signup">register now!</Link>
                </FormItem>
            </Form>
        );
    }
}

export { LoginForm as LoginForm };