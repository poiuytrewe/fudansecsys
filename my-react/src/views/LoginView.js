/* eslint-enable */
import React from 'react';
import { useHistory } from 'react-router-dom';
import { Formik } from 'formik';
import cookie from "react-cookies";
import {formUrlencodedFetch, jsonToUrlencoded, postFetch} from "src/base";
import * as Yup from 'yup';
import {
  Box,
  Button,
  Container,
  TextField,
  Typography,
  makeStyles
} from '@material-ui/core';
import Page from 'src/components/Page';
import {LOGIN_URL} from 'src/settings';//settings和routes到底有什么区别？分别是干嘛的？

const useStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: theme.palette.background.dark,
    height: "100%",
    paddingBottom: theme.spacing(3),
    paddingTop: theme.spacing(3),
  },
  link: {
    textAlign: 'right'
  },
}));


function LoginView  (){
  const classes = useStyles();
  const history = useHistory();
  return (
    <Page className={classes.root} title="Login">
      <Box
        display="flex"
        flexDirection="column"
        height="100%"
        justifyContent="center"
      >
        <Container maxWidth="sm">
          <Formik
            initialValues={{//初始数据设为空
              stuId: "",
              password: "",
            }}
            validationSchema={Yup.object().shape({//数据验证
              stuId: Yup.string().max(255).required("ID is required"),
              password: Yup.string().max(255).required("Password is required"),
            })}
            onSubmit={(values) => {//提交信息
              formUrlencodedFetch({
                url: LOGIN_URL,
                values: values,
                successCallback: () => {
                  cookie.save('loggedIn','yes',{path:"/"});
                  //navigate('', { replace: true });
                  console.log("logged in");
                  history.replace("/app/userManagement");
                },
                errorCallback: () => {
                  //navigate('', { replace: true });
                  //登录失败
                  //window.location.reload();
                  console.log("logged fail");
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
              <form onSubmit={handleSubmit}>
                <Box mb={3}>
                  <Typography color="textPrimary" variant="h2">
                    登录
                  </Typography>
                </Box>
                <TextField
                  error={Boolean(touched.stuId && errors.stuId)}
                  fullWidth
                  helperText={touched.stuId && errors.stuId}
                  label="学/工号"
                  margin="normal"
                  name="stuId"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  variant="outlined"
                />
                <TextField
                  error={Boolean(touched.password && errors.password)}
                  fullWidth
                  helperText={touched.password && errors.password}
                  label="密码"
                  placeholder="初始密码为fdu+学/工号"
                  margin="normal"
                  name="password"
                  onBlur={handleBlur}
                  onChange={handleChange}
                  type="password"
                  variant="outlined"
                />
                <Box my={2}>
                  <Button
                    color="primary"
                    // disabled={isSubmitting}
                    fullWidth
                    size="large"
                    type="submit"
                    variant="contained"
                  >
                    登录
                  </Button>
                </Box>
                {/* <Box my={2} className={classes.link}>
                  <Link href="/main/reset">修改密码</Link>
                </Box> */}
                {/* <Typography
                  color="textSecondary"
                  variant="body1"
                >
                  Don&apos;t have an account?
                  {' '}
                  <Link
                    component={RouterLink}
                    to="/register"
                    variant="h6"
                  >
                    Sign up
                  </Link>
                </Typography> */}
              </form>
            )}
          </Formik>
        </Container>
      </Box>
    </Page>
  );
};

export default LoginView;
