import styled from 'styled-components';

const StyledWrapper = styled.div`
  font-size: 12px;
  div.tabs {
    div.tab {
      padding: 6px 0px;
      border: none;
      border-bottom: solid 2px transparent;
      margin-right: 1.25rem;
      color:  var(--vscode-editor-foreground);
      opacity: 0.5;
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
        opacity: 1 !important;
        border-bottom: solid 2px var(--vscode-tab-activeModifiedBorder) !important;
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
