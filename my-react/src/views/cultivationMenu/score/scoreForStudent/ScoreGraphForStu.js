import {useEffect, useState} from "react";
import {MNG_RETURN_SCORE, MNG_RETURN_SCORE_VITAL} from "../../../../settings";
import ReactEcharts from 'echarts-for-react'

export default function ScoreGraphForStu(props){
    const {userDetail}=props
    let xLine
    let yLine
    const [option1,setOption1]=useState({})
    const [option2,setOption2]=useState({})

    const onClick={
        "click":(e)=>{console.log(e)}
    }

    const getScoreVital=()=>{
        fetch(`${MNG_RETURN_SCORE_VITAL}?stuId=${userDetail.stuId}`,{})
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                setOption2({
                    title:{
                        left:"center",
                        text:`平均分数：${((res.data[1]+2*res.data[2]+3*res.data[3]+4*res.data[4]+5*res.data[5])/(res.data[0]+res.data[1]+res.data[2]+res.data[3]+res.data[4]+res.data[5])).toFixed(2)}`,
                        top:"50%"
                    },
                    tooltip: {
                        trigger: "item"
                    },
                    legend:{//图例组件
                        orient: "vertical",
                        left: "20%",
                        top:"20%"
                    },
                    series:[
                        {
                            radius: ['40%', '70%'],
                            avoidLabelOverlap: false,
                            itemStyle: {
                                borderRadius: 10,
                                borderColor: '#fff',
                                borderWidth: 2
                            },
                            label:{
                                show: false
                            },
                            labelLine: {
                                show: false
                            },
                            type: "pie",
                            data: [
                                {value: res.data[0], name: "未评分的总数"},
                                {value: res.data[1], name: "评分为1的总数"},
                                {value: res.data[2], name: "评分为2的总数"},
                                {value: res.data[3], name: "评分为3的总数"},
                                {value: res.data[4], name: "评分为4的总数"},
                                {value: res.data[5], name: "评分为5的总数"}
                            ]
                        }
                    ]
                })
            })
    }

    useEffect(()=>{
        getScoreVital()
        fetch(`${MNG_RETURN_SCORE}?stuId=${userDetail?.stuId}`,{})
            .then((res)=>res.json())
            .catch((error)=>console.log(error))
            .then((res)=>{
                xLine=res?.data.map((score)=>{
                    return (score.year+"年"+score .season+"季")
                })

                yLine=res?.data.map((score)=>{
                    return score.score
                })
                setOption1({
                    title:{
                        left:"center",
                        text:`${userDetail.name}的评分统计`
                    },
                    xAxis:{
                        type: "category",
                        data:xLine
                    },
                    yAxis:{type: 'value'},
                    series:[{
                        data:yLine,
                        type:"line",
                        smooth:true
                    }]})
            })
    },[])

    return (
        (option1 !== {} && option2!=={}) && (//等到option加载完毕再进行渲染
        <>
            <ReactEcharts option={option1}>
            </ReactEcharts>

            <ReactEcharts option={option2} onEvents={onClick}></ReactEcharts>
        </>
    ))
}
