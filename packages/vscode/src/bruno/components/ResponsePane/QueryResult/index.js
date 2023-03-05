import React from 'react';
import StyledWrapper from './StyledWrapper';

const QueryResult = ({ value }) => {

  return (
    <StyledWrapper className="px-3 w-full">
      <pre style={{ fontSize: 12 }}>
        {value}
      </pre>
    </StyledWrapper>
  );
};

export default QueryResult;
