import React from 'react';
import {
  Container,
} from '@material-ui/core';
import Page from 'src/components/Page';
import EssayRecommendation from './EssayRecommendation';
import ViewStyle from "../../../components/Style/ViewStyle";

const EssayRecommendationView = () => {
  const classes = ViewStyle()

  return (
    <Page
      className={classes.root}
      title="推荐论文"
    >
      <Container maxWidth={false}>
        <EssayRecommendation></EssayRecommendation>
      </Container>
    </Page>
  );
};

export default EssayRecommendationView;
