import React from 'react';
import {
  Container,
} from '@material-ui/core';
import Page from 'src/components/Page';
import UserManage from './UserManage.js';
import ViewStyle from "../../components/Style/ViewStyle";


const UserManagementView = () => {
  const classes = ViewStyle()

  return (
    <Page
      className={classes.root}
      title="用户管理"
    >
      <Container maxWidth={false}>
        <UserManage></UserManage>
      </Container>
    </Page>
  );
};

export default UserManagementView;
