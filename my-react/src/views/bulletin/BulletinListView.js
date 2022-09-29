import React from "react";
import {
  Container,
} from "@material-ui/core";
import Page from "src/components/Page";
import ViewStyle from "../../components/Style/ViewStyle";
import BulletinList from "./BulletinList";

const BulletinListView = () => {
  const classes=ViewStyle()

  return (
    <Page className={classes.root} title="通知记录">
      <Container maxWidth={false}>
        <BulletinList />
      </Container>
    </Page>
  );
};

export default BulletinListView;
