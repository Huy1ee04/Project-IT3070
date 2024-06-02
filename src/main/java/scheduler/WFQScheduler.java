package Scheduler;

import container.Flow;
import Item.PackageItem;
import javafx.application.Platform;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import transmisstion.QueueOut;

import java.util.ArrayList;

public class WFQScheduler {
    private static int order = 0;

    public static void simulationWFQ(ArrayList<Flow> flows, QueueOut queueOut) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> processFlows(flows, queueOut)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private static void processFlows(ArrayList<Flow> flows, QueueOut queueOut) {
        int totalWeight = flows.stream().filter(flow -> !flow.isEmpty()).mapToInt(Flow::getWeight).sum();
        if (totalWeight == 0) {
            return;
        }

        int resource = queueOut.getThroughput();
        for (Flow flow : flows) {
            if (!flow.isEmpty()) {
                flow.setResorceAlled(flow.getWeight() * resource / totalWeight);

                Platform.runLater(() -> {
                    flow.getResourceAlloc().setText("Allocation: " + flow.getResorceAllocation());
                });

                flow.setPackageOut();
                while (!flow.getPackageOut().isEmpty()) {
                    PackageItem pkg = flow.getPackageOut().getFirst();
                    pkg.setOrderTrans(++order);
                    pkg.setSrcFlow(flow);
                    queueOut.addPackage(pkg);
                    flow.getPackageOut().removeFirst();

                    Platform.runLater(() -> {
                        if (!flow.getHbox().getChildren().isEmpty()) {
                            flow.getHbox().getChildren().removeFirst();
                        }
                        queueOut.gethBox().getChildren().addFirst(pkg.getNode());
                    });
                }

                int resourceReturn = flow.returnResource();
                resource += resourceReturn;

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
    }
}
