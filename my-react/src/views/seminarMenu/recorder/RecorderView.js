import React, {useContext} from 'react';
import {
  Container,
} from '@material-ui/core';
import Page from 'src/components/Page';
import ViewStyle from "../../../components/Style/ViewStyle";
import {UserContext} from "../../../layouts/Context";
import Arrangement from "./Arrangement/Arrangement";
import ArrangementForStu from "./Arrangement/ArrangementForStu";


const RecorderView = () => {
  const classes = ViewStyle()
  const {userInfo}=useContext(UserContext)

  return (
    <Page
      className={classes.root}
      title="导读安排"
    >
      <Container maxWidth={false}>
        {userInfo.groupId==0?<Arrangement/>:<ArrangementForStu/>}{/**groupId=0说明是博士或者是老师**/}
      </Container>
    </Page>
  );
};

export default RecorderView;
