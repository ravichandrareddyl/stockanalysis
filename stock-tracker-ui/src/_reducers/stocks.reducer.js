import { stockConstants } from '../_constants';

export function stocks(state = {}, action) {
  switch (action.type) {
    case stockConstants.GETSTOCKS_REQUEST:
      return {
        loading: true
      };
    case stockConstants.GETSTOCKS_SUCCESS:
      return {
        items: action.items
      };
    case stockConstants.GETSTOCKS_FAILURE:
      return { 
        error: action.error
      };
    default:
      return state
  }
}