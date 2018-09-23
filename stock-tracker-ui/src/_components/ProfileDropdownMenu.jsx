import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { Menu, Dropdown, Icon } from 'antd';

class ProfileDropdownMenu extends React.Component {
    render() {
        const { handleMenuClick, currentUser } = this.props;
        const dropdownMenu = (
            <Menu onClick={handleMenuClick} className="profile-dropdown-menu">
                <Menu.Item key="user-info" className="dropdown-item" disabled>
                    <div className="user-full-name-info">
                        {currentUser}
                    </div>
                    <div className="username-info">
                        @{currentUser}
                    </div>
                </Menu.Item>
                <Menu.Divider />
                <Menu.Item key="profile" className="dropdown-item">
                    <Link to={`/users/${currentUser}`}>Profile</Link>
                </Menu.Item>
                <Menu.Item key="logout" className="dropdown-item">
                    Logout
              </Menu.Item>
            </Menu>
        );

        return (
            <Dropdown
                overlay={dropdownMenu}
                trigger={['click']}
                getPopupContainer={() => document.getElementsByClassName('profile-menu')[0]}>
                <a className="ant-dropdown-link">
                    <Icon type="user" className="nav-icon" style={{ marginRight: 0 }} /> <Icon type="down" />
                </a>
            </Dropdown>
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

const connectedAppHeader = connect(mapStateToProps)(ProfileDropdownMenu);
export { connectedAppHeader as ProfileDropdownMenu };