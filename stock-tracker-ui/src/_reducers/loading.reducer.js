const loadingReducer = (state = {}, action) => {
    const { type } = action;
    //console.log('type===>'+ type);
    //console.log('action===>'+ action);
    const matches = /(.*)_(REQUEST|SUCCESS|FAILURE)/.exec(type);
    //console.log('matches===>'+ matches);
    // not a *_REQUEST / *_SUCCESS /  *_FAILURE actions, so we ignore them
    if (!matches) return state;  
    //console.log('matches===>'+ matches);
    const [, requestName, requestState] = matches;
    return {
      ...state,
      // Store whether a request is happening at the moment or not
      // e.g. will be true when receiving GET_TODOS_REQUEST
      //      and false when receiving GET_TODOS_SUCCESS / GET_TODOS_FAILURE
      [requestName]: requestState === 'REQUEST',
    };
};

export {loadingReducer as loader};