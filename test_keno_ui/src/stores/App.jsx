import rootReducer from '../reducers/root'
import { createStore, applyMiddleware } from 'redux'
import thunk from 'redux-thunk'

const appStore = createStore(
    rootReducer,
    applyMiddleware(thunk)
);

export default appStore;