import React from "react";
import { Formik } from "formik";
import * as Yup from "yup";
import {
  Box,
  Button,
  TextField,
  Typography,
  Dialog,
  makeStyles,
} from "@material-ui/core";
import { postFetch } from "src/base";
import {ADD_SECTION_URL, ADD_SEMINAR_URL, UPDATE_SECTION_URL} from "src/settings";
import alertBox from "../../../components/AlertBox";

const useStyles = makeStyles((theme) => ({}));

const SeminarSectionEditForm = (props) => {
  const { onClose, open, sectionDetail } = props;

  const classes = useStyles();

  const handleClose = () => {
    onClose();
  };

  return (
    <Dialog onClose={handleClose} open={open}>
      <Formik
        initialValues={sectionDetail}
        onSubmit={(values) => {
          postFetch({
            url: sectionDetail?.id ? UPDATE_SECTION_URL:ADD_SECTION_URL,
            values,
            successCallback: () => {
              alertBox({text:"操作成功",severity:"success"})
              handleClose();
            },
          });
        }}
      >
        {({
          errors,
          handleBlur,
          handleChange,
          handleSubmit,
          isSubmitting,
          touched,
          values,
        }) => (
          <form onSubmit={handleSubmit} className="dialogForm">
            <Box mb={1}>
              <Typography color="textPrimary" variant="h6">
                {sectionDetail?.id ?"编辑专题" : "添加专题"}
              </Typography>
            </Box>
            <TextField
              fullWidth
              label="演讲时间"
              name="date"
              type="date"
              margin="normal"
              value={values?.date}
              onChange={handleChange}
              InputLabelProps={{shrink:true}}
              />

            <TextField
              fullWidth
              label="演讲主题"
              margin="normal"
              placeholder="必填"
              name="topic"
              value={values?.topic}
              onBlur={handleBlur}
              onChange={handleChange}
              variant="outlined"
            />
            <TextField
                multiline
                rows={3}
                label="推荐文章"
                onBlur={handleBlur}
                placeholder="可不填"
                margin="normal"
                fullWidth
                name="paperRec"
                value={values.paperRec}
                onChange={handleChange}
                variant="outlined"
            />
            <Box my={2}>
              <Button
                color="primary"
                fullWidth
                size="large"
                type="submit"
                variant="contained"
              >
                提交
              </Button>
              <Button
                color="secondary"
                fullWidth
                size="large"
                onClick={()=>{
                  onClose()
                }}
                variant="contained"
                >取消</Button>
            </Box>
          </form>
        )}
      </Formik>
    </Dialog>
  );
};
export default SeminarSectionEditForm;
