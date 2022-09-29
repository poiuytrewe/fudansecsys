import React, { useState, useContext, useEffect } from "react";
import {
  Button,
  Table,
  TableCell,
  TableBody,
  TableRow,
  TableHead,
  Divider,
  makeStyles,
  TextField,
} from "@material-ui/core";
import { UserContext } from "src/layouts/Context";
import {
  MNG_DELETE_DEVICE_URL, U_APPLY_DEVICE_URL,
} from "src/settings";
import {deleteFetch, postFetch} from "src/base";
import corfirmModal from "src/components/ConfirmModal";
import EditDevice from './EditDevice'
import DeviceApplyHistory from "./DeviceApplyHistory";
import {getAllDevices} from "../../../service/deviceService";
import {Pagination} from "@material-ui/lab";
import alertBox from "../../../components/AlertBox";

const useStyles = makeStyles((theme) => ({
  root: {
    // flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    padding: theme.spacing(2),
  },
  header: {
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
    paddingBottom: theme.spacing(2),
    "& .MuiTextField-root": {
      width: "150px",
    },
    "& .MuiButton-root": {
      width: "150px",
      height: "40px",
    },
  },
  Pagination: {
    padding:theme.spacing(2),
    '& .MuiPagination-ul':{
      justifyContent: 'center',
    }
  },
}));

const TYPES = [//设备类型
  {
    id: 100,
    name: "所有设备",
  },
  {
    id: 10,
    name: "显示器",
  },
  {
    id: 20,
    name: "移动设备",
  },
  {
    id: 30,
    name: "台式主机",
  },
  {
    id: 50,
    name: "服务器",
  },
  {
    id: 40,
    name: "其它设备",
  },
  {
    id: 0,
    name: "无详细指明"
  }
];
export default function DeviceTable(props) {
  const { refreshHistory } = props;
  const classes = useStyles();
  const { userInfo } = useContext(UserContext);
  const [openEditDevice, setOpenEditDevice] = useState(false);//默认不打开编辑页面
  const [openHitory, setOpenHitory] = useState(false);
  const [type, setType] = useState(100)//设备类型默认显示所有设备
  const [deviceDetail, setDeviceDetail] = useState({});
  const [page, setPage]=useState(1);
  const [pageNo,setPageNo]=useState(0);
  const [devices,setDevices]=useState([]);
  const [refresh, setRefresh]=useState(false);
  const [selectName, setSelectName]=useState("");

  const deleteDevice = (id, name) => {//删除设备操作
    const cor = corfirmModal({
      title: `确定要删除[${name}]吗？`,
      handleCorfirm: () => {
        cor.close();
        deleteFetch({
          url: `${MNG_DELETE_DEVICE_URL}?id=${id}`,
          successCallback: ()=>{
            setRefresh((prev)=>!prev);
          }
        });
      },
    });
  };

  const handleApply=(device)=>{
    const cor=corfirmModal({
      title:"确定要申请这个设备吗？",
      handleCorfirm:()=>{
        cor.close()
        if(device.borrow){
          alertBox({text:"该设备已被申请，请申请别的设备"})
          return
        }
        else{
          postFetch({
            url:U_APPLY_DEVICE_URL,
            values:{
              deviceId:device.id
            },
            successCallback:()=>{
              alertBox({text:"申请成功", severity:"success"})
              setRefresh((p)=>!p)
            }
          })
        }
      }
    })
  }

  const hasPermission = (userInfo.isDeviceMng==1) //检验用户权限

  useEffect(()=>{
    getAllDevices({page, type, selectName}).then((res)=>{
      setDevices(res.data||[]);
      setPageNo(Math.ceil(res?.paging?.total /10) || 0);
    })
  },[refresh,page,type,selectName])

  return (
    <div className={classes.root}>


      <div className={classes.header}>

        <TextField
          select
          label="设备类型"
          size="small"
          value={type}
          onChange={(event) => {
            setPage(1)
            if(pageNo>0){
              setPageNo(1)
            }
            setType(parseInt(event.target.value))

          }}
          SelectProps={{
            native: true,
          }}
          variant="outlined"
        >
          {TYPES.map((option) => (
            <option key={option.id} value={option.id}>
              {option.name}
            </option>
          ))}
        </TextField>

        <TextField
          label="根据借用人搜索设备"
          size="small"
          onChange={(e)=>{
            setPage(1)
            setSelectName(e.target.value)}}
          />

        {hasPermission && (//如果有管理员权限，就显示“添加设备”的按钮
          <Button variant="outlined" onClick={() => setOpenEditDevice(true)}>
            添加设备
          </Button>
        )}

      </div>


      <Divider />


      <Table>
        <TableHead>
          <TableRow>
            <TableCell>设备类型</TableCell>
            <TableCell>设备编号</TableCell>
            <TableCell>设备品牌</TableCell>
            <TableCell>设备型号</TableCell>
            <TableCell>负责人</TableCell>
            <TableCell>当前保管人</TableCell>
            <TableCell align="center">操作</TableCell>
          </TableRow>
        </TableHead>
        {/* type;设备类型 model;设备型号 name;设备名称 principal;负责人姓名
          inventory;库存 inventoryUnit;库存单位 */}
        <TableBody>
          {devices.map((device) => (
            <TableRow hover key={device.id}>
              <TableCell>
                {TYPES.find((type) => type.id === device.type).name}
              </TableCell>
              <TableCell>{device.id}</TableCell>
              <TableCell>{device.name}</TableCell>
              <TableCell>{device.model}</TableCell>
              <TableCell>{device.principal}</TableCell>
              <TableCell>{device.borrow}</TableCell>
              <TableCell align="center">
                <Button
                    color="primary"
                    size="small"
                    variant="text"
                    onClick={() => {
                      handleApply(device)
                    }}
                >
                  申请设备
                </Button>
                {hasPermission && (
                  <>
                    <Button
                      color="primary"
                      size="small"
                      variant="text"
                      onClick={() => {
                        setDeviceDetail(device);
                        setOpenHitory(true);
                      }}
                    >
                      借用记录
                    </Button>
                    <Button
                      color="primary"
                      size="small"
                      variant="text"
                      onClick={() => {
                        setOpenEditDevice(true);
                        setDeviceDetail(device);
                      }}
                    >
                      编辑
                    </Button>
                    <Button
                      color="primary"
                      size="small"
                      variant="text"
                      onClick={() => deleteDevice(device.id, device.name)}
                    >
                      删除
                    </Button>
                  </>
                )}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      {pageNo >1 &&(
          <Pagination
            className={classes.Pagination}
            count={pageNo}
            color="primary"
            onChange={(e,i)=>setPage(i)}
            />
      )}

      <EditDevice
        open={openEditDevice}
        onClose={() => {
          setOpenEditDevice(false);
          setDeviceDetail({});
          setRefresh((prev)=>!prev);
        }}
        deviceDetail={deviceDetail}
      />
      <DeviceApplyHistory
        open={openHitory}
        onClose={() => {
          setOpenHitory(false);
          setDeviceDetail({});
          setRefresh((prev)=>!prev);
        }}
        deviceDetail={deviceDetail}
      />
    </div>
  );
}
