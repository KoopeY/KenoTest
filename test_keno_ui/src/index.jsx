import React from 'react'
import ReactDOM from 'react-dom'
import { Provider } from 'react-redux'

import appStore from './stores/App'
import Game from "./containers/Game";
import {Api} from "./middleware/Api"

import 'semantic-ui-css/semantic.min.css';

setInterval(() => appStore.dispatch(Api.getRoundInfo()), 1000);

ReactDOM.render (
    <Provider store={appStore}>
        <Game/>
    </Provider>,
    document.getElementById('root')
);