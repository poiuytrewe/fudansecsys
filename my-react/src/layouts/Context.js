import React from 'react';

export const UserContext = React.createContext({});

export const RefreshContext =React.createContext({});//这个是负责刷新的，一旦对通知的数量进行了更改，就会启用他，
