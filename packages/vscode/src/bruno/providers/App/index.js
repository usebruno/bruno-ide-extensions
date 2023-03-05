import React, { useEffect } from 'react';
import get from 'lodash/get';
import { useDispatch } from 'react-redux';
import { setResponse } from '../Store/slices/app';

export const AppContext = React.createContext();

export const AppProvider = (props) => {
  const dispatch = useDispatch();

  useEffect(() => {
    const listenerId = window.addEventListener('message', event => {
      const command = get(event, 'data.command');
      if(command === 'runResult') {
        const result = get(event, 'data.result');
        dispatch(setResponse({ response: result }));
      }
    });

    return () => {
      window.removeEventListener(listenerId);
    }
  }, [dispatch]);

  return (
    <AppContext.Provider {...props} value="appProvider">
      {props.children}
    </AppContext.Provider>
  );
};

export default AppProvider;
