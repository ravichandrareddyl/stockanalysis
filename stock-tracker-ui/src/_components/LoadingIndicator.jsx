import React from 'react';
import { Spin, Icon } from 'antd';

class LoadingIndicator extends React.Component {
    render() {
        const antIcon = <Icon type="loading-3-quarters" style={{ fontSize: 30 }} spin />;
        return (
            <Spin indicator={antIcon} style = {{display: 'block', textAlign: 'center', marginTop: 30}} />
        );
    }
}

export {LoadingIndicator as LoadingIndicator};