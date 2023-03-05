import React from 'react';
import StopWatch from '../../StopWatch';
import StyledWrapper from './StyledWrapper';

const Overlay = () => {
  return (
    <StyledWrapper className="mt-4 px-3 w-full">
      <div className="overlay">
        <div style={{ marginBottom: 15, fontSize: 26 }}>
          <div style={{ display: 'inline-block', fontSize: 24, marginLeft: 5, marginRight: 5 }}>
            <StopWatch />
          </div>
        </div>
        <svg xmlns="http://www.w3.org/2000/svg" className="animate-spin icon icon-tabler icon-tabler-refresh" width="24" height="24" viewBox="0 0 24 24" strokeWidth="2" stroke="currentColor" fill="none" strokeLinecap="round" strokeLinejoin="round">
          <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
          <path d="M20 11a8.1 8.1 0 0 0 -15.5 -2m-.5 -4v4h4" />
          <path d="M4 13a8.1 8.1 0 0 0 15.5 2m.5 4v-4h-4" />
        </svg>
      </div>
    </StyledWrapper>
  );
};

export default Overlay;
