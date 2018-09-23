import { combineReducers } from 'redux';

import { authentication } from './authentication.reducer';
import { users } from './users.reducer';
import { alert } from './alert.reducer';
import { stocks } from './stocks.reducer';
import { loader } from './loading.reducer';
import { model } from './model.reducer';

const rootReducer = combineReducers({
  authentication,
  users,
  alert,
  stocks,
  loader,
  model
});

export default rootReducer;