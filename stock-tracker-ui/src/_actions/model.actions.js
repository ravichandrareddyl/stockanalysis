import { modelConstants } from '../_constants';

export const modelActions = {
    showModel,
    hideModel
};

function showModel() {
    return { type: modelConstants.MODEL_SHOW };
}

function hideModel() {
    return { type: modelConstants.MODEL_CANCEL };
}