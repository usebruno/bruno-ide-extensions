import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isLoading: false,
  selectedTab: 'response'
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
    }
  }
});

export const {
  startLoading,
  endLoading,
  selectTab
} = appSlice.actions;

export default appSlice.reducer;
