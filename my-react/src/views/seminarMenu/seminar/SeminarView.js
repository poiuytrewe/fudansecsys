import React from "react";
import { Container, makeStyles } from "@material-ui/core";
import Page from "src/components/Page";
import SeminarHead from "./SeminarHead";
import ViewStyle from "../../../components/Style/ViewStyle";

const SeminarView = () => { //这里应该是有两部分，一部分是硕士生的演讲论文，一部分是博士生的推荐论文，比较繁琐
  const classes = ViewStyle()

  return (
    <Page className={classes.root} title="演讲安排">
      <Container maxWidth={false}>
        <SeminarHead></SeminarHead>
      </Container>
    </Page>
  );
};

export default SeminarView;

