import { combineReducers } from 'redux'
import { game } from './Game'
import { reducer as formReducer } from 'redux-form'

const rootReducer = combineReducers({
    game,
    form: formReducer
});

export default rootReducer;