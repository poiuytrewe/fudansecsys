import React, { useEffect, useState, useContext } from "react";
import { makeStyles } from "@material-ui/core";
import Page from "src/components/Page";
import DeviceNormal from "./DeviceNormal";
const useStyles = makeStyles((theme) => ({
  root: {
    // flexGrow: 1,
    padding: theme.spacing(3)
  },
}));

const DeviceView = () => {
  const classes = useStyles();
  return (
    <Page className={classes.root} title="设备管理">
      <DeviceNormal />
    </Page>
  );
};

export default DeviceView;
