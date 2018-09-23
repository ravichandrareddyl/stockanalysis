import { modelConstants } from '../_constants';

export function model(state = {}, action) {
  switch (action.type) {
    case modelConstants.MODEL_SHOW:
      return {
        visible: true
      };
    case modelConstants.MODEL_CANCEL:
      return {
        visible: false
      };
    default:
      return state
  }
}