import React from 'react';
import get from 'lodash/get';
import classnames from 'classnames';
import { useSelector, useDispatch } from 'react-redux';
import { selectTab } from '../../providers/Store/slices/app';
import Overlay from './Overlay';
import QueryResult from './QueryResult';
import ResponseHeaders from './ResponseHeaders';
import Timeline from './Timeline';
import TestResults from './TestResults';
import TestResultsLabel from './TestResultsLabel';
import StatusCode from './StatusCode';
import ResponseTime from './ResponseTime';
import ResponseSize from './ResponseSize';

import StyledWrapper from './StyledWrapper';

const safeStringifyJSON = (json, pretty) => {
  try {
    return pretty ? JSON.stringify(json, null, 2) : JSON.stringify(json);
  } catch (e) {
    return json;
  }
};

const ResponsePane = () => {
  const dispatch = useDispatch();

  const handleSelectTab = (tab) => {
    dispatch(selectTab({ selectedTab: tab }));
  };

  const {
    isLoading,
    selectedTab,
    response: responseResult
  } = useSelector((state) => state.app);
  console.log(responseResult);

  const getTabPanel = (tab) => {
    switch (tab) {
      case 'response': {
        const response = get(responseResult, 'response.data', null);
        return <QueryResult
          value={response ? safeStringifyJSON(response.data, true) : ''}
        />;
      }
      case 'headers': {
        return <ResponseHeaders headers={get(responseResult, 'response.headers', {})} />;
      }
      case 'timeline': {
        return <Timeline
          request={get(responseResult, 'request', {})}
          response={get(responseResult, 'response', {})}
        />;
      }
      case 'tests': {
        return <TestResults
          results={get(responseResult, 'testResults', [])}
          assertionResults={get(responseResult, 'assertionResults', [])}
        />;
      }

      default: {
        return <div>404 | Not found</div>;
      }
    }
  };

  if (isLoading) {
    return (
      <StyledWrapper className="flex h-full relative">
        <Overlay />
      </StyledWrapper>
    );
  }

  const getTabClassname = (tabName) => {
    return classnames(`tab select-none ${tabName}`, {
      active: tabName === selectedTab
    });
  };

  return (
    <StyledWrapper className="flex flex-col h-full relative">
      <div className="flex items-center px-3 tabs" role="tablist">
        <div className={getTabClassname('response')} role="tab" onClick={() => handleSelectTab('response')}>
          Response
        </div>
        <div className={getTabClassname('headers')} role="tab" onClick={() => handleSelectTab('headers')}>
          Headers
        </div>
        <div className={getTabClassname('timeline')} role="tab" onClick={() => handleSelectTab('timeline')}>
          Timeline
        </div>
        <div className={getTabClassname('tests')} role="tab" onClick={() => handleSelectTab('tests')}>
          <TestResultsLabel
            results={get(responseResult, 'testResults', [])}
            assertionResults={get(responseResult, 'assertionResults', [])}
          />
        </div>
        {!isLoading ? (
          <div className="flex flex-grow justify-end items-center">
            <StatusCode status={get(responseResult, 'response.status', -1)} />
            <ResponseTime duration={get(responseResult, 'response.duration', 0)} />
            <ResponseSize size={get(responseResult, 'response.size', 0)} />
          </div>
        ) : null}
      </div>
      <section className="flex flex-grow mt-5">{getTabPanel(selectedTab)}</section>
    </StyledWrapper>
  );
};

export default ResponsePane;
