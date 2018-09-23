import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

import { userActions, stockActions } from '../_actions';

class HomePage extends React.Component {
    // componentDidMount() {
    //     this.props.dispatch(stockActions.getStocks());
    // }

    render() {
        const { user, stocks } = this.props;
        return (
            <div className="col-md-6 col-md-offset-3">
                <h1>Hi {user.name}!</h1>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const { stocks, authentication } = state;
    const { user } = authentication;
    return {
        user,
        stocks
    };
}

const connectedHomePage = connect(mapStateToProps)(HomePage);
export { connectedHomePage as HomePage };