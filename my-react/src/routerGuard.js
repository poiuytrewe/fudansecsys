// routerGuard.jsx
import React from "react"
import { Route, Redirect } from "react-router-dom"
import cookie from 'react-cookies';

{/**这个文件的主要作用是在成功登录的前提下，根据路由路径渲染对应的组件；如果没登陆或者登陆失败会重新导向登录路径**/}

const checkLoginStatus = () => {//检查登录状态，如果登录成功，返回true，否则返回false
    if(cookie.load("loggedIn")==null) return false;
    return true;
}

export function RouteWithSubRoutes(route) {
    if(route.auth && checkLoginStatus()==false)
    {//没登陆就先登录
        console.log("请先登录")
        return <Redirect to="/main/login"/>
    }
    return (
      <Route /**根据路由选择渲染的组件**/
        path={route.path}
        render={props => (
          // pass the sub-routes down to keep nesting
          <route.component  {...props}  routes={route.routes} />
            //route.component是根据route选择要渲染的组件
        )}
      />
    );
  }
