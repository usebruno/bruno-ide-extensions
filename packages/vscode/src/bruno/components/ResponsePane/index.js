import React from 'react';
import classnames from 'classnames';
import { useSelector, useDispatch } from 'react-redux';
import { selectTab } from '../../providers/Store/slices/app';
import StyledWrapper from './StyledWrapper';

const ResponsePane = () => {
  const dispatch = useDispatch();

  const handleSelectTab = (tab) => {
    dispatch(selectTab({ selectedTab: tab }));
  };

  const {
    isLoading,
    selectedTab
  } = useSelector((state) => state.app);
  console.log(selectedTab);

  const getTabPanel = (tab) => {
    switch (tab) {
      case 'response': {
        return <div>Response</div>;
      }
      case 'headers': {
        return <div>Headers</div>;
      }
      case 'timeline': {
        return <div>Timeline</div>;
      }

      default: {
        return <div>404 | Not found</div>;
      }
    }
  };

  if (isLoading) {
    return (
      <StyledWrapper className="flex h-full relative">
        Loading
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
      </div>
      <section className="flex flex-grow mt-5">{getTabPanel(selectedTab)}</section>
    </StyledWrapper>
  );
};

export default ResponsePane;
