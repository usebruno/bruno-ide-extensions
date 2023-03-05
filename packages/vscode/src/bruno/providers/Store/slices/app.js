import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isLoading: true,
  selectedTab: 'response',
  response: null
};

export const appSlice = createSlice({
  name: 'app',
  initialState,
  reducers: {
    startLoading: (state, action) => {
      state.isLoading = action.payload.isLoading;
    },
    endLoading: (state, action) => {
      state.isLoading = false;
    },
    selectTab: (state, action) => {
      state.selectedTab = action.payload.selectedTab;
    },
    setResponse: (state, action) => {
      state.isLoading = false;
      state.response = action.payload.response;
    }
  }
});

export const {
  startLoading,
  endLoading,
  selectTab,
  setResponse
} = appSlice.actions;

export default appSlice.reducer;
