import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
//import { BrowserRouter as Router } from 'react-router-dom';
import { Router } from 'react-router';
import { store , history} from './_helpers';
import { App } from './App';
import 'bootstrap/dist/css/bootstrap.min.css';

// setup fake backend
//import { configureFakeBackend } from './_helpers';
//configureFakeBackend();

render(
    <Provider store={store}>
        <Router history={history} >
            <App />
        </Router>
    </Provider>,
    document.getElementById('app')
);