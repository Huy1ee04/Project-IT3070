package manager;

import container.Flow;
import javafx.scene.control.TextInputDialog;
import transmisstion.QueueOut;

import java.util.Optional;

public class UpdateSizeManager {
    public static void updateQueueOutputSize(QueueOut queueOut){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input throughput");
        dialog.setHeaderText("Enter the throughput of Queue Output");
        dialog.setContentText("Size: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(size->{
            if(!size.trim().isEmpty()){
                int queueSize = Integer.parseInt(size);
                queueOut.setThroughput(queueSize);
            }
        });
    }
    public static void updateFlowSize(Flow flow){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input throughput");
        dialog.setHeaderText("Enter the weight of Flow "+flow.getNameFlow());
        dialog.setContentText("Size: ");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(size->{
            if(!size.trim().isEmpty()){
                int flowSize = Integer.parseInt(size);
                flow.setWeight(flowSize);
            }
        });
    }

}
