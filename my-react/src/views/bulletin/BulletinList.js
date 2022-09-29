import React, {useState, useEffect, useContext} from "react";
import {
    Box,
    Container,
    Typography,
    Button, Card, Divider, Badge,
} from "@material-ui/core";
import Page from "src/components/Page";
import { GET_ALL_BULLETIN_URL, MARK_READ_ALL } from "src/settings";
import BulletinCard from "./BulletinCard";
import { postFetch } from "src/base";
import {UserContext} from "../../layouts/Context";
import ContentStyle from "../../components/Style/ContentStyle";
import Pagination from "@material-ui/lab/Pagination";
import NotificationsIcon from "@material-ui/icons/NotificationsOutlined";

const BulletinList = () => {
    const classes=ContentStyle()
    const [refresh,setRefresh]=useState(false)
    const {userInfo}=useContext(UserContext)
    const [pageNo,setPageNo]=useState(0)
    const [page,setPage]=useState(1)
    const [bulletins, setBulletins] = useState([]); //所有通知
    //向后台调取所有通知

    const getAllBulletin = ({page}) => {//只调取属于自己的通知
        fetch(`${GET_ALL_BULLETIN_URL}?stuId=${userInfo.stuId}&limit=${10}&offset=${(page-1)*10}`, {})
            .then((res) => res.json())
            .catch((error) => console.error("Error:", error))
            .then((response) => {
                setBulletins(response?.data || []);
                setPageNo(Math.ceil(response?.paging.total /10) || 0)
            });
    };

    const handleReadAll=()=>{ //全部已读
        fetch(`${MARK_READ_ALL}?stuId=${userInfo.stuId}`,{
            method:"POST"
        })
            .then(()=>{
                // setRefresh((p)=>!p)
                window.location.reload()
            })
    }

    useEffect(()=>{
        getAllBulletin({page})
    }, [page, pageNo, refresh]);
    return (
        <div>
            <Card>
                <Box className={classes.header}>
                    <Typography color="textPrimary" size="small" component="h4">
                        通知记录
                    </Typography>

                    <Button
                        color="primary"
                        size="small"
                        variant="outlined"
                        onClick={() =>{
                            handleReadAll()
                        }
                        }
                    >
                        全部已读
                    </Button>
                </Box>

                <Divider/>

                <Box minWidth={800}>
                    {bulletins.map((bul) => (
                        <BulletinCard
                            refresh={()=>{
                                setRefresh((p)=>!p)
                            }}
                            bulletin={bul}
                        />
                    ))}

                </Box>
                {pageNo > 1 && (
                    <Pagination
                        className={classes.Pagination}
                        count={pageNo}
                        color="primary"
                        onChange={(e, i) => setPage(i)}
                    />
                )}

            </Card>

        </div>
    );
};

export default BulletinList;
