package com.lrbj.grpcserver;

import com.lrbj.grpc.lib.InterruptGrpc;
import com.lrbj.grpc.lib.InterruptStatus;
import lombok.extern.slf4j.Slf4j;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@GrpcService(InterruptStatus.class)
public class InterruptService extends InterruptGrpc.InterruptImplBase {
    @Override
    public void getInterruptStatusData(com.lrbj.grpc.lib.InterruptStatus.RequestParam request,
                                       io.grpc.stub.StreamObserver<com.lrbj.grpc.lib.InterruptStatus.Message> responseObserver) {
        InterruptStatus.RequestParam param = InterruptStatus.RequestParam.newBuilder().setLocationBuilding(request.getLocationBuilding())
                                              .setLocationPark(request.getLocationPark())
                                              .setLocationRegion(request.getLocationRegion())
                                              .build();
        log.info("request " + request.toString());
        List< InterruptStatus.data> dataList = new ArrayList<>();
        InterruptStatus.data.Builder dataBuilder = InterruptStatus.data.newBuilder();
        InterruptStatus.data.interruptProject.Builder interruptProject = InterruptStatus.data.interruptProject.newBuilder();
        interruptProject.setFloors("[{\"name\":\"3F\",\"id\":\"427\"},{\"name\":\"2F\",\"id\":\"426\"},{\"name\":\"1F\",\"id\":\"425\"}]");
        interruptProject.addProject("所有消防报警设备");
        dataBuilder.setApplicationId("LHZD-20191010-014");
        dataBuilder.setApplyUser("admin");
        dataBuilder.setInterruptStartTime(0);
        dataBuilder.setInterruptEndTime(0);
        dataBuilder.setExtensionStartTime(0);
        dataBuilder.setInterruptEndTime(0);
        dataBuilder.setLocationBuilding("龙华园区");
        dataBuilder.setLocationPark("A区");
        dataBuilder.setLocationRegion("A01");
        dataBuilder.addInterruptProjects(interruptProject);
        InterruptStatus.data.Builder data1 = InterruptStatus.data.newBuilder();

        final InterruptStatus.Message.Builder replyBuilder = InterruptStatus.Message.newBuilder().addDatalist(dataBuilder);
        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();

        log.info("Returning " + dataBuilder.toString());
    }

}
