package scheduler;

import container.Flow;
import item.Packet;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import transmisstion.QueueOut;

import java.util.ArrayList;

public class WeightedFairQueueScheduler {
    public static int order = 0;

    public static void simulationWFQ(ArrayList<Flow> flows, QueueOut queueOut, Text resourceRemain) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> processFlows(flows, queueOut,resourceRemain)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private static void processFlows(ArrayList<Flow> flows, QueueOut queueOut, Text resourceRemain) {
        int totalWeight = 0;
        for(Flow flow : flows){
            if(!flow.isEmpty()){
                totalWeight += flow.getWeight();
            }
        }
        if (totalWeight == 0) {
            return;
        }

        int resource = queueOut.getThroughput();
        for (Flow flow : flows) {
            if (!flow.isEmpty()) {
                flow.setResorceAlled(flow.getWeight() * resource / totalWeight);
                flow.getResourceAlloc().setText("Allocation: " + flow.getResorceAllocation());
                flow.setPackageOut();
                for (Packet pkg : flow.getPackageOut()) {
                    pkg.setOrderTrans(++order);
                    queueOut.add(pkg);
                    Platform.runLater(() -> {
                        StackPane stackPane = new StackPane();

                        Text orderText = new Text(Integer.toString(pkg.getOrderTrans()));
                        orderText.setFill(Paint.valueOf("RED"));
                        orderText.setMouseTransparent(true);
                        stackPane.getChildren().add(pkg.getNode());
                        stackPane.getChildren().add(orderText);
                        queueOut.gethBox().getChildren().addFirst(stackPane);


                    });
                }
                flow.updateHbox();
                flow.clearPackageOut();
                int resourceReturn = flow.returnResource();
                resource += resourceReturn;
                resource -= flow.getResourceRecently();
                totalWeight -= flow.getWeight();
                Platform.runLater(() -> {
                    flow.getResourceReturn().setText("Return: " + resourceReturn);
                });
            }
        }
        Platform.runLater(() -> {
            Timeline currentTimeline = (Timeline) queueOut.gethBox().getProperties().get("timeline");
            if (currentTimeline != null) {
                currentTimeline.stop();
            }
        });
        resourceRemain.setText("Resource Remaining: "+resource);
    }

}
