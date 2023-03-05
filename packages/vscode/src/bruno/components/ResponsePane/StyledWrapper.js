import styled from 'styled-components';

const StyledWrapper = styled.div`
  div.tabs {
    div.tab {
      padding: 6px 0px;
      border: none;
      border-bottom: solid 2px transparent;
      margin-right: 1.25rem;
      color: grey;
      cursor: pointer;

      &:focus,
      &:active,
      &:focus-within,
      &:focus-visible,
      &:target {
        outline: none !important;
        box-shadow: none !important;
      }

      &.active {
        color: black !important;
        border-bottom: red !important;
      }
    }
  }

  .some-tests-failed {
    color: red !important;
  }

  .all-tests-passed {
    color: green !important;
  }
`;

export default StyledWrapper;
