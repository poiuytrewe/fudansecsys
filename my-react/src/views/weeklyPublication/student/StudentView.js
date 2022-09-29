import ViewStyle from "../../../components/Style/ViewStyle";
import Page from "../../../components/Page";
import {Container} from "@material-ui/core";
import {useContext, useEffect, useState} from "react";
import {UserContext} from "../../../layouts/Context";
import WeeklyPublicationForStu from "./WeeklyPublicationForStu";
import {useParams} from "react-router-dom";
import {MNG_GET_USER_DETAIL} from "../../../settings";
import WeeklyPublicationAllTime from "./WeeklyPublicationAllTime";

export default function StudentView(){
    const classes=ViewStyle()
    const {userInfo}=useContext(UserContext)
    const {stuId}=useParams()
    const [userDetail, setUserDetail]=useState({})
    const [selfPermission, setSelfPermission]=useState(true)
    const [done,setDone]=useState(false)
    /**当stuId为空时，说明是本人操作；当stuId不为空，并且userInfo.stuId和stuId不一样的时候，说明不是本人操作**/

    useEffect(()=>{
        if(stuId==null){
            setSelfPermission(true)
            setDone(true)
        }
        else{
            fetch(`${MNG_GET_USER_DETAIL}?stuId=${stuId}`)
                .then((res)=>res.json())
                .catch((error) => console.error("Error:", error))
                .then((res)=>{
                    setUserDetail(res?.data || {})
                    setSelfPermission(false)
                })
                .then(()=>{
                    setDone(true)
                })
        }
    },[])

    return (
        <Page className={classes.root} title="周报模块">
            <Container maxWidth={false}>
                {done ==true && (
                    selfPermission == true?(
                        <WeeklyPublicationForStu userInfo={userInfo}/>
                    ):(
                        <WeeklyPublicationAllTime userInfo={userDetail}/>
                    )
                )}
            </Container>
        </Page>
    )
}
