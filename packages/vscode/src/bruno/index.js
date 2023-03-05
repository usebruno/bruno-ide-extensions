import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import ReduxStore from './providers/Store';
import 'tailwindcss/dist/tailwind.min.css';

import ResponsePane from './components/ResponsePane';

const App = () => (
  <Provider store={ReduxStore}>
    <ResponsePane />
  </Provider>
);

ReactDOM.render(<App />, document.getElementById('root'));

