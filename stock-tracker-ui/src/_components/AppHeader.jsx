import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Layout, Menu, Dropdown, Icon } from 'antd';
import { ProfileDropdownMenu } from './ProfileDropdownMenu';
import { userActions } from '../_actions';

import './AppHeader.css';
const Header = Layout.Header;

class AppHeader extends React.Component {

  constructor(props) {
    super(props);

    this.handleMenuClick = this.handleMenuClick.bind(this);
  }

  handleMenuClick({ key }) {
    if(key === "logout") {
      this.props.dispatch(userActions.logout());
    }
  }

  render() {
    const { user } = this.props;
    let menuItems;
    if (user) {
      menuItems = [
        <Menu.Item key="/">
          <Link to="/">
            <Icon type="home" className="nav-icon" />
          </Link>
        </Menu.Item>,
        <Menu.Item key="/profile" className="profile-menu">
          <ProfileDropdownMenu
            currentUser={user.name}
            handleMenuClick={this.handleMenuClick} />
        </Menu.Item>
      ];
    } else {
      menuItems = [
        <Menu.Item key="/login">
          <Link to="/login">Login</Link>
        </Menu.Item>,
        <Menu.Item key="/signup">
          <Link to="/signup">Signup</Link>
        </Menu.Item>
      ];
    }

    return (
      <Header className="app-header">
        <div className="container">
          <div className="app-title" >
            <Link to="/">Stock Tracker</Link>
          </div>
          <Menu
            className="app-menu"
            mode="horizontal"
           // selectedKeys="test"
            style={{ lineHeight: '64px' }} >
            {menuItems}
          </Menu>
        </div>
      </Header>
    );
  }
}

function mapStateToProps(state) {
  // what ever details we get here from state can be accessible in 
  // html template.
  const { authentication } = state;
  const { user } = authentication;
  return {
    user
  };
}

const connectedAppHeader = connect(mapStateToProps)(AppHeader);
export { connectedAppHeader as AppHeader };