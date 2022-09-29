import React, { useEffect, useState, useContext } from "react";
import {
    Box,
    Button,
    Card,
    Typography,
    Divider,
    makeStyles,
} from "@material-ui/core";
import { useParams, useHistory } from "react-router-dom";
import { MNG_GET_USER_DETAIL, U_GET_USER_DETAIL } from "src/settings";
import { UserContext } from "src/layouts/Context";
import SelfEditForm from "./selfEditForm";
const useStyles = makeStyles((theme) => ({
    header: {
        display: "flex",
        justifyContent: "space-between",
        padding: "16px",
        "& p": {
            lineHeight: 2,
        },
    },
    box: {
        padding: '16px'
    }
}));
export default function SelfEditView() {
    const { id } = useParams();
    const { userInfo } = useContext(UserContext);
    const history = useHistory();
    const [userDetail, setUserDetail] = useState({});
    const [refresh, setRefresh]=useState(false);
    const classes = useStyles();
    const getUserInfo = () => {
        fetch(
            `${
                userInfo.isEducateMng>0 ? MNG_GET_USER_DETAIL : U_GET_USER_DETAIL
            }?stuId=${id}`,
            {}
        )
            .then((res) => res.json())
            .catch((error) => console.error("Error:", error))
            .then((response) => {
                setUserDetail(response?.data || {});
            });
    };
    useEffect(getUserInfo, [refresh]);

    return (
        <div>
            <Card>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small">
                        编辑学生信息
                    </Typography>

                    {
                        userInfo.isEducateMng==1 && (
                            <Button
                                color="primary"
                                size="small"
                                variant="outlined"
                                onClick={history.goBack}
                            >
                                返回
                            </Button>
                        )
                    }
                </Box>
                <Divider />
                <Box minWidth={800} className={classes.box}>
                    {userDetail?.id > 0 && <SelfEditForm userDetail={userDetail} handleOnRefresh={()=>{
                        setRefresh((prev)=>!prev);
                    }}/>}
                </Box>
            </Card>
        </div>
    );
}
