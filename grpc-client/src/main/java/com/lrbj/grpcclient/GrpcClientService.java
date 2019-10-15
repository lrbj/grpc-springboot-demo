package com.lrbj.grpcclient;


import com.google.protobuf.Internal;
import com.lrbj.grpc.lib.GreeterGrpc;
import com.lrbj.grpc.lib.GreeterOuterClass;
import com.lrbj.grpc.lib.InterruptGrpc;
import com.lrbj.grpc.lib.InterruptStatus;
import com.lrbj.grpcclient.vo.InterruptProjectVo;
import com.lrbj.grpcclient.vo.InterruptStatusVo;
import io.grpc.Channel;


import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.codec.http2.Http2SecurityUtil;
import io.netty.handler.ssl.*;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class GrpcClientService {

    //两种获取Channel方式
    //方法一
    @GrpcClient("local-grpc-server")
    private Channel serverChannel;

    public String sendMessage(String name) {
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(serverChannel);
        GreeterOuterClass.HelloReply response = stub.sayHello(GreeterOuterClass.HelloRequest.newBuilder().setName(name).build());
        return response.getMessage();
    }

    //方法二
    @Value("${grpc.client.local-grpc-server.port}")
    private Integer port;

    private String host = "0.0.0.0";

    public List<InterruptStatusVo> getResult(String building, String park, String region) {
        //通过端口号去获取
        io.grpc.Channel channel = NettyChannelBuilder.forAddress(host, port)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();

        InterruptGrpc.InterruptBlockingStub stub  =  InterruptGrpc.newBlockingStub(channel);
        InterruptStatus.RequestParam.Builder param = InterruptStatus.RequestParam.newBuilder();
        if(Objects.nonNull(building)){
            param.setLocationBuilding(building);
        }
        if(Objects.nonNull(park)){
            param.setLocationPark(park);
        }
        if(Objects.nonNull(region)){
            param.setLocationRegion(region);
        }

        InterruptStatus.Message response = stub.getInterruptStatusData(param.build());

        List<InterruptStatusVo> datalist = new ArrayList<>();
        List<InterruptStatus.data>  data = response.getDatalistList();
        for(InterruptStatus.data data1: data){
            InterruptStatusVo interruptStatusVo = new InterruptStatusVo();
            BeanUtils.copyProperties(data1, interruptStatusVo);
            List<InterruptStatus.data.interruptProject> interruptProjects = data1.getInterruptProjectsList();
            List<InterruptProjectVo> interruptProjectVoList = new ArrayList<>();
            for(InterruptStatus.data.interruptProject project: interruptProjects){
                InterruptProjectVo interruptProjectVo = new InterruptProjectVo();
                interruptProjectVo.setFloors(project.getFloors());
                com.google.protobuf.ProtocolStringList strings = project.getProjectList();

                int count = strings.size();
                String[] projects = new String[count];
                for(int i = 0; i < count; i++){
                    projects[i] = strings.get(i);
                }
                interruptProjectVo.setProject(projects);
                interruptProjectVoList.add(interruptProjectVo);
            }
            interruptStatusVo.setInterruptProjects(interruptProjectVoList);
            datalist.add(interruptStatusVo);
        }
        return  datalist;
    }




}
