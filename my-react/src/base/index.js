import alertBox from "src/components/AlertBox";
import {
  MNG_ADD_BULLETIN_URL,
  MNG_UPDATE_DEVICE_URL,
  // U_DEVICE_ALLOCATION_URL,
  // U_DEVICE_USAGE_URL,
  U_GET_ALL_DEVICE_URL
} from "../settings";

/**这个文件主要应该是负责提供对于数据的增删改查方法**/

export const jsonToFormData = (params) => {
  const formData = new FormData();
  Object.keys(params).forEach((key) => {
    formData.append(key, params[key]);
  });
  return formData;
};

export const jsonToUrlencoded = (params) => {
  const urlSearchParams = new URLSearchParams();
  Object.keys(params).forEach((key) => {
    urlSearchParams.append(key, params[key]);
  });
  return urlSearchParams;
};

export const formDataFetch = ({
  url,
  values,
  successCallback,
  errorCallback,
  type = "POST",
}) => {
  fetch(url, {
    method: type,
    body: jsonToFormData(values),
  })
    .then((res) => res.json())
    .catch((error) => console.error("Error:", error))
    .then((response) => {
      if (response?.success) {
        successCallback && successCallback();
      } else {
        errorCallback && errorCallback();
      }
    });
};

function ascCompare(property){//从小到大排序
  return function (a,b){
    let value1=a[property]
    let value2=b[property]
    return value1-value2
  }
}

export const formUrlencodedFetch = ({
  url,
  values = {},
  successCallback,
  errorCallback,
  type = "POST",
}) => {
  fetch(url, {
    method: type,
    headers: {
      "content-type": "application/x-www-form-urlencoded",

    },
    body: jsonToUrlencoded(values),
  })
    .then((res) => {
      return res.json()
    })
    .catch((error) => console.error("Error:", error))
    .then((response) => {
      console.log(response?.success)
      if (response?.success) {
        fetch(`${U_GET_ALL_DEVICE_URL}?borrow=${response?.data.name}`)
            .then((res)=>res.json())
            .catch((error)=>console.error(error))
            .then((res)=>{
              console.log(res)
              const newDate=res?.data.sort(ascCompare("modifyTime"))
              const oldTime=new Date(newDate[0]?.modifyTime)
              const nowTime=new Date().getTime()
              const poorTime=nowTime-oldTime
              if(poorTime/(1000*3600*24) >= 30){
                postFetch({
                  url:MNG_ADD_BULLETIN_URL,
                  values:{
                    stuId:response?.data.stuId,
                    title:"请定时确认借用的设备是否还在使用中",
                    content:"如果借用的设备已经不再使用，请及时在设备管理模块进行归还设备，并将设备归还到设备管理员手中"
                  },
                  successCallback:()=>{
                    postFetch({
                      url:MNG_UPDATE_DEVICE_URL,
                      values:{
                        id:newDate[0].id,
                        modifyTime:nowTime
                      }
                    })
                  }
                })
              }
            })
        successCallback && successCallback();
      } else {
        // alert(response?.msg || "操作失败")
        alertBox({ text: response?.msg || "操作失败", severity: "error" });
        errorCallback && errorCallback();
      }
    });
};

export const postFetch = ({
  url,
  values = {},
  successCallback,
  errorCallback,
  type = "POST",
}) => {
  fetch(url, {
    method: type,
    headers: {
      "content-type": "application/json",
    },
    body: JSON.stringify(values),
  })
    .then((res) => res.json())
    .catch((error) => console.error("Error:", error))
    .then((response) => {
      if (response?.success) {
        successCallback && successCallback();
      } else {
        // alert(response?.msg || "操作失败");
        alertBox({ text: response?.msg || "操作失败", severity: "error" });
        errorCallback && errorCallback();
      }
    });
};

export const formFetch = ({ url, successCallback }) => {
  fetch(url, {
    method: "POST",
    headers: {
      "content-type": "application/x-www-form-urlencoded",
    },
  })
    .then((res) => res.json())
    .catch((error) => console.error("Error:", error))
    .then((response) => {
      // alert(response?.success ? "删除成功" : response?.msg || "删除失败");
      alertBox({
        text: response?.success ? "操作成功" : response?.msg || "操作失败",
        severity: response?.success ? "success" : "error",
      });
      successCallback && successCallback();
    });
};

export const deleteFetch = ({ url, successCallback }) => {
  fetch(url, {
    method: "POST",
    headers: {
      "content-type": "application/x-www-form-urlencoded",
    },
  })
    .then((res) => res.json())
    .catch((error) => console.error("Error:", error))
    .then((response) => {
      // alert(response?.success ? "删除成功" : response?.msg || "删除失败");
      alertBox({
        text: response?.success ? "删除成功" : response?.msg || "删除失败",
        severity: response?.success ? "success" : "error",
      });
      successCallback && successCallback();
    });
};
