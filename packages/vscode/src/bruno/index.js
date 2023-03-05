import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import ReduxStore from './providers/Store';
import AppProvider from './providers/App';
import ResponsePane from './components/ResponsePane';
import 'tailwindcss/dist/tailwind.min.css';

const App = () => (
  <Provider store={ReduxStore}>
    <AppProvider>
      <ResponsePane />
    </AppProvider>
  </Provider>
);

ReactDOM.render(<App />, document.getElementById('root'));

