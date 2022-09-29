import {MNG_GET_ALL_DEVICE_URL} from "../settings";

export const getAllDevices=async ({ page = 1, limit = 10, type, selectName})=>{
    try{
        let response =await fetch(
            `${MNG_GET_ALL_DEVICE_URL}?limit=${limit}&offset=${(page - 1) * limit}&type=${type}&selectName=${selectName}`
        );
        return await response.json();
    } catch (error){
        console.log("Request Failed",error);
    }
};
